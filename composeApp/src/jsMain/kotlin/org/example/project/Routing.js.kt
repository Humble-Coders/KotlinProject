package org.example.project

import kotlinx.browser.window

actual fun createWebRouting(): WebRouting = WebRouting()

actual class WebRouting {
    actual fun getCurrentRoute(): String {
        return window.location.hash.removePrefix("#")
    }
    
    actual fun navigateTo(route: String) {
        window.location.hash = "#$route"
    }
    
    actual fun onRouteChange(callback: (String) -> Unit): () -> Unit {
        val listener: (dynamic) -> Unit = {
            callback(window.location.hash.removePrefix("#"))
        }
        window.addEventListener("hashchange", listener)
        return {
            window.removeEventListener("hashchange", listener)
        }
    }
}

