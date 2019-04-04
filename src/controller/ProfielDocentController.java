package controller;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import model.PrIS;
import model.persoon.Docent;
import server.Conversation;
import server.Handler;

public class ProfielDocentController implements Handler {
	private PrIS informatieSysteem;
	
	/**
	 * De StudentController klasse moet alle student-gerelateerde aanvragen
	 * afhandelen. Methode handle() kijkt welke URI is opgevraagd en laat
	 * dan de juiste methode het werk doen. Je kunt voor elke nieuwe URI
	 * een nieuwe methode schrijven.
	 * 
	 * @param infoSys - het toegangspunt tot het domeinmodel
	 */
	public ProfielDocentController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}

	public void handle(Conversation conversation) {
		if (conversation.getRequestedURI().startsWith("/docent/informatie")) {
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
		Docent lDocentZelf = informatieSysteem.getDocent(lGebruikersnaam);
		
		
		JsonObjectBuilder lJsonObjectBuilder = Json.createObjectBuilder();
		lJsonObjectBuilder.add("naam", lDocentZelf.getFullName()); // en teruggekregen gebruikersrol als JSON-object...
		lJsonObjectBuilder.add("docentnummer", lDocentZelf.getDocentNummer()); // en teruggekregen gebruikersrol als JSON-object...


		String lJsonOut = lJsonObjectBuilder.build().toString();

		conversation.sendJSONMessage(lJsonOut); // terugsturen naar de Polymer-GUI!
	}

}
