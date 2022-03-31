package com.gaoyu.gyviewlibrary;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.os.Bundle;

import com.gaoyu.progressbar.ProgressConfig;
import com.gaoyu.progressbar.RingProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import gaoyu.gyviewlibrary.R;


/**
 * 进度条测试页面
 *
 * @author Created by gaoyu on 2022/3/31 15:36
 */
public class ProgressBarActivity extends AppCompatActivity {
    
    private RingProgressBar mProgressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        
        initView();
        
        initData();
        
        initEvent();
    }
    
    private void initView() {
        mProgressBar = findViewById(R.id.progress_bar);
    }
    
    private void initData() {
        int[] color = {Color.RED, Color.YELLOW, Color.GREEN, ContextCompat.getColor(this, R.color.teal_200), Color.BLUE, ContextCompat.getColor(this, R.color.purple_500)};
        float[] radius = {0, 0.17f, 0.34f, 0.51f, 0.68f, 0.85f};
        ProgressConfig config = mProgressBar.getSetting();
        config.setBgRingShader(new SweepGradient(0, 0, color, radius));
        config.setBgDiameter(300f);
        config.setBgRingWidth(20f);
    }
    
    private void initEvent() {
    
    }
}
