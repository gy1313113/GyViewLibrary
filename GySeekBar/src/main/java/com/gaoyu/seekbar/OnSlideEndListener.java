package com.gaoyu.seekbar;

/**
 * 用手滑动滑块结束的监听
 *
 * @author Created by gaoyu on 2022/5/27 14:34
 */
public interface OnSlideEndListener {
    
    /**
     * 滑动结束
     * @param progress 当前进度
     */
    void onEnd(float progress);
}
