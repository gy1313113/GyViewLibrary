package com.gaoyu.gyviewlibrary;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gaoyu.seekbar.OnProgressChangeListener;
import com.gaoyu.seekbar.OnSlideEndListener;
import com.gaoyu.seekbar.SeekBar;
import com.gaoyu.seekbar.SeekBarConfig;

import java.text.DecimalFormat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import gaoyu.gyviewlibrary.R;

/**
 * 拖动条测试页面
 *
 * @author Created by gaoyu on 2022/5/23 10:53
 */
public class SeekBarActivity extends AppCompatActivity {
    
    private SeekBar mSeekBar;
    private SeekBar mSeekBar2;
    private SeekBarConfig mConfig;
    private SeekBarConfig mConfig2;
    private DecimalFormat format;
    private Button mBtnRemoveSlider;
    private Button mBtnReduction;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);
        
        initView();
        
        initData();
        
        initEvent();
    }
    
    private void initView() {
        mSeekBar = findViewById(R.id.seek_bar);
        mSeekBar2 = findViewById(R.id.seek_bar_2);
        mBtnRemoveSlider = findViewById(R.id.btn_remove_slider);
        mBtnReduction = findViewById(R.id.btn_reduction);
    }
    
    private void initData() {
        format = new DecimalFormat("0.0");
        mConfig = mSeekBar.getSetting();
        mConfig2 = mSeekBar2.getSetting();
        mSeekBar.setText(format.format(mSeekBar.getProgress()) + "%");
        mSeekBar2.setText(format.format(mSeekBar2.getProgress()) + "%");
    }
    
    private void initEvent() {
        mSeekBar.setOnProgressChangeListener(progress -> mSeekBar.setText(format.format(progress) + "%", false));
        
        mSeekBar2.setOnProgressChangeListener(progress -> mSeekBar2.setText(format.format(progress) + "%", false));
        
        mSeekBar2.setOnSlideEndListener(progress -> {
            if (progress == 100f) {
                ok();
            } else {
                animator();
            }
        });
        
        mBtnRemoveSlider.setOnClickListener(v -> {
            if (mConfig.getSliderBg() != null && mConfig2.getSliderBg() != null) {
                mConfig.setSliderBg(null);
                mConfig2.setSliderBg(null);
                mBtnRemoveSlider.setText("添加滑块");
            } else {
                mConfig.setSliderBgRes(R.drawable.simple_slider);
                mConfig2.setSliderBgRes(R.drawable.simple_slider);
                mBtnRemoveSlider.setText("移除滑块");
            }
            mSeekBar.invalidate();
            mSeekBar2.invalidate();
        });
        
        mBtnReduction.setOnClickListener(v -> {
            mConfig2.setSliderBgRes(R.drawable.simple_slider);
            mConfig2.setBgDrawableRes(R.drawable.simple_bg);
            mSeekBar2.setText(format.format(mSeekBar2.getProgress()) + "%");
            mSeekBar2.setOnProgressChangeListener(progress -> mSeekBar2.setText(format.format(progress) + "%", false));
        });
    }
    
    private void animator() {
        ValueAnimator a = ValueAnimator.ofFloat(0, 1);
        a.setDuration((long) (1000 * mSeekBar2.getProgress() / 100));
        final float endProgress = mSeekBar2.getProgress();
        a.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            mSeekBar2.setProgress((1 - value) * endProgress);
        });
        a.start();
    }
    
    private void ok() {
        mConfig2.setSliderBg(null);
        mConfig2.setBgDrawableRes(R.drawable.simple_ok);
        mSeekBar2.setText(null,false);
        mSeekBar2.removeOnProgressChangeListener();
        mSeekBar2.setProgress(0);
    }
}
