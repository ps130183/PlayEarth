package com.km.rmbank.customview.step;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.km.rmbank.R;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengSong on 18/6/29.
 * 步骤指示器
 * 目前只可以水平方向显示，有待升级
 */

public class StepView extends View {

    private List<StepContent> stepContentList;

    //当前圆点
    private int curStep = 0;
    //圆的半径
    private int stepDotRadius;

    private int stepSpace;
    private int stepTextSize;
    private int stepTextColor;

    private int stepIntroTextSize;
    private int stepCheckColor;
    private int stepNotCheckColor;

    private Paint checkPaint;
    private Paint unCheckPaint;
    private Paint stepNumPaint;

    private int orientation;

    private float linePercent = 100;
    private boolean isNext = true;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StepView);
        stepDotRadius = ta.getDimensionPixelOffset(R.styleable.StepView_step_dot_radius,dp2px(15));
        stepSpace = ta.getDimensionPixelOffset(R.styleable.StepView_step_space,dp2px(10));
        stepTextSize = ta.getDimensionPixelOffset(R.styleable.StepView_step_text_size,sp2px(14));
        stepIntroTextSize = ta.getDimensionPixelOffset(R.styleable.StepView_step_intro_text_size,sp2px(14));
        stepTextColor = ta.getColor(R.styleable.StepView_step_text_color,Color.WHITE);
        stepCheckColor = ta.getColor(R.styleable.StepView_step_check_color,0xff3285ff);
        stepNotCheckColor = ta.getColor(R.styleable.StepView_step_not_check_color,0xff8b8b8b);

        stepContentList = new ArrayList<>();
        stepContentList.add(new StepContent("1","第一步"));
        stepContentList.add(new StepContent("2","第二步"));
        stepContentList.add(new StepContent("3","第三步"));
        initPaint();
    }


    private void initPaint() {
        checkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        checkPaint.setColor(stepCheckColor);
        checkPaint.setTextSize(stepIntroTextSize);
        checkPaint.setTextAlign(Paint.Align.CENTER);
        checkPaint.setStrokeWidth(dp2px(1));

        unCheckPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        unCheckPaint.setColor(stepNotCheckColor);
        unCheckPaint.setTextSize(stepIntroTextSize);
        unCheckPaint.setTextAlign(Paint.Align.CENTER);
        unCheckPaint.setStrokeWidth(dp2px(1));

        stepNumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        stepNumPaint.setColor(stepTextColor);
        stepNumPaint.setTextSize(stepTextSize);
        stepNumPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int defaultHeight = stepDotRadius * 2 + stepSpace + stepIntroTextSize;

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(0, defaultHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(0, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, defaultHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int left = getLeft();
        int top = getTop();
        int right = getRight();
        int bottom = getBottom();

        int width = right - left;
        int height = bottom - top;

        //步骤总数
        int totalDots = stepContentList.size();
        //根据步骤数 将宽度值分成相等的块
        int blockWidth = width / totalDots;

        //圆的坐标中心
        int[][] dotXY = new int[totalDots][2];

        //画圆
        for (int i = 0; i < totalDots; i++) {
            //计算圆的中心点
            dotXY[i][0] = blockWidth * i + blockWidth / 2;
            dotXY[i][1] = stepDotRadius;
            if (i < curStep) {
                canvas.drawCircle(dotXY[i][0], dotXY[i][1], stepDotRadius, checkPaint);
            } else if (i == curStep){
                if (isNext && linePercent == 100){//下一步
                    canvas.drawCircle(dotXY[i][0], dotXY[i][1], stepDotRadius, checkPaint);
                } else if (!isNext){//上一步
                    canvas.drawCircle(dotXY[i][0], dotXY[i][1], stepDotRadius, checkPaint);
                } else {
                    canvas.drawCircle(dotXY[i][0], dotXY[i][1], stepDotRadius, unCheckPaint);
                }

            }else {
                canvas.drawCircle(dotXY[i][0], dotXY[i][1], stepDotRadius, unCheckPaint);
            }

        }

        if (totalDots > 1) {
            int line = dotXY[1][0] - dotXY[0][0] - stepDotRadius;
            //画线
            for (int i = 0; i < totalDots - 1; i++) {
                int startX = dotXY[i][0] + stepDotRadius;
                int startY = dotXY[i][1];

                int endX = dotXY[i + 1][0] - stepDotRadius;
                int endY = dotXY[i + 1][1];
                canvas.drawLine(startX, startY, endX, endY, unCheckPaint);

                int curLine = isNext ? curStep - 1 : curStep;
                if (i < curLine) {
                    canvas.drawLine(startX, startY, endX, endY, checkPaint);
                } else if (i == curLine){
                    endX = startX + (int) (line / 100 * linePercent);
                    canvas.drawLine(startX, startY, endX, endY, checkPaint);
                }

            }
        }
        //画文字
        for (int i = 0; i < totalDots; i++) {
            StepContent stepContent = stepContentList.get(i);
            int top1 = 0;
            int bottom1 = 0;
            if (!TextUtils.isEmpty(stepContent.getStepName())) {
                Paint.FontMetrics fontMetrics = stepNumPaint.getFontMetrics();
                top1 = (int) fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
                bottom1 = (int) fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

                int baseLineY = dotXY[i][1] - top1 / 2 - bottom1 / 2;//基线中间点的y轴计算公式
                canvas.drawText(String.valueOf(i + 1), dotXY[i][0], baseLineY, stepNumPaint);
            }


            int introTop = dotXY[i][1] + stepDotRadius + stepSpace;
            int introCenterY = introTop + stepIntroTextSize / 2;
            Paint.FontMetrics introMetrics = checkPaint.getFontMetrics();
            top1 = (int) introMetrics.top;
            bottom1 = (int) introMetrics.bottom;
            int baselineY2 = introCenterY - top1 / 2 - bottom1 / 2;
            if (i <= curStep) {
                canvas.drawText(stepContent.getStepContent(), dotXY[i][0], baselineY2, checkPaint);
            } else {
                canvas.drawText(stepContent.getStepContent(), dotXY[i][0], baselineY2, unCheckPaint);
            }

        }

    }


    public void addStepContent(List<StepContent> stepContents) {
        stepContentList.clear();
        stepContentList.addAll(stepContents);
        postInvalidate();
    }

    public void setCurStep(int step) {
        isNext = getCurStep() < step;
        this.curStep = step;
        startAnimator();
    }

    public int getCurStep() {
        return curStep;
    }

    /**
     * 下一步
     */
    public void nextStep() {
        if (curStep + 1 < stepContentList.size()) {
            setCurStep(curStep + 1);
        }
    }

    /**
     * 上一步
     */
    public void preStep() {
        if (curStep - 1 >= 0) {
            setCurStep(curStep - 1);
        }
    }

    /**
     * 开始动画
     */
    private void startAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (isNext){
                    linePercent = (float) animation.getAnimatedValue();
                } else {
                    linePercent = 100 - (float) animation.getAnimatedValue();
                }
                postInvalidate();
            }
        });
        animator.start();
    }

    private int dp2px(final float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int sp2px(final float spValue) {
        final float fontScale = this.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
