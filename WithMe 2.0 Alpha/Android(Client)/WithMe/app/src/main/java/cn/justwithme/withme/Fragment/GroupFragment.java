package cn.justwithme.withme.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.justwithme.withme.Activity.GroupChatActivity;
import cn.justwithme.withme.Activity.NaviActivity;
import cn.justwithme.withme.Entity.Group;
import cn.justwithme.withme.Entity.User;
import cn.justwithme.withme.R;
import cn.justwithme.withme.Utils.HttpUtils;

/**
 * Created by 桐小目 on 2017/2/22.
 */

public class GroupFragment extends Fragment {
    private ListView listView;
    private List<Group> groups = new ArrayList<>();
    private List<Map<String,Object>> relationsView = new ArrayList<Map<String, Object>>();
    private SimpleAdapter simpleAdapter;
    private User currentUser;
    private View view;
    private Activity naviActivity;

    public GroupFragment(User currentUser, NaviActivity naviActivity) {
        this.currentUser = currentUser;
        this.naviActivity = naviActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab, container, false);
        //初始化控件
        initView();
        getAllGroups();
        return view;
    }

    //初始化控件
    private void initView(){
        listView = (ListView) view.findViewById(R.id.list);
    }

    //获取当前用户的所有群组
    private void getAllGroups(){
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                String response = (String)message.obj;
                String jsonRelationList = JSON.parseObject(response).getString("groups");
                groups = JSONArray.parseArray(jsonRelationList,Group.class);
                updateListView();
                listenItems();
            }
        };
        new Thread() {
            public void run() {
                String user = "userId="+currentUser.getUserId();
                String response = HttpUtils.doPostRequest("getUserGroups",user);
                Message message = Message.obtain();
                message.obj = response;
                mHandler.sendMessage(message);
            }
        }.start();

    }

    //更新群组列表
    private void updateListView(){
        for(int i=0;i<groups.size();i++){
            Group group = groups.get(i);
            Map<String,Object> listItem = new HashMap<String,Object>();
            listItem.put("groupName",group.getGroupName());
            listItem.put("userIsOnline","");
            listItem.put("icon",R.drawable.photos);
            relationsView.add(listItem);
        }
        simpleAdapter = new SimpleAdapter(naviActivity,relationsView,R.layout.relation_item,new String[]{"icon","groupName","userIsOnline"},new int[]{R.id.relation_icons,R.id.user_name,R.id.online_status});
        listView.setAdapter(simpleAdapter);
    }
    //监听点击群组列表事件
    private void listenItems(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("我点击了"+position);
                Intent intent = new Intent(naviActivity,GroupChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("currentUser",currentUser);
                bundle.putSerializable("chattingGroup",groups.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
