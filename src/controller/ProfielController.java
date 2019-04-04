package controller;

import java.util.Calendar;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import model.PrIS;
import model.klas.Klas;
import model.persoon.Student;
import server.Conversation;
import server.Handler;

public class ProfielController implements Handler {
	private PrIS informatieSysteem;
	
	/**
	 * De StudentController klasse moet alle student-gerelateerde aanvragen
	 * afhandelen. Methode handle() kijkt welke URI is opgevraagd en laat
	 * dan de juiste methode het werk doen. Je kunt voor elke nieuwe URI
	 * een nieuwe methode schrijven.
	 * 
	 * @param infoSys - het toegangspunt tot het domeinmodel
	 */
	public ProfielController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}

	public void handle(Conversation conversation) {
		if (conversation.getRequestedURI().startsWith("/student/informatie")) {
			ophalen(conversation);
		} 
		else {
		}
	}

	/**
	 * Deze methode haalt eerst de opgestuurde JSON-data op. Daarna worden
	 * de benodigde gegevens uit het domeinmodel gehaald. Deze gegevens worden
	 * dan weer omgezet naar JSON en teruggestuurd naar de Polymer-GUI!
	 * 
	 * @param conversation - alle informatie over het request
	 */
	private void ophalen(Conversation conversation) {
		JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String lGebruikersnaam = lJsonObjectIn.getString("username");
		Student lStudentZelf = informatieSysteem.getStudent(lGebruikersnaam);
		
		Klas lKlas = informatieSysteem.getKlasVanStudent(lStudentZelf);		            // klas van de student opzoeken
		
		JsonObjectBuilder lJsonObjectBuilder = Json.createObjectBuilder();
		lJsonObjectBuilder.add("klas", lKlas.getKlasCode()); // en teruggekregen gebruikersrol als JSON-object...
		lJsonObjectBuilder.add("naam", lStudentZelf.getFullName()); // en teruggekregen gebruikersrol als JSON-object...
		lJsonObjectBuilder.add("studentNummer", lStudentZelf.getStudentNummer()); // en teruggekregen gebruikersrol als JSON-object...

		String lJsonOut = lJsonObjectBuilder.build().toString();

		conversation.sendJSONMessage(lJsonOut); // terugsturen naar de Polymer-GUI!
	}

}
