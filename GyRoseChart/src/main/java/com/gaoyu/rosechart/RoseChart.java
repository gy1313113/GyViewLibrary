package com.gaoyu.rosechart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.Op;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.List;

import androidx.annotation.RequiresApi;

/**
 * 玫瑰图
 *
 * @author Created by gaoyu on 2022/1/17 16:46
 */
public class RoseChart extends View {
    
    private List<RoseChartData> data;
    
    /**
     * 内圆半径
     */
    private float insideRadius = 0;
    
    public RoseChart(Context context) {
        super(context);
    }
    
    public RoseChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public RoseChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    public RoseChart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    
    /**
     * 返回图表内数据
     *
     * @return 数据
     */
    public List<RoseChartData> getData() {
        return data;
    }
    
    /**
     * 设置数据，之前有数据的重绘
     *
     * @param data 区块数据列表
     */
    public void setData(List<RoseChartData> data) {
        if (this.data != null) {
            this.data = data;
            invalidate();
        } else {
            this.data = data;
        }
        
    }
    
    public float getInsideRadius() {
        return insideRadius;
    }
    
    /**
     * 设置内圆半径
     *
     * @param insideRadius 内圆半径
     */
    public void setInsideRadius(float insideRadius) {
        this.insideRadius = insideRadius;
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        
        super.onLayout(changed, left, top, right, bottom);
    }
    
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //确定中心圆点
        PointF point = new PointF(getWidth() / 2f, getHeight() / 2f);
        //将原点坐标移到中心圆点
        canvas.translate(point.x, point.y);
        //画布绕原点逆时针旋转90度，让起始位置在正上方
        canvas.rotate(-90f);
        //可使用的最大半径
        int maxArcRadius = Math.min(getWidth(), getHeight()) / 2;
        //确定内圆路径
        Path insidePath = new Path();
        insidePath.addCircle(0, 0, insideRadius, Direction.CW);
        //绘制
        float totalQty = totalQty();
        float oldAngle = 0;
        for (int i = 0; i < data.size(); i++) {
            //扇形半径
            float arcRadius = data.get(i).getRadius() + insideRadius;
            if (arcRadius > maxArcRadius) {
                arcRadius = maxArcRadius;
            }
            //扫过的角度
            float crossAngle = (data.get(i).getQty() / totalQty) * 360;
            //确定包裹外圆的矩形，每个扇形的都不同
            RectF outsideRectF = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
            
            //规划编辑区域并填充颜色
            canvas.save();
            //先绘制一个完整的扇形路径
            Path path = new Path();
            //arcTo()方法，有一侧的弧线连着起始点
            path.arcTo(outsideRectF, oldAngle, crossAngle);
            path.lineTo(0, 0);
            //取扇形路径中，不与内圆路径相交的部分
            path.op(insidePath, path, Op.REVERSE_DIFFERENCE);
            //剪辑路径
            canvas.clipPath(path);
            canvas.drawColor(Color.parseColor(data.get(i).getColor()));
            canvas.restore();
            
            oldAngle = oldAngle + crossAngle;
        }
    }
    
    
    /**
     * dp转px
     */
    private float convertDpToPixel(float dp) {
        DisplayMetrics mDisplayMetrics = null;
        
        if (getContext() != null) {
            mDisplayMetrics = getContext().getResources().getDisplayMetrics();
        }
        
        if (mDisplayMetrics == null) {
            
            Log.e("RoseChart",
                "DisplayMetrics Error");
            return dp;
        }
        
        return dp * mDisplayMetrics.density;
    }
    
    /**
     * 计算总量
     */
    private float totalQty() {
        float count = 0;
        for (int i = 0; i < data.size(); i++) {
            count = count + data.get(i).getQty();
        }
        return count;
    }
}
