package com.example.hp_user.goalbehavioranalysis;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import android.graphics.Color;
import android.util.DisplayMetrics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CheckAnalysisOutcome_Activity extends AppCompatActivity {

    //为饼状图声明
    private PieChart bingChart;

    //为spinner声明
    private static final String[] m_1={"所有工位总工作效率","各工位统计图","某工位年统计图","某工位月统计图"};
    private TextView view;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private String yichangproportion;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkanalysisoutcome_layout);

     //接收饼状图数据
        Intent intent =getIntent();
        String data=intent.getStringExtra("extra_data");

        try {
            /*获取服务器返回的JSON数据*/
            JSONObject jsonObject1= new JSONObject(data);
            yichangproportion=jsonObject1.getString("ab_proportion");//获取JSON数据中status字段值
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "the Error parsing data "+e.toString());
        }

        float yichangdata = Float.parseFloat(yichangproportion)*100;


        //饼状图
        bingChart= (PieChart) findViewById(R.id.bingPieChart);
                PieData mPieData = getPieData(4, 100,yichangdata);
                showChart(bingChart, mPieData);


        //为返回主界面按钮设置监听

        Button return_home_3_button=(Button)findViewById(R.id.return_home_3);
        return_home_3_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent return_home_3_intent = new Intent(CheckAnalysisOutcome_Activity.this,Home_Activity.class);
                startActivity(return_home_3_intent);
            }
        });


        //为下拉列表进行配置
        view = (TextView) findViewById(R.id.spinnerText_1);
        spinner = (Spinner) findViewById(R.id.spinner_1);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m_1);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter添加到spinner中
        spinner.setAdapter(adapter);
        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        //设置默认值
        spinner.setVisibility(View.VISIBLE);

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(arg2==1) {

                    Intent intent_1 = new Intent(CheckAnalysisOutcome_Activity.this, AllWorkSpot_Activity.class);
                    startActivity(intent_1);
                }
                if(arg2==2) {
                    Intent intent_2 = new Intent(CheckAnalysisOutcome_Activity.this, WorkSpotYear_Activity.class);
                    startActivity(intent_2);
                }
                 if(arg2==3) {
                   Intent intent_3 = new Intent(CheckAnalysisOutcome_Activity.this, WorkSpotMonth_Activity.class);
                 startActivity(intent_3);
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }}
        );


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
               mLegend.setPosition(LegendPosition.RIGHT_OF_CHART);  //最右边显示
            //      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
               mLegend.setXEntrySpace(10f);
             mLegend.setYEntrySpace(0f);
            mLegend.setWordWrapEnabled(true);
            mLegend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);


        pieChart.animateXY(1000, 1000);  //设置动画
              // mChart.spin(2000, 0, 360);
            }

    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener{
        public void onItemSelected(AdapterView<?>arg0,View arg1,int arg2,long arg3){
            //     view.setText("显示方式"+m[arg2]);
        }
        public void onNothingSelected(AdapterView<?>arg0){

        }
    }


  /*  //获取查看统计分析数据
    private String checkallworkspot() throws IOException {
        String returnResult=null;

        MyApplication myApp = (MyApplication) getApplication();

        String urlstr="http://"+myApp.ip+"/behavior_analysis/assets/php/getPieData.php";

        //建立网络连接
        URL url = new URL(urlstr);
        HttpURLConnection http= (HttpURLConnection) url.openConnection();

        //往网页写入POST数据，和网页POST方法类似，参数间用‘&’连接
        http.setDoOutput(true);
        http.setRequestMethod("POST");
        // OutputStream out=http.getOutputStream();
        // out.write(str.getBytes());//post提交参数
        // out.flush();
        // out.close();

        //读取网页返回的数据
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(http.getInputStream()));//获得输入流
        String line="";
        StringBuilder sb=new StringBuilder();//建立输入缓冲区
        while (null!=(line=bufferedReader.readLine())){//结束会读入一个null值
            sb.append(line);//写缓冲区
        }
        returnResult= sb.toString();//返回结果

        //try {
            /*获取服务器返回的JSON数据
        //   JSONObject jsonObject1= new JSONObject(result);
        // returnResult=jsonObject1.getInt("status");//获取JSON数据中status字段值
        // } catch (Exception e) {
        // TODO: handle exception
        //  Log.e("log_tag", "the Error parsing data "+e.toString());
        // }
        return returnResult;
    }
*/



}
