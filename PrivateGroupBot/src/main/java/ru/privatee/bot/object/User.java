package ru.privatee.bot.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.pengrad.telegrambot.model.ChatMember;
import com.pengrad.telegrambot.model.ChatMember.Status;

import ru.privatee.bot.tgbot.Bot;
import ru.privatee.bot.utils.AcceptType;

public class User implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//private String name;
private long chatId;
private ArrayList<Long> chatList;
int status;
private String url;
private AcceptType acceptType;
private long acceptTime;
private boolean setMsgB;
private boolean admin;
private String name;
public User(long chatId, String name) {
	this.name = name;
	this.admin = false;
    this.acceptType = AcceptType.Null;
	this.chatId =chatId;
	this.chatList = new ArrayList<Long>();
}
public String toString() {
	return "ChatId:"+chatId;
}
public void setAccess(AcceptType type) {

	this.acceptType = type;
}
public AcceptType getAcceptType() {
	return acceptType;
}
public void addChat(long chatId) {
	chatList.add(chatId);
}
public boolean isAdmin() {
	return admin;
}
public void setURL(String url) {
	this.url = url;
}
public void setAcceptTime(long time) {
	this.acceptTime = time;
}
public long getAcceptTime() {
	return this.acceptTime;
}
public String getUrl() {
	return url;
}
public List<Long> getChatList(){
	return chatList;
}
public Long getChatId() {
	return chatId;
}
public boolean getMsgB() {
	return setMsgB;
}
public void setMsgB(boolean b) {
	this.setMsgB = b;
	
}
public void setAdmin(boolean b) {
	this.admin = true;
	
}
public String getName() {
	return name;
}
}

