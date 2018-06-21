package com.main.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.main.entity.Message;
import com.main.service.MessageService;

@Controller
public class MessageController {
	
	@Resource private MessageService messageService;

	@RequestMapping(value="/getMessageRecordBetweenUsers",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getMessageRecordBetweenUsers(int userIdA,int userIdB){
		List<Message> list = messageService.getMessageByType(0);
		List<Message> lists = new ArrayList<Message>();
		for(int i=0;i<list.size();i++){
			Message message = list.get(i);
			if(((message.getFrom() == userIdA && message.getTo() == userIdB)||(message.getFrom() == userIdB && message.getTo() == userIdA))&& message.getIsTransport() == 1)
				lists.add(message);
		}
		String messages = JSONArray.toJSONString(lists,SerializerFeature.UseSingleQuotes);
//		System.out.println("我到了这里："+messages);
		Map<String,Object> resoult = new HashMap<String,Object>();
		resoult.put("resoult",messages);
		return resoult;
	}
	
	@RequestMapping(value="/getMessageRecordBetweenUserAndGroup",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getMessageRecordBetweenUserAndGroup(int id,int userId){
		List<Message> list = messageService.getMessageByType(2);
		List<Message> lists = new ArrayList<Message>();
		for(int i=0;i<list.size();i++){
			Message message = list.get(i);
			if(message.getTo() == id && message.getIsTransport() == 1){
				message.setType(1);
				message.setTo(userId);
			}
			lists.add(message);
		}
		String messages = JSONArray.toJSONString(lists,SerializerFeature.UseSingleQuotes);
		Map<String,Object> resoult = new HashMap<String,Object>();
		resoult.put("resoult",messages);
		return resoult;
	}
	
	//fastJson(json)对javaBean的date以及timestamp直接转换为json对象时会乱码，这里配合bean（entity）中的注解，就可以保证转化的日期格式正确
	private static SerializeConfig mapping = new SerializeConfig();  
	private static String dateFormat;  
	static {  
	    dateFormat = "yyyy-MM-dd HH:mm:ss";  
	    mapping.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));  
	}
}