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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class DayOfMonthEfficiency_Activity extends AppCompatActivity {

    //声明LineChart
    private LineChart mLineChart4;
    //  private Typeface mTf;

    //为spinner_12声明
    private static final String[] m_12={"某工位月统计图","所有工位总工作效率","各工位统计图","某工位年统计图"};
    private TextView view_12;
    private Spinner spinner_12;
    private ArrayAdapter<String> adapter_12;

    //为spinner_13声明
    private static final String[] m_13={"效率","时长"};
    private TextView view_13;
    private Spinner spinner_13;
    private ArrayAdapter<String> adapter_13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dayofmonthefficiency_layout);


        //折线图
        mLineChart4 = (LineChart) findViewById(R.id.LineChart4);

        //      mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        LineData mLineData = getLineData(36, 100);
        showChart(mLineChart4, mLineData, Color.rgb(114, 188, 223));

        //为返回主界面按钮设置监听
        Button return_home_9_button=(Button)findViewById(R.id.return_home_9);
        return_home_9_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent return_home_9_intent = new Intent(DayOfMonthEfficiency_Activity.this,Home_Activity.class);
                startActivity(return_home_9_intent);
            }
        });


        //为下拉列表m_12进行配置
        view_12 = (TextView) findViewById(R.id.spinnerText_12);
        spinner_12= (Spinner) findViewById(R.id.spinner_12);
        //将可选内容与ArrayAdapter连接起来
        adapter_12 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m_12);
        //设置下拉列表的风格
        adapter_12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter添加到spinner中
        spinner_12.setAdapter(adapter_12);
        //添加事件Spinner事件监听
        spinner_12.setOnItemSelectedListener(new DayOfMonthEfficiency_Activity.SpinnerSelectedListener());
        //设置默认值
        spinner_12.setVisibility(View.VISIBLE);

        spinner_12.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(arg2==1) {
                    Intent intent_1 = new Intent(DayOfMonthEfficiency_Activity.this,CheckAnalysisOutcome_Activity.class);
                    startActivity(intent_1);
                }
                if(arg2==2) {
                    Intent intent_2 = new Intent(DayOfMonthEfficiency_Activity.this, AllWorkSpot_Activity.class);
                    startActivity(intent_2);
                }
                if(arg2==3) {
                    Intent intent_3 = new Intent(DayOfMonthEfficiency_Activity.this, WorkSpotYear_Activity.class);
                    startActivity(intent_3);
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }}
        );

        //为下拉列表m_13进行配置
        view_13 = (TextView) findViewById(R.id.spinnerText_13);
        spinner_13= (Spinner) findViewById(R.id.spinner_13);
        //将可选内容与ArrayAdapter连接起来
        adapter_13 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m_13);
        //设置下拉列表的风格
        adapter_13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter添加到spinner中
        spinner_13.setAdapter(adapter_13);
        //添加事件Spinner事件监听
        spinner_13.setOnItemSelectedListener(new DayOfMonthEfficiency_Activity.SpinnerSelectedListener());
        //设置默认值
        spinner_13.setVisibility(View.VISIBLE);

        spinner_13.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                 if(arg2==1) {
                   Intent intent1 = new Intent(DayOfMonthEfficiency_Activity.this, WorkSpotMonth_Activity.class);
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

    // 折线图设置显示的样式
    private void showChart(LineChart lineChart, LineData lineData, int color) {
        lineChart.setDrawBorders(false);  //是否在折线图上添加边框

        // no description text
        lineChart.setDescription("号");// 数据描述
        lineChart.setDescriptionPosition(485,510);//数据描述的位置
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid background
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色
        lineChart.setGridBackgroundColor(Color.WHITE ); // 表格的的颜色，在这里是是给颜色设置一个透明度

        // enable touch gestures
        lineChart.setTouchEnabled(true); // 设置是否可以触摸

        // enable scaling and dragging
        lineChart.setDragEnabled(true);// 是否可以拖拽
        lineChart.setScaleEnabled(true);// 是否可以缩放

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);//

        //lineChart.setBackgroundColor(color);// 设置背景

        // add data
        lineChart.setData(lineData); // 设置数据

        // get the legend (only possible after setting data)
        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的

        // modify the legend ...
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(8f);// 字体
        mLegend.setTextColor(Color.BLACK);// 颜色


        //      mLegend.setTypeface(mTf);// 字体
        // x轴设定
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        lineChart.animateX(2500); // 立即执行的动画,x轴
    }
    /**
     * 生成一个数据
     * @param count 表示图表中有多少个坐标点
     * @param range 用来生成range以内的随机数
     * @return
     */
    private LineData getLineData(int count, float range) {
        count=32;
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            // x轴显示的数据，这里默认使用数字下标显示
            xValues.add("" + i);
        }

        // y轴的数据
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float value = (float) ((Math.random() * range) + 3)/100;
            yValues.add(new Entry(value, i));
        }

        // create a dataset and give it a type
        // y轴的数据集合
        LineDataSet dataSet1 = new LineDataSet(yValues, "每天工作效率");
        // mLineDataSet.setFillAlpha(110);



        //用y轴的集合来设置参数
        dataSet1.setLineWidth(1.75f); // 线宽
        dataSet1.setCircleSize(3f);// 显示的圆形大小
        //lineDataSet.setColor(Color.BLUE);// 显示颜色
        dataSet1.setCircleColor(Color.BLUE);// 圆形的颜色
        dataSet1.setHighLightColor(Color.BLUE); // 高亮的线的颜色

        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        lineDataSets.add(dataSet1); // add the datasets

        // create a data object with the datasets
        LineData lineData = new LineData(xValues, lineDataSets);

        return lineData;
    }
}
