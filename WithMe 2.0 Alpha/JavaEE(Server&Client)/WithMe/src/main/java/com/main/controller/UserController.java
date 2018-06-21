package com.main.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.main.entity.User;
import com.main.service.UserService;
/*
 * ����MVC����ģʽ�ο����沩�ͣ�
 * http://blog.csdn.net/Earl_yuan/article/details/50382431
 * ���ڷ�����淶���Բο����沩������Ƶ
 * http://blog.csdn.net/bjyfb/article/details/8998267
 * http://www.imooc.com/video/9346
 */


@Controller
public class UserController {
	
	@Autowired private UserService userService;
	
	@RequestMapping(value="/doFindUserByName",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findUserByName(String userName){
		User user = userService.getUser(userName);
		//通过HashMap来构建Json数据，其实和Server里面通过JSONObject来构建效果是一样的
		Map<String,Object> resoult = new HashMap<String,Object>();
		if(user!=null){
			resoult.put("userId", user.getUserId());
			resoult.put("userName", user.getUserName());
			resoult.put("userNickName", user.getUserNickName());
			resoult.put("userIsOnline", user.getUserIsOnline());
			resoult.put("userRole", user.getUserRole());
		}
		else
			resoult=null;
		return resoult;
	};
	
	@RequestMapping(value="/doFindUserById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findUserById(int userId){
		User user = userService.getUser(userId);
		Map<String,Object> resoult = new HashMap<String,Object>();
		if(user!=null){
			resoult.put("userId", user.getUserId());
			resoult.put("userName", user.getUserName());
			resoult.put("userNickName", user.getUserNickName());
			resoult.put("userIsOnline", user.getUserIsOnline());
			resoult.put("userRole", user.getUserRole());
		}
		else
			resoult=null;
		return resoult;
	};
	
	
}