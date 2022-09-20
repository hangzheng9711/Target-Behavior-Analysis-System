package com.example.hp_user.goalbehavioranalysis;

import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;

import static android.R.id.list;
import static android.icu.util.Calendar.getInstance;

import java.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.widget.CalendarView;
import java.util.Date;
import android.os.Handler;
import android.os.Message;

public class Home_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        SysApplication.getInstance().addActivity(this);

        Intent intent=getIntent();
        final String data = intent.getStringExtra("extra_data");
       // Toast.makeText(Home_Activity.this,data+"",Toast.LENGTH_LONG).show();



        //为退出登录设置监听
        Button logout_button=(Button)findViewById(R.id.logout);
        logout_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent logout_intent = new Intent(Home_Activity.this,Login_Activity.class);
                startActivity(logout_intent);
            }
        });

        //为退出系统设置监听
        Button exit_button=(Button)findViewById(R.id.exit);
        exit_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                SysApplication.getInstance().exit();
            }
        });

        //为查看工位信息按钮设置监听
        Button check_workspot_button=(Button)findViewById(R.id.check_workspot);

        check_workspot_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//查看工位信息按钮监听事件
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                           String result = checkworkspot();
                            if(result!=null) {
                                Looper.prepare();
                                Intent i = new Intent(Home_Activity.this, CheckWorkSpot_Activity.class);
                               i.putExtra("extra_data", result);
                                startActivity(i);
                                Looper.loop();
                            }


                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();
            }
        });

        //为查看行为信息按钮设置监听
        Button check_behavior_button=(Button)findViewById(R.id.check_behavior);
        check_behavior_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = checkbehavior();
                            if(result!=null) {
                                Looper.prepare();
                                Intent i = new Intent(Home_Activity.this, CheckBehavior_Activity.class);
                                i.putExtra("extra_data", result);
                                startActivity(i);
                                Looper.loop();
                            }


                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();
            }
        });


        //为查看统计分析结果按钮设置监听
        Button check_analysis_outcome_button=(Button)findViewById(R.id.check_analysis_outcome);
        check_analysis_outcome_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = checkoutcome();
                            if(result!=null) {
                                Looper.prepare();
                                Intent i = new Intent(Home_Activity.this, CheckOutcome.class);
                                i.putExtra("extra_data", result);
                                startActivity(i);
                                Looper.loop();
                            }


                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();
            }
        });

        //为修改密码设置监听
        Button chword_button=(Button)findViewById(R.id.change_password);
        chword_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent chword_intent = new Intent(Home_Activity.this,InputOldPassword_Activity.class);
                chword_intent.putExtra("extra_data",data);
                startActivity(chword_intent);
            }
        });



    }

    //获取查看行为信息
    private String checkbehavior() throws IOException {
        String returnResult=null;
       SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
        String year= sdf.format(new java.util.Date());

        MyApplication myApp = (MyApplication) getApplication();


        JSONObject jsonObject=new JSONObject();
        try{
            //封装json数据
            jsonObject.put("year",year);
        }catch (JSONException e){
            e.printStackTrace();
        }
        String json = String.valueOf(jsonObject);
        String str="json="+json;


        String urlstr="http://"+myApp.ip+"/behavior_analysis/assets/php/getBehavior2.php";

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
        returnResult= sb.toString();//返回结果

        //try {
            /*获取服务器返回的JSON数据*/
        //   JSONObject jsonObject1= new JSONObject(result);
        // returnResult=jsonObject1.getInt("status");//获取JSON数据中status字段值
        // } catch (Exception e) {
        // TODO: handle exception
        //  Log.e("log_tag", "the Error parsing data "+e.toString());
        // }
        return returnResult;
    }

    //获取查看工位信息
    private String checkworkspot() throws IOException {
        String returnResult=null;

        MyApplication myApp = (MyApplication) getApplication();

        String urlstr="http://"+myApp.ip+"/behavior_analysis/assets/php/getStation_all.php";

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
            /*获取服务器返回的JSON数据*/
        //   JSONObject jsonObject1= new JSONObject(result);
        // returnResult=jsonObject1.getInt("status");//获取JSON数据中status字段值
        // } catch (Exception e) {
        // TODO: handle exception
        //  Log.e("log_tag", "the Error parsing data "+e.toString());
        // }
        return returnResult;
    }



    //获取查看统计分析数据
    private String checkoutcome() throws IOException {
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
            /*获取服务器返回的JSON数据*/
        //   JSONObject jsonObject1= new JSONObject(result);
        // returnResult=jsonObject1.getInt("status");//获取JSON数据中status字段值
        // } catch (Exception e) {
        // TODO: handle exception
        //  Log.e("log_tag", "the Error parsing data "+e.toString());
        // }
        return returnResult;
    }


}


