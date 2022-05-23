package com.gaoyu.seekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * 拖动条
 *
 * @author Created by gaoyu on 2022/5/23 8:37
 */
public class SeekBar extends View implements ISeekBar {
    
    private SeekBarConfig mConfig;
    
    private float progress;
    
    private Paint bgPaint;
    
    private Paint pgPaint;
    
    private float headWidth;
    /**
     * 滑块的中心位置
     */
    private float sliderCenter;
    
    public SeekBar(Context context) {
        super(context);
        init();
    }
    
    public SeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public SeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public SeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    
    private void init() {
        mConfig = new SeekBarConfig(getContext());
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Style.STROKE);
        pgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pgPaint.setStyle(Style.STROKE);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        changeOriginPoint(canvas);
        drawBackground(canvas);
        drawPassArea(canvas);
        drawSlider(canvas);
        drawMoveText(canvas);
    }
    
    /**
     * 改变坐标原点
     */
    private void changeOriginPoint(Canvas canvas) {
        //画布原点移到新的坐标原点
        canvas.translate(0, getHeight() / 2f);
    }
    
    /**
     * 绘制背景
     */
    private void drawBackground(Canvas canvas) {
        bgPaint.setColor(mConfig.getBgColor());
        bgPaint.setStrokeWidth(mConfig.getBgLineWidth());
        bgPaint.setStrokeCap(mConfig.isOpenBgCap() ? Cap.ROUND : Cap.SQUARE);
        headWidth = Math.max(mConfig.getBgLineWidth() / 2, mConfig.getSliderWidth() / 2);
        canvas.drawLine(headWidth, 0, getWidth() - headWidth, 0, bgPaint);
    }
    
    /**
     * 绘制经过的区域
     */
    private void drawPassArea(Canvas canvas) {
        pgPaint.setColor(mConfig.getPgColor());
        pgPaint.setStrokeWidth(mConfig.getBgLineWidth());
        pgPaint.setStrokeCap(mConfig.isOpenBgCap() ? Cap.ROUND : Cap.SQUARE);
        sliderCenter = (getWidth() - headWidth) * progress / 100f;
        canvas.drawLine(headWidth, 0, sliderCenter, 0, pgPaint);
    }
    
    /**
     * 绘制滑块
     */
    private void drawSlider(Canvas canvas) {
        Drawable slider = mConfig.getSliderBg();
        int w;
        int h;
        //设置的大小优先级高于自身的大小,如果超出了控件的大小,则使用控件的大小
        if (mConfig.getSliderWidth() != 0) {
            w = mConfig.getSliderWidth();
        } else {
            w = slider.getIntrinsicWidth();
        }
        if (mConfig.getSliderHeight() != 0) {
            h = mConfig.getSliderHeight();
        } else {
            h = slider.getIntrinsicHeight();
        }
        if (getWidth() < w) {
            w = getWidth();
        }
        if (getHeight() < h) {
            h = getHeight();
        }
        slider.setBounds((int) sliderCenter - w / 2, -h / 2, (int) sliderCenter + w / 2, h / 2);
        slider.draw(canvas);
    }
    
    /**
     * 绘制跟随滑动的文本
     */
    private void drawMoveText(Canvas canvas) {
    
    }
    
    @Override
    public SeekBarConfig getSetting() {
        return mConfig;
    }
    
    @Override
    public float getProgress() {
        return progress;
    }
    
    @Override
    public void setProgress(float progress) {
        this.progress = progress;
    }
}
