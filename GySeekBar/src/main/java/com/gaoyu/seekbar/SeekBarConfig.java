package com.gaoyu.seekbar;

import android.content.Context;
import android.graphics.Typeface;
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
     * Drawable背景，有它则设置的背景色失效
     */
    private Drawable bgDrawable;
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
     * Drawable进度条，有它则设置的进度条颜色失效，背景和进度条都是Drawable时，线帽属性也失效
     */
    private Drawable pgDrawable;
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
    /**
     * 文本颜色
     */
    private int textColor;
    /**
     * 文本大小
     */
    private int textSize;
    /**
     * 字体
     */
    private Typeface typeface;
    /**
     * 文本的位置偏移(像素 PX)
     */
    private int textOffSet;
    /**
     * 设置最大的文本数量
     */
    private int maxText;
    
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
    
    public Drawable getBgDrawable() {
        return bgDrawable;
    }
    
    /**
     * Drawable背景，有它则设置的背景色失效
     *
     * @param bgDrawable 背景的Drawable对象
     */
    public void setBgDrawable(Drawable bgDrawable) {
        this.bgDrawable = bgDrawable;
    }
    
    /**
     * Drawable背景，有它则设置的背景色失效
     *
     * @param bgDrawableRes 背景的Drawable资源id
     */
    public void setBgDrawableRes(@DrawableRes int bgDrawableRes) {
        this.bgDrawable = ContextCompat.getDrawable(mContext, bgDrawableRes);
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
    
    public Drawable getPgDrawable() {
        return pgDrawable;
    }
    
    /**
     * Drawable进度条，有它则设置的进度条颜色失效，背景和进度条都是Drawable时，线帽属性也失效
     *
     * @param pgDrawable 进度条的Drawable对象
     */
    public void setPgDrawable(Drawable pgDrawable) {
        this.pgDrawable = pgDrawable;
    }
    
    /**
     * Drawable进度条，有它则设置的进度条颜色失效，背景和进度条都是Drawable时，线帽属性也失效
     *
     * @param pgDrawableRes 进度条的Drawable资源id
     */
    public void setPgDrawableRes(@DrawableRes int pgDrawableRes) {
        this.pgDrawable = ContextCompat.getDrawable(mContext, pgDrawableRes);
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
    
    public int getTextColor() {
        return textColor;
    }
    
    /**
     * 设置文本颜色
     *
     * @param textColor 色值
     */
    public void setTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
    }
    
    /**
     * 设置文本颜色
     *
     * @param textColorRes 颜色资源id
     */
    public void setTextColorRes(@ColorRes int textColorRes) {
        this.textColor = ContextCompat.getColor(mContext, textColorRes);
    }
    
    public int getTextSize() {
        return textSize;
    }
    
    /**
     * 设置字体大小
     *
     * @param textSize 字体大小
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
    
    public Typeface getTypeface() {
        return typeface;
    }
    
    /**
     * 设置字体
     *
     * @param typeface 字体
     */
    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }
    
    public int getTextOffSet() {
        return textOffSet;
    }
    
    /**
     * 设置文本偏移
     *
     * @param textOffSet 文本偏移量(像素 PX)
     */
    public void setTextOffSet(@Px int textOffSet) {
        this.textOffSet = textOffSet;
    }
    
    public int getMaxText() {
        return maxText;
    }
    
    /**
     * 设置最大的文本数量(如果文本都较短，可以适当减小，默认为5，
     * 如果设置小于等于0，则不再限制文本长度，此时如果文本不断变化长度，则整个拖动条都会不断自适应变化长度)
     *
     * @param maxText 文本数量
     */
    public void setMaxText(int maxText) {
        this.maxText = maxText;
    }
}
