package com.main.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.main.entity.Message;
import com.main.entity.User;
import com.main.service.MessageService;
import com.main.service.UserService;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.web.context.ContextLoader;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by 桐小目 on 2017/2/15.
 */
public class Server extends WebSocketServer {
    //构造方法
    public Server(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    //记录当前在线人数
    private int onLineCount = 0;
    //在线清单
    Map<String,WebSocket> onLineList = new HashMap<String,WebSocket>();
    //获取MessageService和UserService对象
    private MessageService messageService=(MessageService) ContextLoader.getCurrentWebApplicationContext().getBean("messageService");
    private UserService userService=(UserService)ContextLoader.getCurrentWebApplicationContext().getBean("userService");

    private String userId;

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
            //获取参数中当前用户id
            String param = clientHandshake.getResourceDescriptor();
            userId = param.substring(1, param.length());
            sendMessage(webSocket, "WebSocket连接 " + userId + " 建立成功");
            System.out.println("userId: " + userId);
            //在线人数加一
            addOnLineCount();
            //将当前用户在线状态置1
            User user = userService.getUser(Integer.valueOf(userId));
            user.setUserIsOnline(1);
            userService.updateUser(user);
            //将当前用户id和WebSocket对象加入在线清单
            onLineList.put(userId, webSocket);
            //检查自己是否有未接收的消息
            List<Message> messageList = messageService.getMessageUnReceive(Integer.valueOf(userId));
            if (messageList != null) {
                for (int i = 0; i < messageList.size(); i++) {
                    Message message = messageList.get(i);
                    String jsonMessage = getMessage(message);
                    sendMessage(onLineList.get(userId), jsonMessage);
                    message.setIsTransport(1);
                    messageService.updateMessage(message);
                }
            }
    }

    @Override
    public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {
        System.out.println("断开连接"+webSocket);
        //在线人数减一
        subOnLineCount();
        //将用户在线状态置0
        User user = userService.getUser(Integer.valueOf(userId));
        user.setUserIsOnline(0);
        userService.updateUser(user);
        //从在线清单中移除当前用户
        onLineList.remove(userId);
    }

    @Override
    public void onMessage(WebSocket webSocket, String jsonMessage) {
        Message message = new Message();
        //存储数据库
        JSONObject jsonObjectMessage = JSON.parseObject(jsonMessage);
        message.setFrom(jsonObjectMessage.getIntValue("from"));
        message.setContent(jsonObjectMessage.getString("content"));
        message.setType(jsonObjectMessage.getIntValue("type"));
        message.setTime(Timestamp.valueOf(jsonObjectMessage.getString("time")));
        JSONArray users =  jsonObjectMessage.getJSONArray("to");
        //发送给自己,不过发之前先判断一下是普通消息还是通知性消息，通知性消息不必给自己发回去了，服务器直接转发
        if(jsonObjectMessage.getIntValue("type") == 0){
            String self = jsonObjectMessage.getString("from");
            sendMessage(onLineList.get(self),jsonMessage);
        }
        //记录群消息
        if(jsonObjectMessage.getIntValue("type") == 1){
            message.setTo(users.getInteger(0));
            message.setType(2);
            message.setIsTransport(1);
            messageService.addMessage(message);
            message.setType(1);
        }
        for(int i = 0;i < users.size();i++){
            if(!(jsonObjectMessage.getIntValue("type") == 1 && i==0)){
                String user = String.valueOf(users.get(i));
                //分别发送给每个指定用户
                message.setTo(Integer.valueOf(user));
                //先判断该用户是否在线,在线则发送并且设置发送状态为1，不在线则直接设置发送状态为0，并且添加到数据库
                WebSocket webSocket1 = onLineList.get(user);
                if(webSocket1!=null && !webSocket1.equals("")){
                    sendMessage(webSocket1,jsonMessage);
                    message.setIsTransport(1);
                    messageService.addMessage(message);
                }
                else{
                    message.setIsTransport(0);
                    if(message.getType()!=3 && message.getType()!=4)
                        messageService.addMessage(message);
                }
            }
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        e.printStackTrace();
    }


    public void sendMessage(WebSocket webSocket,String message){
       webSocket.send(message);
    }

    public String getMessage(Message message){
        //使用JSONObject方法构建Json数据
        JSONObject jsonObjectMessage = new JSONObject();
        jsonObjectMessage.put("from", String.valueOf(message.getFrom()));
        jsonObjectMessage.put("to", new String[] {String.valueOf(message.getTo())});
        jsonObjectMessage.put("content", String.valueOf(message.getContent()));
        jsonObjectMessage.put("type", String.valueOf(message.getType()));
        jsonObjectMessage.put("time", message.getTime().toString());
        return jsonObjectMessage.toString();
    }

    public int getOnLineCount(){
        return  this.onLineCount;
    }

    public void addOnLineCount(){
        onLineCount++;
    }

    public void subOnLineCount(){
        onLineCount--;
    }
}
