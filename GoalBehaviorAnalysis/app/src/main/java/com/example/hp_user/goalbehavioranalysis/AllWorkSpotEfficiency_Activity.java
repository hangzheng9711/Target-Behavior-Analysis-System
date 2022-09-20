package com.example.hp_user.goalbehavioranalysis;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class AllWorkSpotEfficiency_Activity extends AppCompatActivity {

    //为柱状图声明
    private BarChart zhuChart2;
    //private BarData mBarData;
    private ArrayList<String> xValues = new ArrayList<String>();
    private ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();

    //为spinner_4声明
    private static final String[] m_4={"各工位统计图","所有工位总工作效率","某工位年统计图","某工位月统计图"};
    private TextView view_4;
    private Spinner spinner_4;
    private ArrayAdapter<String> adapter_4;

    //为spinner_5声明
    private static final String[] m_5={"效率","时长"};
    private TextView view_5;
    private Spinner spinner_5;
    private ArrayAdapter<String> adapter_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allworkspotefficiency_layout);

        //柱状图
        zhuChart2= (BarChart) findViewById(R.id.Bar_Chart2);
        BarData mPieData = getBarData(4, 100);
        showBarChart(zhuChart2, mPieData);



        //为返回主界面按钮设置监听
        Button return_home_4_button=(Button)findViewById(R.id.return_home_5);
        return_home_4_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent return_home_4_intent = new Intent(AllWorkSpotEfficiency_Activity.this,Home_Activity.class);
                startActivity(return_home_4_intent);
            }
        });


        //为下拉列表m_4进行配置
        view_4 = (TextView) findViewById(R.id.spinnerText_4);
        spinner_4 = (Spinner) findViewById(R.id.spinner_4);
        //将可选内容与ArrayAdapter连接起来
        adapter_4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m_4);
        //设置下拉列表的风格
        adapter_4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter添加到spinner中
        spinner_4.setAdapter(adapter_4);
        //添加事件Spinner事件监听
        spinner_4.setOnItemSelectedListener(new AllWorkSpotEfficiency_Activity.SpinnerSelectedListener());
        //设置默认值
        spinner_4.setVisibility(View.VISIBLE);

        spinner_4.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(arg2==1) {
                    Intent intent_1 = new Intent(AllWorkSpotEfficiency_Activity.this, CheckAnalysisOutcome_Activity.class);
                    startActivity(intent_1);
                }
                 if(arg2==2) {
                   Intent intent_2 = new Intent(AllWorkSpotEfficiency_Activity.this, WorkSpotYear_Activity.class);
                 startActivity(intent_2);
                }
                 if(arg2==3) {
                   Intent intent_3 = new Intent(AllWorkSpotEfficiency_Activity.this, WorkSpotMonth_Activity.class);
                 startActivity(intent_3);
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }}
        );

        //为下拉列表m_5进行配置
        view_5 = (TextView) findViewById(R.id.spinnerText_5);
        spinner_5 = (Spinner) findViewById(R.id.spinner_5);
        //将可选内容与ArrayAdapter连接起来
        adapter_5= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m_5);
        //设置下拉列表的风格
        adapter_5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter添加到spinner中
        spinner_5.setAdapter(adapter_5);
        //添加事件Spinner事件监听
        spinner_5.setOnItemSelectedListener(new AllWorkSpotEfficiency_Activity.SpinnerSelectedListener());
        //设置默认值
        spinner_5.setVisibility(View.VISIBLE);

        spinner_5.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(arg2==1) {
                    Intent intent1 = new Intent(AllWorkSpotEfficiency_Activity.this, AllWorkSpot_Activity.class);
                    startActivity(intent1);
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }}
        );


    }

    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener{
        public void onItemSelected(AdapterView<?>arg0,View arg1,int arg2,long arg3){
            //     view.setText("显示方式"+m[arg2]);
        }
        public void onNothingSelected(AdapterView<?>arg0){

        }
    }

    //柱状图
    private void showBarChart(BarChart barChart, BarData barData) {
        barChart.setDrawBorders(false);  ////是否在折线图上添加边框


        //  XAxis xAxis = barChart.getXAxis();


        barChart.setDescription("工位号");// 数据描述
        barChart.setDescriptionPosition(470,510);//数据描述的位置

        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        barChart.setNoDataTextDescription("You need to provide data for the chart.");

        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

        barChart.setTouchEnabled(true); // 设置是否可以触摸

        barChart.setDragEnabled(true);// 是否可以拖拽
        barChart.setScaleEnabled(true);// 是否可以缩放

        barChart.setPinchZoom(false);//

//      barChart.setBackgroundColor();// 设置背景

        barChart.setDrawBarShadow(true);

        barChart.setData(barData); // 设置数据

        Legend mLegend = barChart.getLegend(); // 设置比例图标示

        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.BLACK);// 颜色

//      X轴设定
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.animateX(2500); // 立即执行的动画,x轴
    }



    public BarData getBarData(int count, float range) {
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            xValues.add("" + (i + 1) + "");// 设置每个柱壮图的文字描述
        }

        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float value = (float) ((Math.random() * range/*1以内的随机数*/) + 3)/100;
            yValues.add(new BarEntry(value, i));
        }
        // y轴的数据集合
        BarDataSet barDataSet = new BarDataSet(yValues, "各工位异常工作时间");
        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            colors.add(Color.parseColor("#00FFFF"));
            // colors.add(Color.parseColor(color[i]));
        }
        barDataSet.setColors(colors);
        // 设置栏阴影颜色
        barDataSet.setBarShadowColor(Color.parseColor("#01000000"));
        ArrayList<BarDataSet> barDataSets = new ArrayList<>();
        barDataSets.add(barDataSet);
        barDataSet.setValueTextColor(Color.parseColor("#FF8F9D"));
        // 绘制值
        barDataSet.setDrawValues(true);
        BarData barData = new BarData(xValues, barDataSets);
        return barData;
    }

}
