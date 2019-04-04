package model.les;

import java.io.Serializable;
import java.util.ArrayList;

import model.klas.Klas;
import model.persoon.Docent;

public class Les implements Serializable {
	private String naam;
	private String cursuscode;
	private String startweek;
	private String startDag;
	private String startdatum;
	private String starttijd;
	private String einddag;
	private String einddatum;
	private String eindtijd;
	private String duur;
	private String werkvorm;
	private ArrayList<Docent> docenten;
	private String lokaalnummers;
	private ArrayList<Klas> ingeroosterdeKlassen;
	private String faculteit;
	private String grootte;
	private String opmerkingen;
	
	public Les(String naam, String cursuscode, String startweek, String startDag, String startdatum, String starttijd, String einddag, String einddatum, String eindtijd, String duur, String werkvorm, ArrayList<Docent> docenten, String lokaalnummers, ArrayList<Klas> ingeroosterdeKlassen, String faculteit, String grootte, String opmerkingen) {
		this.naam = naam;
		this.cursuscode = cursuscode;
		this.startweek = startweek;
		this.startDag = startDag;
		this.startdatum = startdatum;
		this.starttijd = starttijd;
		this.einddag = einddag;
		this.einddatum = einddatum;
		this.eindtijd = eindtijd;
		this.duur = duur;
		this.werkvorm = werkvorm;
		this.docenten = docenten;
		this.lokaalnummers = lokaalnummers;
		this.ingeroosterdeKlassen = ingeroosterdeKlassen;
		this.faculteit = faculteit;
		this.grootte = grootte; 
		this.opmerkingen = opmerkingen;
	}
	
	public String getCursuscode() {
		return this.cursuscode;
	}
	
	public String getStartTijd() {
		return this.starttijd;
	}
	
	public String getEindTijd() {
		return this.eindtijd;
	}
	
	public String getDuur() {
		return this.duur;
	}
	public String getNaam() {
		return this.naam;
	}
	
	public String getStartDatum() {
		return this.startdatum;
	}
	
	public ArrayList<Docent> getDocenten() {
		return this.docenten;
	}
	
	public ArrayList<Klas> getIngeroosterdeKlassen() {
		return this.ingeroosterdeKlassen;
	}
}
