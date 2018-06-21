package com.main.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/*
 * ������ΰѵ�ǰ��¼�û�����Ϣ����Session�У�@SessionAttributesע���ʹ�÷��������л��ƿ��Բο����沩��
 * http://www.cnblogs.com/waytofall/p/3460533.html
 * �˴�ѡ�����Servlet��HttpSession
 * 
 * 2017/01/14
 */






import org.springframework.web.bind.annotation.ResponseBody;

import com.main.entity.User;
import com.main.entity.UserDetail;
import com.main.service.UserDetailService;
import com.main.service.UserService;

@Controller
public class LoginController {
	
	@Resource private UserService userService;
	@Resource private UserDetailService userDetailService;
	
	@RequestMapping(value="/main")
	public String main(){
		return "main";
	}
	
	@RequestMapping(value="/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="/doLogin",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doLogin(String userName,String userPassword,HttpSession httpSession){
		String result="fail";
		User user = userService.getUser(userName);
		UserDetail userDetail = userDetailService.getUserDetail(userName);
		if(user!=null){
			if(Objects.equals(userDetail.getUserDetailPassword(), userPassword)){
				httpSession.setAttribute("currentUser",user);
				result = "success";
			}
			else{
				result = "wrong";
			}
		}
		else{
			result = "unexist";
		}
		Map<String, Object> results = new HashMap<String,Object>();
		results.put("result", result);
		if(result.equals("success"))
			results.put("user", JSON.toJSON(user));
		return results;
	}
	
	
	@RequestMapping(value="/doLogout")
	public String doLogout(HttpSession httpSession){
		httpSession.removeAttribute("currentUser");
		return "redirect:login";
	}
}