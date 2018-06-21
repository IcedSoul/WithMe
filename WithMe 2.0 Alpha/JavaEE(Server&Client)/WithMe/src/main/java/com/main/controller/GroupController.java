package com.main.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.main.entity.Group;
import com.main.entity.UserGroupRelation;
import com.main.service.GroupService;
import com.main.service.UserGroupRelationService;
import com.main.service.UserService;

/*
 * 群组的Controller，控制群组的创建，更新和删除
 * 
 * */

@Controller
public class GroupController {
	@Resource private GroupService groupService;
	@Resource private UserGroupRelationService userGroupRelationService;
	@Resource private UserService userService;
	
	@RequestMapping(value="/createGroup",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> createGroup(String groupName,String groupIntroduction,int groupCreaterId){
		//群组的groupId在这里随机生成（五位数字）
		Group group = new Group();
		String groupId =String.valueOf((int) (Math.random()*100000));
		while(groupService.getGroup(groupId)!=null){
			groupId =String.valueOf((int) (Math.random()*100000));
		}
		group.setGroupId(groupId);
		group.setGroupCreaterId(groupCreaterId);
		group.setGroupIntroduction(groupIntroduction);
		group.setGroupName(groupName);
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		group.setGroupCreateTime(timestamp);
		group.setGroupUserCount(0);
		group.setGroupMembers("");
		groupService.addGroup(group);
		UserGroupRelation userGroupRelation = new UserGroupRelation();
		//这里两个id不是一回事，一个是逻辑id，一个是业务id，要区分开
		Group groups = groupService.getGroup(groupId);
		userGroupRelation.setGroupId(groups.getId());
		userGroupRelation.setUserId(groupCreaterId);
		userGroupRelation.setEnterGroupTime(timestamp);
		userGroupRelation.setGroupUserNickName(userService.getUser(groupCreaterId).getUserNickName());
		userGroupRelation.setGroupLevel(10);
		userGroupRelationService.addUserGroupRelation(userGroupRelation);
		groups.setGroupMembers(String.valueOf(groups.getId()+","+String.valueOf(groupCreaterId)));
		groups.setGroupUserCount(groups.getGroupUserCount()+1);
		groupService.updateGroup(groups);
		Map<String,Object> resoult = new HashMap<String,Object>();
		resoult.put("resoult", groupId);
		return resoult;
	}
	
	@RequestMapping(value="/findGroupById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> findGroupById(int id){
		Group group = groupService.getGroup(id);
		String JsonGroup = JSONArray.toJSONString(group,SerializerFeature.UseSingleQuotes);
		Map<String,Object> resoult = new HashMap<String,Object>();
		resoult.put("resoult", JsonGroup);
		return resoult;
	}
	
	@RequestMapping(value="/findGroupByGroupId",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> findGroupByGroupId(String groupId){
		Group group = groupService.getGroup(groupId);
		String JsonGroup = JSONArray.toJSONString(group,SerializerFeature.UseSingleQuotes);
		Map<String,Object> resoult = new HashMap<String,Object>();
		resoult.put("resoult", JsonGroup);
		return resoult;
	}
	
	private static SerializeConfig mapping = new SerializeConfig();  
	private static String dateFormat;  
	static {  
	    dateFormat = "yyyy-MM-dd HH:mm:ss";  
	    mapping.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));  
	}
}