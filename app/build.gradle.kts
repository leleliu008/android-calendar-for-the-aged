plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        applicationId = "com.fpliu.calendar_for_the_aged"
        versionCode = 1650669576
        versionName = "1.0.0"

        //只需要支持中文和英文即可，其他语言不必支持
        resourceConfigurations += setOf("en", "zh")
    }

    sourceSets {
        getByName("main") {
            jniLibs.srcDir("src/main/libs")
            java.srcDirs("src/main/kotlin")
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("../keystore.jks")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    dataBinding {
        isEnabled = false
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
        }
        getByName("release") {
            signingConfig = signingConfigs.findByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-common.txt", "proguard-nolog.txt")
        }
    }

    compileOptions {
        //使用JAVA8语法解析
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        jniLibs {
            excludes += setOf("kotlin/*", "META-INF/*")
        }
        resources {
            excludes += setOf(
                "publicsuffixes.gz",
                "build-data.properties",
                "jsr305_annotations/Jsr305_annotations.gwt.xml",
                "okhttp3/internal/publicsuffix/publicsuffixes.gz",
                "kotlin/**/*.kotlin_builtins",
                "kotlin/**/*.kotlin_metadata",
                "kotlin/*",
                "META-INF/*"
            )
        }
    }

    lint {
        abortOnError = false
        checkReleaseBuilds = false
        disable += setOf("ContentDescription", "HardcodedText")
    }

    bundle {
        abi {
            enableSplit = false
        }
    }

    ndkVersion = "23.1.7779620"

    externalNativeBuild {
        cmake {
            version = "3.18.1"
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }

    buildFeatures {
        prefab = true
    }
}

dependencies {
    //内存泄漏检测工具LeakCanary
    //https://github.com/square/leakcanary
    debugApi("com.squareup.leakcanary:leakcanary-android:1.5.4")
    releaseApi("com.squareup.leakcanary:leakcanary-android-no-op:1.5.4")

    //https://github.com/uber/AutoDispose
    //autodispose-android has a ViewScopeProvider for use with Android View classes.
    api("com.uber.autodispose:autodispose-android-ktx:1.1.0")

    api("com.fpliu:Android-CrashHandler:1.0.0")
    api("com.fpliu:Android-StatusBar-Util:1.0.2")
    api("com.fpliu:Android-Font-Config-api:1.0.0")
    api("com.fpliu:Android-Font-Assets-Alibaba_PuHuiTi_Light:1.0.0")

    api("com.fpliu:Android-BaseUI:2.0.12")
    api("com.fpliu:Android-CustomDrawable:1.0.0")
    api("com.fpliu:Android-CustomDimen:1.0.0")

    api("com.fpliu.ndk.pkg.prefab.android.21:chinese-calendar:2022.04.24@aar")
}