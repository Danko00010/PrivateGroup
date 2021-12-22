package ru.privatee.bot.object;

public class BillPay {
private String bill;
private String url;
private long chatId;

public BillPay(String bill, String url, long chatId) {
	this.bill = bill;
	this.url = url;
	this.chatId = chatId;
}
public String getBill() {
	return bill;
}
public String getUrl() {
	return url;
}
public Long getChatId() {
	return chatId;
}
}
