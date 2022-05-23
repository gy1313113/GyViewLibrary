package com.gaoyu.seekbar;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Px;
import androidx.core.content.ContextCompat;

/**
 * 拖动条的配置文件
 *
 * @author Created by gaoyu on 2022/5/23 9:00
 */
public class SeekBarConfig {
    
    private final Context mContext;
    
    /**
     * 背景色
     */
    private int bgColor;
    /**
     * 背景条的宽度(像素 PX)
     */
    private int bgLineWidth;
    /**
     * 背景条的线帽开启与否
     */
    private boolean openBgCap;
    /**
     * 进度条颜色
     */
    private int pgColor;
    /**
     * 滑块背景
     */
    private Drawable sliderBg;
    /**
     * 滑块宽度(像素 PX)
     */
    private int sliderWidth;
    /**
     * 滑块高度(像素 PX)
     */
    private int sliderHeight;
    
    public SeekBarConfig(Context context) {
        this.mContext = context;
    }
    
    public int getBgColor() {
        return bgColor;
    }
    
    /**
     * 设置背景色
     *
     * @param bgColor 色值
     */
    public void setBgColor(@ColorInt int bgColor) {
        this.bgColor = bgColor;
    }
    
    /**
     * 设置背景色
     *
     * @param bgColorRes 颜色资源id
     */
    public void setBgColorRes(@ColorRes int bgColorRes) {
        this.bgColor = ContextCompat.getColor(mContext, bgColorRes);
    }
    
    public int getBgLineWidth() {
        return bgLineWidth;
    }
    
    /**
     * 设置背景条的宽度
     *
     * @param bgLineWidth 背景条宽度(像素 PX)
     */
    public void setBgLineWidth(@Px int bgLineWidth) {
        this.bgLineWidth = bgLineWidth;
    }
    
    public boolean isOpenBgCap() {
        return openBgCap;
    }
    
    /**
     * 设置背景条的线帽开启与否
     *
     * @param openBgCap 背景条线帽是否开启
     */
    public void setOpenBgCap(boolean openBgCap) {
        this.openBgCap = openBgCap;
    }
    
    public int getPgColor() {
        return pgColor;
    }
    
    /**
     * 设置进度条颜色
     *
     * @param pgColor 色值
     */
    public void setPgColor(@ColorInt int pgColor) {
        this.pgColor = pgColor;
    }
    
    /**
     * 设置进度条颜色
     *
     * @param pgColorRes 颜色资源id
     */
    public void setPgColorRes(@ColorRes int pgColorRes) {
        this.pgColor = ContextCompat.getColor(mContext, pgColorRes);
    }
    
    public Drawable getSliderBg() {
        return sliderBg;
    }
    
    /**
     * 设置滑块背景
     *
     * @param sliderBg 滑块背景
     */
    public void setSliderBg(Drawable sliderBg) {
        this.sliderBg = sliderBg;
    }
    
    /**
     * 设置滑块背景
     *
     * @param sliderBgRes 滑块背景资源
     */
    public void setSliderBgRes(@DrawableRes int sliderBgRes) {
        this.sliderBg = ContextCompat.getDrawable(mContext, sliderBgRes);
    }
    
    public int getSliderWidth() {
        return sliderWidth;
    }
    
    /**
     * 设置滑块宽度
     *
     * @param sliderWidth 滑块宽度(像素 PX)
     */
    public void setSliderWidth(@Px int sliderWidth) {
        this.sliderWidth = sliderWidth;
    }
    
    public int getSliderHeight() {
        return sliderHeight;
    }
    
    /**
     * 设置滑块高度
     *
     * @param sliderHeight 滑块高度(像素 PX)
     */
    public void setSliderHeight(@Px int sliderHeight) {
        this.sliderHeight = sliderHeight;
    }
    
    /**
     * 设置滑块大小
     *
     * @param sliderWidth  滑块宽度(像素 PX)
     * @param sliderHeight 滑块高度(像素 PX)
     */
    public void setSliderSize(@Px int sliderWidth, @Px int sliderHeight) {
        this.sliderWidth = sliderWidth;
        this.sliderHeight = sliderHeight;
    }
}
