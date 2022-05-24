package com.gaoyu.gyviewlibrary;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gaoyu.seekbar.OnProgressChangeListener;
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
    private SeekBarConfig mConfig;
    private DecimalFormat format;
    private Button mBtnRemoveSlider;
    
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
        mBtnRemoveSlider = findViewById(R.id.btn_remove_slider);
    }
    
    private void initData() {
        format = new DecimalFormat("0.0");
        mConfig = mSeekBar.getSetting();
        mSeekBar.setText(format.format(mSeekBar.getProgress()) + "%");
    }
    
    private void initEvent() {
        mSeekBar.setOnProgressChangeListener(progress -> mSeekBar.setText(format.format(progress) + "%", false));
        
        mBtnRemoveSlider.setOnClickListener(v -> {
            if (mConfig.getSliderBg() != null) {
                mConfig.setSliderBg(null);
                mBtnRemoveSlider.setText("添加滑块");
            } else {
                mConfig.setSliderBgRes(R.drawable.simple_slider);
                mBtnRemoveSlider.setText("移除滑块");
            }
            mSeekBar.invalidate();
        });
    }
}
