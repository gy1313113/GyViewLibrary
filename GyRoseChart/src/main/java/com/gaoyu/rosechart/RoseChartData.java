package com.gaoyu.rosechart;

/**
 * 每个扇形区块的数据
 *
 * @author Created by gaoyu on 2022/1/18 9:17
 */
public class RoseChartData {
    
    /**
     * 区块名称
     */
    private String name;
    
    /**
     * 区块数值
     */
    private int qty;
    
    /**
     * 区块颜色(例如#333333)
     */
    private String color;
    
    /**
     * 区块的半径(从圆心到扇形边缘)
     */
    private float radius;
    
    /**
     * 区块飞离中心点的距离(不包括中心圆的)
     */
    private float offset;
    
    public RoseChartData() {
    
    }
    
    /**
     *
     * @param name 区块名称
     * @param qty 区块数值
     * @param color 区块颜色(#333333)
     * @param radius 区块的半径(从圆心到扇形边缘)
     */
    public RoseChartData(String name, int qty, String color, float radius) {
        this.name = name;
        this.qty = qty;
        this.color = color;
        this.radius = radius;
    }
    
    /**
     *
     * @param name 区块名称
     * @param qty 区块数值
     * @param color 区块颜色(#333333)
     * @param radius 区块的半径(从圆心到扇形边缘)
     * @param offset 区块飞离中心点的距离(不包括中心圆的)
     */
    public RoseChartData(String name, int qty, String color, float radius, float offset) {
        this.name = name;
        this.qty = qty;
        this.color = color;
        this.radius = radius;
        this.offset = offset;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getQty() {
        return qty;
    }
    
    public void setQty(int qty) {
        this.qty = qty;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public float getRadius() {
        return radius;
    }
    
    public void setRadius(float radius) {
        this.radius = radius;
    }
    
    public float getOffset() {
        return offset;
    }
    
    public void setOffset(float offset) {
        this.offset = offset;
    }
}
