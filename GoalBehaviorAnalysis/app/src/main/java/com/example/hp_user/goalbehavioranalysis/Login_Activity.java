package com.example.hp_user.goalbehavioranalysis;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
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


public class Login_Activity extends AppCompatActivity {

    public EditText t_account;
    private EditText t_password;
    Button b_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        SysApplication.getInstance().addActivity(this);

        b_login=(Button)findViewById(R.id.login);
        t_account = (EditText) findViewById(R.id.account_number);
        t_password = (EditText) findViewById(R.id.password);
        //隐藏密码
        t_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        //为退出系统设置监听
        Button exit_button=(Button)findViewById(R.id.exit2);
        exit_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                SysApplication.getInstance().exit();
            }
        });
     //   MyApplication1 myApp=(MyApplication1)getApplication() ;
        //Toast.makeText(Login_Activity.this,myApp.ip,Toast.LENGTH_SHORT).show();

        b_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//登陆按钮监听事件
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int result = login();
                            //login()为向php服务器提交请求的函数，返回数据类型为int
                            if (result == 0) {
                                Log.e("log_tag", "登陆成功！");
                               // Intent intent=new Intent(Login_Activity.this,InputOldPassword_Activity.class);
                                //intent.putExtra("extra_data",t_account.getText().toString());
                                //startActivity(intent);
                                //Toast toast=null;
                                Looper.prepare();
                                //Toast.makeText(Login_Activity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Login_Activity.this, Home_Activity.class);
                             //   i.putExtra("extra_data",t_account.getText().toString());
                                startActivity(i);
                                Looper.loop();
                            } else if (result == 1) {
                                Log.e("log_tag", "密码错误！");
                                //Toast toast=null;
                                Looper.prepare();
                                Toast.makeText(Login_Activity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            } else if (result == 2) {
                                Log.e("log_tag", "不存在该用户！");
                                //Toast toast=null;
                                Looper.prepare();
                                Toast.makeText(Login_Activity.this, "不存在该用户！", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();
            }
        });
    }
    /*
    *用户登录提交post请求
    * 向服务器提交数据json={"account":user_id,"password":input_pwd}
    * 返回JSON数据{"status":1}
    */
    private int login() throws IOException {
        int returnResult=0;
        /*获取用户名和密码*/
        MyApplication myApp = (MyApplication) getApplication();
       myApp.user_id=t_account.getText().toString();
        //String user_id=
        String input_pwd=t_password.getText().toString();
        if(myApp.user_id==null||myApp.user_id.length()<=0){
            Looper.prepare();
            Toast.makeText(Login_Activity.this,"请输入账号", Toast.LENGTH_LONG).show();
            Looper.loop();
            return 0;
        }
        if(input_pwd==null||input_pwd.length()<=0){
            Looper.prepare();
            Toast.makeText(Login_Activity.this,"请输入密码", Toast.LENGTH_LONG).show();
            Looper.loop();
            return 0;
        }

        JSONObject jsonObject=new JSONObject();
        try{
            //封装json数据
            jsonObject.put("account",myApp.user_id);
            jsonObject.put("password",input_pwd);

        }catch (JSONException e){
            e.printStackTrace();
        }
        String json = String.valueOf(jsonObject);
        String str="json="+json;



        String urlstr="http://"+myApp.ip+"/behavior_analysis/assets/php/isPassword.php";

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
            /*获取服务器返回的JSON数据*/
            JSONObject jsonObject1= new JSONObject(result);
            returnResult=jsonObject1.getInt("status");//获取JSON数据中status字段值
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "the Error parsing data "+e.toString());
        }
        return returnResult;
    }
}