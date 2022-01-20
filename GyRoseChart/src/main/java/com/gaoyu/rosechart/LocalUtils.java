package com.gaoyu.rosechart;

/**
 * 本地工具类
 *
 * @author Created by gaoyu on 2022/1/19 15:35
 */
class LocalUtils {
    
    /**
     * 触摸点相对于圆心的角度(正上方为0度，顺时针)
     *
     * @param radiusX 圆心
     * @param radiusY 圆心
     * @param x       触摸点
     * @param y       触摸点
     * @return 触摸点相对于圆心的角度
     */
    public static float getTouchedPointAngle(float radiusX, float radiusY, float x, float y) {
        float dx = x - radiusX;
        float dy = y - radiusY;
        //角度
        double a;
        //三角形的斜边长
        double c = Math.sqrt(dx * dx + dy * dy);
        
        if (dx > 0.0f) {
            if (dy < 0.0f)
                // 0 ~ 90
                a = Math.asin(dx / c);
            else
                // 90 ~ 180
                a = Math.asin(dy / c) + Math.PI / 2;
        } else {
            if (dy > 0.0f)
                // 180 ~ 270
                a = Math.PI * 3 / 2 - Math.asin(dy / c);
            else
                // 270 ~ 360
                a = Math.PI * 2 - Math.asin(Math.abs(dx) / c);
        }
        return (float) (a * 180.0D / Math.PI);
    }
    
    /**
     * 判断输入位置是否在内圆外和最大扇形半径形成的外圆内
     *
     * @param radiusX     圆心
     * @param radiusY     圆心
     * @param x           触摸点
     * @param y           触摸点
     * @param smallRadius 内圆半径
     * @param bigRadius   外圆半径
     * @return 是否
     */
    public static boolean isInDrawArea(float radiusX, float radiusY, float x, float y, float smallRadius, float bigRadius) {
        float dx = x - radiusX;
        float dy = y - radiusY;
        //三角形的斜边长，这里其实就是半径
        double c = Math.sqrt(dx * dx + dy * dy);
        return c > smallRadius && c < bigRadius;
    }
    
    /**
     * 返回X轴偏移距离
     *
     * @param offset     设置的偏移距离
     * @param oldAngel   扇形起始角度
     * @param crossAngel 扇形跨越的角度
     * @return X轴偏移距离
     */
    public static float offsetX(float offset, float oldAngel, float crossAngel) {
        //计算方向角
        float angel = oldAngel + crossAngel / 2;
        //注意此时坐标轴已经被逆时针旋转了90度
        return offset * (float) Math.cos(Math.toRadians(angel));
    }
    
    /**
     * 返回Y轴偏移距离
     *
     * @param offset     设置的偏移距离
     * @param oldAngel   扇形起始角度
     * @param crossAngel 扇形跨越的角度
     * @return Y轴偏移距离
     */
    public static float offsetY(float offset, float oldAngel, float crossAngel) {
        //计算方向角
        float angel = oldAngel + crossAngel / 2;
        //注意此时坐标轴已经被逆时针旋转了90度
        return offset * (float) Math.sin(Math.toRadians(angel));
    }
}
