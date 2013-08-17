package com.minebans.ircbot;

import org.pircbotx.Channel;
import org.pircbotx.User;

public abstract class Command {
	
	protected MineBansIRCBot bot;
	protected String name;
	
	public Command(MineBansIRCBot bot, String name){
		this.bot = bot;
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public abstract void execute(User user, Channel channel, String[] args);
	
}
