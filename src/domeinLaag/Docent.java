package domeinLaag;

import java.util.ArrayList;

public class Docent {
    private int docentnummer;
    private String naam;
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

    public Docent(String naam, int docentnummer, String email, boolean status, int pogingen, String wachtwoord) {
        this.naam = naam;
        this.docentnummer = docentnummer;
        this.email = email;
        this.status = status;
        this.pogingen = pogingen;
        this.wachtwoord = wachtwoord;
    }

    public String getNaam() {
        return naam;
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
