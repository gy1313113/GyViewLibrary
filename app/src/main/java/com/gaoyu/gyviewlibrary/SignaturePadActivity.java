package com.gaoyu.gyviewlibrary;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.gaoyu.signaturepad.ImageSaveUtil;
import com.gaoyu.signaturepad.ImageSaveUtil.BitmapSaveListener;
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
    private Button save;
    
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
        save = findViewById(R.id.btn_save);
    }
    
    private void initData() {
        sp.getConfig().setBgColor(Color.parseColor("#40a9FF"));
        sp.getConfig().setTextColor(Color.parseColor("#333333"));
        sp.setFileName("签名" + System.currentTimeMillis());
    }
    
    private void initEvent() {
        save.setOnClickListener(v -> {
            //现在Android10之后，都得动态获取权限
            if (Build.VERSION.SDK_INT >= 23) {
                int REQUEST_CODE_CONTACT = 101;
                String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //验证是否许可权限
                for (String str : permissions) {
                    if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                        //申请权限
                        this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                        return;
                    } else {
                        sp.saveSignature();
                    }
                }
            }
        });
        
        sp.setBitmapSaveListener(new BitmapSaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(SignaturePadActivity.this, "保存成功", Toast.LENGTH_LONG).show();
            }
            
            @Override
            public void onFailed() {
                Toast.makeText(SignaturePadActivity.this, "保存失败", Toast.LENGTH_LONG).show();
            }
        });
    }
}
