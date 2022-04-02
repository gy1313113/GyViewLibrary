package com.gaoyu.gyviewlibrary;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gaoyu.progressbar.ProgressConfig;
import com.gaoyu.progressbar.RingProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import gaoyu.gyviewlibrary.R;


/**
 * 进度条测试页面
 *
 * @author Created by gaoyu on 2022/3/31 15:36
 */
public class ProgressBarActivity extends AppCompatActivity {
    
    private RingProgressBar mProgressBar;
    private float startProgress;
    private float endProgress;
    private Button mBtnProgress;
    private Button mBtnClear;
    private Button mBtnHead;
    private TextView mTvEnd;
    private TextView mTvNow;
    private ProgressConfig config;
    
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
        mBtnProgress = findViewById(R.id.btn_progress);
        mBtnClear = findViewById(R.id.btn_clear);
        mBtnHead = findViewById(R.id.btn_head);
        mTvEnd = findViewById(R.id.tv_end);
        mTvNow = findViewById(R.id.tv_now);
    }
    
    @SuppressLint("SetTextI18n")
    private void initData() {
        int[] color = {Color.GREEN, Color.RED};
        float[] radius = {0, 1f};
        config = mProgressBar.getSetting();
        config.setBgRingColorRes(R.color.bg_color_gray_white_tint);
        config.setBgDiameter(600f);
        config.setBgRingWidth(80f);
        config.setRingShader(new SweepGradient(0, 0, color, radius));
        mTvEnd.setText("目标进度:" + endProgress + "%");
        mTvNow.setText("当前进度:" + "0.0" + "%");
    }
    
    @SuppressLint("SetTextI18n")
    private void initEvent() {
        mBtnProgress.setOnClickListener(v -> {
            if (endProgress <= 100) {
                float randomProgress = endProgress + (float) (100 * Math.random());
                if (randomProgress > 100) {
                    randomProgress = 100;
                }
                endProgress = randomProgress;
                mTvEnd.setText("目标进度:" + endProgress + "%");
                animator(endProgress);
            }
        });
        
        mBtnClear.setOnClickListener(v -> {
            mProgressBar.stopAnimator();
            mProgressBar.setProgress(0, 0, true);
            startProgress = 0;
            endProgress = 0;
            mTvEnd.setText("目标进度:" + "0.0" + "%");
            mTvNow.setText("当前进度:" + "0.0" + "%");
        });
        
        mBtnHead.setOnClickListener(v -> {
            if (config.hasRingHead()) {
                config.setRingHead(false);
                mBtnHead.setText("开启线帽");
            } else {
                config.setRingHead(true);
                mBtnHead.setText("关闭线帽");
            }
        });
    }
    
    @SuppressLint("SetTextI18n")
    private void animator(float end) {
        mProgressBar.setProgress(startProgress, end, false);
        float cross = mProgressBar.getCrossProgress();
        mProgressBar.animator(5000 * (long) cross / 100, (value, progress) -> {
            startProgress = progress;
            mTvNow.setText("当前进度:" + progress + "%");
        });
    }
}
