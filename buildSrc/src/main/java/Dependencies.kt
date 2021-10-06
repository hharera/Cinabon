object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object DefaultConfig {
    const val buildToolsVersion = "30.0.3"
    const val appId = "com.harera.hyperpanda"
    const val minSdk = 21
    const val targetSdk = 31
    const val compileSdk = 31
}

object Modules {
    const val loading = ":ui-components:loading"
    const val categoryName = ":ui-components:category-name"
    const val imageSlider = ":ui-components:image-slider"
    const val product = ":ui-components:product"
    const val chooseLocation = ":ui-components:choose-location"

    const val addCategory = ":manager-features:add-category"
    const val managerNavigation = ":manager-navigation"

    const val userNavigation = ":user-navigation"

    const val common = ":common"
    const val model = ":data:model"
    const val repository = ":data:repository"
    const val featureCategories = ":features:categories"
}

object Core {
    // For Styles, colors & anything COMMON view related.
    const val views = ":core:views" // TODO: Rename this to designSystem.
    const val network = ":core:network"
    const val navigation = ":core:navigation"
}

object Features {
    const val movies = ":features:movies"
    const val actors = ":features:actors"
}

object Common {
    const val models = ":common:models"
    const val uiComponents = ":common:ui-components"
}

object Versions {
    const val Ad_MOB_VERSION = "20.4.0"
    const val ACTIVITY_VERSION = "1.3.1"
    const val PLAY_SERVICE_TASKS = "17.2.1"
    const val LIFECYCLE_COMPILER_VERSION: String = "2.0.0"
    const val CONSTRAINT_LAYOUT_VERSION = "2.1.1"
    const val INTUIT_VERSION = "1.0.6"
    const val GLIGER_VERSION = "1.1.0"
    const val SEARCH_BAR = "0.7.5"
    const val CIRCLE_IMAGE = "3.1.0"

    //Firebase
    const val FIREBASE_DATABASE_VERSION = "20.0.2"
    const val FIREBASE_AUTH_VERSION = "21.0.1"
    const val FIREBASE_FIRESTORE_VERSION = "23.0.3"
    const val FIREBASE_STORAGE_VERSION = "20.0.0"
    const val FIREBASE_BOM_VERSION = "26.1.1"

    //Google Services
    const val GOOGLE_AUTH_VERSION = "19.2.0"
    const val GOOGLE_AUTH_PHONE_VERSION = "17.5.1"
    const val GOOGLE_PLACES = "2.4.0"
    const val GOOGLE_MAPS_VERSION = "17.0.1"
    const val LEGACY_VERSION = "1.0.0"

    const val KOTLIN_VERSION = "1.5.30"
    const val HILT_VERSION = "2.37"
    const val NAV_VERSION = "2.3.1"
    const val COROUTINES_VERSION = "1.5.0"
    const val GRADLE_TOOLS_VERSION = "7.0.2"
    const val GMS_GOOGLE_SERVICES = "4.3.10"
    const val Jvm_TARGET = "4.3.10"

    const val FRAGMENT_VERSION = "1.3.3"
    const val LIFECYCLE_VERSION = "2.3.1"
    const val JETPACK_NAVIGATION_VERSION = "2.3.5"

    const val RETROFIT_VERSION = "2.9.0"
    const val LOGGING_INTERCEPTOR_VERSION = "4.9.0"
    const val MOCK_WEB_SERVER_VERSION = "4.9.0"

    const val COIL_VERSION = "1.1.1"

    const val TIMBER_VERSION = "4.7.1"

    const val PICASSO_VERSION = "2.71828"

    const val TEST_MOCKK_VERSION = "1.11.0"
    const val TEST_JUNIT_VERSION = "4.13.2"

    const val TEST_RUNNER_VERSION = "1.4.0"

    const val LEAK_CANARY_VERSION = "2.6"

    const val ANDROIDX_CORE_Testing_VERSION = "2.1.0"

    const val ANDROIDX_CORE_VERSION = "1.3.2"
    const val ANDROIDX_APP_COMPAT_VERSION = "1.3.1"
    const val GOOGLE_MATERIAL_VERSION = "1.4.0"
    const val ANDROIDX_RAINT_LAYOUT_VERSION = "2.1.1"
    const val ANDROIDX_SWIPE_REFRESH_LAYOUT_VERSION = "1.1.0"
    const val ANDROIDX_FRAGMENT_TESTING_VERSION = "1.3.3"

    const val DI_KOIN_VERSION = "3.0.2"
}

