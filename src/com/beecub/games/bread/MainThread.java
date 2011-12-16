package com.beecub.games.bread;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    
    private static final String TAG = "beecub";
    
    private SurfaceHolder mSurfaceHolder;
    private MainGamePanel mGamePanel;
    
    private boolean mRunning;
    
    public void setRunning(boolean running) {
        this.mRunning = running;
    }
    
    public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
        super();
        this.mSurfaceHolder = surfaceHolder;
        this.mGamePanel = gamePanel;
    }
    
    @Override
    public void run() {
        Canvas canvas;
        Log.d(TAG, "Starting game loop");
        while (mRunning) {
            canvas = null;
            try {
                canvas = this.mSurfaceHolder.lockCanvas();
                synchronized (mSurfaceHolder) {
                    this.mGamePanel.draw(canvas);               
                }
            } finally {
                if (canvas != null) {
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
    
}
