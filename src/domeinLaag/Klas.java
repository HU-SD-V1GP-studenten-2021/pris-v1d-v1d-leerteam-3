package domeinLaag;

import javafx.scene.control.CheckBox;

import java.util.ArrayList;

public class Klas {
    private String naam;
    private ArrayList<Les> lessen;
    private ArrayList<Student> studenten;
    private int aantalStudenten = 0;
    private int totaalAantalLessen = 0;
    private CheckBox check;


    public Klas(String naam) {
        this.naam = naam;
        this.lessen = new ArrayList<>();
        this.studenten = new ArrayList<>();
        this.check = new CheckBox();
    }

    public CheckBox getCheck() {
        return check;
    }

    public void setCheck(CheckBox check) {
        this.check = check;
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
        return "Klas{" +
                "lessen=" + getLessen() +
                ", studenten=" + getStudenten() +
                ", aantal studenten= " + getAantalStudenten() +
                ", totaal aantal lessen= " + getTotaalAantalLessen() +
                '}';
    }
}
