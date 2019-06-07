package com.github.pedrodimoura.workmanagersample.work.chain

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

class WorkerC(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        Timber.d("WorkerA")
        return Result.success()
    }

}