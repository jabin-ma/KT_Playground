package com.example.svg;

import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;

public class CView2 extends View {


    String TAG="Cview2";
    Paint mBezierPaint = new Paint();
    float fraction = 0f;
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);

    public CView2(Context context) {
        super(context);
        initPaint();

    }


    void setFraction(float fraction){
        this.fraction = fraction;
        invalidate();
    }

    public CView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    void initPaint() {
        mBezierPaint.setAntiAlias(true);
        mBezierPaint.setStrokeWidth(1);
        mBezierPaint.setColor(Color.WHITE);
        valueAnimator.setDuration(10000);
        valueAnimator.addUpdateListener(it->setFraction(it.getAnimatedFraction()));
    }

    private final AnimatedPath edgePath = new AnimatedPath(24,200,
            "M8.74228e-06 100L1.74846e-05 -2.09815e-06C0 77 0 73 0 100C0 127 0 124 0 200L8.74228e-06 100Z",
            "M8.74228e-06 100L1.74846e-05 -2.09815e-06C11.5 77 23.5 73 24 100C23.5 127 10.5 124 0 200L8.74228e-06 100Z");


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        edgePath.setDisplaySize(w,h);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        Log.d(TAG, "onDraw: f:"+fraction);
        canvas.drawPath(edgePath.animatedPath(fraction), mBezierPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return true;
    }
}
