package com.gaoyu.seekbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.gaoyu.seekbar.SeekBarConfig.SliderStyle;

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
    
    private Paint textPaint;
    /**
     * 绘制线时，头尾预留的宽度，防止滑块或线帽显示不全
     */
    private float headWidth;
    /**
     * 滑块的中心位置
     */
    private float sliderCenter;
    
    private float[] sliderPosition;
    /**
     * 上一个点击事件在x轴上的位置
     */
    private float lastX;
    /**
     * 第一次触摸，是否在滑块区域内
     */
    private boolean inArea;
    
    private String mText;
    
    private OnProgressChangeListener mProgressChangeListener;
    
    private OnSlideEndListener mSlideEndListener;
    
    static final SliderStyle[] sliderStyleArray = {
        SliderStyle.NORMAL, SliderStyle.INCLUDE
    };
    
    public SeekBar(Context context) {
        super(context);
        init();
    }
    
    public SeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(attrs);
    }
    
    public SeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttrs(attrs);
    }
    
    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public SeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initAttrs(attrs);
    }
    
    private void init() {
        mConfig = new SeekBarConfig(getContext());
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Style.STROKE);
        pgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pgPaint.setStyle(Style.STROKE);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置文本的绘制水平居中
        textPaint.setTextAlign(Align.CENTER);
    }
    
    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.SeekBar);
            mConfig.setBgColor(t.getColor(R.styleable.SeekBar_bg_color, 0xffbfbfbf));
            mConfig.setBgDrawable(t.getDrawable(R.styleable.SeekBar_bg_drawable));
            mConfig.setBgLineWidth(t.getDimensionPixelSize(R.styleable.SeekBar_bg_line_width, 20));
            mConfig.setOpenBgCap(t.getBoolean(R.styleable.SeekBar_open_bg_cap, true));
            mConfig.setPgColor(t.getColor(R.styleable.SeekBar_pg_color, 0xffbb86fc));
            mConfig.setPgDrawable(t.getDrawable(R.styleable.SeekBar_pg_drawable));
            mConfig.setSliderBg(t.getDrawable(R.styleable.SeekBar_slider_bg));
            mConfig.setSliderStyle(sliderStyleArray[t.getInt(R.styleable.SeekBar_slider_style, 0)]);
            mConfig.setSliderWidth(t.getDimensionPixelSize(R.styleable.SeekBar_slider_width, 100));
            mConfig.setSliderHeight(t.getDimensionPixelSize(R.styleable.SeekBar_slider_height, 100));
            mConfig.setTextColor(t.getColor(R.styleable.SeekBar_text_color, 0xff333333));
            mConfig.setTextSize(t.getDimensionPixelSize(R.styleable.SeekBar_text_size, 20));
            mConfig.setTextOffSet(t.getDimensionPixelSize(R.styleable.SeekBar_text_offset, 0));
            mConfig.setMaxText(t.getInt(R.styleable.SeekBar_max_text, 5));
            progress = t.getFloat(R.styleable.SeekBar_progress, 0);
            mText = t.getString(R.styleable.SeekBar_text);
            t.recycle();
        }
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        
        int w = widthSpecSize;
        int h = heightSpecSize;
        
        int wm = widthSpecMode;
        int hm = heightSpecMode;
        
        int minHeight;
        if (mConfig.getSliderBg() != null && mConfig.getSliderHeight() == 0) {
            Drawable slider = mConfig.getSliderBg();
            minHeight = slider.getIntrinsicHeight();
        } else {
            minHeight = mConfig.getSliderHeight();
        }
        minHeight = Math.max(minHeight, mConfig.getBgLineWidth());
        minHeight = Math.max(minHeight, mConfig.getTextSize());
        minHeight = Math.max(minHeight, mConfig.getTextSize() + Math.abs(mConfig.getTextOffSet()) * 2);
        
        //处理wrap_content的几种特殊情况,数值为PX
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            wm = MeasureSpec.EXACTLY;//宽度自适应时，当match_parent处理
            h = minHeight;
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            wm = MeasureSpec.EXACTLY;
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            h = minHeight;
        }
        
        super.onMeasure(MeasureSpec.makeMeasureSpec(w, wm), MeasureSpec.makeMeasureSpec(h, hm));
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
        //确定滑块模式，防止空指针，默认普通模式
        if (mConfig.getSliderStyle() == null) {
            mConfig.setSliderStyle(SliderStyle.NORMAL);
        }
        //获取文本宽度
        float textWidth;
        if (mConfig.getMaxText() > 0) {
            textWidth = mConfig.getMaxText() * mConfig.getTextSize();
        } else {
            textWidth = mText.length() * mConfig.getTextSize();
        }
        //确定背景条头和尾的预留空间，确保能完整显示滑块、文本和线帽
        headWidth = 0;
        headWidth = Math.max(headWidth, textWidth / 2f);
        if (mConfig.getSliderStyle() == SliderStyle.NORMAL) {
            headWidth = Math.max(headWidth, mConfig.getSliderWidth() / 2f);
        }
        if (mConfig.isOpenBgCap() && mConfig.getBgDrawable() == null) {
            headWidth = Math.max(headWidth, mConfig.getBgLineWidth() / 2f);
        }
        //绘制背景条
        if (mConfig.getBgDrawable() != null) {
            Drawable bgDrawable = mConfig.getBgDrawable();
            bgDrawable.setBounds(
                (int) headWidth,
                -mConfig.getBgLineWidth() / 2,
                (int) (getWidth() - headWidth),
                mConfig.getBgLineWidth() / 2
            );
            bgDrawable.draw(canvas);
        } else {
            bgPaint.setColor(mConfig.getBgColor());
            bgPaint.setStrokeWidth(mConfig.getBgLineWidth());
            bgPaint.setStrokeCap(mConfig.isOpenBgCap() ? Cap.ROUND : Cap.SQUARE);
            canvas.drawLine(headWidth, 0, getWidth() - headWidth, 0, bgPaint);
        }
    }
    
    /**
     * 绘制经过的区域
     */
    private void drawPassArea(Canvas canvas) {
        //确定滑块中心
        switch (mConfig.getSliderStyle()) {
            case NORMAL:
                sliderCenter = (getWidth() - headWidth * 2) * progress / 100f + headWidth;
                break;
            case INCLUDE:
                sliderCenter = (getWidth() - headWidth * 2 - mConfig.getSliderWidth()) * progress / 100f
                    + headWidth + (mConfig.getSliderWidth() / 2f);
                break;
        }
        //绘制经过区域
        if (mConfig.getPgDrawable() != null) {
            Drawable pgDrawable = mConfig.getPgDrawable();
            if (progress > 0) {
                pgDrawable.setBounds(
                    (int) headWidth,
                    -mConfig.getBgLineWidth() / 2,
                    (int) sliderCenter,
                    mConfig.getBgLineWidth() / 2
                );
                pgDrawable.draw(canvas);
            }
        } else {
            pgPaint.setColor(mConfig.getPgColor());
            pgPaint.setStrokeWidth(mConfig.getBgLineWidth());
            pgPaint.setStrokeCap(mConfig.isOpenBgCap() ? Cap.ROUND : Cap.SQUARE);
            if (progress > 0) {
                canvas.drawLine(headWidth, 0, sliderCenter, 0, pgPaint);
            }
        }
    }
    
    /**
     * 绘制滑块
     */
    private void drawSlider(Canvas canvas) {
        //没有设置滑块背景不会绘制滑块
        if (mConfig.getSliderBg() != null) {
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
            //绘制滑块
            slider.setBounds(
                (int) sliderCenter - w / 2,
                -h / 2,
                (int) sliderCenter + w / 2,
                h / 2
            );
            slider.draw(canvas);
            
            Rect f = slider.getBounds();
            sliderPosition = new float[]{f.left, f.top, f.right, f.bottom};
        }
    }
    
    /**
     * 绘制跟随滑动的文本
     */
    private void drawMoveText(Canvas canvas) {
        if (mText != null) {
            textPaint.setColor(mConfig.getTextColor());
            textPaint.setTextSize(mConfig.getTextSize());
            if (mConfig.getTypeface() != null) {
                textPaint.setTypeface(mConfig.getTypeface());
            }
            //获取字体的基本属性
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            canvas.drawText(
                mText,
                0,
                Math.min(Math.max(mConfig.getMaxText(), 1), mText.length()),
                sliderCenter,
                mConfig.getTextOffSet() - fontMetrics.ascent / 2f,
                textPaint
            );
        }
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //防止上层ViewGroup拦截点击事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(e);
    }
    
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                inArea = mConfig.getSliderBg() != null && x >= sliderPosition[0] - 10 && x <= sliderPosition[2] + 10;
                break;
            case MotionEvent.ACTION_MOVE:
                if (inArea) {
                    float dx = x - lastX;
                    progress = progress + dx * (100f / getWidth());
                    if (progress <= 0) {
                        progress = 0;
                    } else if (progress >= 100) {
                        progress = 100;
                    }
                    setProgress(progress);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mSlideEndListener != null) {
                    mSlideEndListener.onEnd(progress);
                }
                break;
        }
        lastX = x;
        return true;
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
        if (mProgressChangeListener != null) {
            mProgressChangeListener.onChange(progress);
        }
        invalidate();
    }
    
    @Override
    public String getText() {
        return mText;
    }
    
    @Override
    public void setText(String text) {
        this.mText = text;
        invalidate();
    }
    
    @Override
    public void setText(String text, boolean reDraw) {
        this.mText = text;
        if (reDraw) {
            invalidate();
        }
    }
    
    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        this.mProgressChangeListener = listener;
    }
    
    public void setOnSlideEndListener(OnSlideEndListener listener) {
        this.mSlideEndListener = listener;
    }
}
