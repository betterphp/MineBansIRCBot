package com.minebans.ircbot;

import java.io.IOException;
import java.util.List;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import com.minebans.webapilib.data.PlayerBan;

public class LookupCommand extends Command {
	
	public LookupCommand(MineBansIRCBot bot){
		super(bot, "lookup");
	}
	
	@Override
	public void execute(User user, Channel channel, String[] args){
		if (args.length != 1){
			channel.sendMessage(Colors.RED + "Usage: !lookup <player_name>");
			return;
		}
		
		try{
			List<PlayerBan> bans = this.bot.getAPI().getPlayerBans(args[0]);
			
			channel.sendMessage(args[0] + " has been banned by " + bans.size() + " server(s):");
			
			for (PlayerBan ban : bans){
				channel.sendMessage("  - " + ban.getServerName() + " for " + ban.getReason().toLowerCase());
			}
		}catch (IllegalArgumentException e){
			channel.sendMessage(Colors.RED + "Invalid player name");
		}catch (IOException e){
			channel.sendMessage(user.getNick() + " " + e.getMessage());
		}
	}
	
}
