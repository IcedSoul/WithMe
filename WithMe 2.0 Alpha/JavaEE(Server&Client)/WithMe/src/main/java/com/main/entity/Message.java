package com.main.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

@Entity  
@Table(name="message") 
public class Message {
	private int id;
	private int from;
	private int to;
	private String content;
	private int type;
	private Timestamp time;
	private int isTransport;
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="from_id")
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	
	@Column(name="to_id")
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="type")
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	@Column(name="time")
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	@Column(name="is_transport")
	public int getIsTransport() {
		return isTransport;
	}
	public void setIsTransport(int isTransport) {
		this.isTransport = isTransport;
	}
}
