package com.beecub.games.bread.model;

import java.util.Date;
import java.util.Random;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.beecub.games.bread.MainGamePanel;
import com.beecub.games.bread.R;

public class Bread {
    
    private Drawable mDrawable;
    private Drawable mMouth;
    private Drawable mEyes;
    private Resources mResources;
    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;
    private long mMouthTime = 0;
    private long mMouthStartTime = 0;
    private int mWinkType = 0;
    private boolean mWink = false;
    
    public Bread(Resources resources, int age) {
        mResources = resources;
        mMouth = mResources.getDrawable(R.drawable.bread_face_mouth_normal);
        mEyes = mResources.getDrawable(R.drawable.bread_face_eyes_normal);
        if(age == 2) {
            mDrawable = mResources.getDrawable(R.drawable.bread);
        }
        
        updateBounds();
    }
    
    public void onDraw(Canvas canvas) {
        
        // loaf
        mDrawable.setBounds(mLeft, mTop, mRight, mBottom);
        mDrawable.draw(canvas);
        
        // mouth
        if(mMouthTime > 0) {
            if(new Date().getTime() - mMouthTime > mMouthStartTime) {
                setFace(0, 0);
            }
        }
        mMouth.setBounds(mLeft, mTop, mRight, mBottom);
        mMouth.draw(canvas);
        
        // eyes
        wink();
        mEyes.setBounds(mLeft, mTop, mRight, mBottom);
        mEyes.draw(canvas);
    }
    
    public void updateBounds() {
        mLeft = (int) (MainGamePanel.mCanvasWidth / 2 - mDrawable.getIntrinsicWidth() / 2.5);
        mRight = (int) (MainGamePanel.mCanvasWidth / 2 + mDrawable.getIntrinsicHeight() / 2.5);
        mTop = (int) (MainGamePanel.mCanvasHeight - MainGamePanel.mCanvasHeight / 2.5 - mDrawable.getIntrinsicHeight() / 2.5);
        mBottom = (int) (MainGamePanel.mCanvasHeight - MainGamePanel.mCanvasHeight / 2.5 + mDrawable.getIntrinsicHeight() / 2.5);
    }
    
    private void wink() {
        if(!mWink) {
            Random random = new Random();
            int rand = random.nextInt(50) + 1;
            if(rand == 1) {
                mWink = true;
                mWinkType = 1;
            }
        }
        if(mWink) {
            switch (mWinkType) {
                case 1:
                    mEyes = mResources.getDrawable(R.drawable.bread_face_eyes_wink_1);
                    break;
                case 2:
                    mEyes = mResources.getDrawable(R.drawable.bread_face_eyes_normal);
                    mWink = false;
                    break;
            }
            mWinkType++;
        }
        
    }
    
    public void setFace(int type, long time) {
        mMouthStartTime = new Date().getTime();
        mMouthTime = time;
        if(type == 0) {
            mMouth = mResources.getDrawable(R.drawable.bread_face_mouth_normal);
        }
        else if(type == 1) {
            mMouth = mResources.getDrawable(R.drawable.bread_face_mouth_happy);
        }
    }
    
    public int getRight() {
        return mRight;
    }
    public int getTop() {
        return mTop;
    }
}
