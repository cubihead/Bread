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
            // try locking the canvas for exclusive pixel editing
            // in the surface
            try {
                canvas = this.mSurfaceHolder.lockCanvas();
                synchronized (mSurfaceHolder) {
                    // update game state 
                    this.mGamePanel.update();
                    // render state to the screen
                    // draws the canvas on the panel
                    this.mGamePanel.draw(canvas);               
                }
            } finally {
                // in case of an exception the surface is not left in 
                // an inconsistent state
                if (canvas != null) {
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }   // end finally
        }
    }
    
}
