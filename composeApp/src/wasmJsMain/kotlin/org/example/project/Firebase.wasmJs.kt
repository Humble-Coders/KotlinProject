package org.example.project

import kotlin.js.ExperimentalWasmJsInterop
import kotlinx.coroutines.await
import kotlinx.browser.window
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response

// Top-level property for Date.now() - required for Wasm JS interop
@OptIn(ExperimentalWasmJsInterop::class)
private val dateNowJs: Double = js("Date.now()")
// Top-level function for Date.toISOString() - required for Wasm JS interop
@OptIn(ExperimentalWasmJsInterop::class)
private val dateToIsoStringFn: () -> String = js("() => new Date().toISOString()")

@OptIn(ExperimentalWasmJsInterop::class)
actual suspend fun submitToFirebase(fields: Map<String, String>, databaseUrl: String) {
    println("=== Starting Firebase Submission ===")
    println("URL: $databaseUrl")
    
    // Get current timestamp
    val timestamp = dateNowJs.toLong()
    val submittedAt = dateToIsoStringFn()
    
    // Build Firestore document structure
    // Firestore REST API expects fields in a specific format with "stringValue", "integerValue", etc.
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
    
    // Create headers using Headers API for Kotlin/Wasm
    val headers = Headers()
    headers.append("Content-Type", "application/json")
    
    // For Wasm, create RequestInit with proper headers
    val requestInit = RequestInit(
        method = "POST",
        headers = headers,
        body = firestoreDocument as kotlin.js.JsAny?
    )
    
    try {
        println("Sending request...")
        
        val response = window.fetch(databaseUrl, requestInit).await<Response>()

        println("Response received, status: ${response.status}")
        
        val status = response.status.toInt()
        
        // Get response text
        val responseText = try {
            response.text().await()
        } catch (e: Exception) {
            "Unable to read response body"
        }
        
        println("Response body: $responseText")
        
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
