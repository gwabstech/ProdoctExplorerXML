package com.gwabs.prodoctexplorerxml

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProductExplorerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // You can perform any app-level initialization here
    }
}
