/*
 * Temporary workaround for [KT-80582](https://youtrack.jetbrains.com/issue/KT-80582)
 * 
 * This file should be safe to be removed once the ticket is closed and the project is updated to Kotlin version which solves that issue.
 */
config.watchOptions = config.watchOptions || {
    ignored: [ "**/node_modules"]
}

if (config.devServer) {
    config.devServer.static = config.devServer.static.map(file => {
        if (typeof file === "string") {
            // Enable watching for kotlin directory (compiled output)
            // so webpack detects when Gradle recompiles
            if (file.includes("kotlin") || file.endsWith("kotlin")) {
                return {
                    directory: file,
                    watch: true,
                }
            }
            return {
                directory: file,
                watch: false,
            }
        } else {
            return file
        }
    })
    
    // Ensure webpack watches for changes in the compiled output
    config.devServer.watchFiles = {
        paths: ['kotlin/**/*'],
        options: {
            usePolling: false,
        }
    }
}