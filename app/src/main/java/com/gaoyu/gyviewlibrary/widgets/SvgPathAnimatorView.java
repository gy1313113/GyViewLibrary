package com.gaoyu.gyviewlibrary.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.PathMeasure;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;

import com.sdsmdg.harjot.vectormaster.VectorMasterDrawable;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import gaoyu.gyviewlibrary.R;

/**
 * 使用svg转的xml活得的路径制作动画
 *
 * @author Created by gaoyu on 2022/9/29 13:43
 */
@RequiresApi(api = VERSION_CODES.LOLLIPOP)
public class SvgPathAnimatorView extends View {
    
    private PathModel body, leftEye, rightEye, mouth;
    private Path bodyPath, leftEyePath, rightEyePath, mouthPath;
    private PathMeasure bodyMeasure, leftEyeMeasure, rightEyeMeasure, mouthMeasure;
    private List<Path> recyclePathList;//路径回收列表
    private Paint mPaint, mPaintFill;
    private ValueAnimator bodyAnimator, leftEyeAnimator, rightEyeAnimator, mouthAnimator;
    private float bodyAnimatorValue, leftEyeAnimatorValue, rightEyeAnimatorValue, mouthAnimatorValue;
    
    public SvgPathAnimatorView(Context context) {
        super(context);
        initView(R.drawable.ic_lucky_2);
    }
    
    public SvgPathAnimatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(R.drawable.ic_lucky_2);
    }
    
    public SvgPathAnimatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(R.drawable.ic_lucky_2);
    }
    
    public SvgPathAnimatorView(Context context, @DrawableRes int resId) {
        super(context);
        initView(resId);
    }
    
    private void initView(@DrawableRes int resId) {
        VectorMasterDrawable drawable = new VectorMasterDrawable(getContext(), resId);
        body = drawable.getPathModelByName("body");
        leftEye = drawable.getPathModelByName("left_eye");
        rightEye = drawable.getPathModelByName("right_eye");
        mouth = drawable.getPathModelByName("mouth");
        bodyPath = body.getPath();
        leftEyePath = leftEye.getPath();
        rightEyePath = rightEye.getPath();
        mouthPath = mouth.getPath();
        bodyMeasure = new PathMeasure(bodyPath, false);
        leftEyeMeasure = new PathMeasure(leftEyePath, false);
        rightEyeMeasure = new PathMeasure(rightEyePath, false);
        mouthMeasure = new PathMeasure(mouthPath, false);
        
        recyclePathList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            recyclePathList.add(new Path());
        }
        
        initPaint();
        
        initAnimator();
    }
    
    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Style.STROKE);
        mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFill.setStyle(Style.FILL);
    }
    
    private void initAnimator() {
        bodyAnimator = ValueAnimator.ofFloat(0, 1).setDuration(2000);
        leftEyeAnimator = ValueAnimator.ofFloat(0, 1).setDuration(1000);
        rightEyeAnimator = ValueAnimator.ofFloat(0, 1).setDuration(1000);
        mouthAnimator = ValueAnimator.ofFloat(0, 1).setDuration(1000);
        
        bodyAnimator.addUpdateListener(animation -> {
            bodyAnimatorValue = (float) animation.getAnimatedValue();
            invalidate();
        });
        leftEyeAnimator.addUpdateListener(animation -> {
            leftEyeAnimatorValue = (float) animation.getAnimatedValue();
            invalidate();
        });
        rightEyeAnimator.addUpdateListener(animation -> {
            rightEyeAnimatorValue = (float) animation.getAnimatedValue();
            invalidate();
        });
        mouthAnimator.addUpdateListener(animation -> {
            mouthAnimatorValue = (float) animation.getAnimatedValue();
            invalidate();
        });
        
        bodyAnimator.start();
        leftEyeAnimator.start();
        rightEyeAnimator.start();
        mouthAnimator.start();
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        
        int w = widthSpecSize;
        int h = heightSpecSize;
        
        int wm = widthSpecMode;
        int hm = heightSpecMode;
        
        //处理wrap_content的几种特殊情况,数值为PX
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            wm = MeasureSpec.EXACTLY;//自适应时，当match_parent处理
            hm = MeasureSpec.EXACTLY;
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            w = h;
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            h = w;
        }
        
        super.onMeasure(MeasureSpec.makeMeasureSpec(w, wm), MeasureSpec.makeMeasureSpec(h, hm));
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        recyclePathList.get(0).reset();
        if (bodyAnimatorValue == 1) {
            canvas.drawPath(bodyPath, mPaintFill);
        } else {
            bodyMeasure.getSegment(0, bodyAnimatorValue * bodyMeasure.getLength(), recyclePathList.get(0), true);
            canvas.drawPath(recyclePathList.get(0), mPaint);
        }
        
        recyclePathList.get(1).reset();
        if (leftEyeAnimatorValue == 1) {
            canvas.drawPath(leftEyePath, mPaintFill);
        } else {
            leftEyeMeasure.getSegment(0, leftEyeAnimatorValue * leftEyeMeasure.getLength(), recyclePathList.get(1), true);
            canvas.drawPath(recyclePathList.get(1), mPaint);
        }
        
        recyclePathList.get(2).reset();
        if (rightEyeAnimatorValue == 1) {
            canvas.drawPath(rightEyePath, mPaintFill);
        } else {
            rightEyeMeasure.getSegment(0, rightEyeAnimatorValue * rightEyeMeasure.getLength(), recyclePathList.get(2), true);
            canvas.drawPath(recyclePathList.get(2), mPaint);
        }
        
        recyclePathList.get(3).reset();
        if (mouthAnimatorValue == 1) {
            canvas.drawPath(mouthPath, mPaintFill);
        } else {
            mouthMeasure.getSegment(0, mouthAnimatorValue * mouthMeasure.getLength(), recyclePathList.get(3), true);
            canvas.drawPath(recyclePathList.get(3), mPaint);
        }
    }
}
