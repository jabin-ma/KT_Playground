package com.jabin.vsynctracker

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager


private const val CHANNEL_DEFAULT_IMPORTANCE = "default";
private const val ONGOING_NOTIFICATION_ID = 101

class MyService : Service() {

    private lateinit var floatingWindow:FloatingWindow;

    override fun onCreate() {
        createNotificationChannel()
        floatingWindow = FloatingWindow(this)
        val text = PerfettoView(this)
        text.setTextColor(Color.MAGENTA)
        text.textSize = 25f
        text.updateTime()
        floatingWindow.setContentView(text)
    }

    private fun createNotificationChannel() {
        val tracingChannel = NotificationChannel(
            CHANNEL_DEFAULT_IMPORTANCE,
            getString(R.string.notification_channel_title),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        tracingChannel.setBypassDnd(true)
        tracingChannel.enableVibration(true)
        tracingChannel.setSound(null, null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            tracingChannel.isBlockable = false
        }

        val notificationManager: NotificationManager = getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(tracingChannel)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE)
            }

        val notification: Notification = Notification.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
            .setContentTitle(getString(R.string.notification_content_title))
            .setContentText(getString(R.string.notification_content_text))
            .setSmallIcon(android.R.drawable.ic_popup_sync)
            .setContentIntent(pendingIntent)
            .setTicker("TICKER.........")
            .build()

        startForeground(ONGOING_NOTIFICATION_ID, notification)
        floatingWindow.attachWindow()
        return START_NOT_STICKY
    }


   /* fun initView(){
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        layoutParams.format = PixelFormat.RGBA_8888
        layoutParams.gravity = Gravity.START or Gravity.TOP
        layoutParams.flags =
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        if (Settings.canDrawOverlays(this)) {
            val mTextClock = PerfettoView(this)
            mTextClock.setTextColor(Color.MAGENTA)
            mTextClock.textSize = 25f
            mTextClock.updateTime()
            windowManager.addView(mTextClock, layoutParams)
            windowManager.updateViewLayout(mTextClock.rootView, layoutParams)
        }
    }*/




    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    companion object{
        const val TAG = "MyService"
    }
}