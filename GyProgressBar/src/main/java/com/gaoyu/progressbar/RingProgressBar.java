package com.gaoyu.progressbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * 环形进度条
 *
 * @author Created by gaoyu on 2022/3/31 10:32
 */
public class RingProgressBar extends View implements IProgressBar {
    /**
     * 中心圆点
     */
    private PointF point;
    /**
     * 背景环的画笔
     */
    private Paint bgRingPaint;
    /**
     * 进度条部分属性配置
     */
    private ProgressConfig mConfig;
    
    public RingProgressBar(Context context) {
        super(context);
        init();
    }
    
    public RingProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public RingProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public RingProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    
    private void init() {
        mConfig = new ProgressConfig(getContext());
        bgRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgRingPaint.setStyle(Style.STROKE);
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        
        super.onLayout(changed, left, top, right, bottom);
    }
    
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //确定中心圆点
        point = new PointF(getWidth() / 2f, getHeight() / 2f);
        //将原点坐标移到中心圆点
        canvas.translate(point.x, point.y);
        //画布绕原点顺时针旋转90度，让起始位置在正下方
        canvas.rotate(90f);
        //绘制圆环
        if (mConfig.getBgRingShader() != null) {
            Shader shader= mConfig.getBgRingShader();
            bgRingPaint.setShader(shader);
        } else {
            bgRingPaint.setColor(mConfig.getBgRingColor());
        }
        bgRingPaint.setStrokeWidth(mConfig.getBgRingWidth());
        canvas.drawCircle(0, 0, mConfig.getBgDiameter(), bgRingPaint);
    }
    
    /**
     * 重绘
     */
    public void reDraw() {
        invalidate();
    }
    
    /**
     * 获取进度条的配置文件
     */
    @Override
    public ProgressConfig getSetting() {
        return mConfig;
    }
}
