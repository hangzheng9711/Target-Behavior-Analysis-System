package com.example.hp_user.goalbehavioranalysis;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment {

    private EditText day_gongweihao;
    private EditText day_year;
    private EditText day_month;
    //声明LineChart
    private LineChart mLineChart_day;
    private Button button;
    private int length=0;

    String[][] array2 = new String[100][3];
    float [] Yvalule_day = new float[32];

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case 2:
                    //获得消息数据
                    String data = (String)msg.obj;
                    //解析json
                    jsonJX(data);

                    LineData mLineData = getLineData(length+1, 100,Yvalule_day);
                    showChart(mLineChart_day, mLineData, Color.rgb(114, 188, 223));
                    // float value =  Float.parseFloat(shuzu[0][0]);
                    //Toast.makeText(getActivity(),value+"", Toast.LENGTH_SHORT).show();


                    //BarData mPieData = getBarData(length,100);
                    // showBarChart(zhuChart1, mPieData);

                    break;
                default:
                    break;

            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.dayzhexian, container, false);

        day_gongweihao = (EditText)view.findViewById(R.id.workspot3_day) ;
        day_year =(EditText)view.findViewById(R.id.year_day) ;
        day_month=(EditText)view.findViewById(R.id.month_day) ;
        button = (Button)view.findViewById(R.id.ack_month_day);
        //折线图
        mLineChart_day = (LineChart)view.findViewById(R.id.LineChart_day);

        //      mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//登陆按钮监听事件
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            String result = zhexianchart();

                            if(result!=null) {
                                Looper.prepare();
                                //jsonJX(result);
                                // showZhuchart();
                                //  Toast.makeText(getActivity(),shuzu[0][0], Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }



                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();

                //   LineData mLineData = getLineData(13, 100);
                //  showChart(mLineChart1, mLineData, Color.rgb(114, 188, 223));

            }
        });


        return view;
    }
    //获取数据
    //获取查看统计分析数据
    private String zhexianchart() throws IOException {
        String returnResult=null;
        String workspot= day_gongweihao.getText().toString();
        String year=day_year.getText().toString();
        String month=day_month.getText().toString();
        if(workspot==null||workspot.length()<=0){
            Looper.prepare();
            Toast.makeText(getActivity(),"请输入工位号", Toast.LENGTH_LONG).show();
            Looper.loop();
            return null;
        }
        if(year==null||year.length()<=0){
            Looper.prepare();
            Toast.makeText(getActivity(),"请输入年", Toast.LENGTH_LONG).show();
            Looper.loop();
            return null;
        }
        if(month==null||month.length()<=0){
            Looper.prepare();
            Toast.makeText(getActivity(),"请输入月", Toast.LENGTH_LONG).show();
            Looper.loop();
            return null;
        }

        MyApplication myApp = (MyApplication) getActivity().getApplication();

        JSONObject jsonObject=new JSONObject();
        try{
            //封装json数据
            jsonObject.put("station_number",workspot);
            jsonObject.put("year",year);
            jsonObject.put("month",month);

        }catch (JSONException e){
            e.printStackTrace();
        }
        String json = String.valueOf(jsonObject);
        String str="json="+json;


        String urlstr="http://"+myApp.ip+"/behavior_analysis/assets/php/getGraphData11.php";

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
        message.what=2;
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
                    //String station_count = object.getString("station_count");
                    String abnormal_time = object.getString("abnormal_time");
                    String total_time =object.getString("total_time");
                    //shuzu[i][0]=station_count;
                    array2[i][0]=abnormal_time;
                    array2[i][1]=total_time;
                    Yvalule_day[i]=Float.parseFloat(array2[i][0]);
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

    // 折线图设置显示的样式
    private void showChart(LineChart lineChart, LineData lineData, int color) {
        lineChart.setDrawBorders(false);  //是否在折线图上添加边框

        // no description text
        lineChart.setDescription("月");// 数据描述
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
    public LineData getLineData(int count, float range,float[]ydata) {
        // count=13;
        ArrayList<String> xzuobiao = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            // x轴显示的数据，这里默认使用数字下标显示
            xzuobiao.add("" + i);
        }

        // y轴的数据
        ArrayList<Entry> yzhi = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            // float value = (float) (Math.random() * range) + 3;
            //  float value =  Float.parseFloat(array1[i][0]);
            yzhi.add(new Entry(ydata[i], i));
        }

        // create a dataset and give it a type
        // y轴的数据集合
        LineDataSet dataSet1 = new LineDataSet(yzhi, "每天异常行为时间");
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
        LineData lineData = new LineData(xzuobiao, lineDataSets);

        return lineData;
    }

}
