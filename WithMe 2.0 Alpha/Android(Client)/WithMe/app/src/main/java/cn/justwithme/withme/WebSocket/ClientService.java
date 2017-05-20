package cn.justwithme.withme.WebSocket;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.alibaba.fastjson.JSON;

import org.java_websocket.drafts.Draft_10;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.justwithme.withme.Activity.ChatActivity;
import cn.justwithme.withme.Entity.Message;
import cn.justwithme.withme.Utils.CONSTANTS;


public class ClientService extends Service {
    private String userId = null;
    private String urlAddress = null;
    public Client client = null;
    private ClientBinder binder = new ClientBinder();

    public class ClientBinder extends Binder {
        public Service getService(){
            return ClientService.this;
        }
    }

    @Override
    public void onCreate(){
        System.out.print("------------创建服务成功------------"+urlAddress);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        userId = intent.getStringExtra("currentUserId");
        System.out.println("-----------启动服务成功-----------");
        urlAddress = CONSTANTS.wsAddress +"/"+userId;
        System.out.println("开启服务："+urlAddress);
        startClient();
        super.onStartCommand(intent,flags,startId);
        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    @Override
    public void onDestroy(){
        stopClient();
        System.out.println("-------服务已经关闭--------");
        super.onDestroy();
    }

    private void startClient(){
        try {
            URI uri = new URI(urlAddress);
            client = new Client(uri,new Draft_10(),Integer.valueOf(userId));
            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void stopClient(){
        client.close();
    }

}
