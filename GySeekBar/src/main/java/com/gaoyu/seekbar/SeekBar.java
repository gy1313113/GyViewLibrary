package com.gaoyu.seekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.PointF;
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
    
    private Paint bgPaint;
    
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
        float capWidth = mConfig.getBgLineWidth() / 2;
        canvas.drawLine(capWidth, 0, getWidth() - capWidth, 0, bgPaint);
    }
    
    /**
     * 绘制经过的区域
     */
    private void drawPassArea(Canvas canvas) {
    
    }
    
    /**
     * 绘制滑块
     */
    private void drawSlider(Canvas canvas) {
    
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
}