object Libs {
    //adMob
    const val adMob = "com.google.android.gms:play-services-ads-lite:${Versions.Ad_MOB_VERSION}"

    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT_VERSION}"
    //Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.HILT_VERSION}"
    const val hiltAndroidCompiler =
        "com.google.dagger:hilt-android-compiler:${Versions.HILT_VERSION}"

    //UI
    const val activity = "androidx.activity:activity-ktx:${Versions.ACTIVITY_VERSION}"

    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT_VERSION}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.ANDROIDX_APP_COMPAT_VERSION}"
    const val materialDesign =
        "com.google.android.material:material:${Versions.GOOGLE_MATERIAL_VERSION}"

    //Image Loader
    const val picasso = "com.squareup.picasso:picasso:${Versions.PICASSO_VERSION}"

    //test
    const val testRunner = "androidx.test:runner:${Versions.TEST_RUNNER_VERSION}"
    const val androidCoreTesting =
        "androidx.arch.core:core-testing:${Versions.ANDROIDX_CORE_Testing_VERSION}"

    //gms play
    const val playServicesMaps =
        "com.google.android.gms:play-services-maps:${Versions.GOOGLE_MAPS_VERSION}"

    const val googleServices = "com.google.gms:google-services:4.3.10"
    const val playServicesTasks =
        "com.google.android.gms:play-services-tasks:${Versions.PLAY_SERVICE_TASKS}"
    const val playServicesAuth =
        "com.google.android.gms:play-services-auth:${Versions.GOOGLE_AUTH_VERSION}"

    const val awareness = "com.google.android.gms:play-services-:18.0.2"
    const val safetynet = "com.google.android.gms:play-services-:17.0.1"

    const val playServicesAuthPhone =
        "com.google.android.gms:play-services-auth-api-phone:${Versions.GOOGLE_AUTH_PHONE_VERSION}"

    //firebase
    const val firebaseAuth = "com.google.firebase:firebase-auth:${Versions.FIREBASE_AUTH_VERSION}"

    const val firebaseDatabase =
        "com.google.firebase:firebase-database:${Versions.FIREBASE_DATABASE_VERSION}"

    const val firebaseStorage =
        "com.google.firebase:firebase-storage:${Versions.FIREBASE_STORAGE_VERSION}"

    const val firebaseFirestore =
        "com.google.firebase:firebase-firestore:${Versions.FIREBASE_FIRESTORE_VERSION}"

    const val firebaseBom =
        "com.google.firebase:firebase-bom:${Versions.FIREBASE_BOM_VERSION}"

    //Material SearchBar
    const val searchBar = "com.github.mancj:MaterialSearchBar:${Versions.SEARCH_BAR}"

    //dots indicator
    const val indicator = "com.tbuonomo:dotsindicator:4.2"

    //circle image
    const val circleImage = "de.hdodenhof:circleimageview:${Versions.CIRCLE_IMAGE}"

    //gligar picker
    const val gligarPicker = "com.opensooq.supernova:gligar:${Versions.GLIGER_VERSION}"

    //intuit
    const val intuit = "com.intuit.sdp:sdp-android:${Versions.INTUIT_VERSION}"

    //places
    const val googlePlaces = "com.google.android.libraries.places:places:${Versions.GOOGLE_PLACES}"

    const val gradleTools = "com.android.tools.build:gradle:${Versions.GRADLE_TOOLS_VERSION}"

    // Kotlin
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_VERSION}"

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}"

    // Coroutines
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES_VERSION}"
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES_VERSION}"

    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES_VERSION}"
    const val androidxCore = "androidx.core:core-ktx:${Versions.ANDROIDX_CORE_VERSION}"
    const val androidxAppCompat =
        "androidx.appcompat:appcompat:${Versions.ANDROIDX_APP_COMPAT_VERSION}"
    const val googleMaterial =
        "com.google.android.material:material:${Versions.GOOGLE_MATERIAL_VERSION}"

    const val androidXraintLayout =
        "androidx.raintlayout:raintlayout:${Versions.ANDROIDX_RAINT_LAYOUT_VERSION}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}"
    const val moshi = "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT_VERSION}"

    const val interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.LOGGING_INTERCEPTOR_VERSION}"

    // MockWebServer
    const val mockwebServer =
        "com.squareup.okhttp3:mockwebserver:${Versions.MOCK_WEB_SERVER_VERSION}"

    // Timber
    const val timber = "com.jakewharton.timber:timber:${Versions.TIMBER_VERSION}"

    // Coil
    const val coil = "io.coil-kt:coil:${Versions.COIL_VERSION}"

    // DI Koin
    const val koinAndroidx = "io.insert-koin:koin-android:${Versions.DI_KOIN_VERSION}"

    const val koinJvmTest = "io.insert-koin:koin-test-junit4:${Versions.DI_KOIN_VERSION}"

    // Fragments
    const val fragments = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_VERSION}"

    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_VERSION}"
    const val lifeCycleCompiler =
        "androidx.lifecycle:lifecycle-compiler:${Versions.LIFECYCLE_COMPILER_VERSION}"
    const val lifeCycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_VERSION}"
    const val viewModels =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_VERSION}"

    // SwipeRefresh layout
    const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.ANDROIDX_SWIPE_REFRESH_LAYOUT_VERSION}"

    //Navigation
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.JETPACK_NAVIGATION_VERSION}"

    const val navigationUi =
        "androidx.navigation:navigation-ui-ktx:${Versions.JETPACK_NAVIGATION_VERSION}"
    const val navigationSafeArgsPlugin =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.JETPACK_NAVIGATION_VERSION}"

    // ||
    // TESTING
    // ||

    // Mockk
    const val testMockk = "io.mockk:mockk:${Versions.TEST_MOCKK_VERSION}"
    const val testMockkInstrumented = "io.mockk:mockk-android:${Versions.TEST_MOCKK_VERSION}"

    // Junit 4
    const val junit4 = "junit:junit:${Versions.TEST_JUNIT_VERSION}"
    const val junitExt = "androidx.test.ext:junit:1.1.2"

    // Leak Canary
    const val leakCanary =
        "com.squareup.leakcanary:leakcanary-android:${Versions.LEAK_CANARY_VERSION}"

    // Espresso
    const val espresso = "androidx.test.espresso:espresso-core:3.3.0"

    // Fragments
    const val fragmentTest =
        "androidx.fragment:fragment-testing:${Versions.ANDROIDX_FRAGMENT_TESTING_VERSION}"

}