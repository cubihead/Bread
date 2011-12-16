package com.beecub.games.bread;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.beecub.games.bread.model.Balloon;
import com.beecub.games.bread.model.Bar;
import com.beecub.games.bread.model.Bread;

public class MainGamePanel extends SurfaceView implements
        SurfaceHolder.Callback {

    private static final String TAG = "beecub";
    
    private MainThread mThread;
    public static Resources mResources;
    
    public static int mCanvasWidth;
    public static int mCanvasHeight;
    
    private static Bitmap mBackground;
    private static Bitmap mBackgroundClouds;
    private static float mBackgroundCloudsOffset = 0.0f;
    
    private static Drawable mMoney;
    
    private static Bar mAgeBar;
    
    public MainGamePanel(Context context, AttributeSet attributes) {
        super(context);
        getHolder().addCallback(this);
        
        mResources = context.getResources();
        
        mThread = new MainThread(getHolder(), this);
        BreadActivity.mBread = new Bread(mResources, 2);
        
        mBackground = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.background_001);
        mBackgroundClouds = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.background_001_clouds);
        
        BreadActivity.mBalloon = new Balloon(mResources, 1, -1000, -1000);
        
        mMoney = mResources.getDrawable(R.drawable.icon_face_plain);

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
        mBackground = BitmapFactory.decodeResource(mResources, R.drawable.background_001);
        mBackground = Bitmap.createScaledBitmap(mBackground, mCanvasWidth, mCanvasHeight, true);
        
        mBackgroundClouds = BitmapFactory.decodeResource(mResources, R.drawable.background_001_clouds);
        mBackgroundClouds = Bitmap.createScaledBitmap(mBackgroundClouds, mCanvasWidth, mCanvasHeight, true);
        
        BreadActivity.mBread.updateBounds();
        BreadActivity.mBalloon = new Balloon(mResources, 1, BreadActivity.mBread.getRight(), BreadActivity.mBread.getTop());
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
            handleTouch((int)event.getX(), (int)event.getY());
        } if (event.getAction() == MotionEvent.ACTION_MOVE) {
            
        } if (event.getAction() == MotionEvent.ACTION_UP) {            
            Log.d(TAG, "Coords Up: x=" + event.getX() + ",y=" + event.getY());
        }
        return true;
    }
    
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        
        drawBackground(canvas);
        BreadActivity.mBread.onDraw(canvas);
        
        mAgeBar = new Bar(mResources, true, 43, mResources.getDrawable(R.drawable.icon_face_plain));
        mAgeBar.setPercent(BreadActivity.mAge);
        mAgeBar.onDraw(canvas);
        drawMoney(canvas);
        
        BreadActivity.mBalloon.onDraw(canvas);
    }
    
    private void drawMoney(Canvas canvas) {
        mMoney.setBounds(15, 55, 15 + 20, 55 + 20);
        mMoney.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Style.FILL);
        paint.setTextSize(20);
        //paint.setTypeface(PlanetActivity.mTypeface);
        paint.setAntiAlias(true);
        canvas.drawText(mResources.getString(R.string.money) + ": " + (int)BreadActivity.mMoney, 40, 75, paint);
    }
    
    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(mBackground, 0, 0, null);
        mBackgroundCloudsOffset -= 0.1;
        if (mBackgroundCloudsOffset < 0)
          mBackgroundCloudsOffset += mBackgroundClouds.getWidth();
        canvas.save();
        canvas.translate(mBackgroundCloudsOffset, 0);
        canvas.drawBitmap(mBackgroundClouds, 0, 0, null);
        canvas.restore();
        canvas.save();
        canvas.translate(mBackgroundCloudsOffset - mBackgroundClouds.getWidth(), 0);
        canvas.drawBitmap(mBackgroundClouds, 0, 0, null);
        canvas.restore();
    }
    
    private void handleTouch(int x, int y) {
        BreadActivity.mBalloon.checkTouch(x, y);        
    }
}
