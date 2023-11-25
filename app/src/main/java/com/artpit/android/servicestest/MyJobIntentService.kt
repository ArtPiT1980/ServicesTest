package com.artpit.android.servicestest

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class MyJobIntentService() : JobIntentService() {
    companion object {
        private const val PAGE = "page"
        private const val JOB_ID = 111

        fun enqueue(context: Context, page: Int) {
            enqueueWork(
                context,
                MyJobIntentService::class.java,
                JOB_ID,
                newIntent(context, page))
        }

        private fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyJobIntentService::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }

    override fun onHandleWork(intent: Intent) {
        log("onHandleIntent")
        val page = intent.getIntExtra(PAGE, 0)
        for (i in 0 ..< 5) {
            Thread.sleep(1000)
            log("Timer $i $page")
        }
    }

    override fun onCreate() {
        super.onCreate()
        log("OnCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyJobIntentService: $message")
    }
}