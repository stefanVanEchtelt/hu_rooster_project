package controller;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import model.PrIS;
import server.Conversation;
import server.Handler;
import java.util.List;

import model.klas.Klas;
import model.les.Les;

import model.persoon.Docent;
import model.persoon.Student;

public class RoosterController implements Handler {
	private PrIS informatieSysteem;
	
	public RoosterController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	
	public void handle(Conversation conversation) {
		if (conversation.getRequestedURI().startsWith("/rooster/les/ophalen")) {
			ophalenLes(conversation);
		} else {
			ophalenDag(conversation);
		}
		
		
	}
	
	private void ophalenLes(Conversation conversation) {		
		JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String datum = lJsonObjectIn.getString("datum");
		String username = lJsonObjectIn.getString("username");
		String les = lJsonObjectIn.getString("les");
		
		Student s = informatieSysteem.getStudent(username);
		Klas k = informatieSysteem.getKlas(s.getKlasCode());
		List<Les> lessen = k.getLessenByDate(datum);
		Les cles = null;
		
		for (Les l : lessen) {
			if (l.getCursuscode().equals(les)) {
				for (Docent d : l.getDocenten()) {
					System.out.println(d.getGebruikersnaam());
				}
				cles = l;
			}
		}
		
		System.out.println(cles);
		
		JsonObjectBuilder lJsonObjectBuilder = Json.createObjectBuilder();
		
		lJsonObjectBuilder 
			.add("les_datum", cles.getStartDatum())
			.add("start_tijd", cles.getStartTijd())
			.add("eind_tijd", cles.getEindTijd())
			.add("cursus_code", cles.getCursuscode());

		String lJsonOut = lJsonObjectBuilder.build().toString();
		
		conversation.sendJSONMessage(lJsonOut);
	}
	
	private void ophalenDag(Conversation conversation) {		
		JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String datum = lJsonObjectIn.getString("datum");
		String username = lJsonObjectIn.getString("username");
		String type = lJsonObjectIn.getString("type");
		
		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();
		
		List<Les> lessen = null;
		if (type.equals("Leerling")) {
			Student s = informatieSysteem.getStudent(username);
			Klas k = informatieSysteem.getKlas(s.getKlasCode());
			lessen = k.getLessenByDate(datum);
			
		} else {
			Docent d = informatieSysteem.getDocent(username);
			lessen = d.getLessenByDate(datum);
		}
		
		for (Les l : lessen) {
			JsonObjectBuilder lJsonObjectBuilderVoorStudent = Json.createObjectBuilder();
			lJsonObjectBuilderVoorStudent
					.add("naam", l.getNaam())
					.add("start_tijd", l.getStartTijd())
					.add("eind_tijd", l.getEindTijd())
					.add("duur", l.getDuur())
					.add("cursuscode", l.getCursuscode());
		  
			lJsonArrayBuilder.add(lJsonObjectBuilderVoorStudent);	
		}
		
		String lJsonOutStr = lJsonArrayBuilder.build().toString();
		conversation.sendJSONMessage(lJsonOutStr);	
	}
}
