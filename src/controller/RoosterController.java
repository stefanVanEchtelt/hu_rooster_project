package controller;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import model.PrIS;
import server.Conversation;
import server.Handler;
import java.util.ArrayList;
import java.util.List;

import model.klas.Klas;
import model.les.Les;
import model.persoon.Student;

public class RoosterController implements Handler {
	private PrIS informatieSysteem;
	
	public RoosterController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	
	public void handle(Conversation conversation) {
		ophalen(conversation);
	}
	
	private void ophalen(Conversation conversation) {
		System.out.println("OPPPPHALLLEENENN");
		
		JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String datum = lJsonObjectIn.getString("datum");
		String klasCode = lJsonObjectIn.getString("klascode");
		
		Klas k = informatieSysteem.getKlas(klasCode);
		List<Les> lessen = k.getLessenByDate(datum);
		
		System.out.println("xx");
		System.out.println(datum);
		System.out.println(klasCode);
		
		System.out.println(lessen);
   
		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();
		
		for (Les l : lessen) {
			JsonObjectBuilder lJsonObjectBuilderVoorStudent = Json.createObjectBuilder();
			lJsonObjectBuilderVoorStudent.add("naam", "OODC").add("start_datum", l.getStartDatum()).add("real_name", l.getNaam());					     
		  
		  lJsonArrayBuilder.add(lJsonObjectBuilderVoorStudent);	
		}
		
		String lJsonOutStr = lJsonArrayBuilder.build().toString();
		conversation.sendJSONMessage(lJsonOutStr);	
	}
}
