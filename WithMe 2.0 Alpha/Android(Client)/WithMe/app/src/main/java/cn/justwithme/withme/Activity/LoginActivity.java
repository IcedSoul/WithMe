package cn.justwithme.withme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import cn.justwithme.withme.Entity.User;
import cn.justwithme.withme.R;
import cn.justwithme.withme.Utils.HttpUtils;
import cn.justwithme.withme.WebSocket.ClientService;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    private EditText userNameInput;
    private EditText passwordInput;
    private String userName;
    private String userPassword;
    private String user;
    private Button login;
    private Button register;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameInput = (EditText)findViewById(R.id.userName);
        passwordInput = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.register);
        userNameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    userNameInput.setHint("");
                else
                    userNameInput.setHint("用户名");
            }
        });
        passwordInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    passwordInput.setHint("");
                else
                    passwordInput.setHint("密码");
            }
        });
//        userNameInput.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                userNameInput.setHint("");
//            }
//        });

//        passwordInput.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                passwordInput.setHint("");
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameInput.getText().toString();
                userPassword = passwordInput.getText().toString();
                user = "userName="+userName+"&userPassword="+userPassword;
                if(!userName.equals("") && !userName.equals("") && !userPassword.equals("") && !userPassword.equals("")){
                    new Thread() {
                        public void run() {
                            String response = HttpUtils.doPostRequest("doLogin",user);
                            Message message = Message.obtain();
                            message.obj = response;
                            mHandler.sendMessage(message);
                        }
                    }.start();
                    mHandler= new Handler() {
                        @Override
                        public void handleMessage(Message message) {
                            JSONObject resultJson = JSON.parseObject((String) message.obj);
                            String finalResult = resultJson.getString("result");
                            if(finalResult.equals("success")){
                                User user = JSON.parseObject(resultJson.getString("user"),User.class);
                                SweetAlertDialog svd = new SweetAlertDialog(LoginActivity.this);
                                svd.setTitleText("登录成功");
                                svd.setContentText("欢迎来到WithMe");
                                svd.setConfirmText("好的");
                                svd.show();
//                                Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                                Intent clientIntent = new Intent();
                                clientIntent.putExtra("currentUserId",String.valueOf(user.getUserId()));
                                clientIntent.setClass(LoginActivity.this, ClientService.class);
                                System.out.println("新登录用户id为："+user.getUserId());
                                startService(clientIntent);
                                Intent intent = new Intent();
                                intent.setClass(LoginActivity.this,NaviActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("currentUser",user);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                            else if(finalResult.equals("unexist")){
                                SweetAlertDialog svd = new SweetAlertDialog(LoginActivity.this);
                                svd.setTitleText("登录失败");
                                svd.setContentText("是不是用户名记错了？");
                                svd.setConfirmText("重新输入");
                                svd.show();
                            }
//                                Toast.makeText(getApplicationContext(), "是不是用户名记错了？", Toast.LENGTH_SHORT).show();
                            else if(finalResult.equals("wrong")){
                                SweetAlertDialog svd = new SweetAlertDialog(LoginActivity.this);
                                svd.setTitleText("登录失败");
                                svd.setContentText("密码好像不对呢");
                                svd.setConfirmText("重新输入");
                                svd.show();
                            }
//                                Toast.makeText(getApplicationContext(), "密码不对呢", Toast.LENGTH_SHORT).show();
                        }
                    };
                }
                else{
                    SweetAlertDialog svd = new SweetAlertDialog(LoginActivity.this);
                    svd.setTitleText("用户名和密码不能为空");
                    svd.setConfirmText("重新输入");
//                    svd.setContentText("用户名和密码不能为空");
                    svd.show();
//                    Toast.makeText(getApplicationContext(), "用户名密码不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                //测试
//                Intent intent = new Intent(LoginActivity.this,NaviActivity.class);
//                startActivity(intent);
            }
        });
    }


}
