package com.example.svg;

import android.graphics.Path;
import android.graphics.PointF;

import androidx.core.graphics.PathParser;
import androidx.core.graphics.PathParser.PathDataNode;


class MixedPath {
        private PointF[] mPathPoints;
        private PathDataNode[] mPathData;
        private Path mPath;

        public MixedPath(PointF[] mPathPoints) {
            this.mPathPoints = mPathPoints;
        }


        public static PointF[] makeEdgePoint(int offsetX, int width, int height) {
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


        public static MixedPath createEdgePath(int offsetX, int width, int height){
            return new MixedPath(makeEdgePoint(offsetX,width,height));
        }

        String pathDataStr(){
            return "M" + mPathPoints[0].x + ',' + mPathPoints[0].y +
                    'C' +
                    mPathPoints[1].x + ',' + mPathPoints[1].y + ' ' +
                    mPathPoints[2].x + ',' + mPathPoints[2].y + ' ' +
                    mPathPoints[3].x + ',' + mPathPoints[3].y +
                    'C' +
                    mPathPoints[4].x + ',' + mPathPoints[4].y + ' ' +
                    mPathPoints[5].x + ',' + mPathPoints[5].y + ' ' +
                    mPathPoints[6].x + ',' + mPathPoints[6].y;
        }

        PointF[] pointF(){
            return mPathPoints;
        }

        PathDataNode[] pathData(){
            if (mPathData == null ){
                mPathData = PathParser.createNodesFromPathData(pathDataStr());
            }
            return mPathData;
        }

        Path path(){
            if (mPath == null){
                mPath = PathParser.createPathFromPathData(pathDataStr());
            }
            return mPath;
        }

        PathDataNode[] deepCopy(){
            return PathParser.deepCopyNodes(pathData());
        }
    }