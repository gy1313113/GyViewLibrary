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
    
    /**
     * 预留给绘制分割线的角度
     */
    private float splitAngel;
    
    AngelData(float angel) {
        this.angel = angel;
    }
    
    AngelData(float angel, float splitAngel) {
        this.angel = angel;
        this.splitAngel = splitAngel;
    }
    
    public float getAngel() {
        return angel;
    }
    
    public void setAngel(float angel) {
        this.angel = angel;
    }
    
    public float getSplitAngel() {
        return splitAngel;
    }
    
    public void setSplitAngel(float splitAngel) {
        this.splitAngel = splitAngel;
    }
    
    /**
     * 返回分割线的绘制角度
     *
     * @return 分割线的绘制角度
     */
    public float getDrawAngel() {
        return (angel - (splitAngel / 2));
    }
}
