# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/dd/Work/android-sdk-linux/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# -ignorewarnings

# Remove logs
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# Proguard configurations for common Android libraries:
# https://github.com/krschultz/android-proguard-snippets

########### CRASHLYTICS BEGIN ###########

-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

########### CRASHLYTICS END ###########

########### FACEBOOK BEGIN ###########

# Facebook 3.2

-keep class com.facebook.** { *; }
-keepattributes Signature

########### FACEBOOK END ###########

########### GSON BEGIN ###########

## GSON 2.2.4 specific rules ##

# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

-keepattributes EnclosingMethod

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

########### GSON END ###########

########### KOTLIN BEGIN ###########

-dontwarn kotlin.**

########### KOTLIN END ###########

########### LEAKCANARY BEGIN ###########

-dontwarn com.squareup.haha.guava.**
-dontwarn com.squareup.haha.perflib.**
-dontwarn com.squareup.haha.trove.**
-dontwarn com.squareup.leakcanary.**
-keep class com.squareup.haha.** { *; }
-keep class com.squareup.leakcanary.** { *; }

# Marshmallow removed Notification.setLatestEventInfo()
-dontwarn android.app.Notification

########### LEAKCANARY END ###########

########### OKIO BEGIN ###########

-dontwarn okio.**

########### OKIO END ###########

########### REALM BEGIN ###########

# Proguard Configuration for Realm (http://realm.io)
# For detailed discussion see: https://groups.google.com/forum/#!topic/realm-java/umqKCc50JGU
# Additionally you need to keep your Realm Model classes as well
# For example:
# -keep class com.yourcompany.realm.** { *; }

-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class *
-dontwarn javax.**
-dontwarn io.realm.**

########### REALM END ###########

########### RETROFIT BEGIN ###########

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

########### RETROFIT END ###########

########### RXJAVA BEGIN ###########

# RxJava 0.21

-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}

########### RXJAVA END ###########

########### STETHO BEGIN ###########

# Updated as of Stetho 1.1.1
#
# Note: Doesn't include Javascript console lines. See https://github.com/facebook/stetho/tree/master/stetho-js-rhino#proguard
-keep class com.facebook.stetho.** { *; }

########### STETHO END