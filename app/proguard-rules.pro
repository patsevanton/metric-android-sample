# Sentry SDK uses reflection over some of its own APIs and okhttp internals.
-keepattributes SourceFile,LineNumberTable

# Keep line numbers for readable stack traces even when minified.
-renamesourcefileattribute SourceFile
-keepattributes *Annotation*

# Keep Sentry SDK and its integrations.
-dontwarn io.sentry.**
-keep class io.sentry.** { *; }

# Keep OkHttp classes referenced by SentryOkHttpInterceptor.
-dontwarn okhttp3.**
-dontwarn okio.**
