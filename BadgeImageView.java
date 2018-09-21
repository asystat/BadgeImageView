package com.mobiag.awto.utils;

/**
 * Created by SBreit
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class BadgeImageView extends android.support.v7.widget.AppCompatImageView {

/* private class attributes. Feel free to tweak them to fit your project */
    private int mValue = 10;
    private boolean mShow = true;
    private int mContainerColor = Color.RED;
    private int mTextColor = Color.WHITE;
    private int mRadius = 0; // <--- don't worry, this gets calculated on draw
    private int mTextSize = 12;
    private int mTypeface = Typeface.BOLD;
    private float mScale;
    private Paint mPaint;
    private Context mContext;

    public BadgeImageView(Context context) {
        super(context);
        mContext = context;
    }

    public BadgeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public BadgeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    private Paint getPaint(){
        if(mPaint == null){
                mPaint = new Paint();
                Resources resources = mContext.getResources();
                mScale = resources.getDisplayMetrics().density;
                Typeface plain = mPaint.getTypeface();
                Typeface typeface = Typeface.create(plain, mTypeface);
                mPaint.setTypeface(typeface);
                mPaint.setTextSize(mTextSize*mScale);
            }
        return mPaint;
    }

    public void reset(){
        mValue = 0;
        mTypeface = Typeface.BOLD;
        mContainerColor = Color.WHITE;
        mTextSize = 12;
        mTextColor = Color.parseColor("lime");
        mShow = true;
    }

    public void setBadgeValue(int badgeValue) {
        //Don't show badge if value is less than one
        if(badgeValue < 1)
            mShow = false;
        mValue = badgeValue;
        invalidate();
    }

    public int getBadgeValue() {
        return mValue;
    }

    public void showBadge(boolean showBadge){
        mShow = showBadge;
        invalidate();
    }

    public void setBadgeColor(int badgeColor) {
        mContainerColor = badgeColor;
        invalidate();
    }

    public int getBadgeColor() {
        return mContainerColor;
    }

    public void setBadgeTextColor(int badgeTextColor) {
        mTextColor = badgeTextColor;
        invalidate();
    }

    public int getBadgeTextColor() {
        return mTextColor;
    }

    public void setBadgeTextSize(int badgeTextSize) {
        mTextSize = badgeTextSize;
        mRadius = badgeTextSize*2/3;
        invalidate();
    }

    public int getBadgeTextSize() {
        return mTextSize;
    }

    public int getBadgeRadius() {
        return mRadius;
    }

    public void setTypeface(int typeface) {
        mTypeface = typeface;
        invalidate();
    }


    public int getTypeface() {
        return mTypeface;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(mShow){
            getPaint();
            float pivotX = getPivotX();
            float pivotY = getPivotY();
            int viewHeight = getMeasuredHeight();
            int viewWidth = getMeasuredWidth();
            mRadius = (mTextSize * 2) / 3;
            float textWidth;

            textWidth = mPaint.measureText(mValue + "");

            if (textWidth > mRadius * 2 * mScale) {
                mRadius = (int) ((textWidth * 2 / 3) / mScale);
            }
            float x = pivotX + viewWidth / 2 - mRadius * mScale;
            float y = pivotY - viewHeight / 2 + mRadius * mScale;
            mPaint.setColor(mContainerColor);
            canvas.drawCircle(x, y, mRadius * mScale, mPaint);
            mPaint.setColor(mTextColor);

            canvas.drawText(mValue + "", x - textWidth / 2,
                    y + mTextSize * mScale / 3, mPaint);
        }
    }
}
