package controller;

import javax.json.JsonObject;

import model.PrIS;
import server.Conversation;
import server.Handler;

public class RoosterController implements Handler {
	private PrIS informatieSysteem;
	
	public RoosterController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	
	public void handle(Conversation conversation) {
		ophalen(conversation);
	}
	
	private void ophalen(Conversation conversation) {
		JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String datum = lJsonObjectIn.getString("datum");
		String klas = lJsonObjectIn.getString("klas");
		
		conversation.sendJSONMessage("xxx");	
	}
}
