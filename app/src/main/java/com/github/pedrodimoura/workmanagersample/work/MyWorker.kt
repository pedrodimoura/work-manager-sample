package com.github.pedrodimoura.workmanagersample.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

class MyWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        const val TAG = "one_time_worker_tag"
    }

    override fun doWork(): Result {
        return try {
            for (x in 1..10) { Timber.d("Next: $x") }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

}