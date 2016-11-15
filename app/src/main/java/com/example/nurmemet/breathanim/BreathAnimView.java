package com.example.nurmemet.breathanim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nurmemet on 2016/11/10.
 */

public class BreathAnimView extends SurfaceView implements SurfaceHolder.Callback {
    private Paint mPaint;
    private int mColor = Color.RED;
    private float mRadius = 250.0f;
    private List<PointF> mCenterList = new ArrayList<>();
    private List<CircleDrawer> mCircleList = new ArrayList<>();

    private DrawThread mDrawThread;




    public BreathAnimView(Context context) {
        super(context);
        init();
    }

    public BreathAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BreathAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);
        mPaint.setAlpha(80);
        PointF p = null;

        mCircleList.add(new CircleDrawer(0, 0, mRadius, mColor, mPaint));
        mCircleList.add(new CircleDrawer(2 * mRadius - 50, mRadius - 50, mRadius, mColor, mPaint));
        mCircleList.add(new CircleDrawer(1080 - mRadius, mRadius - 80, mRadius, mColor, mPaint));
        mCircleList.add(new CircleDrawer(1080 - mRadius - 60, 2 * mRadius, mRadius, mColor, mPaint));
        mCircleList.add(new CircleDrawer(mRadius, 600, mRadius, mColor, mPaint));
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        mDrawThread = new DrawThread(mCircleList);
        synchronized (mDrawThread){
            mDrawThread.start();
        }


    }

    private PointF getRandomCircleCenter() {
        PointF p = new PointF();
        int sed = 1000;
        ;
        p.x = (float) (1 + Math.random() * (sed - 1 + 1));
        p.y = (float) (1 + Math.random() * (sed - 1 + 1));
        return p;
    }


    @Override
    public void surfaceCreated(final SurfaceHolder surfaceHolder) {
        System.out.println("surface created");
        synchronized (mDrawThread){
            mDrawThread.mSurfaceHolder = surfaceHolder;
            mDrawThread.mPause=false;
            mDrawThread.notify();
        }


    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        synchronized (mDrawThread){
            mDrawThread.mPause=true;
            mDrawThread.notify();
        }
        System.out.println("surface destroyed");
    }


    public void onDestroy() {
        synchronized (mDrawThread){
            mDrawThread.mQuite=true;
            mDrawThread.notify();
        }

    }

    public void onResume() {
        synchronized (mDrawThread){
            mDrawThread.mPause=false;
            mDrawThread.notify();
        }

    }

    public void onPause() {
        synchronized (mDrawThread){
            mDrawThread.mPause=true;
            mDrawThread.notify();
        }

    }

    static class DrawThread extends Thread {

        boolean mQuite = false;
        boolean mPause = true;
        SurfaceHolder mSurfaceHolder;
        List<CircleDrawer> mList;

        public DrawThread(List<CircleDrawer> mList) {
            this.mList = mList;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (this){
                    if (mQuite) {
                        System.out.println("thread end");
                        return;
                    }
                    if (!mPause&&mSurfaceHolder!=null){
                        Canvas canvas = mSurfaceHolder.lockCanvas(null);
                        drawCircle(canvas);
                        mSurfaceHolder.unlockCanvasAndPost(canvas);

                    }else{
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }

        private void drawCircle(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            for (int i = 0; i < mList.size(); i++) {
                CircleDrawer circle = mList.get(i);
                mList.get(i).update(canvas);
            }
        }
    }


}
