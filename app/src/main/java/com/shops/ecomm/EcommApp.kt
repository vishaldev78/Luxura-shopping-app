package com.shops.ecomm

import android.app.Application
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EcommApp : Application() {
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51TI6HYRtLqieFrm3VhkOEv4gdVkYsSUT29DeNykD2BqmKLRZ9mNAEGHc7Zq3PPocUXL6emua80RdHn0n2clDVzLe008xDLizlA"
        )
    }
}
