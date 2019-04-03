package controller;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import model.PrIS;
import model.klas.Klas;
import server.Conversation;
import server.Handler;

public class KlassenController implements Handler {
	private PrIS informatieSysteem;
	
	public KlassenController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	
	public void handle(Conversation conversation) {
		ophalen(conversation);
	}
	
	private void ophalen(Conversation conversation) {
		System.out.println("KLAS OPHALEN");

		ArrayList<Klas> klassen = informatieSysteem.getKlassen();
		
		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();

		for (Klas k : klassen) {
			JsonObjectBuilder lJsonObjectBuilderVoorKlassen = Json.createObjectBuilder();
			lJsonObjectBuilderVoorKlassen
					.add("klascode", k.getKlasCode())
					.add("naam", k.getNaam())
					.add("de_studenten", k.getStudenten().toString());
		  
			lJsonArrayBuilder.add(lJsonObjectBuilderVoorKlassen);	
		}
		
		String lJsonOutStr = lJsonArrayBuilder.build().toString();
		conversation.sendJSONMessage(lJsonOutStr);	
	}
}
