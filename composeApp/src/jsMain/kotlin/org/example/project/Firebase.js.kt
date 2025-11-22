package org.example.project

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual suspend fun submitToFirebase(fields: Map<String, String>, databaseUrl: String) {
    console.log("=== Starting Firebase Submission ===")
    console.log("URL:", databaseUrl)

    val timestamp = js("Date.now()").toString()
    val now = js("new Date().toISOString()").toString()

    // Build JSON string manually
    val jsonBody = buildString {
        append("""{"fields":{""")

        fields.entries.forEachIndexed { index, (key, value) ->
            if (index > 0) append(",")
            append(""""$key":{"stringValue":"${escapeJsonString(value)}"}""")
        }

        append(""","timestamp":{"integerValue":"$timestamp"}""")
        append(""","submittedAt":{"stringValue":"$now"}""")
        append("}}")
    }

    console.log("Request body:", jsonBody)
    console.log("Sending request via XMLHttpRequest...")

    return suspendCancellableCoroutine { continuation ->
        try {
            // Use XMLHttpRequest instead of fetch to avoid Kotlin/Wasm interop issues
            val xhr = js("new XMLHttpRequest()")

            // Set up timeout
            xhr.timeout = 15000

            // Handle successful response
            js("""
                xhr.onload = function() {
                    console.log("Response status:", xhr.status);
                    console.log("Response text:", xhr.responseText);
                    
                    if (xhr.status >= 200 && xhr.status < 300) {
                        console.log("=== Submission Successful ===");
                        continuation.resume(null);
                    } else {
                        console.error("HTTP Error:", xhr.status, xhr.responseText);
                        continuation.resumeWithException(
                            new Error("Failed to submit: HTTP " + xhr.status + " - " + xhr.responseText)
                        );
                    }
                };
            """)

            // Handle network errors
            js("""
                xhr.onerror = function() {
                    console.error("=== Network Error ===");
                    continuation.resumeWithException(new Error("Network error occurred"));
                };
            """)

            // Handle timeout
            js("""
                xhr.ontimeout = function() {
                    console.error("=== Request Timeout ===");
                    continuation.resumeWithException(new Error("Request timeout after 15 seconds"));
                };
            """)

            // Open and send the request
            js("""
                xhr.open('POST', databaseUrl, true);
                xhr.setRequestHeader('Content-Type', 'application/json');
                xhr.send(jsonBody);
            """)

            console.log("Request sent!")

        } catch (e: Exception) {
            console.error("=== Exception in setup ===")
            console.error("Error:", e.message)
            continuation.resumeWithException(Exception("Failed to send request: ${e.message}"))
        }
    }
}

private fun escapeJsonString(str: String): String {
    return str
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")
        .replace("'", "\\'")
}