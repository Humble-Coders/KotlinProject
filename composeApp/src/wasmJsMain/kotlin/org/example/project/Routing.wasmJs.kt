package org.example.project

// WASM doesn't support kotlinx.browser.window directly
// Using a simple state-based approach for WASM
actual fun createWebRouting(): WebRouting = WebRouting()

actual class WebRouting {
    private var currentRoute: String = ""
    private var routeChangeCallback: ((String) -> Unit)? = null
    
    actual fun getCurrentRoute(): String {
        return currentRoute
    }
    
    actual fun navigateTo(route: String) {
        currentRoute = route
        routeChangeCallback?.invoke(route)
    }
    
    actual fun onRouteChange(callback: (String) -> Unit): () -> Unit {
        routeChangeCallback = callback
        callback(currentRoute) // Call immediately with current route
        return {
            routeChangeCallback = null
        }
    }
}

