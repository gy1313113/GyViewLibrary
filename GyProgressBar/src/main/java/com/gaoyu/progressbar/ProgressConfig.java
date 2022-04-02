package com.gaoyu.progressbar;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Interpolator;
import android.graphics.Shader;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

/**
 * 进度条部分属性配置
 * 优先级 shader > color
 * 着色器推荐SweepGradient
 *
 * @author Created by gaoyu on 2022/3/31 11:05
 */
public class ProgressConfig {
    
    private final Context mContext;
    
    /**
     * 背景环的直径(包含环宽）
     */
    private float bgDiameter;
    
    /**
     * 背景环的宽
     */
    private float bgRingWidth;
    
    /**
     * 背景环的颜色
     */
    private int bgRingColor;
    
    /**
     * 背景环的着色器
     */
    private Shader bgRingShader;
    
    /**
     * 环的颜色
     */
    private int ringColor;
    
    /**
     * 环的图层
     */
    private Shader ringShader;
    
    /**
     * 动画的插值器
     */
    private TimeInterpolator mInterpolator;
    
    /**
     * 线帽
     */
    private boolean ringHead;
    
    ProgressConfig(Context context) {
        this.mContext = context;
    }
    
    /**
     * 背景环的直径(包含环宽）
     */
    public void setBgDiameter(float diameter) {
        this.bgDiameter = diameter;
    }
    
    /**
     * 背景环的宽
     */
    public void setBgRingWidth(float ringWidth) {
        this.bgRingWidth = ringWidth;
    }
    
    /**
     * 背景环的颜色
     *
     * @param color 色值
     */
    public void setBgRingColor(@ColorInt int color) {
        this.bgRingColor = color;
        removeBgRingShader();
    }
    
    /**
     * 背景环的颜色
     *
     * @param color 颜色资源id
     */
    public void setBgRingColorRes(@ColorRes int color) {
        this.bgRingColor = ContextCompat.getColor(mContext, color);
        removeBgRingShader();
    }
    
    /**
     * 背景环的着色器
     */
    public void setBgRingShader(Shader bgRingShader) {
        this.bgRingShader = bgRingShader;
    }
    
    /**
     * 环的颜色
     *
     * @param color 色值
     */
    public void setRingColor(@ColorInt int color) {
        this.ringColor = color;
        removeRingShader();
    }
    
    /**
     * 环的颜色
     *
     * @param color 颜色资源id
     */
    public void setRingColorRes(@ColorRes int color) {
        this.ringColor = ContextCompat.getColor(mContext, color);
        removeRingShader();
    }
    
    /**
     * 环的着色器
     */
    public void setRingShader(Shader ringShader) {
        this.ringShader = ringShader;
    }
    
    /**
     * 环动画的时间插值器，作为不断更新的进度条时，不建议加，动画一会快一会慢
     */
    public void setInterpolator(TimeInterpolator interpolator) {
        this.mInterpolator = interpolator;
    }
    
    /**
     * 环上是否要帽子
     */
    public void setRingHead(boolean ringHead) {
        this.ringHead = ringHead;
    }
    
    public float getBgDiameter() {
        return bgDiameter;
    }
    
    public float getBgRingWidth() {
        return bgRingWidth;
    }
    
    public int getBgRingColor() {
        return bgRingColor;
    }
    
    public Shader getBgRingShader() {
        return bgRingShader;
    }
    
    public int getRingColor() {
        return ringColor;
    }
    
    public Shader getRingShader() {
        return ringShader;
    }
    
    public TimeInterpolator getInterpolator() {
        return mInterpolator;
    }
    
    public boolean hasRingHead() {
        return ringHead;
    }
    
    /**
     * 清空背景环的着色器
     */
    public void removeBgRingShader() {
        bgRingShader = null;
    }
    
    /**
     * 清空环的着色器
     */
    public void removeRingShader() {
        ringShader = null;
    }
}
