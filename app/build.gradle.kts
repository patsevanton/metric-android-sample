import io.sentry.android.gradle.extensions.InstrumentationFeature
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.sentry)
}

android {
    namespace = "com.example.metricdemo"
    compileSdk = 35
    buildToolsVersion = "35.0.1"

    defaultConfig {
        applicationId = "com.example.metricdemo"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    buildFeatures {
        compose = true
    }
}

sentry {
    org = System.getenv("SENTRY_ORG") ?: "myorg"
    projectName = System.getenv("SENTRY_PROJECT") ?: "android-demo"
    authToken = System.getenv("SENTRY_AUTH_TOKEN")
    url = System.getenv("SENTRY_URL")
    autoUploadProguardMapping = System.getenv("SENTRY_AUTH_TOKEN") != null
    includeSourceContext = System.getenv("SENTRY_AUTH_TOKEN") != null
    tracingInstrumentation {
        enabled = true
        features = setOf(
            InstrumentationFeature.DATABASE,
            InstrumentationFeature.FILE_IO,
            InstrumentationFeature.OKHTTP,
            InstrumentationFeature.COMPOSE
        )
    }
    autoInstallation {
        sentryVersion = libs.versions.sentryAndroid.get()
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.okhttp)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.sentry.android)
    implementation(libs.sentry.okhttp)
    implementation(libs.sentry.compose.android)
    debugImplementation(libs.androidx.ui.tooling)
}
