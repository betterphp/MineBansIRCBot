package com.minebans.ircbot;

import java.io.File;
import java.io.PrintStream;

import org.pircbotx.PircBotX;

import com.minebans.webapilib.MineBansWebAPI;

public class MineBansIRCBot extends PircBotX {
	
	private static final String VERSION = "0.1";
	
	private String host;
	private String channel;
	private String nsPass;
	
	private MineBansWebAPI api;
	
	private CommandHandler commandHandler;
	
	private MineBansIRCBot(String host, String nick, String channel, String nsPass){
		this.host = host;
		this.channel = channel;
		this.nsPass = nsPass;
		
		this.setName(nick);
		this.setLogin(nick);
		this.setVersion("MineBans IRC Bot v" + VERSION);
		this.setMessageDelay(500L);
		this.setAutoReconnect(true);
		
		this.api = new MineBansWebAPI(null);
		
		this.commandHandler = new CommandHandler(this, "!");
		
		this.commandHandler.registerCommand(new LookupCommand(this));
	}
	
	public MineBansWebAPI getAPI(){
		return this.api;
	}
	
	private void connect(){
		try{
			this.connect(this.host);
		}catch (Exception e){
			e.printStackTrace();
			return;
		}
		
		this.identify(this.nsPass);
		this.joinChannel(this.channel);
	}
	
	public static void main(String[] args){
		if (args.length != 4){
			System.out.println("Usage: java -jar MineBansIRCBot.jar <host> <nick> <channel> <nickserv_password>");
			return;
		}
		
		try{
			PrintStream stream = new PrintStream(new File("irb-bot.log"));
			
		    System.setOut(stream);
		    System.setErr(stream);
		}catch (Exception e){
		     e.printStackTrace();
		}
		
		MineBansIRCBot bot = new MineBansIRCBot(args[0], args[1], args[2], args[3]);
		
		bot.connect();
	}
	
}
