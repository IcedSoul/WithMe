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
import cn.justwithme.withme.Entity.Group;
import cn.justwithme.withme.Entity.MessageShow;
import cn.justwithme.withme.Entity.User;
import cn.justwithme.withme.R;
import cn.justwithme.withme.Utils.HttpUtils;
import cn.justwithme.withme.WebSocket.ClientService;

public class GroupChatActivity extends AppCompatActivity {
    private User currentUser;
    private Group chattingGroup;
    //定义控件
    private TextView chattingUserNameOutput;
    private ListView listView;
    private EditText textInput;
    private Button sendText;
    private List<MessageShow> showMessageList;
    private ChatMessageAdapter chatMessageAdapter;

    private ClientService clientService;
    public static GroupChatActivity.MyHandler messageHandler;

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            String message = (String)msg.obj;
            System.out.println("Activity接收到了消息"+message);
            cn.justwithme.withme.Entity.Message message1 = JSON.parseObject(message,cn.justwithme.withme.Entity.Message.class);
            if(message1.getType() == 1)
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
        setContentView(R.layout.activity_group_chat);

        //绑定Service
        bindClientService();

        //初始化控件
        initView();

        //初始化按钮监听
        initListener();

        //获取当前用户的信息
        getCurrentChattingUserInfo();

        //初始化数据
        initData();

    }

    @Override
    protected void onDestroy(){
        unBindClientService();
        super.onDestroy();
    }

    //绑定Service
    private void bindClientService(){
        messageHandler = new GroupChatActivity.MyHandler();
        Intent intent = new Intent();
        intent.setClass(GroupChatActivity.this,ClientService.class);
        bindService(intent,serviceConnection, Service.BIND_AUTO_CREATE);
    }

    //解除绑定Service
    private void unBindClientService(){
        unbindService(serviceConnection);
    }

    private void initView(){
        chattingUserNameOutput = (TextView) findViewById(R.id.chatting_group_name);
        textInput = (EditText) findViewById(R.id.text_group_input);
        sendText = (Button) findViewById(R.id.send_group_message);
        listView = (ListView) findViewById(R.id.message_group_list);
    }

    //初始化数据
    private void initData(){
        showMessageList = new ArrayList<MessageShow>();
        chatMessageAdapter = new ChatMessageAdapter(this,showMessageList);
        listView.setAdapter(chatMessageAdapter);
        getMessageRecord();
    }

    private void initListener(){
        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = textInput.getText().toString();
                        if(text.equals("") || text.isEmpty())
                            Toast.makeText(getApplicationContext(), "发送信息不能为空", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(getApplicationContext(), "发送信息为"+text, Toast.LENGTH_SHORT).show();
                            int[] to = new int[2000];
                            System.out.println("群组成员为："+chattingGroup.getGroupMembers());
                            String[] usersId = chattingGroup.getGroupMembers().split(",");
                            for(int i=0;i<usersId.length;i++){
                                to[i] = Integer.valueOf(usersId[i]);
                            }
                            clientService.client.sendMessage(currentUser.getUserId(),to,text,1);
                            textInput.setText("");
                        }
                    }
                });
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
                        messageShow.setTime(dataBaseMessage.getTime());
                    }
                    else {
                        messageShow.setMessageType(0);
                        messageShow.setTime(dataBaseMessage.getFrom()+" "+dataBaseMessage.getTime());
                    }
                    messageShow.setMessageStyle(0);
                    messageShow.setContents(dataBaseMessage.getContent());
                    showMessageList.add(messageShow);
                }
                System.out.print(allMessages);
                chatMessageAdapter.notifyDataSetChanged();
                listView.setSelection(chatMessageAdapter.getCount()-1);
            }
        };
        new Thread() {
            public void run() {
                String user = "id="+chattingGroup.getGroupId()+"&userId="+currentUser.getUserId();
                String response = HttpUtils.doPostRequest("getMessageRecordBetweenUserAndGroup",user);
                Message message = Message.obtain();
                message.obj = response;
                mHandler.sendMessage(message);
            }
        }.start();
    }

    private void getCurrentChattingUserInfo() {
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        chattingGroup = (Group) intent.getSerializableExtra("chattingGroup");
        chattingUserNameOutput.setText(chattingGroup.getGroupName());
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
        newMessageShow.setTime(message1.getFrom()+"  "+message1.getTime());
        showMessageList.add(newMessageShow);
        chatMessageAdapter.notifyDataSetChanged();
        listView.setSelection(chatMessageAdapter.getCount()-1);
    }

}
