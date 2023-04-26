package com.example.svg;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class CView extends View {
    Paint mBezierPaint = new Paint();
    MixedPath mStartMixedPath;
    MixedPath mEndMixedPath;

    /**
     * just for debug
     */
    Paint mControlPaint = new Paint();
    Path mDrawablePath = new Path();
    private PathParser.PathDataNode[] mCurrentPathData;


    public CView(Context context) {
        super(context);
        initPaint();
    }

    public CView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    void initPaint() {
        mBezierPaint.setStrokeWidth(1);
        mBezierPaint.setColor(Color.RED);
        mControlPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mStartMixedPath = MixedPath.createEdgePath(0, 0, getMeasuredHeight());
        mEndMixedPath = MixedPath.createEdgePath(0, (int) (getMeasuredWidth() * 0.5), getMeasuredHeight());

        mCurrentPathData = mStartMixedPath.deepCopy();
    }

    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
    float index = 0f;

    @Override
    protected void onDraw(Canvas canvas) {
        PathParser.interpolatePathDataNodes(mCurrentPathData, mStartMixedPath.pathData(), mEndMixedPath.pathData(), index);
        PathParser.PathDataNode.nodesToPath(mCurrentPathData, mDrawablePath);
        canvas.drawPath(mDrawablePath, mBezierPaint);


        if (!valueAnimator.isStarted()) {
            valueAnimator.setInterpolator(new FastOutSlowInInterpolator());
            valueAnimator.setDuration(1000);
            valueAnimator.addUpdateListener(animation -> {
                        index = animation.getAnimatedFraction();
                        invalidate();
                    }
            );
            valueAnimator.start();
        }
        for (PointF pointF : mEndMixedPath.pointF()) {
            canvas.drawCircle(pointF.x, pointF.y, 10, mControlPaint);
        }
        super.onDraw(canvas);
    }


    PointF[] makeBezierPath(Path out, int offsetX, int width, int height) {
        PointF[] controlPoint = makeControlPoint(offsetX, width, height);
        out.set(PathParser.createPathFromPathData(convertBezierPathStr(controlPoint)));
        return controlPoint;
    }

    String convertBezierPathStr(PointF[] controlPoint) {
        return "M" + controlPoint[0].x + ',' + controlPoint[0].y +
                'C' +
                controlPoint[1].x + ',' + controlPoint[1].y + ' ' +
                controlPoint[2].x + ',' + controlPoint[2].y + ' ' +
                controlPoint[3].x + ',' + controlPoint[3].y +
                'C' +
                controlPoint[4].x + ',' + controlPoint[4].y + ' ' +
                controlPoint[5].x + ',' + controlPoint[5].y + ' ' +
                controlPoint[6].x + ',' + controlPoint[6].y;
    }

    PointF[] makeControlPoint(int offsetX, int width, int height) {
        final float[] step = {0, 0.191f, 0.3333f, 0.5f, 0.6666f, 0.809f, 1f};
        return new PointF[]{
                new PointF(offsetX, height * step[0]),
                new PointF(offsetX, height * step[1]),
                new PointF(width, height * step[2]),
                new PointF(width, height * step[3]),
                new PointF(width, height * step[4]),
                new PointF(offsetX, height * step[5]),
                new PointF(offsetX, height * step[6])
        };
    }


}
