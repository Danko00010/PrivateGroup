package ru.privatee.bot.listener;

import java.util.HashMap;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.response.BaseResponse;
import com.qiwi.billpayments.sdk.model.BillStatus;
import com.qiwi.billpayments.sdk.model.out.BillResponse;

import ru.privatee.bot.Config;
import ru.privatee.bot.Qiwi;
import ru.privatee.bot.object.BillPay;
import ru.privatee.bot.object.User;
import ru.privatee.bot.tgbot.Bot;
import ru.privatee.bot.tgbot.events.CallbackQueryEvent.onCallBackQueryEvent;
import ru.privatee.bot.utils.AcceptType;
import ru.privatee.bot.utils.TGUtils;
import ru.privatee.bot.utils.UserUtils;

public class onCallBackQuery implements onCallBackQueryEvent {
public static HashMap<Long, BillPay> bills = new HashMap<Long, BillPay>();
	@Override
	public void onCallBackQuery(Update up) {
		String data = up.callbackQuery().data();
		long chatId = up.callbackQuery().message().chat().id();
		int msgId = up.callbackQuery().message().messageId();
		switch (data) {
		case "buy":{
			Bot.editMessage(chatId, msgId, "ℹ️ Чтобы ознакомиться с каналом, выбери необходимый, нажав на соответствующую кнопку",TGUtils.getKeyboardMarkup("💳 Оплатить;oplata_1:👈 Назад;backO"));
			break;
		}
		case "users":{
			HashMap<Long, User> users = UserUtils.getUsers();
			Bot.editMessage(chatId, msgId, "Список пользователей: ", TGUtils.getKeyboardMarkup("Назад;back_admins"));
			for(User u:users.values()) {
				String s = "";
				if(u.isAdmin()) {
					s = ":Забрать доступ;changerole'"+u.getChatId()+"'false";
				}else {
					s = ":Выдать доступ;changerole'"+u.getChatId()+"'true";
				}
				Bot.sendMessageButton(chatId, "UserId: "+u.getChatId()+"\n"
						+ "Доступ: "+u.getAcceptType().toString(), TGUtils.getKeyboardMarkup("Удалить пользователя;deleteuser'"+u.getChatId()+s));
			}
			return;
		}
		case "back_admins":{
			Bot.editMessage(chatId, msgId,"Доступные возможности: ", TGUtils.getKeyboardMarkup("Список пользователей;users:Рассылка сообщений;msgAll"));
		return;
		}
		case "msgAll":{
			User us = UserUtils.getUsers().get(chatId);
			Bot.editMessage(chatId, msgId, "Введите сообщение: ", TGUtils.getKeyboardMarkup("Назад;back_admins"));
			us.setMsgB(true);
			HashMap<Long, User> users = UserUtils.getUsers();
			   users.remove(us.getChatId());
			   users.put(us.getChatId(), us);
				UserUtils.saveUser(users);
			break;
		}
		case "backO":{
			Bot.editMessage(chatId, msgId, "ℹ️ Чтобы ознакомиться с каналом, выбери необходимый, нажав на соответствующую кнопку",TGUtils.getKeyboardMarkup("♻️VIP доступ - на 1 месяц;vip_1moth:♾ VIP доступ - навсегда;vip_all"));
			break;
		}
		case "check_1":{
			if(!bills.containsKey(chatId)) {
				Bot.sendAnswerCallBackQuery(up.callbackQuery(), false, "Выставленный счёт был закрыт! ");
				
				return;
			}
			BillResponse res = Qiwi.checkBill(bills.get(chatId));
			System.out.println(res.getStatus().getValue());
			if(res.getStatus().getValue().equals(BillStatus.WAITING)) {
				Bot.sendAnswerCallBackQuery(up.callbackQuery(), false, "Ваш платеж проверяется");
				return;
			}
			if(res.getStatus().getValue().equals(BillStatus.PAID)) {
				bills.remove(chatId);
				System.out.println("l1");
				User us = UserUtils.getUsers().get(chatId);
				System.out.println("l2");
				us.setAccess(AcceptType.OneMoth);
				System.out.println("l3");
				us.setURL("https://t.me/+WU3WbSEQuq03MmQy");
				
				HashMap<Long, User> users = UserUtils.getUsers();
			   users.remove(us.getChatId());
			   users.put(us.getChatId(), us);
				UserUtils.saveUser(users);
		
				InlineKeyboardMarkup board = new InlineKeyboardMarkup();
				InlineKeyboardButton button1 = new InlineKeyboardButton("Перейти на VIP канал");
				button1.url("https://t.me/+WU3WbSEQuq03MmQy");
				board.addRow(button1);
				Bot.editMessage(chatId, msgId, "Оплата прошла успешно! Теперь вам доступен VIP канал", board);
	
				return;
			}
			break;
		}
		case "check_2":{
			if(!bills.containsKey(chatId)) {
				Bot.sendAnswerCallBackQuery(up.callbackQuery(), false, "Выставленный счёт был закрыт! ");
				
				return;
			}
			BillResponse res = Qiwi.checkBill(bills.get(chatId));
			
			if(res.getStatus().getValue().equals(BillStatus.WAITING)) {
				Bot.sendAnswerCallBackQuery(up.callbackQuery(), false, "Ваш платеж проверяется");
				return;
			}
			if(res.getStatus().getValue().equals(BillStatus.PAID)) {
				bills.remove(chatId);
				User us = UserUtils.getUsers().get(chatId);
				us.setAccess(AcceptType.Forever);
				us.setURL("https://t.me/+WU3WbSEQuq03MmQy");
				us.setAcceptTime(System.currentTimeMillis());
				HashMap<Long, User> users = UserUtils.getUsers();
				   users.remove(us.getChatId());
				   users.put(us.getChatId(), us);
					UserUtils.saveUser(users);
					
				InlineKeyboardMarkup board = new InlineKeyboardMarkup();
				InlineKeyboardButton button1 = new InlineKeyboardButton("Перейти на VIP канал");
				button1.url("https://t.me/+WU3WbSEQuq03MmQy");
				board.addRow(button1);
				 Bot.editMessage(chatId, msgId, "Оплата прошла успешно! Теперь вам доступен VIP канал", board);
				return;
			}
			break;
		}
		case "qiwi_1":{
			 if(bills.containsKey(chatId)) {
		        	bills.remove(chatId);
		        }
			BillPay bill = Qiwi.getPaymentUrl(Config.getDouble("PriceOneMonth"), chatId);
			bills.put(chatId, bill);
			System.out.println(bill.getUrl());
			InlineKeyboardMarkup board = new InlineKeyboardMarkup();
			InlineKeyboardButton button1 = new InlineKeyboardButton("✅ Перейти к оплате");
			button1.url(bill.getUrl());
			board.addRow(button1);
			InlineKeyboardButton button2 = new InlineKeyboardButton("⏳ Я оплатил");
			button2.callbackData("check_1");
			board.addRow(button2);
			BaseResponse s = Bot.editMessage(chatId, msgId, "✅ Счёт на оплату сформирован. Доступы к закрытым сообществам будут открыты, как только Вы оплатите его.", board);
			System.out.println(s.description());
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					long t = 10*1000*60;
					try {
						Thread.sleep(t);
					} catch (InterruptedException e) {
					}
					bills.remove(chatId);
					Bot.editMessage(chatId, msgId, "Выставленный счет был закрыт, создайте новый запрос на оплату.\n Выберите способ оплаты 👇", TGUtils.getKeyboardMarkup(""
							+ "🥝 Qiwi;qiwi_1@💳 Банковская карта;qiwi_1"));
					return;
				}
			}).start();
			break;
		}
		case "qiwi_2":{
	        if(bills.containsKey(chatId)) {
	        	bills.remove(chatId);
	        }
			BillPay bill = Qiwi.getPaymentUrl(Config.getDouble("PriceForever"), chatId);
			bills.put(chatId, bill);
			System.out.println(bill.getUrl());
			InlineKeyboardMarkup board = new InlineKeyboardMarkup();
			InlineKeyboardButton button1 = new InlineKeyboardButton("✅ Перейти к оплате");
			button1.url(bill.getUrl());
			board.addRow(button1);
			InlineKeyboardButton button2 = new InlineKeyboardButton("⏳ Я оплатил");
			button2.callbackData("check_2");
			board.addRow(button2);
			BaseResponse s = Bot.editMessage(chatId, msgId, "✅ Счёт на оплату сформирован. Доступы к закрытым сообществам будут открыты, как только Вы оплатите его.", board);
			System.out.println(s.description());
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					long t = 10*1000*60;
					try {
						Thread.sleep(t);
					} catch (InterruptedException e) {
					}
					bills.remove(chatId);
					Bot.editMessage(chatId, msgId, "Выставленный счет был закрыт, создайте новый запрос на оплату.\n Выберите способ оплаты 👇", TGUtils.getKeyboardMarkup(""
							+ "🥝 Qiwi;qiwi_2@💳 Банковская карта;qiwi_2"));
					return;
				}
			}).start();
			break;
		}
		case "oplata_1":{
			Bot.editMessage(chatId, msgId, "Выберите способ оплаты 👇", TGUtils.getKeyboardMarkup(""
					+ "🥝 Qiwi;qiwi_1@💳 Банковская карта;qiwi_1"));
			break;
		}
		case "oplata_2":{
			Bot.editMessage(chatId, msgId, "Выберите способ оплаты 👇", TGUtils.getKeyboardMarkup(""
					+ "🥝 Qiwi;qiwi_2:💳 Банковская карта;qiwi_2"));
			break;
		}
		case "vip_1moth":{
			
			
			Bot.editMessage(chatId, msgId, "Тариф: ♻️ VIP доступ - на 1 месяц ♻️\r\n"
					+ "Цена: "+Config.getDouble("PriceOneMonth")+" RUB\r\n"
					+ "Срок действия: 1 месяц \r\n"
					+ "\r\n"
					+ "Описание тарифа: \r\n"
					+ "Вы получите доступ ко всем 5-ти тарифам на 1 месяц.\r\n"
					+ "Тарифы:\r\n"
					+ "1) Сливы OnlyFans\r\n"
					+ "2) Сливы Знаменитостей\r\n"
					+ "3) Сливы TikTok\r\n"
					+ "4) Каталог моделей\r\n"
					+ "5) Архивы",TGUtils.getKeyboardMarkup("💳 Оплатить;oplata_1:👈 Назад;backO"));
			
			break;
		}
		case "vip_all":{
			Bot.editMessage(chatId, msgId, "Тариф: ♾ VIP доступ - навсегда ♾\r\n"
					+ "Цена: "+Config.getDouble("PriceForever")+" RUB\r\n"
					+ "Срок действия: бессрочно \r\n"
					+ "\r\n"
					+ "Описание тарифа: \r\n"
					+ "Вы получите доступ ко всем 5-ти тарифам на 1 месяц. \r\n"
					+ "Тарифы: \r\n"
					+ "1) Сливы OnlyFans \r\n"
					+ "2) Сливы Знаменитостей \r\n"
					+ "3) Сливы TikTok \r\n"
					+ "4) Каталог моделей \r\n"
					+ "5) Архивы",TGUtils.getKeyboardMarkup("💳 Оплатить;oplata_2:👈 Назад;backO"));
			break;
		}
		}
		if(data.split("'")[0].equalsIgnoreCase("changerole")) {
			Long l = new Long(data.split("'")[1]);
			String p = data.split("'")[2];
			HashMap<Long, User> users = UserUtils.getUsers();
			  User other =  users.get(l);
			  users.remove(l);
			  if(p.equals("true")) {
			  other.setAdmin(true);
			  }
			  if(p.equals("false")) {
				  other.setAdmin(false);
			  }
				UserUtils.saveUser(users);
		}
		if(data.split("'")[0].equalsIgnoreCase("deleteuser")) {
			Long l = new Long(data.split("'")[1]);
			HashMap<Long, User> users = UserUtils.getUsers();
			   users.remove(l);
				UserUtils.saveUser(users);
				Bot.editMessage(chatId, msgId, "Пользователь удален.", new InlineKeyboardMarkup());
		}
	}

}
