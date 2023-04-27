package com.example.svg;

import android.graphics.Matrix;
import android.graphics.Path;

import androidx.core.graphics.PathParser;

public class AnimatedPath {


    float displayWidth, displayHeight;
    float viewportWidth, viewportHeight;

    private final Matrix mPostMatrix = new Matrix();

    PathParser.PathDataNode[] mStartPath;
    PathParser.PathDataNode[] mEndPath;
    PathParser.PathDataNode[] mCurrentPathData;
    Path mFinalPath = new Path();

    public AnimatedPath(float viewportWidth, float viewportHeight, String startPathData, String endPathData) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.mStartPath = PathParser.createNodesFromPathData(startPathData);
        this.mEndPath = PathParser.createNodesFromPathData(endPathData);
        this.mCurrentPathData = PathParser.deepCopyNodes(mStartPath);
    }


    public void setDisplaySize(float displayWidth, float displayHeight) {
        this.displayHeight = displayHeight;
        this.displayWidth = displayWidth;
        updateMatrix();
    }

    boolean adaptiveWidth = true;
    private void updateMatrix() {
        mPostMatrix.reset();
        float width = adaptiveWidth?displayHeight * (viewportWidth/viewportHeight):displayWidth;
        mPostMatrix.postScale(width / viewportWidth, displayHeight / viewportHeight);
        mPostMatrix.postRotate(10, displayWidth * 0.5f, displayHeight * 0.5f);
    }

    public Path animatedPath(float fraction) {
        // reset path
        mFinalPath.reset();

        // Calculate the next pathData
        PathParser.interpolatePathDataNodes(mCurrentPathData, mStartPath, mEndPath, fraction);

        // convert to path.
        PathParser.PathDataNode.nodesToPath(mCurrentPathData, mFinalPath);

        // scale and rotate
        mFinalPath.transform(mPostMatrix);

        // finished
        return mFinalPath;
    }
}
