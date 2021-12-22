package ru.privatee.bot.listener;

import java.util.HashMap;
import java.util.List;

import javax.print.attribute.standard.MediaSize.Other;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.response.BaseResponse;

import ru.privatee.bot.Config;
import ru.privatee.bot.object.Channel;
import ru.privatee.bot.object.User;
import ru.privatee.bot.tgbot.Bot;
import ru.privatee.bot.tgbot.events.MessageEvnet.onNewChatMessageListener;
import ru.privatee.bot.utils.AcceptType;
import ru.privatee.bot.utils.TGUtils;
import ru.privatee.bot.utils.UserUtils;

public class OnNewMessage implements onNewChatMessageListener {

	@Override
	public void onCommandEvent(Update up) {
		register(up);
		String[] args = up.message().text().split(" ");
		User us = UserUtils.getUsers().get(up.message().from().id());
		long chatId = up.message().chat().id();
		switch (args[0].toLowerCase()) {
		case "/start":{
			startMessage(up);
			break;
		}
		case "/admin":{
			if(!us.isAdmin()) {
			//	return;
			}
			Bot.sendMessageButton(chatId, "Доступные возможности: ", TGUtils.getKeyboardMarkup("Список пользователей;users:Рассылка сообщений;msgAll"));
			return;
		}
		case "⌛️":{
			System.out.println(us.getAcceptType());
			if(us.getAcceptType()!=AcceptType.Null){
				Bot.sendMessageButton(chatId, "⌛️ Доступна подписка '"+us.getAcceptType().toString()+"':", TGUtils.getUrlButton("VIP", us.getUrl()));
			return;
			}
			
			Bot.sendMessageButton(chatId, "⌛️ У Вас нет действующей подписки.\r\n"
					+ "\r\n"
					+ "Ознакомьтесь с каналами, нажав на соответствующую кнопку.", TGUtils.getKeyboardMarkup("Приобрести доступ;buy"));
		return;
		}
		case "💵":{
			Bot.sendMessageButton(chatId, "ℹ️ Чтобы ознакомиться с каналом, выбери необходимый, нажав на соответствующую кнопку", TGUtils.getKeyboardMarkup("♻️VIP доступ - на 1 месяц;vip_1moth:♾ VIP доступ - навсегда;vip_all"));
		}
		case "dev":{
			Bot.sendMessage(chatId, "Разработчик бота @dnk10");
			return;
			}
		default:{
			if(us.getMsgB()) {
				HashMap<Long, User> users = UserUtils.getUsers();
				for(User other:users.values()) {
					Bot.sendAllMsg(other.getChatId(), up.message());
				}
				Bot.sendMessage(chatId, "Рассылка прошла успешно!");
				return;
			}
			
			return;
		}
		
		}

	}

	private void startMessage(Update up) {
		 String[][] s = {{"💵 Приобрести доступ", "⌛️ Мои подписки"}};
		Bot.sendReplyKeyboard(up.message().chat().id(), "📰 Главное меню:", TGUtils.getKeyBoard(s));
	}

	private User register(Update up) {
		HashMap<Long, User> users = UserUtils.getUsers();
		User us = null;
		 String[][] s = {{"💵 Приобрести доступ", "⌛️ Мои подписки"}};
		if(!users.containsKey(up.message().from().id())) {
			String name = up.message().from().username();
			if(name.equals("null")) {
				name = up.message().from().firstName();
			}
		 us = new User(up.message().from().id(), name);
		 if(users.size() == 0) {
			 us.setAdmin(true);
		 }
		 users.put(us.getChatId(), us);
		 UserUtils.saveUser(users);
		 Bot.sendReplyKeyboard(up.message().chat().id(), "Приветствую, друзья! "
		 		+ ""
		 		+ "Это бот создан для автоматической покупки доступа в VIP канала Сливочная 💧 "
		 		+ ""
		 		+ "Чтобы приобрести подписку, выберите нужный Вам тариф ниже👇", TGUtils.getKeyBoard(s));
		 return us;
		}
		us = users.get(up.message().from().id());
		return us;
	}

}
