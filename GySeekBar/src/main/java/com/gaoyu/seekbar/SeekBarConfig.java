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
}
