package com.example.svg

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.PathParser.PathDataNode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(CView2(this))
    }

    fun setPathData(obj:PathDataNode){
        Log.d("MJP","$obj")
    }
}