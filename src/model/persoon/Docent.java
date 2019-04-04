package model.persoon;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import model.les.Les;

public class Docent extends Persoon {
	private ArrayList<Les> deLessen = new ArrayList<Les>();
	private int docentNummer;
	private String volleNaam;

	public Docent(String voornaam, String tussenvoegsel, String achternaam, String wachtwoord, String gebruikersnaam,
			int docentNummer) {
		super(voornaam, tussenvoegsel, achternaam, wachtwoord, gebruikersnaam);
		docentNummer = 0;
		this.volleNaam = voornaam + " " + tussenvoegsel + " " + achternaam;
	}

	public String getDocentNummer() {
		return Integer.toString(this.docentNummer);
	}

	public void setDocentNummer(int docentNummer) {
		this.docentNummer = docentNummer;
	}
	
	public String getFullName(){
		return this.volleNaam;
	}

	public List<Les> getLessen() {
		return Collections.unmodifiableList(deLessen);
	}
	
	public List<Les> getLessenByDate(String date) {
		ArrayList<Les> lessen = new ArrayList<Les>();
		for (Les l : this.deLessen) {
			if (l.getStartDatum().equals(date)) {
				lessen.add(l);
			}
		}
		
		return Collections.unmodifiableList(lessen);
	}
	
	public void voegLesToe(Les pLes) {
		if (!this.getLessen().contains(pLes)) {
			this.deLessen.add(pLes);
		}
	}
}
