package com.gaoyu.gyviewlibrary;

import androidx.appcompat.app.AppCompatActivity;
import gaoyu.gyviewlibrary.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    
    private Button mBtnRoseChart;
    private Button mBtnProgressBar;
    private Button mBtnSeekBar;
    private Button mBtnSignaturePad;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();
        
        initEvent();
    }
    
    private void initView() {
        mBtnRoseChart = findViewById(R.id.btn_rose_chart);
        mBtnProgressBar = findViewById(R.id.btn_progress_bar);
        mBtnSeekBar = findViewById(R.id.btn_seek_bar);
        mBtnSignaturePad = findViewById(R.id.btn_signature_pad);
    }
    
    private void initEvent() {
        mBtnRoseChart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RoseChartActivity.class);
            startActivity(intent);
        });
        
        mBtnProgressBar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProgressBarActivity.class);
            startActivity(intent);
        });
    
        mBtnSeekBar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SeekBarActivity.class);
            startActivity(intent);
        });
    
        mBtnSignaturePad.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignaturePadActivity.class);
            startActivity(intent);
        });
    }
}