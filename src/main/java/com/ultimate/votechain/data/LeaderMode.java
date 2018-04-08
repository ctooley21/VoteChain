package com.ultimate.votechain.data;

public class LeaderMode {

	public LeaderMode() {
		
	}
	public void Heartbeat(int beatVal, String ipAdress) {
		System.out.println("Sending Heartbeat " + beatVal);
		InitializeNetwork.sendMessage(ipAdress, 9001, Integer.toString(beatVal));
	}

}
