package com.grechur.wanandroid.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.grechur.wanandroid.R;


/**
 * Created by zz on 2018/6/20.
 */

public class CircleLoadView extends View{
    //三个颜色的画笔
    private Paint mLeftPaint,mCenterPaint,mRightPaint;
    //自定义的颜色
    private int mLeftColor,mCenterColor,mRightColor;
    //圆半径
    private int mRadius;
    //圆距离
    private int mDistance;
    //最大距离
    private int mMaxDistance;
    //动画
    private ValueAnimator mAnimator;
    private ValueAnimator mExportAnimator;

    public CircleLoadView(Context context) {
        this(context,null);
    }

    public CircleLoadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleLoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleLoadView);
        mLeftColor = array.getColor(R.styleable.CircleLoadView_leftColor, Color.YELLOW);
        mCenterColor = array.getColor(R.styleable.CircleLoadView_centerColor,Color.BLACK);
        mRightColor = array.getColor(R.styleable.CircleLoadView_rightColor,Color.RED);
        mLeftPaint = getPaintByColor(mLeftColor);
        mCenterPaint = getPaintByColor(mCenterColor);
        mRightPaint = getPaintByColor(mRightColor);

        array.recycle();
    }

    /**
     * 通过颜色获取画笔
     * @param color
     * @return
     */
    private Paint getPaintByColor(int color){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        mRadius = width/32;
        mDistance = width/8+2*mRadius;
        mMaxDistance = width/8+2*mRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawCircle(getWidth()/2-mDistance,getHeight()/2,mRadius,mLeftPaint);
        canvas.drawCircle(getWidth()/2,getHeight()/2,mRadius,mCenterPaint);
        canvas.drawCircle(getWidth()/2+mDistance,getHeight()/2,mRadius,mRightPaint);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancleAnimal();
    }
    
    public void cancleAnimal(){
        if(mAnimator!=null&&mAnimator.isStarted()){
            mAnimator.cancel();
        }
        if(mExportAnimator!=null&&mExportAnimator.isStarted()){
            mExportAnimator.cancel();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initAnimal();

    }
    public void setDistance(int current){
        this.mDistance = current;
        invalidate();
    }

    private void initAnimal(){
        mAnimator = ObjectAnimator.ofFloat(mMaxDistance,0);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setDistance((int) value);

            }
        });
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                int temp;
                temp = mLeftColor;
                mLeftColor = mCenterColor;
                mCenterColor = mRightColor;
                mRightColor = temp;
                mLeftPaint.setColor(mLeftColor);
                mCenterPaint.setColor(mCenterColor);
                mRightPaint.setColor(mRightColor);
                if(mExportAnimator!=null){
                    mExportAnimator.start();
                }else{
                    initExportAnimal();
                }

            }
        });
        mAnimator.start();
    }

    private void initExportAnimal(){
        mExportAnimator = ObjectAnimator.ofFloat(0,mMaxDistance);
        mExportAnimator.setDuration(500);
        mExportAnimator.setInterpolator(new LinearInterpolator());
        mExportAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setDistance((int) value);
            }
        });
        mExportAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(mAnimator!=null){
                    mAnimator.start();
                }else{
                    initAnimal();
                }

            }
        });
        mExportAnimator.start();
    }

}
