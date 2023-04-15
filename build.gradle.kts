buildscript {
    val kotlinVersion = "1.6.21"
    extra["kotlinVersion"] = kotlinVersion
         
    repositories {
        maven { url = uri("https://maven.aliyun.com/repository/central") }
        google()
    }

    dependencies {
        //用于构建出apk/aar的插件
        //https://developer.android.google.cn/studio/releases/gradle-plugin
        classpath("com.android.tools.build:gradle:8.0.0")

        //用于编译Kotlin代码的插件
        //http://kotlinlang.org/docs/reference/using-gradle.html
        classpath(kotlin("gradle-plugin", kotlinVersion))
    }
}

allprojects {
    repositories {
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        google()
    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}
