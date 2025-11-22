package org.example.project

import kotlinx.coroutines.await
import kotlinx.browser.window
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response

// Top-level property for Date.now() - required for Wasm JS interop
private val dateNowJs: Double = js("Date.now()")

actual suspend fun submitToFirebase(fields: Map<String, String>, databaseUrl: String) {
    // Get current timestamp
    val timestamp = dateNowJs.toLong()
    
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
    
    // For Wasm, create RequestInit - headers will be set by browser automatically for JSON content
    val requestInit = RequestInit(
        method = "POST",
        headers = null, // Let browser set Content-Type automatically
        body = firestoreDocument as kotlin.js.JsAny?
    )
    
    val response = window.fetch(databaseUrl, requestInit).await<Response>()

    val status = response.status.toInt()
    val ok = status in 200..299
    if (!ok) {
        throw Exception("Failed to submit form: HTTP $status")
    }

    println("Firestore submission successful: HTTP $status")
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
