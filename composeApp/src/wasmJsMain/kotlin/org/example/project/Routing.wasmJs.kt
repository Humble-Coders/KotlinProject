package org.example.project

import org.w3c.dom.Window
import org.w3c.dom.events.Event

// WASM version using JavaScript interop to access window
@JsName("window")
external val window: Window

actual fun createWebRouting(): WebRouting = WebRouting()

actual class WebRouting {
    private var routeChangeCallback: ((String) -> Unit)? = null
    
    actual fun getCurrentRoute(): String {
        val hash = window.location.hash
        return if (hash.startsWith("#")) hash.substring(1) else hash
    }
    
    actual fun navigateTo(route: String) {
        window.location.hash = "#$route"
        // Manually trigger callback since hashchange might not fire immediately
        routeChangeCallback?.invoke(route)
    }
    
    actual fun onRouteChange(callback: (String) -> Unit): () -> Unit {
        routeChangeCallback = callback
        // Call immediately with current route
        callback(getCurrentRoute())
        
        // Set up hashchange listener
        val listener: (Event) -> Unit = {
            callback(getCurrentRoute())
        }
        window.addEventListener("hashchange", listener)
        
        return {
            routeChangeCallback = null
            window.removeEventListener("hashchange", listener)
        }
    }
}

