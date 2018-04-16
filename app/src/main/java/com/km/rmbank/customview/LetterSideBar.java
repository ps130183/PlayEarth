package com.km.rmbank.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.km.rmbank.R;

import java.util.Arrays;

/**
 * Created by PengSong on 18/3/29.
 */

public class LetterSideBar extends View {

    /*绘制的列表导航字母*/
    private String mLetters[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    /*字母画笔*/
    private Paint wordsPaint;
    /*字母背景画笔*/
    private Paint bgPaint;
    /*每一个字母的宽度*/
    private int itemWidth;
    /*每一个字母的高度*/
    private int itemHeight;
    /*手指按下的字母索引*/
    private int touchIndex = 0;
    /*手指按下的字母改变接口*/
    private SideBarTouchListener mTouchListener;

    private int mWidth;

    private int mTextSize;
    private int selectTextColor;
    private int unSelectTextColor;
    private int selectTextBackgroundColor;

    public LetterSideBar(Context context) {
        this(context,null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterSideBar);
        selectTextColor = typedArray.getColor(R.styleable.LetterSideBar_selectTextColor,Color.RED);
        unSelectTextColor = typedArray.getColor(R.styleable.LetterSideBar_unSelectTextColor,Color.BLACK);
        selectTextBackgroundColor = typedArray.getColor(R.styleable.LetterSideBar_selectTextBackgroundColor,Color.BLUE);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.LetterSideBar_textSize,30);
        typedArray.recycle();
        mWidth = 100;

        wordsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wordsPaint.setTextSize(mTextSize);
        wordsPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(selectTextBackgroundColor);

    }

    //得到画布的宽度和每一个字母所占的高度
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mWidth,heightSpecSize);
        } else if (widthSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mWidth,heightMeasureSpec);
        } else if (heightMeasureSpec == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        }



        itemWidth = getMeasuredWidth();
        //使得边距好看一些
        int height = getMeasuredHeight() - 10;
        itemHeight = height / 27;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mLetters.length; i++) {
            String letter = mLetters[i];
            //判断是不是我们按下的当前字母
            if (touchIndex == i) {
                //绘制文字圆形背景
                canvas.drawCircle(itemWidth / 2, itemHeight / 2 + i * itemHeight, itemHeight / 2, bgPaint);
                wordsPaint.setColor(selectTextColor);
                wordsPaint.setTextSize(mTextSize + 15);
            } else {
                wordsPaint.setColor(unSelectTextColor);
                wordsPaint.setTextSize(mTextSize);
            }

            // 获取字体的宽度
            float measureTextWidth = wordsPaint.measureText(letter);
            // 获取内容的宽度
            int contentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            float x = getPaddingLeft() + (contentWidth - measureTextWidth) / 2;

            // 计算基线位置
            Paint.FontMetrics fontMetrics = wordsPaint.getFontMetrics();
            float baseLine = itemHeight / 2 + (itemHeight * i) + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
            canvas.drawText(mLetters[i],x,baseLine,wordsPaint);
        }
    }

    private boolean mCurrentIsTouch = false;

    /**
     * 当手指触摸按下的时候改变字母背景颜色
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mCurrentIsTouch = true;
                float y = event.getY();
                //关键点===获得我们按下的是那个索引(字母)
                int index = (int) (y / itemHeight);
                if (index != touchIndex)
                    touchIndex = index;
                //防止数组越界
                if (mTouchListener != null && 0 <= touchIndex && touchIndex <= mLetters.length - 1) {
                    //回调按下的字母
                    mTouchListener.onTouch(mLetters[touchIndex], mCurrentIsTouch);
                }

                break;
            case MotionEvent.ACTION_UP:
                mCurrentIsTouch = false;
                if (mTouchListener != null && 0 <= touchIndex && touchIndex <= mLetters.length - 1) {
                    //回调按下的字母
                    mTouchListener.onTouch(mLetters[touchIndex], mCurrentIsTouch);
                }
                //手指抬起,不做任何操作
                break;
        }
        invalidate();
        return true;
    }

    public void setTouchLetter(String curLetter) {
        int position = Arrays.binarySearch(mLetters,curLetter.toUpperCase());
        this.touchIndex = position;
        invalidate();
    }

    /*手指按下了哪个字母的回调接口*/
    public interface SideBarTouchListener {
        void onTouch(String letter, boolean isTouch);
    }

    /*设置手指按下字母改变监听*/
    public void setOnSideBarTouchListener(SideBarTouchListener listener) {
        mTouchListener = listener;
    }
}
