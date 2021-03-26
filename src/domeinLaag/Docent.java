package domeinLaag;

import java.util.ArrayList;

public class Docent {
    private String naam;
    private int medewerkersnummer;
    private String email;
    private int status;
    private int pogingen;
    private String wachtwoord;
    private ArrayList<Klas> klassen = new ArrayList<>();

    public Docent(String naam, int medewerkersnummer, String email, int status, int pogingen, String wachtwoord, Klas klas) {
        this.naam = naam;
        this.medewerkersnummer = medewerkersnummer;
        this.email = email;
        this.status = status;
        this.pogingen = pogingen;
        this.wachtwoord = wachtwoord;
        this.klassen.add(klas);
    }
}
