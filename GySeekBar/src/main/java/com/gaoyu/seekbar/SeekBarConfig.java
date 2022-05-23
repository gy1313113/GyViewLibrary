package com.gaoyu.seekbar;

import android.content.Context;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
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
     * 背景条的宽度
     */
    private float bgLineWidth;
    /**
     * 背景条的线帽开启与否
     */
    private boolean openBgCap;
    
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
    
    public float getBgLineWidth() {
        return bgLineWidth;
    }
    
    /**
     * 设置背景条的宽度
     * @param bgLineWidth 背景条宽度
     */
    public void setBgLineWidth(float bgLineWidth) {
        this.bgLineWidth = bgLineWidth;
    }
    
    public boolean isOpenBgCap() {
        return openBgCap;
    }
    
    /**
     * 设置背景条的线帽开启与否
     * @param openBgCap 背景条线帽是否开启
     */
    public void setOpenBgCap(boolean openBgCap) {
        this.openBgCap = openBgCap;
    }
}
