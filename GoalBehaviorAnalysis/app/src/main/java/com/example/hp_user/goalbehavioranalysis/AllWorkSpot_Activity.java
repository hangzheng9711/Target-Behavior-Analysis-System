package com.example.hp_user.goalbehavioranalysis;


import android.os.Handler;
import android.content.Intent;
import android.graphics.Color;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Handler;
import java.util.logging.LogRecord;




public class AllWorkSpot_Activity extends AppCompatActivity{

    public EditText year1;
    public EditText month1;

     public String shuzu[][];

    //为柱状图声明
    private BarChart zhuChart1;
    //private BarData mBarData;
    private ArrayList<String> xValues = new ArrayList<String>();
    private ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();


    //为spinner_2声明
    private static final String[] m_2={"各工位统计图","所有工位总工作效率","某工位年统计图","某工位月统计图"};
    private TextView view_2;
    private Spinner spinner_2;
    private ArrayAdapter<String> adapter_2;


    //为spinner_3声明
    private static final String[] m_3={"时长","效率"};
    private TextView view_3;
    private Spinner spinner_3;
    private ArrayAdapter<String> adapter_3;

   /* public static final int UPDATE_TEXT =1;
    private Handler handler=new Handler() {

        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                     BarData mPieData = getBarData(100,shuzu);
                     showBarChart(zhuChart1, mPieData);
                    break;
                    default:
                        break;

            }
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allworkspot_layout);
        SysApplication.getInstance().addActivity(this);

        year1 = (EditText) findViewById(R.id.edit_year_all1);
        month1 = (EditText) findViewById(R.id.edit_month_all1);

        //柱状图
        zhuChart1= (BarChart) findViewById(R.id.Bar_Chart1);
        BarData mPieData = getBarData(100,shuzu);
        showBarChart(zhuChart1, mPieData);



        //为返回主界面按钮设置监听
        Button return_home_4_button=(Button)findViewById(R.id.return_home_4);
        return_home_4_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent return_home_4_intent = new Intent(AllWorkSpot_Activity.this,Home_Activity.class);
                startActivity(return_home_4_intent);
            }
        });

        //确认按钮监听
        Button ok_button=(Button)findViewById(R.id.button_allworkok);
        ok_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//查看工位信息按钮监听事件
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = zhuchart();
                           // Toast.makeText(AllWorkSpot_Activity.this,shuzu[0][0],Toast.LENGTH_SHORT).show();
                            if(result!=null) {
                                Looper.prepare();
                                Looper.loop();
                            }


                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();
            }
        });


        //为下拉列表m_2进行配置
        view_2 = (TextView) findViewById(R.id.spinnerText_2);
        spinner_2 = (Spinner) findViewById(R.id.spinner_2);
        //将可选内容与ArrayAdapter连接起来
        adapter_2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m_2);
        //设置下拉列表的风格
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter添加到spinner中
        spinner_2.setAdapter(adapter_2);
        //添加事件Spinner事件监听
        spinner_2.setOnItemSelectedListener(new AllWorkSpot_Activity.SpinnerSelectedListener());
        //设置默认值
        spinner_2.setVisibility(View.VISIBLE);

        spinner_2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(arg2==1) {
                    Intent intent_1 = new Intent(AllWorkSpot_Activity.this, CheckAnalysisOutcome_Activity.class);
                    startActivity(intent_1);
                }
                 if(arg2==2) {
                   Intent intent_2 = new Intent(AllWorkSpot_Activity.this, WorkSpotYear_Activity.class);
                 startActivity(intent_2);
                }
                 if(arg2==3) {
                   Intent intent_3 = new Intent(AllWorkSpot_Activity.this, WorkSpotMonth_Activity.class);
                 startActivity(intent_3);
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }}
        );

        //为下拉列表m_3进行配置
        view_3 = (TextView) findViewById(R.id.spinnerText_3);
        spinner_3 = (Spinner) findViewById(R.id.spinner_3);
        //将可选内容与ArrayAdapter连接起来
        adapter_3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m_3);
        //设置下拉列表的风格
        adapter_3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter添加到spinner中
        spinner_3.setAdapter(adapter_3);
        //添加事件Spinner事件监听
        spinner_3.setOnItemSelectedListener(new AllWorkSpot_Activity.SpinnerSelectedListener());
        //设置默认值
        spinner_3.setVisibility(View.VISIBLE);

        spinner_3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(arg2==1) {

                      Intent intent1 = new Intent(AllWorkSpot_Activity.this, AllWorkSpotEfficiency_Activity.class);
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

        mLegend.setForm(LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.BLACK);// 颜色

//      X轴设定
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);

        barChart.animateX(2500); // 立即执行的动画,x轴
    }



    public BarData getBarData( float range,String s[][]) {
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            xValues.add(""+(i+1)+"");// 设置每个柱壮图的文字描述
        }

        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            float value =  Float.parseFloat(s[i][2]);
            yValues.add(new BarEntry(value, i));
        }
        // y轴的数据集合
        BarDataSet barDataSet = new BarDataSet(yValues, "  ");
        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
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



    private String zhuchart() throws IOException {
        String returnResult=null;

        MyApplication myApp = (MyApplication) getApplication();
       String allyear=year1.getText().toString();
        //String user_id=
        String allmonth=month1.getText().toString();
        if(allyear==null||allyear.length()<=0){
            Looper.prepare();
            Toast.makeText(AllWorkSpot_Activity.this,"请输入年", Toast.LENGTH_LONG).show();
            Looper.loop();
            return null;
        }
        if(allmonth==null||allmonth.length()<=0){
            Looper.prepare();
            Toast.makeText(AllWorkSpot_Activity.this,"请输入月", Toast.LENGTH_LONG).show();
            Looper.loop();
            return null;
        }

        JSONObject jsonObject=new JSONObject();
        try{
            //封装json数据
            jsonObject.put("year",allyear);
            jsonObject.put("month",allmonth);

        }catch (JSONException e){
            e.printStackTrace();
        }
        String json = String.valueOf(jsonObject);
        String str="json="+json;



        String urlstr="http://"+myApp.ip+"/behavior_analysis/assets/php/getGraphData22.php";

        //建立网络连接
        URL url = new URL(urlstr);
        HttpURLConnection http= (HttpURLConnection) url.openConnection();

        //往网页写入POST数据，和网页POST方法类似，参数间用‘&’连接
        http.setDoOutput(true);
        http.setRequestMethod("POST");
        OutputStream out=http.getOutputStream();
        out.write(str.getBytes());//post提交参数
        out.flush();
        out.close();

        //读取网页返回的数据
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(http.getInputStream()));//获得输入流
        String line="";
        StringBuilder sb=new StringBuilder();//建立输入缓冲区
        while (null!=(line=bufferedReader.readLine())){//结束会读入一个null值
            sb.append(line);//写缓冲区
        }
        String result= sb.toString();//返回结果


        jsonJX(result);

        return result;
    }
    private void jsonJX(String data)
    {
        try{
            JSONArray jsonArray = new JSONArray(data);
            for (int i =0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String station_count=jsonObject.getString("station_count");
                String abnormal_time=jsonObject.getString("abnormal_time");
                String total_time=jsonObject.getString("total_time");
                shuzu[i][0]=station_count;
                shuzu[i][1]=total_time;
                shuzu[i][2]=abnormal_time;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
