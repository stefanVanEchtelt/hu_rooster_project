package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import model.klas.Klas;
import model.persoon.Docent;
import model.persoon.Student;
import model.les.Les;

public class PrIS {
	private ArrayList<Docent> deDocenten;
	private ArrayList<Student> deStudenten;
	private ArrayList<Klas> deKlassen;
	private ArrayList<Les> deLessen;

	/**
	 * De constructor maakt een set met standaard-data aan. Deze data moet nog
	 * uitgebreidt worden met rooster gegevens die uit een bestand worden ingelezen,
	 * maar dat is geen onderdeel van deze demo-applicatie!
	 * 
	 * De klasse PrIS (PresentieInformatieSysteem) heeft nu een meervoudige
	 * associatie met de klassen Docent, Student, Vakken en Klassen Uiteraard kan
	 * dit nog veel verder uitgebreid en aangepast worden!
	 * 
	 * De klasse fungeert min of meer als ingangspunt voor het domeinmodel. Op dit
	 * moment zijn de volgende methoden aanroepbaar:
	 * 
	 * String login(String gebruikersnaam, String wachtwoord) Docent
	 * getDocent(String gebruikersnaam) Student getStudent(String gebruikersnaam)
	 * ArrayList<Student> getStudentenVanKlas(String klasCode)
	 * 
	 * Methode login geeft de rol van de gebruiker die probeert in te loggen, dat
	 * kan 'student', 'docent' of 'undefined' zijn! Die informatie kan gebruikt
	 * worden om in de Polymer-GUI te bepalen wat het volgende scherm is dat getoond
	 * moet worden.
	 * 
	 */
	public PrIS() {
		deDocenten = new ArrayList<Docent>();
		deStudenten = new ArrayList<Student>();
		deKlassen = new ArrayList<Klas>(); // Inladen klassen
		deLessen = new ArrayList<Les>();
		
		vulKlassen(deKlassen); // Inladen studenten in klassen
		vulStudenten(deStudenten, deKlassen);
		// Inladen docenten
		vulDocenten(deDocenten);
		vulDeLessen(deLessen);

	} // Einde Pris constructor

