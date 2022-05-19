package com.gaoyu.rosechart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.FillType;
import android.graphics.Path.Op;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.view.GestureDetectorCompat;

/**
 * 玫瑰图
 *
 * @author Created by gaoyu on 2022/1/17 16:46
 */
public class RoseChart extends View {
    
    private List<RoseChartData> data;
    
    /**
     * 中心圆点
     */
    private PointF point;
    
    /**
     * 内圆半径
     */
    private float insideRadius = 0;
    
    private SelectArcItem mSelectArcItem;
    
    /**
     * 所有扇形的终点角度
     */
    private List<AngelData> angelData;
    
    /**
     * 图像内最大的扇形半径
     */
    private float maxArcRadius;
    
    /**
     * 扇形区域间的分割线宽度
     */
    private float mSplitLineWidth = 0;
    
    /**
     * 扇形区域间的分割线颜色(默认#FFFFFF)
     */
    private String mSplitLineColor = "#FFFFFF";
    
    /**
     * 手势检测器对象
     */
    private GestureDetector mDetector;
    
    //代码里new的
    public RoseChart(Context context) {
        super(context);
        init();
    }
    
    //.xml文件里声明的
    public RoseChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public RoseChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public RoseChart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    
    /**
     * 重绘
     */
    public void reDraw() {
        invalidate();
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
    
    public float getSplitLineWidth() {
        return mSplitLineWidth;
    }
    
    /**
     * 设置分割线宽度(建议最大4像素，多了影响小数据显示)
     *
     * @param splitLineWidth 分割线宽度
     */
    public void setSplitLineWidth(float splitLineWidth) {
        mSplitLineWidth = splitLineWidth;
    }
    
    public String getSplitLineColor() {
        return mSplitLineColor;
    }
    
    /**
     * 设置分割线颜色(建议与背景色相同)
     *
     * @param splitLineColor 分割线颜色
     */
    public void setSplitLineColor(String splitLineColor) {
        mSplitLineColor = splitLineColor;
    }
    
    /**
     * 每个扇形item的点击事件
     *
     * @param selectArcItem 事件返回接口
     */
    public void setOnSelectArcItemListener(SelectArcItem selectArcItem) {
        mSelectArcItem = selectArcItem;
    }
    
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);//用手势检测器事件覆盖原来的
    }
    
    /**
     * 初始化
     */
    private void init() {
        //初始化手势检测
        mDetector = new GestureDetector(getContext(), new OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;//这里必须是true，证明点击被消费
            }
            
            @Override
            public void onShowPress(MotionEvent e) {
            
            }
            
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                float x = e.getX();
                float y = e.getY();
                float cx = point.x;
                float cy = point.y;
                //计算触摸点相对于圆心的角度
                float touchAngel = LocalUtils.getTouchedPointAngle(cx, cy, x, y);
                boolean inTouchArea = LocalUtils.isInDrawArea(cx, cy, x, y, insideRadius, maxArcRadius + itemMaxOffset());
                if (angelData != null && inTouchArea) {
                    for (int i = 0; i < angelData.size(); i++) {
                        if (touchAngel < angelData.get(i).getAngel()) {
                            mSelectArcItem.onSelect(i, data.get(i));
                            return true;
                        }
                    }
                }
                return false;
            }
            
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }
            
            @Override
            public void onLongPress(MotionEvent e) {
            
            }
            
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);//宽的测量大小，模式
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);//高的测量大小，模式
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        
        int w = widthSpecSize;   //定义测量宽，高(不包含测量模式)
        int h = heightSpecSize;
        
        //处理wrap_content的几种特殊情况,数值为PX
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            if (getData() == null || getData().size() == 0) {
                w = 0;
                h = 0;
            } else {
                float max = 0;
                for (RoseChartData entity : getData()) {
                    if (entity.getRadius() > max) {
                        max = entity.getRadius();
                    }
                }
                w = (int) (2 * max + 1);
                h = w;
            }
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            w = h;
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            h = w;
        }
        
        setMeasuredDimension(w, h);
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        
        super.onLayout(changed, left, top, right, bottom);
    }
    
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //清空此前的存储的所有角度
        if (angelData != null) {
            angelData.clear();
        } else {
            angelData = new ArrayList<>();
        }
        //最大扇形半径清零
        maxArcRadius = 0f;
        //确定中心圆点
        point = new PointF(getWidth() / 2f, getHeight() / 2f);
        //将原点坐标移到中心圆点
        canvas.translate(point.x, point.y);
        //画布绕原点逆时针旋转90度，让起始位置在正上方
        canvas.rotate(-90f);
        //可使用的最大半径
        int maxRadius = Math.min(getWidth(), getHeight()) / 2;
        //确定内圆路径
        Path insidePath = new Path();
        insidePath.addCircle(0, 0, insideRadius, Direction.CW);
        //绘制
        float totalQty = totalQty();
        float oldAngle = 0;
        if (data != null) {
            //计算预留角度
            float splitAngel = splitAngel();
            //总预留角度
            float allSplitAngel = splitAngel * data.size();
            for (int i = 0; i < data.size(); i++) {
                //当前扇形半径
                float arcRadius = data.get(i).getRadius() + insideRadius;
                if (arcRadius > maxRadius) {
                    arcRadius = maxRadius;
                }
                //扫过的角度
                float crossAngle = (data.get(i).getQty() / totalQty) * (360 - allSplitAngel);
                //确定包裹外圆的矩形，每个扇形的都不同
                RectF outsideRectF = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
                
                //先绘制一个完整的扇形路径
                Path path = new Path();
                //arcTo()方法，有一侧的弧线连着起始点
                path.arcTo(outsideRectF, oldAngle, crossAngle);
                path.lineTo(0, 0);
                //取扇形路径中，不与内圆路径相交的部分
                path.op(insidePath, path, Op.REVERSE_DIFFERENCE);
                //设置某块区域飞离
                float distance = data.get(i).getOffset();
                if (distance != 0) {
                    path.offset(LocalUtils.offsetX(distance, oldAngle, crossAngle),
                        LocalUtils.offsetY(distance, oldAngle, crossAngle));
                }
                path.setFillType(FillType.EVEN_ODD);
                Paint arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                arcPaint.setColor(Color.parseColor(data.get(i).getColor()));
                canvas.drawPath(path, arcPaint);
                
                //将扇形绘制完成后所停留的角度存起来，之后方便区分点击区域
                oldAngle = oldAngle + crossAngle + splitAngel;
                angelData.add(new AngelData(oldAngle, splitAngel));
                
                //更新玫瑰图的扇形区域最大半径
                if (maxArcRadius < arcRadius) {
                    maxArcRadius = arcRadius;
                }
            }
            
            //绘制分割线
            if (mSplitLineWidth != 0) {
                Paint paint = new Paint();
                paint.setColor(Color.parseColor(mSplitLineColor));
                paint.setStrokeWidth(mSplitLineWidth);
                //开启抗锯齿，不然分割线会很粗糙
                paint.setAntiAlias(true);
                for (int i = 0; i < angelData.size(); i++) {
                    canvas.drawLine(0f, 0f, LocalUtils.offsetX(maxArcRadius + itemMaxOffset(), angelData.get(i).getDrawAngel(), 0),
                        LocalUtils.offsetY(maxArcRadius + itemMaxOffset(), angelData.get(i).getDrawAngel(), 0), paint);
                }
            }
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
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                count = count + data.get(i).getQty();
            }
        }
        return count;
    }
    
    /**
     * 计算最大飞离距离
     */
    private float itemMaxOffset() {
        float max = 0;
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getOffset() > max) {
                    max = data.get(i).getOffset();
                }
            }
        }
        return max;
    }
    
    /**
     * 计算扇形最大半径
     */
    private float maxArcRadius() {
        float max = 0;
        if (data != null) {
            //可使用的最大半径
            int maxRadius = Math.min(getWidth(), getHeight()) / 2;
            for (int i = 0; i < data.size(); i++) {
                //当前扇形半径
                float arcRadius = data.get(i).getRadius() + insideRadius;
                if (arcRadius > maxRadius) {
                    arcRadius = maxRadius;
                }
                if (max < arcRadius) {
                    max = arcRadius;
                }
            }
        }
        return max;
    }
    
    /**
     * 计算预留角度
     */
    private float splitAngel() {
        float angel = 0;
        if (mSplitLineWidth != 0 && data != null) {
            //计算分割线与圆心所组成的三角形的夹角，作为预留角度
            angel = LocalUtils.splitAngel(maxArcRadius(), mSplitLineWidth);
        }
        return angel;
    }
}
