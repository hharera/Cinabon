buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.0")
        classpath(Libs.kotlinPlugin)
        classpath(Libs.navigationSafeArgsPlugin)
        classpath(Libs.googleServices)
        classpath(Libs.hiltPlugin)
        classpath ("org.jetbrains.kotlin:kotlin-serialization:${Versions.KOTLIN_VERSION}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}