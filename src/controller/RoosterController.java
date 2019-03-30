package controller;

import javax.json.JsonObject;

import model.PrIS;
import server.Conversation;
import server.Handler;
import java.util.ArrayList;
import java.util.List;

import model.klas.Klas;
import model.les.Les;

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
		String klasCode = lJsonObjectIn.getString("klasCode");
		
		Klas k = informatieSysteem.getKlas(klasCode);
		List<Les> lessen = k.getLessenByDate(datum);
		
		conversation.sendJSONMessage("xxx");	
	}
}
