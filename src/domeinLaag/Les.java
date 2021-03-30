package domeinLaag;

import java.time.LocalDate;
import java.time.LocalTime;

public class Les {
    private LocalDate datum;
    private LocalTime begintijd;
    private LocalTime eindtijd;
    private Klas klas;

    public Les(LocalDate datum, LocalTime begintijd, LocalTime eindtijd, Klas klas) {
        this.datum = datum;
        this.begintijd = begintijd;
        this.eindtijd = eindtijd;
        this.klas = klas;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public LocalTime getBegintijd() {
        return begintijd;
    }

    public LocalTime getEindtijd() {
        return eindtijd;
    }

    public Klas getKlas() {
        return klas;
    }
    public String toString(){

        return datum.toString() + " " + begintijd.toString() + " " + eindtijd.toString();
    }
}
