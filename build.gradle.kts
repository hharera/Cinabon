buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.gradleTools)
        classpath(Libs.kotlinPlugin)
        classpath(Libs.navigationSafeArgsPlugin)
        classpath(Libs.googleServices)
        classpath(Libs.hiltPlugin)
    }
}

allprojects {
    repositories {
//        jcenter()
//        google()
//        maven { url = uri("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}