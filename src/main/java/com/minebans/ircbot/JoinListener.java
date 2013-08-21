package com.minebans.ircbot;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;

public class JoinListener extends ListenerAdapter<MineBansIRCBot> {
	
	private MineBansIRCBot bot;
	
	public JoinListener(MineBansIRCBot bot){
		this.bot = bot;
	}
	
	@Override
	public void onJoin(JoinEvent<MineBansIRCBot> event){
		User user = event.getUser();
		Channel channel = event.getChannel();
		
		if (!user.getNick().equals(this.bot.getNick())){
			String staffList = "";
			
			for (User op : channel.getOps()){
				if (!op.isAway()){
					staffList += op.getNick() + " ";
				}
			}
			
			if (staffList.isEmpty()){
				this.bot.sendNotice(user, "Hi " + user.getNick() + ", There are no staff online at the moment, you may prefer to email support@minebans.com");
			}else{
				this.bot.sendNotice(user, "Hi " + user.getNick() + ", This channel is not always being watched so please be patient, I'll let the staff know you're here.");
				
				for (User op : channel.getOps()){
					this.bot.sendNotice(op, op.getNick() + " a new user just joined; " + user.getNick());
				}
			}
		}
	}
	
}
