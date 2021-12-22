package ru.privatee.bot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import ru.privatee.bot.object.User;
import ru.privatee.bot.utils.UserUtils;

public class Config {
    
	private static File file = new File("config.yml");
	private static Map<String, Object> map;
	private static HashMap<Long, User> users = new HashMap<Long, User>();
	public static void saveConfig(Map<String, Object> map) {
		 PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} 
		 DumperOptions options = new DumperOptions(); 
		 options.setIndent(2); 
		 options.setAllowUnicode(false);
		 options.setPrettyFlow(true); 
		 options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); 
		 Yaml yaml = new Yaml(options); 
		 yaml.dump(map, writer); 
	}
	private static Map<String, Object> getDefaltConfig(){
		 Map<String, Object> map = new HashMap<>(); 
		 
		map.put("Token", "5063239240:AAGF773wb_DvxPb9LlNUIkMb-7HNA6Aw834");
		map.put("QiwiPublicKey","48e7qUxn9T7RyYE1MVZswX1FRSbE6iyCj2gCRwwF3Dnh5XrasNTx3BGPiMsyXQFNKQhvukniQG8RTVhYm3iPxZREA6XAtpGx5VrgDGq1d4D3RsHRcZJBrocXPXwPdaFDmgE1mQcXSAsAZRWe336suGRbN5EYmvsNBFgA8oqxtkPHHVAWNLNTMBkHoFJMc");
		map.put("QiwiSecretKey","eyJ2ZXJzaW9uIjoiUDJQIiwiZGF0YSI6eyJwYXlpbl9tZXJjaGFudF9zaXRlX3VpZCI6InYxZGN6ai0wMCIsInVzZXJfaWQiOiIzNzUyOTcxMDc5MTAiLCJzZWNyZXQiOiI1OTFjMTE1YzA4ZmE2MDA5ZWYyZDE4NDY2ZGM1MTdiYTg1MzI1MWJiZWZiZjRiYzZhNDg3NDNlNmNlOWE4YTg0In19");
		map.put("PriceOneMonth", "339.0");
		map.put("PriceForever", "499.0");
		return map;
	}
	public static void set(String key, Object obj) {
		if(map.containsKey(key)) {
			map.remove(key);
		}
		map.put(key, obj);
		saveConfig(map);
	}
	public static void saveUser(User us) {
		users.remove(us.getChatId());
		users.put(us.getChatId(), us);
		UserUtils.saveUser(users);
	}
	public static Boolean getBoolean(String key) {
		return (boolean) map.get(key);
	}
	public static String getString(String key) {
		return (String) map.get(key);
	}
	@SuppressWarnings("removal")
	public static Double getDouble(String key) {
		Double d = 0.0;
		try {
		d = new Double(getString(key));
		}catch(NumberFormatException ex) {
			System.out.println("Ошибка конфигураций: ошибка формата числа (double): "+key);
			ex.printStackTrace();
		}
		return d;
	}
	@SuppressWarnings("removal")
	public static Long getLong(String key) {
		long lo = 0;
		try {
		lo = new Long(getString(key));
		}catch(NumberFormatException ex) {
			System.out.println("Ошибка конфигураций: ошибка формата числа (long): "+key);
			ex.printStackTrace();
		}
		return lo;
	}
	@SuppressWarnings("removal")
	public static int getInt(String key) {
		int i = 0;
		try {
		i = new Integer(getString(key));
		}catch(NumberFormatException ex) {
			System.out.println("Ошибка конфигураций: ошибка формата числа (int): "+key);
			ex.printStackTrace();
		}
		return i;
	}
	public static List<String> getStringList(String key) {
		List<String> list = new ArrayList<String>();
		for(String s:getString(key).split(",")) {
			list.add(s);
		}
		return list;
	}
	public static void loadConfig(){
		DumperOptions options = new DumperOptions(); 
		 options.setIndent(2); 
		 options.setAllowUnicode(false);
		 options.setPrettyFlow(true); 
		 options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); 
		 Yaml yaml = new Yaml(options); 
		  InputStream inputStream = null;
		 
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			try {
				file.createNewFile();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			saveConfig(getDefaltConfig());
			try {
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} 
		 map = yaml.load(inputStream);
		 users= UserUtils.getUsers();
	}
	public static HashMap<Long, User> getUsers(){
		return users;
	}
	public static  Map<String, Object>  getConfig() {
	return map;
	}
	@SuppressWarnings("unchecked")
	public static List<String> getStringListOld(String string) {
		return (List<String>) map.get(string);
	}
}
