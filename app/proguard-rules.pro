# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/cao_ruixiang/Develop/software/android-sdk-macosx/tools/proguard/proguard-android.txt
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

## basic proguard rules

-keep public class * extends android.app.Application
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver

-keepattributes *Annotation*
-keepattributes Signature

-keepclasseswithmembernames class * {
	native <methods>;
}

-keepclasseswithmembernames class * {
	public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Keep serializable objects
-keepclassmembers class * implements java.io.Serializable {
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}


## support libraries
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-keep public class android.support.v7.** { *; }

## butter knife proguard rules

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
## LIBRARY: OkHttp
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn okio.**

## LIBRARY: Retrofit
-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }
-keep class javax.inject.** { *; }
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-dontwarn rx.**
-dontwarn retrofit.**
-dontwarn com.google.appengine.**
