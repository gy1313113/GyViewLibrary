package com.gaoyu.gyviewlibrary;

import android.graphics.Color;
import android.os.Bundle;

import com.gaoyu.signaturepad.SignaturePad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import gaoyu.gyviewlibrary.R;

/**
 * 签名板测试页面
 *
 * @author Created by gaoyu on 2022/7/27 16:09
 */
public class SignaturePadActivity extends AppCompatActivity {
    
    private SignaturePad sp;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_pad);
        
        initView();
        
        initData();
        
        initEvent();
    }
    
    private void initView() {
        sp = findViewById(R.id.sp);
    }
    
    private void initData() {
        sp.getConfig().setBgColor(Color.parseColor("#40a9FF"));
        sp.getConfig().setTextColor(Color.parseColor("#333333"));
    }
    
    private void initEvent() {
    
    }
}
