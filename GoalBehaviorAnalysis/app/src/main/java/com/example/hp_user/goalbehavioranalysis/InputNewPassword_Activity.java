package com.example.hp_user.goalbehavioranalysis;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class InputNewPassword_Activity extends AppCompatActivity {

    private EditText editText_newpassword;
    private EditText editText_newtwopassword;
    Button b_ok2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputnewpassword_layout);
        SysApplication.getInstance().addActivity(this);

        editText_newpassword = (EditText) findViewById(R.id.new_password);
        editText_newtwopassword = (EditText) findViewById(R.id.new_two_password);
        //隐藏密码
        editText_newpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        editText_newtwopassword.setTransformationMethod(PasswordTransformationMethod.getInstance());


        //为返回主界面按钮设置监听
        Button return_home_button = (Button) findViewById(R.id.return_home_10);
        b_ok2=(Button)findViewById(R.id.ack_newpassword);
        return_home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent return_home_intent = new Intent(InputNewPassword_Activity.this, Home_Activity.class);
                startActivity(return_home_intent);
            }
        });
        b_ok2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//登陆按钮监听事件
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int result = ack2();

                            //login()为向php服务器提交请求的函数，返回数据类型为int
                            if (result == 0) {
                                Log.e("log_tag", "登陆成功！");
                                //Toast toast=null;
                                Looper.prepare();
                                Toast.makeText(InputNewPassword_Activity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                               Intent i = new Intent(InputNewPassword_Activity.this, Login_Activity.class);
                                startActivity(i);
                                Looper.loop();
                            } else if (result == 1) {
                                Log.e("log_tag", "密码错误！");
                                //Toast toast=null;
                                Looper.prepare();
                                Toast.makeText(InputNewPassword_Activity.this, "修改失败！", Toast.LENGTH_SHORT).show();
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


    private int ack2() throws IOException {
        int returnResult=0;
        /*获取用户名密码*/
        //获取用户名
       // Intent intent=getIntent();
        //final String user_id = intent.getStringExtra("extra_data");
         //Toast.makeText(InputNewPassword_Activity.this,user_id+"",Toast.LENGTH_LONG).show();

        MyApplication myApp = (MyApplication) getApplication();

        String input_pwd1=editText_newpassword.getText().toString();
        String input_pwd2=editText_newtwopassword.getText().toString();
       if(input_pwd1==null||input_pwd1.length()<=0){
            Looper.prepare();
            Toast.makeText(InputNewPassword_Activity.this,"请输入密码", Toast.LENGTH_LONG).show();
            Looper.loop();
            return 0;
        }
        if(input_pwd2==null||input_pwd2.length()<=0){
            Looper.prepare();
            Toast.makeText(InputNewPassword_Activity.this,"请输入密码", Toast.LENGTH_LONG).show();
            Looper.loop();
            return 0;
        }
        if(input_pwd1!=null&&input_pwd2!=null&&input_pwd1.length()>0&&input_pwd2.length()>0&&input_pwd1.equals(input_pwd2)==false)
        {
            Looper.prepare();
            Toast.makeText(InputNewPassword_Activity.this,"两次密码不匹配，请重新输入", Toast.LENGTH_LONG).show();
            Looper.loop();
            return 0;
        }

        JSONObject jsonObject=new JSONObject();
        try{
            //封装json数据
            jsonObject.put("chaccount",myApp.user_id);
            jsonObject.put("chpassword",input_pwd1);

        }catch (JSONException e){
            e.printStackTrace();
        }
        String json = String.valueOf(jsonObject);
        String str="json="+json;

        String urlstr="http://"+myApp.ip+"/behavior_analysis/assets/php/chPassword.php";

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
