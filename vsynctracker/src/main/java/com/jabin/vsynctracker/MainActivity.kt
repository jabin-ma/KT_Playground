package com.jabin.vsynctracker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        checkOverlayPermission()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        checkOverlayPermission()
    }

    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "应用没有显示悬浮窗权限，请授权" , Toast.LENGTH_SHORT).show()
            startActivityForResult(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse(
                        "package:$packageName"
                    )
                ), 0
            )
        } else {
            val intent = Intent(this, MyService::class.java)
            startForegroundService(intent)
        }
    }
}