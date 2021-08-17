package com.mimarketplace.buildsrc

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.0"
    const val junit = "junit:junit:4.13"

    const val exoplayer = "com.google.android.exoplayer:exoplayer:2.11.8"

    object SquareUp {
        private const val okHttpVersion = "4.9.1"
        private const val moshiVersion = "1.12.0"

        const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$moshiVersion"
        const val moshiAdapter = "com.squareup.moshi:moshi-adapters:$moshiVersion"
    }

    object DaggerHilt {
        private const val hiltVersion = "2.38.1"
        private const val hiltViewModelVersion = "1.0.0"
        private const val hiltViewModelVersion_ = "1.0.0-alpha03"

        const val hiltAndroidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
        const val hiltCompiler = "com.google.dagger:hilt-compiler:$hiltVersion"

        const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltAndroidTest = "com.google.dagger:hilt-android-testing:$hiltVersion"
    }

    object Accompanist {
        private const val version = "0.2.1"
        const val coil = "dev.chrisbanes.accompanist:accompanist-coil:$version"
    }

    object Kotlin {
        private const val version = "1.5.21"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.5.1"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.3.1"
        const val appcompatResources = "androidx.appcompat:appcompat-resources:1.3.1"
        const val coreKtx = "androidx.core:core-ktx:1.6.0"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.0"
        const val coordinatorlayout = "androidx.coordinatorlayout:coordinatorlayout:1.1.0"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.0-beta01"
        const val vectordrawable = "androidx.vectordrawable:vectordrawable:1.0.1"
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0-alpha01"
        const val extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0-alpha01"
        const val legacy = "androidx.legacy:legacy-support-v4:1.0.0"
        const val viewpager = "androidx.viewpager2:viewpager2:1.0.0-beta02"

        const val cardview = "androidx.cardview:cardview:1.0.0"



        object Test {
            private const val version = "1.2.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2-rc01"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        }
    }

    object Material {
        const val cardview = "com.android.support:cardview-v7:30.0.0"
        const val material = "com.google.android.material:material:1.4.0"

    }


    object Rx {
        const val rxjava2kt = "io.reactivex.rxjava2:rxkotlin:2.3.0"
        const val rxjava2and = "io.reactivex.rxjava2:rxandroid:2.1.1"
    }

    object Work {
        private const val version = "2.4.0"

        const val workRuntime = "androidx.work:work-runtime-ktx:$version"

    }
}