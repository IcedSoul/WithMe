package cn.justwithme.withme.Entity;

import java.io.Serializable;

/**
 * Created by 14437 on 2017/2/22.
 */

public class Group implements Serializable {
    private int id;
    private String groupId;
    private String groupName;
    private int groupCreaterId;
    private String groupCreateTime;
    private String groupIntroduction;
    private int groupUserCount;
    private String groupMembers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupCreaterId() {
        return groupCreaterId;
    }

    public void setGroupCreaterId(int groupCreaterId) {
        this.groupCreaterId = groupCreaterId;
    }

    public String getGroupCreateTime() {
        return groupCreateTime;
    }

    public void setGroupCreateTime(String groupCreateTime) {
        this.groupCreateTime = groupCreateTime;
    }

    public String getGroupIntroduction() {
        return groupIntroduction;
    }

    public void setGroupIntroduction(String groupIntroduction) {
        this.groupIntroduction = groupIntroduction;
    }

    public int getGroupUserCount() {
        return groupUserCount;
    }

    public void setGroupUserCount(int groupUserCount) {
        this.groupUserCount = groupUserCount;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }
}
