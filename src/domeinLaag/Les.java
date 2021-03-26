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
}
