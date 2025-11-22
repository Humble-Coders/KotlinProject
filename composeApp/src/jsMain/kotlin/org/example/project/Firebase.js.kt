package org.example.project

import kotlinx.coroutines.await
import kotlinx.browser.window
import org.w3c.fetch.Response

actual suspend fun submitToFirebase(fields: Map<String, String>, databaseUrl: String) {
    // Get current timestamp
    val timestamp = js("Date.now()").unsafeCast<Double>().toLong()
    
    // Build Firestore document structure
    // Firestore REST API expects fields in a specific format with "stringValue", "integerValue", etc.
    val firestoreFields = buildString {
        append("{")
        fields.forEach { (key, value) ->
            if (length > 1) append(",")
            append("\"$key\": {\"stringValue\": \"${escapeJsonString(value)}\"}")
            append("}")
        }
        // Add timestamp as integerValue
        append(",\"timestamp\": {\"integerValue\": \"$timestamp\"}")
        append("}")
    }
    
    val firestoreDocument = """
    {
        "fields": $firestoreFields
    }
    """.trimIndent()
    
    // Use Kotlin/JS Promise-based fetch API
    val fetchOptions = js("{}")
    fetchOptions.method = "POST"
    fetchOptions.headers = js("{}")
    fetchOptions.headers["Content-Type"] = "application/json"
    fetchOptions.body = firestoreDocument
    
    val response = window.fetch(databaseUrl, fetchOptions).await() as Response

    val ok = response.status.toInt() in 200..299
    if (!ok) {
        val errorText = response.text().await()
        throw Exception("Failed to submit form: ${response.status} ${response.statusText}. $errorText")
    }

    val result = response.text().await()
    println("Firestore response: $result")
}

// Helper function to escape JSON strings for Firestore
private fun escapeJsonString(str: String): String {
    return str
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")
}
