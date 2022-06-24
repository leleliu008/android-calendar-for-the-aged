buildscript {
    val kotlinVersion = "1.6.21"
    extra["kotlinVersion"] = kotlinVersion
         
    repositories {
        maven { url = uri("https://maven.aliyun.com/repository/central") }
        google()
    }

    dependencies {
        //用于构建Android工程的插件
        //https://developer.android.google.cn/studio/releases/gradle-plugin
        classpath("com.android.tools.build:gradle:7.1.3")

        //Kotlin编译的插件
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
