package domeinLaag;

import java.util.ArrayList;

public class Klas {
    private String naam;
    private ArrayList<Les> lessen;
    private ArrayList<Student> studenten;
    private ArrayList<Docent> docenten;
    private int aantalStudenten = 0;
    private int totaalAantalLessen = 0;

    public Klas(String naam) {
        this.naam = naam;
        this.lessen = new ArrayList<>();
        this.studenten = new ArrayList<>();
        this.docenten = new ArrayList<>();
    }

    public void voegLeerlingToe(Student student){
        studenten.add(student);
        aantalStudenten ++;
    }

    public void voegDocentToe(Docent docent){
        docenten.add(docent);
    }

    public void voegLesToe(Les les){
        lessen.add(les);
        totaalAantalLessen ++;
    }

    public String getNaam() {
        return naam;
    }

    public ArrayList<Les> getLessen() {
        return lessen;
    }

    public ArrayList<Student> getStudenten() {
        return studenten;
    }

    public ArrayList<Docent> getDocenten() {
        return docenten;
    }

    public int getAantalStudenten() {
        return aantalStudenten;
    }

    public int getTotaalAantalLessen() {
        return totaalAantalLessen;
    }

    @Override
    public String toString() {
        return "Klas{" +
                "lessen=" + getLessen() +
                ", studenten=" + getStudenten() +
                ", docenten=" + getDocenten() +
                ", aantal studenten= " + getAantalStudenten() +
                ", totaal aantal lessen= " + getTotaalAantalLessen() +
                '}';
    }
}
