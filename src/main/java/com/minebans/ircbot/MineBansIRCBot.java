package com.minebans.ircbot;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.jibble.pircbot.Colors;
import org.jibble.pircbot.PircBot;

import com.minebans.webapilib.MineBansWebAPI;
import com.minebans.webapilib.data.PlayerBan;

public class MineBansIRCBot extends PircBot {
	
	private static final String VERSION = "0.1";
	
	private String host;
	private String channel;
	private String nsPass;
	
	private MineBansWebAPI api;
	
	private MineBansIRCBot(String host, String nick, String channel, String nsPass){
		this.host = host;
		this.channel = channel;
		this.nsPass = nsPass;
		
		this.setName(nick);
		this.setLogin(nick);
		this.setVersion("MineBans IRC Bot v" + VERSION);
		this.setMessageDelay(500L);
		
		this.api = new MineBansWebAPI(null);
		
		this.connect();
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
	
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message){
		if (message.startsWith("!")){
			String[] parts = message.substring(1).split(" ");
			
			if (parts[0].equalsIgnoreCase("lookup")){
				if (parts.length != 2){
					this.sendMessage(channel, Colors.RED + "Usage: !lookup <player_name>");
					return;
				}
				
				try{
					List<PlayerBan> bans = this.api.getPlayerBans(parts[1]);
					
					this.sendMessage(channel, parts[1] + " has been banned by " + bans.size() + " server(s):");
					
					for (PlayerBan ban : bans){
						this.sendMessage(channel, "  - " + ban.getServerName() + " for " + ban.getReason().toLowerCase());
					}
				}catch (IllegalArgumentException e){
					this.sendMessage(channel, Colors.RED + "Invalid player name");
				}catch (IOException e){
					this.sendMessage(channel, sender + " " + e.getMessage());
				}
			}
		}
	}
	
	public void onDisconnect(){
		System.out.println("Lost connection to server, will retry in 10 seconds");
		
		try{
			Thread.sleep(10000);
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		
		this.connect();
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
		
		new MineBansIRCBot(args[0], args[1], args[2], args[3]);
	}
	
}
