package com.gaoyu.gyviewlibrary;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.widget.Button;

import com.gaoyu.seekbar.SmartSeekBar;
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
    
    private SmartSeekBar mSmartSeekBar;
    private SmartSeekBar mSmartSeekBar2;
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
        mSmartSeekBar = findViewById(R.id.seek_bar);
        mSmartSeekBar2 = findViewById(R.id.seek_bar_2);
        mBtnRemoveSlider = findViewById(R.id.btn_remove_slider);
        mBtnReduction = findViewById(R.id.btn_reduction);
    }
    
    private void initData() {
        format = new DecimalFormat("0.0");
        mConfig = mSmartSeekBar.getSetting();
        mConfig2 = mSmartSeekBar2.getSetting();
        mSmartSeekBar.setText(format.format(mSmartSeekBar.getProgress()) + "%");
        mSmartSeekBar2.setText(format.format(mSmartSeekBar2.getProgress()) + "%");
    }
    
    private void initEvent() {
        mSmartSeekBar.setOnProgressChangeListener(progress -> mSmartSeekBar.setText(format.format(progress) + "%", false));
        
        mSmartSeekBar2.setOnProgressChangeListener(progress -> mSmartSeekBar2.setText(format.format(progress) + "%", false));
        
        mSmartSeekBar2.setOnSlideEndListener(progress -> {
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
            mSmartSeekBar.invalidate();
            mSmartSeekBar2.invalidate();
        });
        
        mBtnReduction.setOnClickListener(v -> {
            mConfig2.setSliderBgRes(R.drawable.simple_slider);
            mConfig2.setBgDrawableRes(R.drawable.simple_bg);
            mSmartSeekBar2.setText(format.format(mSmartSeekBar2.getProgress()) + "%");
            mSmartSeekBar2.setOnProgressChangeListener(progress -> mSmartSeekBar2.setText(format.format(progress) + "%", false));
        });
    }
    
    private void animator() {
        ValueAnimator a = ValueAnimator.ofFloat(0, 1);
        a.setDuration((long) (1000 * mSmartSeekBar2.getProgress() / 100));
        final float endProgress = mSmartSeekBar2.getProgress();
        a.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            mSmartSeekBar2.setProgress((1 - value) * endProgress);
        });
        a.start();
    }
    
    private void ok() {
        mConfig2.setSliderBg(null);
        mConfig2.setBgDrawableRes(R.drawable.simple_ok);
        mSmartSeekBar2.setText(null,false);
        mSmartSeekBar2.removeOnProgressChangeListener();
        mSmartSeekBar2.setProgress(0);
    }
}
