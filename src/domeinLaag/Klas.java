package domeinLaag;

import java.util.ArrayList;

public class Klas {
    private String naam;
    private ArrayList<Les> lessen;
    private ArrayList<Student> studenten;
    private ArrayList<Docent> docenten;
    private int aantalStudenten; //lengte van de arraylist

    public Klas(String naam) {
        this.naam = naam;
        this.lessen = new ArrayList<>();
        this.studenten = new ArrayList<>();
        this.docenten = new ArrayList<>();
    }

    public void voegLeerlingToe(Student student){
        studenten.add(student);
    }

    public void voegDocentToe(Docent docent){
        docenten.add(docent);
    }

    public void voegLesToe(Les les){
        lessen.add(les);
    }

    @Override
    public String toString() {
        return "Klas{" +
                "lessen=" + lessen +
                ", studenten=" + studenten +
                ", docenten=" + docenten +
                '}';
    }
}
