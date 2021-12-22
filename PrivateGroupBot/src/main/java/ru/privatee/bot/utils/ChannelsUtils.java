package ru.privatee.bot.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import ru.privatee.bot.object.Channel;
import ru.privatee.bot.object.User;

public class ChannelsUtils {
	static String path = "Channels.dat";
	public static void saveUser(HashMap<Long, Channel> channels) {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path)))
        {
            oos.writeObject(channels);
        }
        catch(Exception ex){
            
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } 
    } 
	@SuppressWarnings("unchecked")
	public static HashMap<Long, Channel> getUsers(){
		HashMap<Long, Channel> newChannels= new HashMap<Long, Channel>();
        
        	ObjectInputStream ois;
			try {
				
				ois = new ObjectInputStream(new FileInputStream(path));
				newChannels=((HashMap<Long, Channel>)ois.readObject());
			} catch (FileNotFoundException e) {
				System.out.println("Фаил "+path+" не найден: "+e.getMessage());
			} catch (IOException e) {
				System.out.println("Ошибка при загрузке данных пользователей: "+e.getMessage());
			} catch (ClassNotFoundException e) {
				System.out.println("Не удалось найти класс при чтении фала пользователей (readObject): "+e.getMessage());
				
			}
        
       
          
        return newChannels;
	}
}
