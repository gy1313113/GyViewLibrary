package com.gaoyu.gyviewlibrary;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.gaoyu.rosechart.RoseChart;
import com.gaoyu.rosechart.RoseChartData;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import gaoyu.gyviewlibrary.R;

/**
 * 玫瑰图测试页面
 *
 * @author Created by gaoyu on 2022/3/31 9:40
 */
public class RoseChartActivity extends AppCompatActivity {
    
    private RoseChart mChart;
    private Button mBtnChange;
    private Button mBtnEmpty;
    private Button mBtnChart;
    private Button mBtnHole;
    private TextView mTvItem;
    
    private boolean isRose = true;
    private boolean hasHole = true;
    
    private List<RoseChartData> list;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rose_chart);
        
        mChart = findViewById(R.id.chart);
        mBtnChange = findViewById(R.id.btn_data);
        mBtnEmpty = findViewById(R.id.btn_empty);
        mBtnChart = findViewById(R.id.btn_chart);
        mBtnHole = findViewById(R.id.btn_hole);
        mTvItem = findViewById(R.id.tv_item);
        
        mTvItem.setText("无");
        
        list = new ArrayList<>();
        list.add(new RoseChartData(null, 80, "#FF40A9FF", 80f));
        list.add(new RoseChartData(null, 120, "#FF73B13B", 170f));
        list.add(new RoseChartData(null, 45, "#FFFAAD14", 120f));
        list.add(new RoseChartData(null, 97, "#FFEE3E3E", 90f));
        list.add(new RoseChartData(null, 83, "#FFBBBBBB", 200f));
        mChart.setInsideRadius(50f);
        mChart.setData(list);
        
        mBtnChange.setOnClickListener(v -> {
            if (isRose) {
                randomRoseList();
            } else {
                randomPieList();
            }
            if (hasHole) {
                mChart.setInsideRadius((float) (Math.random() * 100));
            } else {
                mChart.setInsideRadius(0);
            }
            mChart.setData(list);
        });
        
        mBtnChart.setOnClickListener(v -> {
            if (isRose) {
                isRose = false;
                mBtnChart.setText("玫瑰图");
                randomPieList();
            } else {
                isRose = true;
                mBtnChart.setText("饼图");
                randomRoseList();
            }
            mChart.setData(list);
        });
        
        mBtnHole.setOnClickListener(v -> {
            if (hasHole) {
                hasHole = false;
                mBtnHole.setText("开启内径");
                mChart.setInsideRadius(0);
            } else {
                hasHole = true;
                mBtnHole.setText("关闭内径");
                mChart.setInsideRadius((float) (Math.random() * 100));
            }
            mChart.reDraw();
        });
        
        mChart.setOnSelectArcItemListener((position, item) -> {
            String builder = "第" +
                (position + 1) +
                "个" +
                " " +
                "数值：" +
                item.getQty();
            mTvItem.setText(builder);
            if (list.get(position).getOffset() == 0) {
                list.get(position).setOffset(40);
            } else {
                list.get(position).setOffset(0);
            }
            mChart.setData(list);
        });
        
        mBtnEmpty.setOnClickListener(v -> {
            if (mChart.getSplitLineWidth() == 0) {
                mBtnEmpty.setText("不产生分割线");
                mChart.setSplitLineWidth(4);
            } else {
                mBtnEmpty.setText("产生分割线");
                mChart.setSplitLineWidth(0);
            }
            mChart.reDraw();
        });
    }
    
    private void randomRoseList() {
        list.clear();
        list.add(new RoseChartData(null, (int) (Math.random() * 200), "#FF40A9FF", (float) (Math.random() * 200)));
        list.add(new RoseChartData(null, (int) (Math.random() * 200), "#FF73B13B", (float) (Math.random() * 200)));
        list.add(new RoseChartData(null, (int) (Math.random() * 200), "#FFFAAD14", (float) (Math.random() * 200)));
        list.add(new RoseChartData(null, (int) (Math.random() * 200), "#FFEE3E3E", (float) (Math.random() * 200)));
        list.add(new RoseChartData(null, (int) (Math.random() * 200), "#FFBBBBBB", (float) (Math.random() * 200)));
    }
    
    private void randomPieList() {
        list.clear();
        float radius = (float) (Math.random() * 200);
        radius = (radius > 100 ? radius : 100);
        list.add(new RoseChartData(null, (int) (Math.random() * 200), "#FF40A9FF", radius));
        list.add(new RoseChartData(null, (int) (Math.random() * 200), "#FF73B13B", radius));
        list.add(new RoseChartData(null, (int) (Math.random() * 200), "#FFFAAD14", radius));
        list.add(new RoseChartData(null, (int) (Math.random() * 200), "#FFEE3E3E", radius));
        list.add(new RoseChartData(null, (int) (Math.random() * 200), "#FFBBBBBB", radius));
    }
}
