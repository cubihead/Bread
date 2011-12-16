package com.beecub.games.bread.model;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.beecub.games.bread.BreadActivity;
import com.beecub.games.bread.R;

public class Balloon {
    
    private Drawable mDrawable;
    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;
    private boolean mFinished = false;
    private boolean mFadeOut = false;
    
    public Balloon(Resources mResources, int type, int breadRight, int breadTop) {
        
        if(type == 1) {
            mDrawable = mResources.getDrawable(R.drawable.balloon_love);
        }
        
        mLeft = breadRight - mDrawable.getIntrinsicWidth();
        mTop = breadTop - mDrawable.getIntrinsicWidth();
        mRight = breadRight;
        mBottom = breadTop;
    }
    
    public void onDraw(Canvas canvas) {
        if(mFadeOut) {
            fadeOut();
        }
        mDrawable.setBounds(mLeft, mTop, mRight, mBottom);
        mDrawable.draw(canvas);
    }
    
    private void fadeOut() {
        mLeft += 2;
        mRight -= 2;
        mTop += 2;
        mBottom -= 2;
        
        if(mLeft >= mRight || mTop >= mBottom) {
            mFadeOut = false;
            mFinished = true;
        }
    }
    
    public boolean finished() {
        return mFinished;
    }
    
    private void finish() {
        mFadeOut = true;
        BreadActivity.fulfillNeed();
    }
    
    public void checkTouch(int x, int y) {
        if(!mFadeOut) {
            if(x >= mLeft && x <= mRight) {
                if(y >= mTop && y <= mBottom) {
                    finish();
                }
            }
        }
    }
}
