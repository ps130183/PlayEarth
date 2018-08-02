package com.km.rmbank.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.km.rmbank.R;


/**
 * Created by pengsong on 2016-10-11.
 */
public class CircleProgressView extends View {
    private static final String TAG = "CircleProgressBar";

    private int mMaxProgress = 100;

    private int mProgress = 0;

    private final int mCircleLineStrokeWidth = 8;

    private final int mTxtStrokeWidth = 2;

    // 画圆所在的距形区域
    private final RectF mRectF;

    private final Paint mPaint;

    private final Context mContext;

    private String mTxtHint1;

    private String mTxtHint2;

    private int backgroundColor;
    private int finishedColor;
    private int unfinishColor;


    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }


    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        backgroundColor = typedArray.getColor(R.styleable.CircleProgressView_backgroundColor,this.getResources().getColor(R.color.transparency_gray_50));
        finishedColor = typedArray.getColor(R.styleable.CircleProgressView_finishedColor,0xff999999);
        unfinishColor = typedArray.getColor(R.styleable.CircleProgressView_unfinishColor,0xffe9e9e9);

        mContext = context;
        mRectF = new RectF();
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = this.getWidth();
        int height = this.getHeight();

        if (width != height) {
            int min = Math.min(width, height);
            width = min;
            height = min;
        }

        // 设置画笔相关属性
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.rgb(0xe9, 0xe9, 0xe9));
//        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawColor(backgroundColor);
        mPaint.setStrokeWidth(mCircleLineStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        // 位置
        mRectF.left = mCircleLineStrokeWidth / 2; // 左上角x
        mRectF.top = mCircleLineStrokeWidth / 2; // 左上角y
        mRectF.right = width - mCircleLineStrokeWidth / 2; // 左下角x
        mRectF.bottom = height - mCircleLineStrokeWidth / 2; // 右下角y

        // 绘制圆圈，进度条背景
        canvas.drawArc(mRectF, -90, 360, false, mPaint);
        mPaint.setColor(unfinishColor);
        canvas.drawArc(mRectF, -90, ((float) mProgress / mMaxProgress) * 360, false, mPaint);

        // 绘制进度文案显示
        mPaint.setStrokeWidth(mTxtStrokeWidth);
        String text = mProgress + "%";
        int textHeight = height / 4;
        mPaint.setTextSize(textHeight);
        int textWidth = (int) mPaint.measureText(text, 0, text.length());
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + textHeight / 2, mPaint);

        if (!TextUtils.isEmpty(mTxtHint1)) {
            mPaint.setStrokeWidth(mTxtStrokeWidth);
            text = mTxtHint1;
            textHeight = height / 8;
            mPaint.setTextSize(textHeight);
            mPaint.setColor(finishedColor);
            textWidth = (int) mPaint.measureText(text, 0, text.length());
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText(text, width / 2 - textWidth / 2, height / 4 + textHeight / 2, mPaint);
        }

        if (!TextUtils.isEmpty(mTxtHint2)) {
            mPaint.setStrokeWidth(mTxtStrokeWidth);
            text = mTxtHint2;
            textHeight = height / 8;
            mPaint.setTextSize(textHeight);
            textWidth = (int) mPaint.measureText(text, 0, text.length());
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText(text, width / 2 - textWidth / 2, 3 * height / 4 + textHeight / 2, mPaint);
        }
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        if (mProgress >= mMaxProgress || mProgress <= 0){
            this.setVisibility(GONE);
        } else {
            this.setVisibility(VISIBLE);
        }
        this.invalidate();
    }

    public void setProgressNotInUiThread(int progress) {
        this.mProgress = progress;
        this.postInvalidate();
    }

    public String getmTxtHint1() {
        return mTxtHint1;
    }

    public void setmTxtHint1(String mTxtHint1) {
        this.mTxtHint1 = mTxtHint1;
    }

    public String getmTxtHint2() {
        return mTxtHint2;
    }

    public void setmTxtHint2(String mTxtHint2) {
        this.mTxtHint2 = mTxtHint2;
    }
}
