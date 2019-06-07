package com.github.pedrodimoura.workmanagersample

import android.app.Application
import androidx.work.WorkManager
import timber.log.Timber

class WorkManagerSampleApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}