package com.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dao.MessageDao;
import com.main.entity.Message;
@Service
public class MessageServiceImplement implements MessageService {

	@Autowired private MessageDao messageDao;
	
	@Override
	public List<Message> getMessageByFromId(int from) {
		return messageDao.getMessageByFromId(from);
	}

	@Override
	public List<Message> getMessageByToId(int to) {
		return messageDao.getMessageByToId(to);
	}
	
	@Override
	public List<Message> getMessageUnReceive(int to) {
		return (List<Message>)messageDao.getMessageUnReceive(to);
	}

	@Override
	public List<Message> getMessageByType(int type) {
		return messageDao.getMessageByType(type);
	}

	@Override
	public void addMessage(Message message) {
		messageDao.addMessage(message);

	}

	@Override
	public boolean deleteMessage(Message message) {
		return messageDao.deleteMessage(message);
	}

	@Override
	public boolean updateMessage(Message message) {
		return messageDao.updateMessage(message);
	}

}
