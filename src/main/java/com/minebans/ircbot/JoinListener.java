package com.minebans.ircbot;

import java.util.Random;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;

public class JoinListener extends ListenerAdapter<MineBansIRCBot> {
	
	private Random random;
	
	public JoinListener(){
		this.random = new Random();
	}
	
	@Override
	public void onJoin(JoinEvent<MineBansIRCBot> event){
		User user = event.getUser();
		Channel channel = event.getChannel();
		
		User[] ops = channel.getOps().toArray(new User[0]);
		
		channel.sendMessage("Hi " + user.getNick() + ", if you are here for support please be patient.");
		channel.sendMessage("This channel is not occupied all of the time, you may prefer to email support@minebans.com");
		channel.sendMessage("I'll let " + ops[this.random.nextInt(ops.length)] + " know you're here.");
	}
	
}
