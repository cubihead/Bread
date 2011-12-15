package com.beecub.games.bread;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements
        SurfaceHolder.Callback {

    private static final String TAG = "beecub";
    
    private MainThread mThread;
    public static Context mContext;
    
    public static int mCanvasWidth;
    public static int mCanvasHeight;
    
    public static Drawable mBread;
    public static Bitmap mBackground;
    
    
    public MainGamePanel(Context context, AttributeSet attributes) {
        super(context);
        getHolder().addCallback(this);
        
        mContext = context;
        
        mThread = new MainThread(getHolder(), this);
        mBread = context.getResources().getDrawable(R.drawable.bread);
        //mBackground = context.getResources().getDrawable(R.drawable.background_001);
        mBackground = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.background_001);

        setFocusable(true);
    }
    
    public MainThread getThread() {
        return mThread;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        mCanvasWidth = width;
        mCanvasHeight = height;
        
        Log.d("beecub", "surfaceChanged");
        mBackground = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.background_001);
        mBackground = Bitmap.createScaledBitmap(mBackground, mCanvasWidth, mCanvasHeight, false);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new MainThread(getHolder(), this);
        mThread.setRunning(true);
        mThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");
        mThread.setRunning(false);
        boolean retry = true;
        while (retry) {
            try {
                Log.d(TAG, "Try to join thread");
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "Coords Down: x=" + event.getX() + ",y=" + event.getY());
        } if (event.getAction() == MotionEvent.ACTION_MOVE) {
            
        } if (event.getAction() == MotionEvent.ACTION_UP) {            
            Log.d(TAG, "Coords Up: x=" + event.getX() + ",y=" + event.getY());
        }
        return true;
    }
    
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        
        canvas.drawBitmap(mBackground, 0, 0, null);
        
        int left = mCanvasWidth / 2 - mBread.getIntrinsicWidth() / 2;
        int right = mCanvasWidth / 2 + mBread.getIntrinsicHeight() / 2;
        int top = (int) (mCanvasHeight - mCanvasHeight / 2.5 - mBread.getIntrinsicHeight() / 2);
        int bottom = (int) (mCanvasHeight - mCanvasHeight / 2.5 + mBread.getIntrinsicHeight() / 2);
        mBread.setBounds(left, top, right, bottom);
        mBread.draw(canvas);
    }
    public void update() {
        
    }

}
