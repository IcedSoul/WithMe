package cn.justwithme.withme.WebSocket;


import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.justwithme.withme.Activity.ChatActivity;
import cn.justwithme.withme.Activity.GroupChatActivity;
import cn.justwithme.withme.Entity.User;
import cn.justwithme.withme.Utils.CONSTANTS;
import cn.justwithme.withme.Utils.HttpUtils;

/**
 * Created by 桐小目 on 2017/2/12.
 */
public class Client extends WebSocketClient {

    private int userId;
    private List<User> relations = new ArrayList<User>();

    public Client( URI serverUri , Draft draft ,int userId) {
        super( serverUri, draft );
    }

    public Client(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        onLineStatusNotice(3);
    }

    @Override
    public void onMessage(String message) {
        System.out.println( "received: " + message );
        Message message1 = new Message();
        try {
            message1.obj = message;
            cn.justwithme.withme.Entity.Message message2 = JSON.parseObject(message, cn.justwithme.withme.Entity.Message.class);
            Handler handler = null;
            if (message2.getType() == 0 || message2.getType() == -1) {
                handler = ChatActivity.messageHandler;
            }
            else if (message2.getType() == 1) {
                handler = GroupChatActivity.messageHandler;
            }
            handler.sendMessage(message1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        onLineStatusNotice(4);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public void sendMessage(int from,int[] to,String content,int type){
        cn.justwithme.withme.Entity.Message message = new cn.justwithme.withme.Entity.Message();
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.setFrom(from);
        message.setTo(to);
        message.setContent(content);
        message.setType(type);
        message.setTime(dateFormat.format(now));
        String jsonMessage = JSON.toJSONString(message);
        System.out.println("我发送了信息："+jsonMessage);
        this.send(jsonMessage);
    }

    private void onLineStatusNotice(int type) {
        if(relations == null){
            getAllRelations(userId);
        }
        int to[] = new int[]{};
        for(int i=0;i<relations.size();i++){
            User user = (User)relations.get(i);
            to[i] = user.getUserId();
        }
        String content = null;
        if(type == 3)
            content = CONSTANTS.ONLINE_NOTICE;
        else
            content = CONSTANTS.OFFLINE_NOTICE;
        this.sendMessage(userId,to,content,type);
    }
    //获取当前用户的所有好友
    private void getAllRelations(final int userId){
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                String response = (String)message.obj;
                String jsonRelationList = JSON.parseObject(response).getString("relations");
                relations = JSONArray.parseArray(jsonRelationList,User.class);
            }
        };
        new Thread() {
            public void run() {
                String user = "userId="+userId;
                String response = HttpUtils.doPostRequest("getRelations",user);
                Message message = Message.obtain();
                message.obj = response;
                mHandler.sendMessage(message);
            }
        }.start();

    }
}
