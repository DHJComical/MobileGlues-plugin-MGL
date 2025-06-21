plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.fcl.plugin.mobileglues"
    compileSdk = 35

    ndkVersion = "27.1.12297006"

    defaultConfig {
        applicationId = "com.fcl.plugin.mobilegl"
        minSdk = 26
        targetSdk = 35
        versionCode = 1250
        versionName = "1.2.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("../keystore.jks")
            storePassword = "666666"
            keyAlias = "MGKey"
            keyPassword = "666666"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        configureEach {
            resValue("string","app_name","MobileGL")

            manifestPlaceholders["des"] = "MobileGL (OpenGL 4.0, 1.17+)"
            manifestPlaceholders["renderer"] = "MobileGlues:libMobileGL.so:libMobileGL.so"

            manifestPlaceholders["minMCVer"] = "1.17"
            manifestPlaceholders["maxMCVer"] = "" //为空则不限制 No restriction if empty

            manifestPlaceholders["boatEnv"] = mutableMapOf<String,String>().apply {
                put("LIBGL_ES", "3")
                put("DLOPEN", "libspirv-cross-c-shared.so,libshaderconv.so")
            }.run {
                var env = ""
                forEach { (key, value) ->
                    env += "$key=$value:"
                }
                env.dropLast(1)
            }
            manifestPlaceholders["pojavEnv"] = mutableMapOf<String,String>().apply {
                put("LIBGL_ES", "3")
                put("DLOPEN", "libspirv-cross-c-shared.so,libshaderconv.so")
                put("POJAV_RENDERER", "opengles3")
            }.run {
                var env = ""
                forEach { (key, value) ->
                    env += "$key=$value:"
                }
                env.dropLast(1)
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.documentfile)
    implementation(libs.gson)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.google.material)
    implementation(project(":MobileGlues"))
}
