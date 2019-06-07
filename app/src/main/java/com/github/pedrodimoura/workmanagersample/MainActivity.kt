package com.github.pedrodimoura.workmanagersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.github.pedrodimoura.workmanagersample.work.MyWorker
import com.github.pedrodimoura.workmanagersample.work.chain.WorkerA
import com.github.pedrodimoura.workmanagersample.work.chain.WorkerB
import com.github.pedrodimoura.workmanagersample.work.chain.WorkerC
import com.github.pedrodimoura.workmanagersample.work.chain.WorkerD
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val workManager = WorkManager.getInstance(applicationContext)

        buttonOneTimeWork.setOnClickListener {
            val workRequest =
                OneTimeWorkRequestBuilder<MyWorker>()
                    .addTag(MyWorker.TAG)
                    .build()

            workManager.enqueue(workRequest)
        }

        buttonOneTimeWorkWithConstraints.setOnClickListener {

            val constraints =
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

            val workRequest =
                    OneTimeWorkRequestBuilder<MyWorker>()
                        .addTag(MyWorker.TAG)
                        .setConstraints(constraints)
                        .setInitialDelay(3, TimeUnit.SECONDS)
                        .build()

            workManager.enqueue(workRequest)
        }

        buttonPeriodicWork.setOnClickListener {
            val workRequest =
                PeriodicWorkRequestBuilder<MyWorker>(
                    30, TimeUnit.SECONDS,
                    15, TimeUnit.SECONDS)
                    .addTag(MyWorker.TAG)
                    .build()

            workManager.enqueue(workRequest)
        }

        buttonPeriodicWorkWithConstraints.setOnClickListener {

            val constraints = Constraints.Builder().setRequiresCharging(true).build()

            val workRequest =
                PeriodicWorkRequestBuilder<MyWorker>(
                    30, TimeUnit.SECONDS,
                    15, TimeUnit.SECONDS)
                    .addTag(MyWorker.TAG)
                    .setConstraints(constraints)
                    .build()

            workManager.enqueue(workRequest)
        }

        buttonChainedWork.setOnClickListener {
            val workRequestA = OneTimeWorkRequestBuilder<WorkerA>().build()
            val workRequestB = OneTimeWorkRequestBuilder<WorkerB>().build()
            val workRequestC = OneTimeWorkRequestBuilder<WorkerC>().build()
            val workRequestD = OneTimeWorkRequestBuilder<WorkerD>().build()

            workManager.beginWith(arrayListOf(workRequestA, workRequestB))
                .then(workRequestC)
                .then(workRequestD)
                .enqueue()
                .state.observe(this@MainActivity, Observer {
                    Timber.d("Operation State: $it")
            })
        }
    }

}