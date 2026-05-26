plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.fpliu.calendar_for_the_aged"

    compileSdk = 37

    defaultConfig {
        applicationId = "com.fpliu.calendar_for_the_aged"

        minSdk = 21
        targetSdk = 33

        versionCode = 1656337847
        versionName = "1.0.0"

        //只需要支持中文和英文即可，其他语言不必支持
        resourceConfigurations += setOf("en", "zh")

        ndk {
            abiFilters += setOf("arm64-v8a")
//            abiFilters += setOf("arm64-v8a", "x86_64")
        }

        externalNativeBuild {
            cmake {
                arguments += "-DANDROID_STL=c++_shared"
                cppFlags  += "-std=c++17"
            }
        }
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
        enable = false
    }

    viewBinding {
        enable = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true

            signingConfig = signingConfigs.findByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-common.txt", "proguard-nolog.txt")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    ndkVersion = "27.3.13750724"

    externalNativeBuild {
        cmake {
            version = "3.18.1+"
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }

    buildFeatures {
        prefab = true
    }

    lint {
        abortOnError = false
        checkReleaseBuilds = false
        disable += setOf("ContentDescription", "HardcodedText")
    }

    packaging {
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
                "kotlin-tooling-metadata.json",
                "kotlin/*",
                "META-INF/*"
            )
        }
    }

    bundle {
        abi {
            enableSplit = false
        }
    }
}

dependencies {
    //内存泄漏检测工具LeakCanary
    //https://github.com/square/leakcanary
//    debugApi("com.squareup.leakcanary:leakcanary-android:1.5.4")
//    releaseApi("com.squareup.leakcanary:leakcanary-android-no-op:1.5.4")

    //https://github.com/uber/AutoDispose
    //autodispose-android has a ViewScopeProvider for use with Android View classes.
    api("com.uber.autodispose:autodispose-android-ktx:1.2.0")

    api("com.fpliu:Android-CrashHandler:1.0.0")
    api("com.fpliu:Android-StatusBar-Util:1.0.2")
    api("com.fpliu:Android-Font-Config-api:1.0.0")
    api("com.fpliu:Android-Font-Assets-Alibaba_PuHuiTi_Light:1.0.0")

    api("com.fpliu:Android-BaseUI:2.0.12")
    api("com.fpliu:Android-CustomDrawable:1.0.0")
    api("com.fpliu:Android-CustomDimen:1.0.0")

    api("com.fpliu.ndk.pkg.prefab.android.21:chinese-calendar:2022.06.24@aar")
}
