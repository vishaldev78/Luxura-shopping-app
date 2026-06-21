# Optimize for smaller size
-optimizationpasses 5
-allowaccessmodification
-mergeinterfacesaggressively

# Keep Data classes for GSON
-keep class com.shops.ecomm.data.remote.** { *; }
-keep class com.shops.ecomm.domain.model.** { *; }

# Gson specific rules
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes *Annotation*
-keep class com.google.gson.** { *; }
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.TypeAdapter

# Retrofit
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-dontwarn retrofit2.**

# OkHttp
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# Hilt/Dagger
-keep class dagger.hilt.** { *; }

# Firebase
-keep class com.google.firebase.** { *; }

# Razorpay
-keep class com.razorpay.** { *; }
-dontwarn com.razorpay.**
-keepattributes *Annotation*
-keepclassmembers class * {
    @com.razorpay.VisibleForTesting *;
}

# Stripe
-keep class com.stripe.android.** { *; }
-dontwarn com.stripe.android.**
-keep class com.stripe.model.** { *; }
-keepattributes *Annotation*, SourceFile, LineNumberTable
