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

    public Les(int lesnummer, LocalDate datum, LocalTime begintijd, LocalTime eindtijd, Klas klas) {
        this.lesnummer = lesnummer;
        this.datum = datum;
        this.begintijd = begintijd;
        this.eindtijd = eindtijd;
        this.klas = klas;
    }

    public void setDocent(Docent docent) {
        this.docent = docent;
    }

    public int getLesnummer() {
        return lesnummer;
    }

    public Docent getDocent() {
        return docent;
    }

//    @Override
    public String toString() {
        return "Les heeft als " + docent ;
    }
}
