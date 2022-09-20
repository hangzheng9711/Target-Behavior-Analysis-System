package com.example.hp_user.goalbehavioranalysis;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddWorkSpot extends AppCompatActivity {

    private EditText gongweihao;
    private EditText xzuobiao;
    private EditText yzuobiao;
    private EditText length;
    private EditText width;
    private EditText shexiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addworkspot_layout);
        SysApplication.getInstance().addActivity(this);

        final Intent intent =getIntent();
        String data=intent.getStringExtra("extra_data");
        int data1= Integer.parseInt(data)+1;
        data=String.valueOf(data1);

        gongweihao =(EditText)findViewById(R.id.edit_add1);
        xzuobiao=(EditText)findViewById(R.id.edit_add2);
        yzuobiao=(EditText)findViewById(R.id.edit_add3);
        length=(EditText)findViewById(R.id.edit_add4);
        width=(EditText)findViewById(R.id.edit_add5);
        shexiang=(EditText)findViewById(R.id.edit_add6);

        gongweihao.setText(data);

        //为返回主界面按钮设置监听
        Button return_home_11_button=(Button)findViewById(R.id.return_home11);
        return_home_11_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent return_home_11_intent = new Intent(AddWorkSpot.this,Home_Activity.class);
                startActivity(return_home_11_intent);
            }
        });
        //为确认按钮设置监听
        Button ok_button=(Button)findViewById(R.id.ok_add);
        ok_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//登陆按钮监听事件
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int result = add();

                            //login()为向php服务器提交请求的函数，返回数据类型为int
                            if (result == 0) {
                                Log.e("log_tag", "登陆成功！");
                                //Toast toast=null;
                                Looper.prepare();
                                Toast.makeText(AddWorkSpot.this, "添加成功！", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(AddWorkSpot.this, Home_Activity.class);
                                startActivity(i);
                                Looper.loop();
                            } else if (result == 1) {
                                Log.e("log_tag", "密码错误！");
                                //Toast toast=null;
                                Looper.prepare();
                                Toast.makeText(AddWorkSpot.this, "添加失败！", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            } /*else if (result == 2) {
                                Log.e("log_tag", "不存在该用户！");
                                //Toast toast=null;
                                Looper.prepare();
                                Toast.makeText(Login_Activity.this, "不存在该用户！", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }*/
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();
            }
        });
    }

    private int add() throws IOException {
        int returnResult=0;

        MyApplication myApp = (MyApplication) getApplication();


        String edit1=gongweihao.getText().toString();
        String edit2=xzuobiao.getText().toString();
        String edit3=yzuobiao.getText().toString();
        String edit4=length.getText().toString();
        String edit5=width.getText().toString();
        String edit6=shexiang.getText().toString();
        if(edit1==null||edit1.length()<=0){
            Looper.prepare();
            Toast.makeText(AddWorkSpot.this,"请输入工位号", Toast.LENGTH_LONG).show();
            Looper.loop();
            return 0;
        }
        if(edit2==null||edit2.length()<=0){
            Looper.prepare();
            Toast.makeText(AddWorkSpot.this,"请输入横坐标", Toast.LENGTH_LONG).show();
            Looper.loop();
            return 0;
        }
        if(edit3==null||edit3.length()<=0){
            Looper.prepare();
            Toast.makeText(AddWorkSpot.this,"请输入纵坐标", Toast.LENGTH_LONG).show();
            Looper.loop();
            return 0;
        }
        if(edit4==null||edit4.length()<=0){
            Looper.prepare();
            Toast.makeText(AddWorkSpot.this,"请输入长度", Toast.LENGTH_LONG).show();
            Looper.loop();
            return 0;
        }
        if(edit5==null||edit5.length()<=0){
            Looper.prepare();
            Toast.makeText(AddWorkSpot.this,"请输入宽度", Toast.LENGTH_LONG).show();
            Looper.loop();
            return 0;
        }
        if(edit6==null||edit6.length()<=0){
            Looper.prepare();
            Toast.makeText(AddWorkSpot.this,"请输入摄像头编号", Toast.LENGTH_LONG).show();
            Looper.loop();
            return 0;
        }

        JSONObject jsonObject=new JSONObject();
        try{
            //封装json数据
            jsonObject.put("station_number",edit1);
            jsonObject.put("abscissa",edit2);
            jsonObject.put("ordinate",edit3);
            jsonObject.put("length",edit4);
            jsonObject.put("width",edit5);
            jsonObject.put("video_number",edit6);

        }catch (JSONException e){
            e.printStackTrace();
        }
        String json = String.valueOf(jsonObject);
        String str="json="+json;

        String urlstr="http://"+myApp.ip+"/behavior_analysis/assets/php/addStation.php";

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

        try {
            //获取服务器返回的JSON数据
            JSONObject jsonObject1= new JSONObject(result);
            returnResult=jsonObject1.getInt("status");//获取JSON数据中status字段值
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "the Error parsing data "+e.toString());
        }
        return returnResult;
    }
}
