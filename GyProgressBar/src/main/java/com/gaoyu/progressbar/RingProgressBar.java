package com.gaoyu.progressbar;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;

import java.util.concurrent.atomic.AtomicReference;

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
     * 背景环半径
     */
    private float bgRadius;
    /**
     * 背景环的画笔
     */
    private Paint bgRingPaint;
    /**
     * 环的画笔
     */
    private Paint ringPaint;
    /**
     * 进度条部分属性配置
     */
    private ProgressConfig mConfig;
    /**
     * 开始进度
     */
    private float startProgress;
    /**
     * 结束进度
     */
    private float endProgress;
    /**
     * 动画
     */
    private ValueAnimator a;
    
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
        ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ringPaint.setStyle(Style.STROKE);
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
        //绘制背景圆环
        if (mConfig.getBgRingShader() != null) {
            Shader shader = mConfig.getBgRingShader();
            bgRingPaint.setShader(shader);
        } else {
            bgRingPaint.setColor(mConfig.getBgRingColor());
        }
        bgRingPaint.setStrokeWidth(mConfig.getBgRingWidth());
        bgRadius = mConfig.getBgDiameter() / 2;
        canvas.drawCircle(0, 0, bgRadius, bgRingPaint);
        //绘制圆环
        if (mConfig.getRingShader() != null) {
            Shader shader = mConfig.getRingShader();
            ringPaint.setShader(shader);
        } else {
            ringPaint.setColor(mConfig.getRingColor());
        }
        ringPaint.setStrokeWidth(mConfig.getBgRingWidth());
        RectF f = new RectF(-bgRadius, -bgRadius, bgRadius, bgRadius);
        //原来的进度(不动画)
        canvas.drawArc(f, 0, startProgress * 360 / 100, false, ringPaint);
        //新增的进度
        //这里加减1度是为了在连接处看不到缝隙
        if (startProgress < 1) {
            canvas.drawArc(f, startProgress * 360 / 100, (endProgress - startProgress) * 360 / 100, false, ringPaint);
        } else {
            canvas.drawArc(f, startProgress * 360 / 100 - 1, (endProgress - startProgress) * 360 / 100 + 1, false, ringPaint);
        }
    }
    
    /**
     * 动画
     */
    public void animator(long duration, ProgressAnimatorListener listener) {
        stopAnimator();//结束之前的动画
        
        a = ValueAnimator.ofFloat(0, 1);
        a.setDuration(duration);
        final float progressStart = getStartProgress();
        final float progressEnd = getEndProgress();
        a.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            setProgress(progressStart, progressStart + value * (progressEnd - progressStart), true);
            listener.onAnimatorRunning(value, progressStart + value * (progressEnd - progressStart));
        });
        a.start();
    }
    
    /**
     * 结束动画
     */
    public void stopAnimator() {
        if (a != null && a.isStarted()) {
            a.cancel();
        }
    }
    
    /**
     * 设置进度(0~100)
     * 要使用动画的话，这里建议仅设置参数不重绘
     */
    public void setProgress(float startProgress, float endProgress, boolean reDraw) {
        this.startProgress = startProgress;
        this.endProgress = endProgress;
        if (reDraw) {
            invalidate();
        }
    }
    
    public float getStartProgress() {
        return startProgress;
    }
    
    public float getEndProgress() {
        return endProgress;
    }
    
    /**
     * 会跨越的进度(这里不是已跨越)
     */
    public float getCrossProgress() {
        return endProgress - startProgress;
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
