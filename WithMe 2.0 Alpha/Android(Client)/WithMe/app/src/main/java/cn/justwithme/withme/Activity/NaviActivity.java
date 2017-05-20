package cn.justwithme.withme.Activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.justwithme.withme.Adapaer.FragmentAdapter;
import cn.justwithme.withme.Entity.User;
import cn.justwithme.withme.Fragment.GroupFragment;
import cn.justwithme.withme.Fragment.RelationFragment;
import cn.justwithme.withme.R;
import cn.justwithme.withme.WebSocket.ClientService;


public class NaviActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentAdapter fragmentAdapter;

    private ViewPager mViewPager;

    private TextView navName;

    //定义控件
    private User currentUser;


    //ServiceConnection
    ClientService.ClientBinder binder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        //Activity与Service连接成功时回调此方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("-----------ClientService is binded!-----------");
            binder = (ClientService.ClientBinder) service;
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
        setContentView(R.layout.activity_navi);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //绑定Service
        bindClientService();
        //获取当前用户信息
        getCurrentUserInfo();

        View headerView = navigationView.getHeaderView(0);
        navName = (TextView) headerView.findViewById(R.id.nav_name);
        navName.setText(currentUser.getUserNickName());

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new RelationFragment(currentUser,NaviActivity.this));
        fragments.add(new GroupFragment(currentUser,NaviActivity.this));

        List<String> titleNames = new ArrayList<String>();
        titleNames.add("联系系表");
        titleNames.add("群组表");

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),fragments,titleNames);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(fragmentAdapter);

    }
    @Override
    protected void onDestroy(){
        unBindClientService();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode,event);
    }

    //绑定Service
    private void bindClientService(){
        Intent intent = new Intent();
        intent.setClass(NaviActivity.this,ClientService.class);
        bindService(intent,serviceConnection, Service.BIND_AUTO_CREATE);
    }

    //解除绑定Service
    private void unBindClientService(){
        unbindService(serviceConnection);
    }

    //获取当前用户的信息
    private void getCurrentUserInfo(){
        Intent intent = getIntent();
        currentUser = (User)intent.getSerializableExtra("currentUser");
        System.out.println("--------当前登录的用户为："+currentUser.getUserName()+"当前用户昵称为"+currentUser.getUserNickName());
    }
}
