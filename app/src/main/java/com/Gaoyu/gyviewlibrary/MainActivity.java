package com.Gaoyu.gyviewlibrary;

import androidx.appcompat.app.AppCompatActivity;

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
    
    private RoseChart mChart;
    private Button mBtnChange;
    private Button mBtnEmpty;
    private TextView mTvItem;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mChart = findViewById(R.id.chart);
        mBtnChange = findViewById(R.id.btn_chart);
        mBtnEmpty = findViewById(R.id.btn_empty);
        mTvItem = findViewById(R.id.tv_item);
        
        mTvItem.setText("无");
        
        List<RoseChartData> list = new ArrayList<>();
        list.add(new RoseChartData(null, 80, "#FF40A9FF", 80f));
        list.add(new RoseChartData(null, 120, "#FF73B13B", 170f));
        list.add(new RoseChartData(null, 45, "#FFFAAD14", 120f));
        list.add(new RoseChartData(null, 97, "#FFEE3E3E", 90f));
        list.add(new RoseChartData(null, 83, "#FFBBBBBB", 200f));
        mChart.setInsideRadius(50f);
        mChart.setData(list);
    
        mBtnChange.setOnClickListener(v -> {
            list.clear();
            list.add(new RoseChartData(null, (int) (Math.random() * 200), "#FF40A9FF", (float) (Math.random() * 200)));
            list.add(new RoseChartData(null, (int) (Math.random() * 200), "#FF73B13B", (float) (Math.random() * 200)));
            list.add(new RoseChartData(null, (int) (Math.random() * 200), "#FFFAAD14", (float) (Math.random() * 200)));
            list.add(new RoseChartData(null, (int) (Math.random() * 200), "#FFEE3E3E", (float) (Math.random() * 200)));
            list.add(new RoseChartData(null, (int) (Math.random() * 200), "#FFBBBBBB", (float) (Math.random() * 200)));
            mChart.setInsideRadius((float) (Math.random() * 100));
            mChart.setData(list);
        });
        
        mChart.setOnSelectArcItemListener((position, item) -> {
            String builder = "第" +
                (position + 1) +
                "个" +
                " " +
                "数值：" +
                item.getQty();
            mTvItem.setText(builder);
            if(list.get(position).getOffset() == 0) {
                list.get(position).setOffset(40);
            } else {
                list.get(position).setOffset(0);
            }
            mChart.setData(list);
        });
        
        mBtnEmpty.setOnClickListener(v -> {
            if(mChart.getSplitLineWidth() == 0) {
                mBtnEmpty.setText("不产生分割线");
                mChart.setSplitLineWidth(8);
            } else {
                mBtnEmpty.setText("产生分割线");
                mChart.setSplitLineWidth(0);
            }
            mChart.reDraw();
        });
    }
}