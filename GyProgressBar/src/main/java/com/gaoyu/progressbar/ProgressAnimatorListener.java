package com.gaoyu.progressbar;

/**
 * 进度条动画绘制监听
 *
 * @author Created by gaoyu on 2022/4/1 13:53
 */
public interface ProgressAnimatorListener {
    
    /**
     * 动画进行中
     * @param value 动画进度(0~1)
     * @param progress 进度条当前位置(0~100)
     */
    void onAnimatorRunning(float value, float progress);
}
