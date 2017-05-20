package com.main.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.main.entity.User;
import com.main.entity.UserDetail;
import com.main.service.UserDetailService;
import com.main.service.UserService;

@Controller
public class RegisterController {
	
	@Autowired private UserService userService;
	@Autowired private UserDetailService userDetailService;
	
	@RequestMapping(value="/register")
	public String register(){
		return "register";
	}
	
	@RequestMapping(value="/doRegister",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doRegister(String userName,String userNickName,String userPassword){
		String resoult="fail";
		if(userService.getUser(userName)==null){
			UserDetail userDetail = new UserDetail();
			userDetail.setUserDetailName(userName);
			userDetail.setUserDetailNickName(userNickName);
			userDetail.setUserDetailPassword(userPassword);
			userDetail.setUserDetailRole(0);
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			userDetail.setUserRegisterTime(timestamp);
//			userDetail.setUserAddNewFriend(0);
//			userDetail.setUserFriendId("");
			userDetail.setUserMailNumber("");
			userDetail.setUserPhoneNumber("");
			userDetailService.addUserDetail(userDetail);
			userDetail = userDetailService.getUserDetail(userName);
			User user = new User(userDetail);
			userService.addUser(user);
			resoult = "success";
		}
		else{
			resoult = "exist";
		}
		Map<String, Object> resoults = new HashMap<String,Object>();
		resoults.put("resoult", resoult);
		return resoults;
	}
}
