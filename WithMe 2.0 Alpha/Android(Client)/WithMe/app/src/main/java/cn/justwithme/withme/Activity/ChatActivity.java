package cn.justwithme.withme.Activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.justwithme.withme.Adapaer.ChatMessageAdapter;
import cn.justwithme.withme.Entity.DataBaseMessage;
import cn.justwithme.withme.Entity.MessageShow;
import cn.justwithme.withme.Entity.User;
import cn.justwithme.withme.R;
import cn.justwithme.withme.Utils.HttpUtils;
import cn.justwithme.withme.WebSocket.ClientService;


public class ChatActivity extends AppCompatActivity {
    private User currentUser;
    private User chattingUser;
    //定义控件
    private TextView chattingUserNameOutput;
    private ListView listView;
    private EditText textInput;
    private Button sendText;
    private List<MessageShow> showMessageList;
    private ChatMessageAdapter chatMessageAdapter;

    private ClientService clientService;
    public static MyHandler messageHandler;

    class MyHandler extends Handler{
            @Override
            public void handleMessage(Message msg) {
                String message = (String)msg.obj;
                System.out.println("Activity接收到了消息"+message);
                cn.justwithme.withme.Entity.Message message1 = JSON.parseObject(message,cn.justwithme.withme.Entity.Message.class);
                if(message1.getType() == -1 || message1.getType() == 0)
                    addMessageShow(message);
            }
    }

    //ServiceConnection
    ClientService.ClientBinder binder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        //Activity与Service连接成功时回调此方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("-----------ClientService is binded!-----------");
            binder = (ClientService.ClientBinder) service;
            clientService = (ClientService) binder.getService();
        }
        //Activity与Service断开连接时回调此方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("-----------ClientService is unbinded!-----------");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //绑定Service
        bindClientService();

        //初始化控件
        initView();

        //获取当前用户的信息
        getCurrentChattingUserInfo();

        //初始化数据
        initData();

        //初始化按钮监听
        initListener();

    }

    @Override
    protected void onDestroy(){
        unBindClientService();
        super.onDestroy();
    }

    //绑定Service
    private void bindClientService(){
        messageHandler = new MyHandler();
        Intent intent = new Intent();
        intent.setClass(ChatActivity.this,ClientService.class);
        bindService(intent,serviceConnection, Service.BIND_AUTO_CREATE);
    }

    //解除绑定Service
    private void unBindClientService(){
        unbindService(serviceConnection);
    }

    private void initView(){
        chattingUserNameOutput = (TextView) findViewById(R.id.chatting_user_name);
        textInput = (EditText) findViewById(R.id.text_input);
        sendText = (Button) findViewById(R.id.send_message);
        listView = (ListView) findViewById(R.id.message_list);
    }

    //初始化数据
    private void initData(){
        showMessageList = new ArrayList<MessageShow>();
        getMessageRecord();
        chatMessageAdapter = new ChatMessageAdapter(this,showMessageList);
        listView.setAdapter(chatMessageAdapter);
    }

    private void initListener(){
        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textInput.getText().toString();
                if(text.equals("") || text.isEmpty())
                    Toast.makeText(getApplicationContext(), "发送信息不能为空", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getApplicationContext(), "发送信息为"+text, Toast.LENGTH_SHORT).show();
                    clientService.client.sendMessage(currentUser.getUserId(),new int[]{chattingUser.getUserId()},text,0);
                    textInput.setText("");
                }
            }
        });
    }

    private void getMessageRecord(){
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                String response = (String)message.obj;
                JSONObject resultJson = JSON.parseObject(response);
                String allMessages = resultJson.getString("resoult");
                List<DataBaseMessage> messageRecord = new ArrayList<DataBaseMessage>();
                messageRecord = (List<DataBaseMessage>)JSON.parseArray(allMessages,DataBaseMessage.class);
                for(int i=0;i<messageRecord.size();i++){
                    MessageShow messageShow = new MessageShow();
                    DataBaseMessage dataBaseMessage = messageRecord.get(i);
                    if(dataBaseMessage.getFrom() == currentUser.getUserId()){
                        messageShow.setMessageType(1);
                    }
                    else
                        messageShow.setMessageType(0);
                    messageShow.setMessageStyle(0);
                    messageShow.setContents(dataBaseMessage.getContent());
                    messageShow.setTime(dataBaseMessage.getTime());
                    System.out.println("消息为："+messageShow.getContents());
                    showMessageList.add(messageShow);
                }
                System.out.print(allMessages);
                chatMessageAdapter.notifyDataSetChanged();
                listView.setSelection(chatMessageAdapter.getCount()-1);
            }
        };
        new Thread() {
            public void run() {
                String user = "userIdA="+currentUser.getUserId()+"&userIdB="+chattingUser.getUserId();
                String response = HttpUtils.doPostRequest("getMessageRecordBetweenUsers",user);
                Message message = Message.obtain();
                message.obj = response;
                mHandler.sendMessage(message);
            }
        }.start();
    }

    private void getCurrentChattingUserInfo() {
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        chattingUser = (User) intent.getSerializableExtra("chattingUser");
        chattingUserNameOutput.setText(chattingUser.getUserNickName());
    }

    public void addMessageShow(String message){
        cn.justwithme.withme.Entity.Message message1 = JSON.parseObject(message,cn.justwithme.withme.Entity.Message.class);
        MessageShow newMessageShow = new MessageShow();
        if(message1.getFrom() == currentUser.getUserId()){
            newMessageShow.setMessageType(1);
        }
        else
            newMessageShow.setMessageType(0);
        if(message1.getType()==0 || message1.getType() == -1)
            newMessageShow.setMessageStyle(1);
        newMessageShow.setContents(message1.getContent());
        newMessageShow.setTime(message1.getTime());
        showMessageList.add(newMessageShow);
        chatMessageAdapter.notifyDataSetChanged();
        listView.setSelection(chatMessageAdapter.getCount()-1);
    }

}
