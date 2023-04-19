package com.jabin.vsynctracker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Trace
import android.util.AttributeSet
import android.widget.TextView

class PerfettoView: TextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas?) {
        Trace.beginSection(text.toString())
        super.onDraw(canvas)
        text = System.currentTimeMillis().toString()
        Trace.endSection()
    }


    fun updateTime(){
        text = System.currentTimeMillis().toString()
    }
}