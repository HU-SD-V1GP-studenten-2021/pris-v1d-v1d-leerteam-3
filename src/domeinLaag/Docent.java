package domeinLaag;

import java.util.ArrayList;

public class Docent {
    private String naam;
    private int medewerkersnummer;
    private String email;
    private boolean status;
    private int pogingen;
    private String wachtwoord;
    private ArrayList<Les> lessen = new ArrayList<>();

    public static Docent getAccount() {
        return huidigeAccount;
    }

    public static void setAccount(Docent huidigeAccount) {
        Docent.huidigeAccount = huidigeAccount;
    }

    private static Docent huidigeAccount;

    public Docent(String naam, int medewerkersnummer, String email, boolean status, int pogingen, String wachtwoord) {
        this.naam = naam;
        this.medewerkersnummer = medewerkersnummer;
        this.email = email;
        this.status = status;
        this.pogingen = pogingen;
        this.wachtwoord = wachtwoord;
    }

    public String getNaam() {
        return naam;
    }

    public int getMedewerkersnummer() {
        return medewerkersnummer;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Les> getLessen() {
        return lessen;
    }

    public void addLes(Les les){
        lessen.add(les);
    }

    @Override
    public String toString() {
        return naam;
    }
}
