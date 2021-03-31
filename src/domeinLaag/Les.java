package domeinLaag;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Les {
    private int lesnummer;
    private LocalDate datum;
    private LocalTime begintijd;
    private LocalTime eindtijd;
    private Klas klas;
    private Docent docent;

    public Les(int lesnummer, LocalDate datum, LocalTime begintijd, LocalTime eindtijd) {
        this.lesnummer = lesnummer;
        this.datum = datum;
        this.begintijd = begintijd;
        this.eindtijd = eindtijd;
    }

    public void setKlas(Klas klas) {
        this.klas = klas;
    }

    public void setDocent(Docent docent) {
        this.docent = docent;
    }

    public int getLesnummer() {
        return lesnummer;
    }

    public void setLesnummer(int lesnummer) {
        this.lesnummer = lesnummer;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public LocalTime getBegintijd() {
        return begintijd;
    }

    public void setBegintijd(LocalTime begintijd) {
        this.begintijd = begintijd;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Docent getDocent() {
        return docent;
    }

    public Klas getKlas() {
        return klas;
    }

        @Override
    public String toString() {
        return "Les heeft als " + docent + " " + klas.getNaam() ;
    }
}
