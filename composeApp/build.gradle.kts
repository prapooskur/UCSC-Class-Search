import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            val ktorVersion = "2.3.6"
            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-cio:$ktorVersion")

            implementation("com.mohamedrejeb.ksoup:ksoup-html:0.2.1")

            // jsoup HTML parser library @ https://jsoup.org/
            implementation("org.jsoup:jsoup:1.16.2")

            // logging
            implementation("co.touchlab:kermit:2.0.2")

            // navigation
            val voyagerVersion = "1.0.0-rc10"
            // Navigator
            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
            // BottomSheetNavigator
            implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")
            // TabNavigator
            implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
            // Transitions
            implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
        }
    }
}

android {
    namespace = "com.pras.pisasearch"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.pras.pisasearch"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            // Enables code shrinking, obfuscation, and optimization for only
            // your project's release build type.
            isMinifyEnabled = true

            // Enables resource shrinking, which is performed by the
            // Android Gradle plugin.
            isShrinkResources = true

            // Includes the default ProGuard rules files that are packaged with
            // the Android Gradle plugin. To learn more, go to the section about
            // R8 configuration files.
            proguardFiles (
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
dependencies {
    implementation(libs.androidx.material3)
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Exe, TargetFormat.Deb, TargetFormat.Rpm)
            packageName = "com.pras.pisasearch"
            packageVersion = "1.0.0"
        }
    }
}
