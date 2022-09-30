package com.gaoyu.gyviewlibrary;

import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.widget.LinearLayout.LayoutParams;

import com.gaoyu.gyviewlibrary.widgets.SvgPathAnimatorView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import gaoyu.gyviewlibrary.R;

/**
 * 使用svg转的xml活得的路径制作动画
 *
 * @author Created by gaoyu on 2022/9/29 13:34
 */
@RequiresApi(api = VERSION_CODES.LOLLIPOP)
public class SvgPathAnimatorActivity extends AppCompatActivity {
    
    private SvgPathAnimatorView spav;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_path_animator);
        
        initView();
        
        initData();
        
        initEvent();
    }
    
    private void initView() {
        spav = findViewById(R.id.spav);
    }
    
    
    private void initData() {
    }
    
    private void initEvent() {
    
    }
}
