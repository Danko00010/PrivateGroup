package ru.privatee.bot.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import ru.privatee.bot.object.User;

public class UserUtils {
	static String path = "Users.dat";
	public static void saveUser(HashMap<Long, User> users) {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path)))
        {
            oos.writeObject(users);
        }
        catch(Exception ex){
            
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } 
    } 
	@SuppressWarnings("unchecked")
	public static HashMap<Long, User> getUsers(){
		HashMap<Long, User> newUsers= new HashMap<Long, User>();
        
        	ObjectInputStream ois;
			try {
				
				ois = new ObjectInputStream(new FileInputStream(path));
				newUsers=((HashMap<Long, User>)ois.readObject());
			} catch (FileNotFoundException e) {
				System.out.println("Фаил "+path+" не найден: "+e.getMessage());
			} catch (IOException e) {
				System.out.println("Ошибка при загрузке данных пользователей: "+e.getMessage());
			} catch (ClassNotFoundException e) {
				System.out.println("Не удалось найти класс при чтении фала пользователей (readObject): "+e.getMessage());
				
			}
        
       
          
        return newUsers;
	}
}
