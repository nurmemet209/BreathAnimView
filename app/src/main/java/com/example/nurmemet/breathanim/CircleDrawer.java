package com.example.nurmemet.breathanim;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by nurmemet on 2016/11/10.
 */

public class CircleDrawer {
    public float mCx;
    public float mCy;
    public float mRadius;
    private int mColor;
    private Paint mPaint;
    private float mDelX = 10F;
    private float mDelY = 10F;

    public CircleDrawer(float mCx, float mCy, float mRadius, int mColor, Paint mPaint) {
        this.mCx = mCx;
        this.mCy = mCy;
        this.mRadius = mRadius;
        this.mColor = mColor;
        this.mPaint = mPaint;

    }

    public void update(Canvas canvas) {


        float x= (float) (1+Math.random()*(5-1+1))+mCx;
        float y=(float) (1+Math.random()*(5-1+1))+mCy;
        canvas.drawCircle(x, y, mRadius, mPaint);
    }


    private void resetCenter() {



    }

}
