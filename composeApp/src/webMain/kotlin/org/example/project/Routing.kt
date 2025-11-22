package org.example.project

expect fun createWebRouting(): WebRouting

expect class WebRouting {
    fun getCurrentRoute(): String
    fun navigateTo(route: String)
    fun onRouteChange(callback: (String) -> Unit): () -> Unit
}

