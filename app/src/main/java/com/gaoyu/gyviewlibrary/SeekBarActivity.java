package com.gaoyu.gyviewlibrary;

import android.os.Bundle;

import com.gaoyu.seekbar.SeekBar;
import com.gaoyu.seekbar.SeekBarConfig;

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
    }
    
    private void initData() {
        SeekBarConfig mConfig = mSeekBar.getSetting();
        mConfig.setBgColorRes(R.color.bg_color_gray_white_tint);
        mConfig.setBgLineWidth(20);
        mConfig.setOpenBgCap(true);
        mConfig.setPgColorRes(R.color.purple_200);
        mConfig.setSliderBgRes(R.mipmap.any_pic);
        mConfig.setSliderSize(100,100);
        mSeekBar.setProgress(20f);
    }
    
    private void initEvent() {
    
    }
}
