package model.persoon;

public class Docent extends Persoon {

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

}
