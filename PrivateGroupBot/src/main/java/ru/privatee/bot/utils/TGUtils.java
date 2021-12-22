package ru.privatee.bot.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;

import ru.privatee.bot.object.User;
import ru.privatee.bot.tgbot.Bot;

public class TGUtils {
	//down
public static ReplyKeyboardMarkup getKeyBoard(String[][] ss) {
	
	ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup(ss);
	keyboard.selective(true);
	keyboard.resizeKeyboard(true);
	keyboard.oneTimeKeyboard(false);
	return keyboard;
}
public static void sendAdminsMessage(String message) {
	HashMap<Long, User> users = UserUtils.getUsers();
	for(User us:users.values()) {
		if(us.isAdmin()) {
			Bot.sendMessage(us.getChatId(), message);
		}
	}
}
public static ReplyKeyboardRemove getKeyBoardRemove() {
	ReplyKeyboardRemove keyboard = new ReplyKeyboardRemove();
	return keyboard;
}
public static InlineKeyboardMarkup getUrlButton(String text, String url) {
	InlineKeyboardMarkup board = new InlineKeyboardMarkup();
	InlineKeyboardButton but = new InlineKeyboardButton(text);
	but.url(url);
	board.addRow(but);
	return board;
}
public static InlineKeyboardMarkup getKeyboardMarkup(String buttons0) {
	InlineKeyboardMarkup board = new InlineKeyboardMarkup();
	String[] rows = buttons0.split(":");
	for(String buttons:rows) {
	String[] buttons1 = buttons.split("@");
	List<InlineKeyboardButton> list = new ArrayList<>();
	InlineKeyboardButton[] buts = new InlineKeyboardButton[0];
	for(String s:buttons1) {
		
		String[] b = s.split(";");
		String text = b[0];
		String name = b[1];
		InlineKeyboardButton button = new InlineKeyboardButton(text);
		button.callbackData(name);
		list.add(button);
	}
	buts = convertListToArray(list);
	System.out.println(buts.length);
	board.addRow(buts);
	}
	return board;
}
private static InlineKeyboardButton[] convertListToArray(List<InlineKeyboardButton> list){
	InlineKeyboardButton[] ins = (InlineKeyboardButton[])list.toArray(new InlineKeyboardButton[list.size()]);
    return ins;
}
}
