package cn.justwithme.withme.Entity;

import java.io.Serializable;

/**
 * Created by 14437 on 2017/2/9.
 */

public class User implements Serializable {
    private int userId;
    private String userName;
    private String userNickName;
    private int userIsOnline;
    private int userRole;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public int getUserIsOnline() {
        return userIsOnline;
    }

    public void setUserIsOnline(int userIsOnline) {
        this.userIsOnline = userIsOnline;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }
}
