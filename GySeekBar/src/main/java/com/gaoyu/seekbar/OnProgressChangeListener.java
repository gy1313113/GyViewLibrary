package com.gaoyu.seekbar;

/**
 * 进度变化监听(在XML里的进度设置不会触发该监听)
 *
 * @author Created by gaoyu on 2022/5/24 14:38
 */
public interface OnProgressChangeListener {
    
    /**
     * 进度发生变化
     *
     * @param progress 进度(0~100)
     */
    void onChange(float progress);
}
