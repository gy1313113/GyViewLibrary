package com.gaoyu.rosechart;

/**
 * 选中的扇形区域
 *
 * @author Created by gaoyu on 2022/1/19 13:06
 */
public interface SelectArcItem {
    
    /**
     * 选中的扇形区域
     *
     * @param position 顺位
     * @param item 具体数据
     */
    void onSelect(int position, RoseChartData item);
}
