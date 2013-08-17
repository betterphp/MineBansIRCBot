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
		String staffList = "";
		
		if (!user.getNick().equals(this.bot.getNick())){
			for (User op : channel.getOps()){
				if (!op.isAway()){
					staffList += op.getNick() + " ";
				}
			}
			
			this.bot.sendNotice(channel, "Hi " + user.getNick() + ", This channel is not always occupied so please be patient. You may prefer to email support@minebans.com if nobody is available");
			this.bot.sendNotice(channel, "Staff online: " + staffList.substring(0, staffList.length() - 1));
		}
	}
	
}
