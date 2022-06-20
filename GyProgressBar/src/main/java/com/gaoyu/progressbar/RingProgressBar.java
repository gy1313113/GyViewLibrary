package com.gaoyu.progressbar;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.Op;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
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
     * 背景环半径
     */
    private float bgRadius;
    /**
     * 线帽的半径
     */
    private float headRadius;
    /**
     * 背景环的画笔
     */
    private Paint bgRingPaint;
    /**
     * 环的画笔
     */
    private Paint ringPaint;
    /**
     * 线帽的画笔
     */
    private Paint headPaint;
    /**
     * 线帽的路径
     */
    private Path headPath;
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
        init(context, null);
    }
    
    public RingProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    
    public RingProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    
    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public RingProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    
    private void init(Context context, AttributeSet attrs) {
        mConfig = new ProgressConfig(getContext());
        initAttrs(context, attrs);
        bgRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgRingPaint.setStyle(Style.STROKE);
        ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ringPaint.setStyle(Style.FILL);
        headPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        headPaint.setStyle(Style.FILL);
    }
    
    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.RingProgressBar);
            mConfig.setBgDiameter(t.getDimensionPixelSize(R.styleable.RingProgressBar_rpb_bg_diameter, 600));
            mConfig.setBgRingWidth(t.getDimensionPixelSize(R.styleable.RingProgressBar_rpb_bg_ring_width, 80));
            mConfig.setBgRingColor(t.getColor(R.styleable.RingProgressBar_rpb_bg_ring_color, 0xff666666));
            mConfig.setRingColor(t.getColor(R.styleable.RingProgressBar_rpb_ring_color, 0xff40a9ff));
            mConfig.setRingHead(t.getBoolean(R.styleable.RingProgressBar_rpb_ring_head, false));
            t.recycle();
        }
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);//宽的测量大小，模式
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);//高的测量大小，模式
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        
        int w = widthSpecSize;   //定义测量宽，高(不包含测量模式)
        int h = heightSpecSize;
        
        int minWidth = (int) mConfig.getBgDiameter();
        
        //处理wrap_content的几种特殊情况,数值为PX
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            w = minWidth;
            h = minWidth;
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            w = h;
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            h = w;
        }
        
        if (minWidth > Math.min(w, h)) {
            mConfig.setBgDiameter((float) Math.min(w, h));
        }
        
        if (mConfig.getBgRingWidth() > mConfig.getBgDiameter() / 2) {
            mConfig.setBgRingWidth(mConfig.getBgDiameter() / 2);
        }
        
        setMeasuredDimension(w, h);
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
        //通过Paint的设置画笔宽度来实现圆环时，要注意！圆环整体大小会变大，所以画圆时半径要根据环宽预先缩小
        bgRadius = mConfig.getBgDiameter() / 2 - mConfig.getBgRingWidth() / 2;
        canvas.drawCircle(0, 0, bgRadius, bgRingPaint);
        
        //绘制圆环
        if (mConfig.getRingShader() != null) {
            Shader shader = mConfig.getRingShader();
            ringPaint.setShader(shader);
            headPaint.setShader(shader);
        } else {
            ringPaint.setColor(mConfig.getRingColor());
            headPaint.setColor(mConfig.getRingColor());
        }
        float outsideRadius = mConfig.getBgDiameter() / 2;
        RectF f = new RectF(-outsideRadius, -outsideRadius, outsideRadius, outsideRadius);
        Path outside = new Path();
        Path inside = new Path();
        if (endProgress != 100) {
            //这个方法在sweepAngle为360度时失效
            outside.arcTo(f, 0, endProgress * 360 / 100);
            outside.lineTo(0, 0);
        } else {
            outside.addCircle(0, 0, outsideRadius, Direction.CW);
        }
        inside.addCircle(0, 0, mConfig.getBgDiameter() / 2 - mConfig.getBgRingWidth(), Direction.CW);
        outside.op(inside, outside, Op.REVERSE_DIFFERENCE);
        canvas.drawPath(outside, ringPaint);
        
        //绘制线帽
        if (mConfig.hasRingHead() && endProgress > 0) {
            headPath = new Path();
            headRadius = mConfig.getBgRingWidth() / 2;
            float headX;
            float headY;
            if (endProgress > 0.1) {
                //这里减0.1度是为了在连接处看不到缝隙，因为计算的进度有细微的偏差，有的地方被舍出了，所以要减小
                headX = LocalUtils.getPointX(bgRadius, (float) (endProgress * 360 / 100 - 0.1));
                headY = LocalUtils.getPointY(bgRadius, (float) (endProgress * 360 / 100 - 0.1));
            } else {
                headX = LocalUtils.getPointX(bgRadius, endProgress * 360 / 100);
                headY = LocalUtils.getPointY(bgRadius, endProgress * 360 / 100);
            }
            RectF hf = new RectF(headX - headRadius, headY - headRadius, headX + headRadius, headY + headRadius);
            headPath.addArc(hf, endProgress * 360 / 100, 180);
            headPath.close();
            canvas.drawPath(headPath, headPaint);
        }
    }
    
    /**
     * 动画
     */
    public void animator(long duration, ProgressAnimatorListener listener) {
        stopAnimator();//结束之前的动画
        
        a = ValueAnimator.ofFloat(0, 1);
        a.setDuration(duration).setInterpolator(mConfig.getInterpolator());
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
