package com.github.pedrodimoura.workmanagersample

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.WorkManagerTestInitHelper
import com.github.pedrodimoura.workmanagersample.work.MyWorker
import org.junit.Before
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class WorkerInstrumentedTest {

    private val context = InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setup() {
        WorkManagerTestInitHelper.initializeTestWorkManager(context)
    }

    fun testWorker() {
        val workManager = WorkManager.getInstance(context)

        val workRequest = OneTimeWorkRequestBuilder<MyWorker>().build()

        workManager.enqueue(workRequest).result.get()

        val workInfo = workManager.getWorkInfoById(workRequest.id).get()

        assert(workInfo.state == WorkInfo.State.SUCCEEDED)
    }

}
