package com.example.hp_user.goalbehavioranalysis;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PieFragment extends Fragment {

    //为饼状图声明
    private PieChart bingChart;

    private String yichangproportion;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View view =   inflater.inflate(R.layout.piechart, container, false);
        Bundle bundle=this.getArguments();
        if(bundle!=null)
        {
            String str=bundle.getString("key");

            try {
            /*获取服务器返回的JSON数据*/
                JSONObject jsonObject1= new JSONObject(str);
                yichangproportion=jsonObject1.getString("ab_proportion");//获取JSON数据中status字段值
            } catch (Exception e) {
                // TODO: handle exception
                Log.e("log_tag", "the Error parsing data "+e.toString());
            }
        }
       // Toast.makeText(getActivity(),yichangproportion,Toast.LENGTH_SHORT).show();

        float yichangdata = Float.parseFloat(yichangproportion)*100;


        //饼状图
        bingChart= (PieChart) view.findViewById(R.id.bingPieChart1);
        PieData mPieData = getPieData(4, 100,yichangdata);
        showChart(bingChart, mPieData);


        return view;
    }

    //饼状图获取数据
    private PieData getPieData(int count, float range,float s) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容

        //     for (int i = 0; i < count; i++) {
        //          xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
        //    }
        xValues.add("异常工作时间");
        xValues.add("正常工作时间");


        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        /**
         * 将一个饼形图分成两部分， 四部分的数值比例为20:80
         * 所以 20代表的百分比就是20%
         */
        float abnormal_work_time =s ;
        float normal_work_time  = 100-abnormal_work_time;


        yValues.add(new Entry(abnormal_work_time, 0));
        yValues.add(new Entry(normal_work_time , 1));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, " "/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离
        ArrayList<Integer> colors = new ArrayList<Integer>();
        pieDataSet.setValueTextSize(10f);
        pieDataSet.setValueFormatter(new PercentFormatter());

        // 饼图颜色
        //  colors.add(Color.rgb(205, 205, 205));
        //  colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }


    //实现饼状图显示图形
    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(60f);  //半径
        pieChart.setTransparentCircleRadius(0f); // 半透明圈
        pieChart.setHoleRadius(0);  //实心圆

        pieChart.setDescription("  ");
        pieChart.setDescriptionPosition(385,550);

        //设置饼图右下角的文字大小
        pieChart.setDescriptionTextSize(14);




        pieChart.setDrawSliceText(false);
        pieChart.setDrawCenterText(false);  //饼状图中间不可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");

        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

        //      mChart.setOnAnimationListener(this);

        //pieChart.setCenterText("Quarterly Revenue");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
        //      pieChart.highlightValues(null);
        //      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
        //      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(10f);
        mLegend.setYEntrySpace(0f);
        mLegend.setWordWrapEnabled(true);
        mLegend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);


        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

}