	// deze method is static onderdeel van PrIS omdat hij als hulp methode
	// in veel controllers gebruikt wordt
	// een standaardDatumString heeft formaat YYYY-MM-DD
	public static Calendar standaardDatumStringToCal(String pStadaardDatumString) {
		Calendar lCal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			lCal.setTime(sdf.parse(pStadaardDatumString));
		} catch (ParseException e) {
			e.printStackTrace();
			lCal = null;
		}
		return lCal;
	}

	// deze method is static onderdeel van PrIS omdat hij als hulp methode
	// in veel controllers gebruikt wordt
	// een standaardDatumString heeft formaat YYYY-MM-DD
	public static String calToStandaardDatumString(Calendar pCalendar) {
		int lJaar = pCalendar.get(Calendar.YEAR);
		int lMaand = pCalendar.get(Calendar.MONTH) + 1;
		int lDag = pCalendar.get(Calendar.DAY_OF_MONTH);

		String lMaandStr = Integer.toString(lMaand);
		if (lMaandStr.length() == 1) {
			lMaandStr = "0" + lMaandStr;
		}
		String lDagStr = Integer.toString(lDag);
		if (lDagStr.length() == 1) {
			lDagStr = "0" + lDagStr;
		}
		String lString = Integer.toString(lJaar) + "-" + lMaandStr + "-" + lDagStr;
		return lString;
	}

	public Docent getDocent(String gebruikersnaam) {
		return deDocenten.stream().filter(d -> d.getGebruikersnaam().equals(gebruikersnaam)).findFirst().orElse(null);
	}
	
	public ArrayList<Klas> getKlassen() {
		return deKlassen;
	}

	public Klas getKlasVanStudent(Student pStudent) {
		return deKlassen.stream().filter(k -> k.bevatStudent(pStudent)).findFirst().orElse(null);
	}

	public Student getStudent(String pGebruikersnaam) {
		return deStudenten.stream().filter(s -> s.getGebruikersnaam().equals(pGebruikersnaam)).findFirst().orElse(null);
	}

	public Student getStudent(int pStudentNummer) {
		return deStudenten.stream().filter(s -> s.getStudentNummer() == pStudentNummer).findFirst().orElse(null);
	}

	public Klas getKlas(String klasCode) {
		return deKlassen.stream().filter(k -> k.getKlasCode().equals(klasCode)).findFirst().orElse(null);
	}
	
	public String login(String gebruikersnaam, String wachtwoord) {
		for (Docent d : deDocenten) {
			if (d.getGebruikersnaam().equals(gebruikersnaam)) {
				if (d.komtWachtwoordOvereen(wachtwoord)) {
					return "docent";
				}
			}
		}

		for (Student s : deStudenten) {
			if (s.getGebruikersnaam().equals(gebruikersnaam)) {
				if (s.komtWachtwoordOvereen(wachtwoord)) {
					return "student";
				}
			}
		}

		return "undefined";
	}

	private void vulDocenten(ArrayList<Docent> pDocenten) {
		String csvFile = "././CSV/docenten.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] element = line.split(cvsSplitBy);
				String gebruikersnaam = element[0].toLowerCase();
				String voornaam = element[1];
				String tussenvoegsel = element[2];
				String achternaam = element[3];
				pDocenten.add(new Docent(voornaam, tussenvoegsel, achternaam, "geheim", gebruikersnaam, 1));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close the bufferedReader if opened.
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// verify content of arraylist, if empty add Jos
			if (pDocenten.isEmpty())
				pDocenten.add(new Docent("Jos", "van", "Reenen", "supergeheim", "jos.vanreenen@hu.nl", 1));
		}
	}

	private void vulKlassen(ArrayList<Klas> pKlassen) {
		// TICT-SIE-VIA is de klascode die ook in de rooster file voorkomt
		// V1A is de naam van de klas die ook als file naam voor de studenten van die
		// klas wordt gebruikt
		Klas k1 = new Klas("TICT-SIE-V1A", "V1A");
		Klas k2 = new Klas("TICT-SIE-V1B", "V1B");
		Klas k3 = new Klas("TICT-SIE-V1C", "V1C");
		Klas k4 = new Klas("TICT-SIE-V1D", "V1D");
		Klas k5 = new Klas("TICT-SIE-V1E", "V1E");

		pKlassen.add(k1);
		pKlassen.add(k2);
		pKlassen.add(k3);
		pKlassen.add(k4);
		pKlassen.add(k5);
	}

	private void vulDeLessen(ArrayList<Les> pLessen) {
		String csvFile = "././CSV/rooster.csv";
		BufferedReader br = null;
		String line = "";
		
		try {
			br = new BufferedReader(new FileReader(csvFile));
			int rowCount = 0;
			while ((line = br.readLine()) != null) {
				if (rowCount != 0) {
					line = line.replace("\",\"", ";");
					line = line.replace(",\"", "\";");
					line = line.replace("\"", "");
					
					String[] splittedLes = line.split(";");
						
					String naam = splittedLes[0];
					String cursuscode = splittedLes[1];
					String starkWeek = splittedLes[2];
					String startDag = splittedLes[3];
					String startDatum = splittedLes[4];
					String startTijd = splittedLes[5];
					String eindDag = splittedLes[6];
					String eindDatum = splittedLes[7];
					String eindTijd = splittedLes[8];
					String duur = splittedLes[9];
					String werkvorm = splittedLes[10];
					
					String[] lDocenten = splittedLes[11].split(", ");
					ArrayList<Docent> docenten = new ArrayList<Docent>();
					for (String d : lDocenten) {
						Docent docent = getDocent(d);
						if (docent != null) {
							docenten.add(docent);
						}
					}
					
					String lokaalnummers = splittedLes[12];
					
					String[] klassen = splittedLes[13].split(", ");
					ArrayList<Klas> ingeroosterdeKlassen = new ArrayList<Klas>();
					for (String klas : klassen) {
						Klas k = getKlas(klas);
						if (k != null) {
							ingeroosterdeKlassen.add(k);
						}
					}
					
					String faculteit = splittedLes[14];
					String grootte = splittedLes[15];
					String opmerkingen = splittedLes[16];
					
					Les les = new Les(naam, cursuscode, starkWeek, startDag, startDatum, startTijd, eindDag, eindDatum, eindTijd, duur, werkvorm, docenten, lokaalnummers, ingeroosterdeKlassen, faculteit, grootte, opmerkingen);
					
					for (Klas k : ingeroosterdeKlassen) {
						k.voegLesToe(les);
					}
					
					for (Docent d : docenten) {
						d.voegLesToe(les);
					}
					
					pLessen.add(les);
				}
				rowCount++;
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void vulStudenten(ArrayList<Student> pStudenten, ArrayList<Klas> pKlassen) {
		Student lStudent;
		Student dummyStudent = new Student("Stu", "de", "Student", "geheim", "test@student.hu.nl", 0, "");
		for (Klas k : pKlassen) {
			// per klas
			String csvFile = "././CSV/" + k.getNaam() + ".csv";
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ";";

			try {
				br = new BufferedReader(new FileReader(csvFile));

				while ((line = br.readLine()) != null) {
					// line = line.replace(",,", ", ,");
					// use comma as separator
					
					String[] element = line.split(cvsSplitBy);
					
					String gebruikersnaam = (element[3] + "." + element[2] + element[1] + "@student.hu.nl")
							.toLowerCase();
					// verwijder spaties tussen dubbele voornamen en tussen bv van der
					gebruikersnaam = gebruikersnaam.replace(" ", "");
					String lStudentNrString = element[0];
					int lStudentNr = Integer.parseInt(lStudentNrString);
					// Volgorde 3-2-1 = voornaam, tussenvoegsel en achternaam
					lStudent = new Student(element[3], element[2], element[1], "geheim", gebruikersnaam, lStudentNr, k.getKlasCode());
					pStudenten.add(lStudent);
					k.voegStudentToe(lStudent);
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				// mocht deze klas geen studenten bevatten omdat de csv niet heeft gewerkt:
				if (k.getStudenten().isEmpty()) {
					k.voegStudentToe(dummyStudent);
					System.out.println("Had to add Stu de Student to class: " + k.getKlasCode());
				}
			}

		}
		// mocht de lijst met studenten nu nog leeg zijn
		if (pStudenten.isEmpty())
			pStudenten.add(dummyStudent);
	}

	public Klas getKlas(Class<? extends Student> class1) {
		// TODO Auto-generated method stub
		return null;
	}

}
