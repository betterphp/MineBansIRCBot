package com.minebans.ircbot;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.pircbotx.PircBotX;

import com.minebans.webapilib.MineBansWebAPI;

public class MineBansIRCBot extends PircBotX {
	
	private static final String VERSION = "0.1";
	
	private MineBansWebAPI api;
	private ConfigFile config;
	private CommandHandler commandHandler;
	
	private MineBansIRCBot(File configFile){
		this.api = new MineBansWebAPI(null);
		
		try{
			this.config = new ConfigFile(configFile);
		}catch (IOException e){
			e.printStackTrace();
		}
		
		this.commandHandler = new CommandHandler(this, "!");
		
		this.setName(this.config.getNick());
		this.setLogin(this.config.getNick());
		this.setVersion("MineBans IRC Bot v" + VERSION);
		this.setMessageDelay(250L);
		this.setAutoReconnect(true);
		
		this.commandHandler.registerCommand(new LookupCommand(this));
		
		this.getListenerManager().addListener(new JoinListener(this));
	}
	
	public MineBansWebAPI getAPI(){
		return this.api;
	}
	
	public ConfigFile getConfig(){
		return this.config;
	}
	
	private void connect(){
		try{
			this.connect(this.config.getHost());
		}catch (Exception e){
			e.printStackTrace();
			return;
		}
		
		this.identify(this.config.getNickServPassword());
		this.joinChannel(this.config.getChannel());
	}
	
	public static void main(String[] args){
		try{
			PrintStream stream = new PrintStream(new File("irc-bot.log"));
			
		    System.setOut(stream);
		    System.setErr(stream);
		}catch (Exception e){
		     e.printStackTrace();
		}
		
		MineBansIRCBot bot = new MineBansIRCBot(new File("irc-bot.properties"));
		
		bot.connect();
	}
	
}
