package com.gaoyu.seekbar;

import android.content.Context;
import android.graphics.Canvas;
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
        drawBackground(canvas);
        drawPassArea(canvas);
        drawSlider(canvas);
        drawMoveText(canvas);
    }
    
    /**
     * 绘制背景
     */
    private void drawBackground(Canvas canvas) {
    
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
