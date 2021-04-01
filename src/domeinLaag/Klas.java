package domeinLaag;

import javafx.scene.control.CheckBox;

import java.util.ArrayList;

public class Klas {
    private int klasnummer;
    private String naam;
    private ArrayList<Les> lessen;
    private ArrayList<Student> studenten;
    private int aantalStudenten = 0;
    private int totaalAantalLessen = 0;


    public Klas(int klasnummer, String naam) {
        this.klasnummer = klasnummer;
        this.naam = naam;
        this.lessen = new ArrayList<>();
        this.studenten = new ArrayList<>();
    }

    public void voegStudentToe(Student student){
        studenten.add(student);
        aantalStudenten ++;
    }

    public void voegLesToe(Les les){
        lessen.add(les);
        totaalAantalLessen ++;
    }

    public String getNaam() {
        return naam;
    }

    public int getKlasnummer() {
        return klasnummer;
    }

    public void setKlasnummer(int klasnummer) {
        this.klasnummer = klasnummer;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public ArrayList<Les> getLessen() {
        return lessen;
    }

    public ArrayList<Student> getStudenten() {
        return studenten;
    }

    public int getAantalStudenten() {
        return aantalStudenten;
    }

    public int getTotaalAantalLessen() {
        return totaalAantalLessen;
    }

    @Override
    public String toString() {
        return naam;
    }
}
