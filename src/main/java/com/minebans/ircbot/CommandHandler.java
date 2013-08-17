package com.minebans.ircbot;

import java.util.HashMap;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandHandler extends ListenerAdapter<MineBansIRCBot> {
	
	private String controlChar;
	private HashMap<String, Command> commands;
	
	public CommandHandler(MineBansIRCBot bot, String controlChar){
		this.controlChar = controlChar;
		this.commands = new HashMap<String, Command>();
		
		bot.getListenerManager().addListener(this);
	}
	
	public void registerCommand(Command command){
		this.commands.put(command.getName(), command);
	}
	
	@Override
	public void onMessage(MessageEvent<MineBansIRCBot> event){
		if (event.getMessage().startsWith(this.controlChar)){
			String[] parts = event.getMessage().substring(1).split(" ");
			Command command = this.commands.get(parts[0]);
			
			if (command != null){
				String args[] = new String[parts.length - 1];
				System.arraycopy(parts, 1, args, 0, args.length);
				
				try{
					command.execute(event.getUser(), event.getChannel(), args);
				}catch (Throwable t){
					System.err.println("Unhandled exception caught while executing command '" + command.getName() + "'");
					t.printStackTrace();
				}
			}
		}
	}
	
}
