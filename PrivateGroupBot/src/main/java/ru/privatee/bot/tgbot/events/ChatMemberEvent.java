package ru.privatee.bot.tgbot.events;

import java.util.ArrayList;

import com.pengrad.telegrambot.model.Update;

public class ChatMemberEvent {
	private static ArrayList<onChatMemberEvent> listeners = new ArrayList<onChatMemberEvent>();
	public interface onChatMemberEvent {
		   public void onChatMember(Update up);
		}
	public static void addListener(onChatMemberEvent clazz){
		listeners.add(clazz);
		return;
	}
	public static void removeAll(){
		listeners = new ArrayList<onChatMemberEvent>();
		return;
	}
	public void send(Update up){
		for(onChatMemberEvent tralal:listeners){
			tralal.onChatMember(up);
		}
	}
}
