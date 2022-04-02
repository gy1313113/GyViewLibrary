package com.gaoyu.progressbar;

/**
 * 本地工具类
 *
 * @author Created by gaoyu on 2022/1/19 15:35
 */
class LocalUtils {
    /**
     * 获取点相对于原点的X轴位置
     *
     * @param radius 点到原点的距离
     * @param angle  点到原点的连线与0度线之间的夹角(这里用弧度表示)
     * @return 获取点相对于原点的X轴位置(带正负号)
     */
    public static float getPointX(float radius, float angle) {
        //画布此时顺时针旋转了90度
        return (float) (radius * Math.sin((angle + 90) / 180 * Math.PI));
    }
    
    /**
     * 获取点相对于原点的Y轴位置
     *
     * @param radius 点到原点的距离
     * @param angle  点到原点的连线与0度线之间的夹角(这里用弧度表示)
     * @return 获取点相对于原点的Y轴位置(带正负号)
     */
    public static float getPointY(float radius, float angle) {
        //画布此时顺时针旋转了90度
        return (float) -(radius * Math.cos((angle + 90) / 180 * Math.PI));
    }
}
