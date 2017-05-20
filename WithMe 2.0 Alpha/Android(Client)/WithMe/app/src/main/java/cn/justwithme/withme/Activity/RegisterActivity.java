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

import cn.justwithme.withme.R;
import cn.justwithme.withme.Utils.HttpUtils;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegisterActivity extends AppCompatActivity {

    private String result;
    private EditText userNameInput;
    private EditText userNickNameInput;
    private EditText passwordInput;
    private String userName;
    private String userNickName;
    private String userPassword;
    private String user;
    private Button login;
    private Button register;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userNameInput = (EditText)findViewById(R.id.userName);
        userNickNameInput = (EditText)findViewById(R.id.userNickName);
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
        userNickNameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    userNickNameInput.setHint("");
                else
                    userNickNameInput.setHint("昵称");
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
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameInput.getText().toString();
                userNickName = userNickNameInput.getText().toString();
                userPassword = passwordInput.getText().toString();
                user = "userName="+userName+"&userNickName="+userNickName+"&userPassword="+userPassword;
                if(!userName.equals("") && !userName.equals("") && !userPassword.equals("") && !userPassword.equals("")){
                    new Thread() {
                        public void run() {
                            String response = HttpUtils.doPostRequest("doRegister",user);
                            Message message = Message.obtain();
                            message.obj = response;
                            mHandler.sendMessage(message);
                        }
                    }.start();
                    mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message message) {
                            result = (String) message.obj;
                            JSONObject resultJson = JSON.parseObject(result);
                            String finalResult = resultJson.getString("result");

                            if(finalResult.equals("success")){
                                SweetAlertDialog svd = new SweetAlertDialog(RegisterActivity.this);
                                svd.setTitleText("注册错误");
                                svd.setConfirmText("重新输入");
                                svd.setContentText("该用户名已存在");
                                svd.show();
//                                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                            else if(finalResult.equals("exist")){
                                SweetAlertDialog svd = new SweetAlertDialog(RegisterActivity.this);
                                svd.setTitleText("注册错误");
                                svd.setConfirmText("重新输入");
                                svd.setContentText("该用户名已存在");
                                svd.show();
                            }
//                                Toast.makeText(getApplicationContext(), "用户名已存在", Toast.LENGTH_SHORT).show();
                        }
                    };
                }
                else{
                    SweetAlertDialog svd = new SweetAlertDialog(RegisterActivity.this);
                    svd.setTitleText("用户名、昵称、密码不能为空");
                    svd.setConfirmText("重新输入");
//                    svd.setContentText("用户名和密码不能为空");
                    svd.show();
//                    Toast.makeText(getApplicationContext(), "用户名、昵称、密码不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
