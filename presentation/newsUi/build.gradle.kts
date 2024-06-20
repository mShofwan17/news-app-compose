plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "me.project.newsui"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":shared"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)

    //hilt
    implementation(libs.hilt.android)
    debugImplementation(libs.ui.tooling)
    kapt(libs.hilt.compiler)

    //compose utilities
    implementation(libs.navigationCompose)
    implementation(libs.viewModelCompose)
    implementation(libs.lifecycleCompose)
    implementation(libs.hiltCompose)

    //coroutine test
    implementation(libs.coroutinesTest)

    //coil compose
    implementation(libs.coilCompose)

    //material3
    implementation(libs.material3)

    //paging
    implementation (libs.androidx.paging.runtime.ktx)
    implementation (libs.androidx.paging.compose)
}