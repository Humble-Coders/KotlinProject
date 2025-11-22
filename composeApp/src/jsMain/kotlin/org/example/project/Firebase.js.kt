package org.example.project

import kotlinx.coroutines.await
import kotlinx.browser.window
import org.w3c.fetch.Response

actual suspend fun submitToFirebase(fields: Map<String, String>, databaseUrl: String) {
    println("=== Starting Firebase Submission ===")
    println("URL: $databaseUrl")
    
    // Get current timestamp
    val timestamp = js("Date.now()").unsafeCast<Number>()
    val submittedAt = js("new Date().toISOString()") as String
    
    // Build Firestore document structure
    val firestoreFields = buildString {
        append("{")
        var isFirst = true
        fields.forEach { (key, value) ->
            if (!isFirst) append(",")
            append("\"$key\": {\"stringValue\": \"${escapeJsonString(value)}\"}")
            isFirst = false
        }
        // Add timestamp as integerValue
        if (!isFirst) append(",")
        append("\"timestamp\": {\"integerValue\": \"$timestamp\"}")
        // Add submittedAt as stringValue
        append(",\"submittedAt\": {\"stringValue\": \"${escapeJsonString(submittedAt)}\"}")
        append("}")
    }
    
    val firestoreDocument = """
    {
        "fields": $firestoreFields
    }
    """.trimIndent()
    
    println("Request body: $firestoreDocument")
    
    // Create fetch options
    val fetchOptions = js("{}")
    fetchOptions.method = "POST"
    fetchOptions.headers = js("{}")
    fetchOptions.headers["Content-Type"] = "application/json"
    fetchOptions.body = firestoreDocument
    
    try {
        println("Sending request...")
        
        val response = window.fetch(databaseUrl, fetchOptions).await() as Response
        
        println("Response received, status: ${response.status}")
        
        // Get response text
        val responseText = response.text().await()
        println("Response body: $responseText")
        
        val status = response.status.toInt()
        
        if (status !in 200..299) {
            println("HTTP Error: $status $responseText")
            throw Exception("Failed to submit: HTTP $status - $responseText")
        }
        
        println("=== Submission Successful ===")
        
    } catch (e: Exception) {
        println("=== Submission Failed ===")
        println("Error message: ${e.message}")
        println("Error: $e")
        throw Exception("Network error: ${e.message ?: "Unknown error"}")
    }
}

// Helper function to escape JSON strings for Firestore
private fun escapeJsonString(str: String): String {
    return str
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")
        .replace("'", "\\'")
}
