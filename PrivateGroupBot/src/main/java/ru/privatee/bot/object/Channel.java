package ru.privatee.bot.object;

import java.util.ArrayList;
import java.util.List;

public class Channel {
private static List<Long> list;
public Channel(String name, long id) {
	
}
public static List<Long> getChats(){
	list = new ArrayList<Long>();
	list.add((long) -1350288296);
	list.add((long) -1200512016);
	list.add((long) -1597095002);
	list.add((long) -1771123650);
	return list;
}
public static long getMainChannel() {
	return -1350288296;
}
}
