package com.gaoyu.seekbar;

/**
 * @author Created by gaoyu on 2022/5/23 9:25
 */
interface ISeekBar {
    
    /**
     * 获取设置
     */
    SeekBarConfig getSetting();
    
    /**
     * 获取当前进度(0~100)
     */
    float getProgress();
    
    /**
     * 设置当前进度(0~100)
     */
    void setProgress(float progress);
    
    /**
     * 获取当前跟随滑动的文本
     */
    String getText();
    
    /**
     * 设置当前跟随滑动的文本
     */
    void setText(String text);
    
    /**
     * 设置当前跟随滑动的文本(可不重绘，让setProgress重绘时完成text的更新)
     */
    void setText(String text, boolean reDraw);
}
