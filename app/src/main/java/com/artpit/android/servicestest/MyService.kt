package com.artpit.android.servicestest

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService() : Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    companion object {
        private const val EXTRA_START = "start"
        fun newIntent(context: Context, start: Int): Intent {
            return Intent(context, MyService::class.java).apply {
                putExtra(EXTRA_START, start)
            }
        }
    }
    override fun onCreate() {
        super.onCreate()
        log("OnCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val start: Int = intent?.getIntExtra(EXTRA_START, 1) ?: 0
        log("onStartCommand")

        coroutineScope.launch {
            for (i in start ..< start + 100) {
                delay(1000)
                log("Timer $i")
            }
        }

        //return super.onStartCommand(intent, flags, startId)
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyService: $message")
    }
}