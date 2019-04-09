package controller;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import model.PrIS;
import model.klas.Klas;
import model.les.Les;
import server.Conversation;
import server.Handler;
import model.persoon.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;  

public class LessenInVerledenController implements Handler {
	private PrIS informatieSysteem;
	
	public LessenInVerledenController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	
	public void handle(Conversation conversation) {
			ophalen(conversation);

	}
	
	private void ophalen(Conversation conversation) {
		System.out.println("afgelopen lessen ophalen");
		
		JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		int currentIdLeerling = lJsonObjectIn.getInt("nummerLeerling");
		Student currentLeerling = informatieSysteem.getStudent(currentIdLeerling); 
		Klas klasVanCurrentLeerling = informatieSysteem.getKlas(currentLeerling.getKlasCode());
		
		List<Les> lessenVanCurrentKlas = klasVanCurrentLeerling.getLessen();
		List<String> lessenVoorVandaag = new ArrayList<>();
		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();
		for(Les l : lessenVanCurrentKlas) {
			String sDate = l.getStartDatum();
			Date date = null;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Date curdate = new Date();
			String curdateString = new SimpleDateFormat("yyyy-MM-dd").format(curdate);
			try {
				curdate =  new SimpleDateFormat("yyyy-MM-dd").parse(curdateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(date.before(curdate)) {
				JsonObjectBuilder lJsonObjectBuilderVoorStudent = Json.createObjectBuilder();
				lJsonObjectBuilderVoorStudent
						.add("naam", l.getNaam())
						.add("start_tijd", l.getStartTijd())
						.add("eind_tijd", l.getEindTijd())
						.add("duur", l.getDuur())
						.add("cursuscode", l.getCursuscode());
			  
				lJsonArrayBuilder.add(lJsonObjectBuilderVoorStudent);	
			}
		}
//		int aantalLessen = lessenVoorVandaag.size();
		
		String lJsonOut = lJsonArrayBuilder.build().toString();
		conversation.sendJSONMessage(lJsonOut);

	}
}
