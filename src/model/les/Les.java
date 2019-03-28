package model.les;

public class Les {
	private String naam;
	private String cursuscode;
	private String startweek;
	private String startdatum;
	private String starttijd;
	private String einddag;
	private String einddatum;
	private String eindtijd;
	private String duur;
	private String werkvorm;
	private String docenten;
	private String lokaalnummers;
	private String groepen;
	private String faculteit;
	private String grootte;
	private String opmerkingen;
	
	public Les(String naam, String cursuscode, String startweek, String startdatum, String starttijd, String einddag, String einddatum, String eindtijd, String duur, String werkvorm, String docenten, String lokaalnummers, String groepen, String faculteit, String grootte, String opmerkingen) {
		this.naam = naam;
		this.cursuscode = cursuscode;
		this.startweek = startweek;
		this.startdatum = startdatum;
		this.starttijd = starttijd;
		this.einddag = einddag;
		this.einddatum = einddatum;
		this.eindtijd = eindtijd;
		this.duur = duur;
		this.werkvorm = werkvorm;
		this.docenten = docenten;
		this.lokaalnummers = lokaalnummers;
		this.groepen = groepen;
		this.faculteit = faculteit;
		this.grootte = grootte;
		this.opmerkingen = opmerkingen;
	}
}
