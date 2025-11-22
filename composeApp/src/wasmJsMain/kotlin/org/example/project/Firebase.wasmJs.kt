package org.example.project

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

// External declaration for XMLHttpRequest with proper Wasm-compatible types
external class XMLHttpRequest {
    var timeout: Int
    var status: Short
    var responseText: String
    var onload: (() -> Unit)?
    var onerror: (() -> Unit)?
    var ontimeout: (() -> Unit)?

    fun open(method: String, url: String, async: Boolean)
    fun setRequestHeader(header: String, value: String)
    fun send(body: String?)
}

// Top-level functions for timestamp (required by Kotlin/Wasm)
private fun getCurrentTimestamp(): String = js("Date.now().toString()")

private fun getCurrentISOString(): String = js("new Date().toISOString()")

actual suspend fun submitToFirebase(fields: Map<String, String>, databaseUrl: String) {
    println("=== Starting Firebase Submission (XHR) ===")
    println("URL: $databaseUrl")

    val timestamp = getCurrentTimestamp()
    val now = getCurrentISOString()

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

    println("Request body: $jsonBody")
    println("Using XMLHttpRequest to send...")

    return suspendCoroutine { continuation ->
        try {
            val xhr = XMLHttpRequest()
            xhr.timeout = 15000

            xhr.onload = {
                println("XHR Response status: ${xhr.status}")
                println("XHR Response text: ${xhr.responseText}")

                if (xhr.status in 200..299) {
                    println("=== Submission Successful ===")
                    continuation.resume(Unit)
                } else {
                    println("HTTP Error: ${xhr.status} ${xhr.responseText}")
                    continuation.resumeWithException(
                        Exception("Failed to submit: HTTP ${xhr.status} - ${xhr.responseText}")
                    )
                }
            }

            xhr.onerror = {
                println("=== Network Error ===")
                continuation.resumeWithException(Exception("Network error occurred"))
            }

            xhr.ontimeout = {
                println("=== Request Timeout ===")
                continuation.resumeWithException(Exception("Request timeout after 15 seconds"))
            }

            xhr.open("POST", databaseUrl, true)
            xhr.setRequestHeader("Content-Type", "application/json")
            xhr.send(jsonBody)

            println("XHR request sent!")

        } catch (e: Exception) {
            println("Exception in XHR setup: ${e.message}")
            continuation.resumeWithException(e)
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