package com.minebans.ircbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

public class ConfigFile {
	
	private Properties properties;
	private HashMap<String, String> defaults;
	
	public ConfigFile(File configFile) throws IOException {
		this.properties = new Properties();
		
		this.defaults = new HashMap<String, String>();
		this.defaults.put("host", "irc.esper.net");
		this.defaults.put("channel", "#minebans");
		this.defaults.put("nick", "MineBans");
		this.defaults.put("nickserv-password", "change-me");
		
		if (configFile.exists()){
			FileInputStream input = new FileInputStream(configFile);
			this.properties.load(input);
			input.close();
		}else{
			for (Entry<String, String> entry : this.defaults.entrySet()){
				this.properties.put(entry.getKey(), entry.getValue());
			}
			
			FileOutputStream output = new FileOutputStream(configFile);
			this.properties.store(output, null);
			output.close();
		}
	}
	
	private String getProperty(String key){
		return this.properties.getProperty(key, this.defaults.get(key));
	}
	
	public String getHost(){
		return this.getProperty("host");
	}
	
	public String getChannel(){
		return this.getProperty("channel");
	}
	
	public String getNick(){
		return this.getProperty("nick");
	}
	
	public String getNickServPassword(){
		return this.getProperty("nickserv-password");
	}
	
}
