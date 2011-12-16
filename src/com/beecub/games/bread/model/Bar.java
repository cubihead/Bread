package com.beecub.games.bread.model;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.beecub.games.bread.MainGamePanel;
import com.beecub.games.bread.R;

public class Bar
{
    public static final int MODE_CRITICAL = 2;
    public static final int MODE_NORMAL = 0;
    public static final int MODE_WARNING = 1;
    private Drawable mBackgroundImage;
    private Drawable mYellowBar;
    private Drawable mRedBar;
    private Drawable mGreenBar;
    private int mFillWidth = 0;
    private Drawable mIconImage;
    private boolean mDoubleMarker = false;
    private Drawable mMidPoint;
    private int mMode;
    private int mOffsetTop;
    private int mOffsetLeft;
    private int mWidth = 0;

    public Bar(Resources resources, boolean doubleMarker, int paramDrawable, Drawable paramIcon)
    {
        mDoubleMarker = doubleMarker;
        mIconImage = paramIcon;
        mGreenBar = resources.getDrawable(R.drawable.bar_green);
        mYellowBar = resources.getDrawable(R.drawable.bar_yellow);
        mRedBar = resources.getDrawable(R.drawable.bar_red);
        mMidPoint = resources.getDrawable(R.drawable.mid_mark);
        mBackgroundImage = resources.getDrawable(R.drawable.bar_back);
        updateOffsets(paramDrawable);
    }

    public void onDraw(Canvas canvas)
    {
        int i = MainGamePanel.mCanvasHeight;
        int j = mOffsetLeft;
        mIconImage.setBounds(mOffsetLeft - mIconImage.getIntrinsicWidth() / 2 - 10, i - mOffsetTop - mIconImage.getIntrinsicHeight() / 2, mOffsetLeft - 10, i - mOffsetTop);
        mIconImage.draw(canvas);
        mBackgroundImage.setBounds(j, i - mOffsetTop - mBackgroundImage.getIntrinsicHeight(), j + mWidth, i - mOffsetTop);
        mBackgroundImage.draw(canvas);
        Drawable localDrawable;
        switch (mMode)
        {
            default:
                localDrawable = mYellowBar;
                break;
            case 0:
                localDrawable = mGreenBar;
                break;
            case 1:
                localDrawable = mYellowBar;
                break;
            case 2:
                localDrawable = mRedBar;
                break;
        }
        localDrawable.setBounds(j, i - mOffsetTop - localDrawable.getIntrinsicHeight(), j + mFillWidth, i - mOffsetTop);
        localDrawable.draw(canvas);
        if (mDoubleMarker)
        {
            int k = 0;
            k = j + (mWidth / 5 - mMidPoint.getIntrinsicWidth() / 2);
            mMidPoint.setBounds(k, i - mOffsetTop - mMidPoint.getIntrinsicHeight(), k + mMidPoint.getIntrinsicWidth(), i - mOffsetTop);
            mMidPoint.draw(canvas);
            k = j + (mWidth - (mWidth / 5) - mMidPoint.getIntrinsicWidth() / 2);
            mMidPoint.setBounds(k, i - mOffsetTop - mMidPoint.getIntrinsicHeight(), k + mMidPoint.getIntrinsicWidth(), i - mOffsetTop);
            mMidPoint.draw(canvas);
        }
    }

    public void setPercent(int percent)
    {
        if(percent > 100)
            mFillWidth = mWidth;
        else
            mFillWidth = (int)(percent / 100.0f * mWidth);
        
        // set mode
        if(percent < 20)
            mMode = 1;
        else if(percent >= 80)
            mMode = 2;
        else
            mMode = 0;
            
    }
    
    public void updateOffsets(int paramInt)
    {
        mOffsetTop = MainGamePanel.mCanvasHeight - paramInt;
        mWidth = (int)(0.8F * MainGamePanel.mCanvasWidth);
        mOffsetLeft = ((MainGamePanel.mCanvasWidth - mWidth) / 2);
    }
}

