package domeinLaag;

import java.util.ArrayList;

public class Docent {
    private String naam;
    private int medewerkersnummer;
    private String email;
    private boolean status;
    private int pogingen;
    private String wachtwoord;

    public Docent(String naam, int medewerkersnummer, String email, boolean status, int pogingen, String wachtwoord) {
        this.naam = naam;
        this.medewerkersnummer = medewerkersnummer;
        this.email = email;
        this.status = status;
        this.pogingen = pogingen;
        this.wachtwoord = wachtwoord;
    }
}
