package ru.privatee.bot;


import java.util.HashMap;
import java.util.List;

import ru.privatee.bot.listener.OnNewMessage;
import ru.privatee.bot.listener.onCallBackQuery;
import ru.privatee.bot.object.Channel;
import ru.privatee.bot.object.User;
import ru.privatee.bot.tgbot.Bot;
import ru.privatee.bot.tgbot.events.CallbackQueryEvent;
import ru.privatee.bot.tgbot.events.MessageEvnet;
import ru.privatee.bot.utils.AcceptType;
import ru.privatee.bot.utils.TGUtils;
import ru.privatee.bot.utils.UserUtils;

public class Main {
public static void main(String[] args) {
	System.out.println("BotStart");
Config.loadConfig();
new Qiwi();
botStart();
MessageEvnet.addListener(new OnNewMessage());
CallbackQueryEvent.addListener(new onCallBackQuery());
new Thread(new Runnable() {
	
	@Override
	public void run() {
		while(true) {
	scan();
		}
	}
}).start();
}

private static void scan() {
	HashMap<Long, User> users = UserUtils.getUsers();
	for(User us:users.values()) {
		if(us.getAcceptType() == AcceptType.OneMoth) {
		long time = us.getAcceptTime()-System.currentTimeMillis();
		long push = 1000*60*60*24*30;
		if(time>=push) {
			HashMap<Long, User> users1 = UserUtils.getUsers();
			   users1.remove(us.getChatId());
				UserUtils.saveUser(users1);
			TGUtils.sendAdminsMessage("Исключите из группы пользователя '"+us.getName()+"', его подписка закончилась.");
		}
		}
	}
}

public static void reload() {
	Config.loadConfig();
}
private static void botStart() {
	new Thread(new Runnable() {
		
		@Override
		public void run() {
			new Bot(Config.getString("Token"));
		}
	}).start();
}

}
