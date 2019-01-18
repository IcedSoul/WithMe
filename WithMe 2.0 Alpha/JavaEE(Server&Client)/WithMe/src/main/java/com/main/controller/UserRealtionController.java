package com.main.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.main.entity.User;
import com.main.entity.UserRelation;
import com.main.service.UserRelationService;
import com.main.service.UserService;

@Controller
public class UserRealtionController {
	
	@Resource private UserService userService;
	@Resource private UserRelationService userRelationService;
	
	@RequestMapping(value="/buildRelation",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> buildRelation(int userIdA,int userIdB){
		return userRelationService.addUserRelation(userIdA, userIdB);
	}
	
	@RequestMapping(value="/removeRelation",method=RequestMethod.POST)
	public String removeRelation(int userId,HttpSession httpSession){
		User user = (User)httpSession.getAttribute("currentUser");
		int idA = user.getUserId();
		int idB = userId;
		userRelationService.deleteUserRelation(idA, idB);
		httpSession.setAttribute("friends", userRelationService.getAllFriends(user.getUserId()));
		return "main";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getRelations",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getRelations(int userId){
		List<User> list = new ArrayList<User>();
		list = userRelationService.getAllFriends(userId);
		String relations = JSONArray.toJSONString(list);
		Map<String,Object> resoult = new HashMap<String,Object>();
		resoult.put("relations", relations);
		return resoult;
	}
}