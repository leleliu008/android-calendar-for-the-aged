buildscript {
    val kotlinVersion = "1.9.0"
    extra["kotlinVersion"] = kotlinVersion

    repositories {
        maven { url = uri("https://maven.aliyun.com/repository/central") }
        google()
    }

    dependencies {
        //用于构建出apk/aar的插件
        //https://developer.android.google.cn/studio/releases/gradle-plugin
        classpath("com.android.tools.build:gradle:8.13.0")

        //用于编译Kotlin代码的插件
        //http://kotlinlang.org/docs/reference/using-gradle.html
        classpath(kotlin("gradle-plugin", kotlinVersion))
    }
}

allprojects {
    repositories {
        mavenLocal()
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        maven {
            url = uri("https://raw.githubusercontent.com/leleliu008/ndk-pkg-prefab-aar-maven-repo/master")
        }
        google()
    }
}

tasks.register("clean", Delete::class, { delete(rootProject.layout.buildDirectory) })
