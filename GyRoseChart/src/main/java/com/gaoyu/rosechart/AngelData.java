package com.gaoyu.rosechart;

/**
 * 每个扇形区域的终点角度
 *
 * @author Created by gaoyu on 2022/1/19 13:24
 */
class AngelData {
    
    /**
     * 角度
     */
    private float angel;
    
    AngelData(float angel) {
        this.angel = angel;
    }
    
    public float getAngel() {
        return angel;
    }
    
    public void setAngel(float angel) {
        this.angel = angel;
    }
}
