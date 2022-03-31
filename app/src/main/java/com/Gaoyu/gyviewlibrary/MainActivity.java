package com.Gaoyu.gyviewlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gaoyu.rosechart.RoseChart;
import com.gaoyu.rosechart.RoseChartData;
import com.gaoyu.rosechart.SelectArcItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    private Button mBtnRoseChart;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();
        
        initEvent();
    }
    
    private void initView() {
        mBtnRoseChart = findViewById(R.id.btn_rose_chart);
    }
    
    private void initEvent() {
        mBtnRoseChart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RoseChartActivity.class);
            startActivity(intent);
        });
    }
}