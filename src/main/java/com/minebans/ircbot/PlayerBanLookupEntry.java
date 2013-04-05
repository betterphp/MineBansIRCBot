package com.minebans.ircbot;

public class PlayerBanLookupEntry {
	
	private String player_name;
	private String server_name;
	private int time;
	private String reason;
	private String long_reason;
	
	public String getPlayerName(){
		return this.player_name;
	}
	
	public String getServerName(){
		return this.server_name;
	}
	
	public int getTime(){
		return this.time;
	}
	
	public String getReason(){
		return this.reason;
	}
	
	public String getLongReason(){
		return this.long_reason;
	}
	
}
