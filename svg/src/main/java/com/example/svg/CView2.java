package com.example.svg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;

public class CView2 extends View {


    String TAG="Cview2";
    Paint mBezierPaint = new Paint();

    public CView2(Context context) {
        super(context);
        initPaint();
    }

    public CView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    void initPaint() {
        mBezierPaint.setStrokeWidth(1);
        mBezierPaint.setColor(Color.RED);
    }

    private AnimatedPath edgePath = new AnimatedPath(24,200,
            "M8.74228e-06 100L1.74846e-05 -2.09815e-06C11.5 77 23.5 73 24 100C23.5 127 10.5 124 0 200L8.74228e-06 100Z",
            "M8.74228e-06 100L1.74846e-05 -2.09815e-06C11.5 77 23.5 73 24 100C23.5 127 10.5 124 0 200L8.74228e-06 100Z");


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
/*        float width = h * (viewportWidth/viewportHeight);

        mFinalPathMatrix.setScale( width /viewportWidth,h/viewportHeight);
        mFinalPathMatrix.postRotate(180,w*0.5f,h*0.5f);*/
        edgePath.setDisplaySize(w,h);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawPath(edgePath.animatedPath(0.5f), mBezierPaint);
    }
}
