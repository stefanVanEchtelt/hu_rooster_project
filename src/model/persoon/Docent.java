package model.persoon;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import model.les.Les;

public class Docent extends Persoon {
	private ArrayList<Les> deLessen = new ArrayList<Les>();
	private int docentNummer;

	public Docent(String voornaam, String tussenvoegsel, String achternaam, String wachtwoord, String gebruikersnaam,
			int docentNummer) {
		super(voornaam, tussenvoegsel, achternaam, wachtwoord, gebruikersnaam);
		docentNummer = 0;
	}

	public int getDocentNummer() {
		return docentNummer;
	}

	public void setDocentNummer(int docentNummer) {
		this.docentNummer = docentNummer;
	}

	public List<Les> getLessen() {
		return Collections.unmodifiableList(deLessen);
	}
	
	public void voegLesToe(Les pLes) {
		if (!this.getLessen().contains(pLes)) {
			this.deLessen.add(pLes);
		}
	}
}
