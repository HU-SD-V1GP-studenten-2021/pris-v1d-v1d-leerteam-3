package domeinLaag;

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
    private ArrayList<AanwezigheidPerLesPerStudent> afwezigeVanDezeLes = new ArrayList<>();
    private String afwezigheid;

    public Les(int lesnummer, String lesnaam, LocalDate datum, LocalTime begintijd, LocalTime eindtijd) {
        this.lesnummer = lesnummer;
        this.lesnaam = lesnaam;
        this.datum = datum;
        this.begintijd = begintijd;
        this.eindtijd = eindtijd;
    }

    public void voegafwezigeVanDezeLesToe(AanwezigheidPerLesPerStudent enkeleStudent){
        afwezigeVanDezeLes.add(enkeleStudent);
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
