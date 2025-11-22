import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    js {
        browser()
        binaries.executable()
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    
    sourceSets {
        val jsJodaVersion = "3.2.0"
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            // Material Icons Extended for more icon options
            implementation(compose.materialIconsExtended)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        val jsMain by getting {
            dependencies {
                implementation(npm("@js-joda/core", jsJodaVersion))
            }
        }
        val wasmJsMain by getting {
            dependencies {
                implementation(npm("@js-joda/core", jsJodaVersion))
            }
        }
    }
}
compose.resources {
    customDirectory(
        sourceSetName = "webMain",
        directoryProvider = provider {
            layout.projectDirectory.dir("src/webMain/composeResources")
        }
    )
}

