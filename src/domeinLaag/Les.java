package domeinLaag;

import javafx.scene.control.CheckBox;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Les {
    private int lesnummer;
    private String lesnaam;
    private LocalDate datum;
    private LocalTime begintijd;
    private LocalTime eindtijd;
    private Docent docent;
    private Klas klas;
    private String afwezigheid;

    public Les(int lesnummer, String lesnaam, LocalDate datum, LocalTime begintijd, LocalTime eindtijd) {
        this.lesnummer = lesnummer;
        this.lesnaam = lesnaam;
        this.datum = datum;
        this.begintijd = begintijd;
        this.eindtijd = eindtijd;
    }


    public String getAfwezigheid() {
        return afwezigheid;
    }

    public void setAfwezigheid(String afwezigheid) {
        this.afwezigheid = afwezigheid;
    }

    public void setKlas(Klas klas) {
        this.klas = klas;
    }

    public void setDocent(Docent docent) {
        this.docent = docent;
    }

    public int getLesnummer(){
        return lesnummer;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public LocalTime getBegintijd() {
        return begintijd;
    }

    public Docent getDocent() {
        return docent;
    }

    public Klas getKlas() {
        return klas;
    }

    public String getLesnaam() {
        return lesnaam;
    }

    @Override
    public String toString() {
        return "Les heeft als " + docent + " " + klas.getNaam() ;
    }
}
