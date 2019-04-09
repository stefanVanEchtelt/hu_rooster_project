package controller;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.sun.javafx.geom.AreaOp.AddOp;

import server.Handler;

import model.PrIS;
import model.klas.Klas;
import model.persoon.Student;
import server.Conversation;

public class KlassenInhoudController implements Handler {
	private PrIS informatieSysteem;
	
	public KlassenInhoudController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	
	public void handle(Conversation conversation) {
			ophalen(conversation);
	}
	
	private void ophalen(Conversation conversation) {
		System.out.println("LEERLINGEN OPHALEN");
		
		JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String klasCodeCurrent = lJsonObjectIn.getString("huidigeKlas");
		
		Klas currentKlas = informatieSysteem.getKlas(klasCodeCurrent);
		
		List<Student> leerlingenInKlas = currentKlas.getStudenten();
		
		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();

		for (Student s : leerlingenInKlas) {
			JsonObjectBuilder lJsonObjectBuilderVoorLeerlingen = Json.createObjectBuilder();
			lJsonObjectBuilderVoorLeerlingen
					.add("id", s.getStudentNummer())
					.add("voornaam", s.getVoornaam())
					.add("achternaam", s.getVolledigeAchternaam())
					.add("username", s.getGebruikersnaam());
		  
			lJsonArrayBuilder.add(lJsonObjectBuilderVoorLeerlingen);	
		}
		
		String lJsonOutStr = lJsonArrayBuilder.build().toString();
		conversation.sendJSONMessage(lJsonOutStr);
	}
	
}
