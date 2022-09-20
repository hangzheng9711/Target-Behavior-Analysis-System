package com.example.hp_user.goalbehavioranalysis;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

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
import java.lang.Runnable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhuFragment extends Fragment {

    private EditText year1;
    private EditText month1;
    private Button button;
    private int length=0;
    //为柱状图声明
    private BarChart zhuChart1;
    //private BarData mBarData;
    private ArrayList<String> xValues = new ArrayList<String>();
    private ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();

   String[][] shuzu = new String[100][4];



  private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    //获得消息数据
                   String data = (String)msg.obj;
                    //解析json
                    jsonJX(data);
                   BarData mPieData = getBarData(length,100);
                   showBarChart(zhuChart1, mPieData);
                    // Toast.makeText(getActivity(),length+"", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;

            }
        }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.zhuchart1, container, false);
        year1 = (EditText)view.findViewById(R.id.edit_year_all1);
        month1=(EditText)view.findViewById(R.id.edit_month_all1);
        button=(Button)view.findViewById(R.id.button_allworkok);
        //柱状图
        zhuChart1= (BarChart) view.findViewById(R.id.Bar_Chart1);

       button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//登陆按钮监听事件
             new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            String result = zhuchart();

                            if(result!=null) {
                                Looper.prepare();
                                jsonJX(result);
                               // showZhuchart();
                              //  Toast.makeText(getActivity(),shuzu[0][0], Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }



                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();

           //  BarData mPieData = getBarData(4,100);
             //   showBarChart(zhuChart1, mPieData);

            }
        });

        return  view;
    }

    //获取数据
    //获取查看统计分析数据
  private String zhuchart() throws IOException {
        String returnResult=null;
        String year2=year1.getText().toString();
        String month2=month1.getText().toString();
        if(year2==null||year2.length()<=0){
            Looper.prepare();
            Toast.makeText(getActivity(),"请输入年", Toast.LENGTH_LONG).show();
            Looper.loop();
            return null;
        }
        if(month2==null||month2.length()<=0){
            Looper.prepare();
            Toast.makeText(getActivity(),"请输入月", Toast.LENGTH_LONG).show();
            Looper.loop();
            return null;
        }

        MyApplication myApp = (MyApplication) getActivity().getApplication();

        JSONObject jsonObject=new JSONObject();
        try{
            //封装json数据
            jsonObject.put("year",year2);
            jsonObject.put("month",month2);

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
    //  String result= sb.toString();//返回结果
        returnResult= sb.toString();//返回结果

       Message message = new Message();
        message.what=0;
        message.obj=returnResult;
        handler.sendMessage(message);

        //try {
            //获取服务器返回的JSON数据
        //   JSONObject jsonObject1= new JSONObject(result);
        // returnResult=jsonObject1.getInt("status");//获取JSON数据中status字段值
        // } catch (Exception e) {
        // TODO: handle exception
        //  Log.e("log_tag", "the Error parsing data "+e.toString());
        // }
        return returnResult;
    }

    private void jsonJX(String data){
        try {
            JSONArray jsonArray = new JSONArray(data);
            length=jsonArray.length();
            for(int i=0;i<jsonArray.length();i++){
              JSONObject  object=jsonArray.getJSONObject(i);
                try {
                    String station_count = object.getString("station_count");
                    String abnormal_time = object.getString("abnormal_time");
                    String total_time =object.getString("total_time");
                    shuzu[i][0]=station_count;
                    shuzu[i][1]=abnormal_time;
                    shuzu[i][2]=total_time;
                   // Toast.makeText(getActivity(),shuzu[i][0],Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
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
          //  float value = (float) ((Math.random() * range/*1以内的随机数*/) + 3)/100;
            float value =  Float.parseFloat(shuzu[i][1]);
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

  /*  private  void showZhuchart()
    {
       getActivity().runOnUiThread(new Runnable(){
           @Override
            public void run(){
              //  BarData mPieData = getBarData(shuzu,100);
                // showBarChart(zhuChart1, mPieData);
              // Toast.makeText(getActivity(),1+"",Toast.LENGTH_SHORT).show();

           }
        });
    }*/


}
