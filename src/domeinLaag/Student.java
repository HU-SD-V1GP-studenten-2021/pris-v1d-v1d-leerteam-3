package domeinLaag;

import javafx.scene.control.CheckBox;

import java.util.ArrayList;

public class Student {
    private int studentennummer;
    private String naam;
    private String email;
    private boolean status;
    private int pogingen;
    private double rollCall;
    private String wachtwoord;
    private Klas klas;
    private ArrayList<AanwezigheidPerLesPerStudent> presentie = new ArrayList<>();



    public static Student getAccount() {
        return huidigeAccount;
    }

    public static void setAccount(Student huidigeAccount) {
        Student.huidigeAccount = huidigeAccount;
    }

    private static Student huidigeAccount;

    public Student(String naam, int studentennummer, String email, boolean status, int pogingen, double rollCall, String wachtwoord){
        this.naam = naam;
        this.studentennummer = studentennummer;
        this.email = email;
        this.status = status;
        this.rollCall = rollCall;
        this.pogingen = pogingen;
        this.wachtwoord = wachtwoord;
    }

    public ArrayList<AanwezigheidPerLesPerStudent> getPresentie() {
        return presentie;
    }

    public void voegPresentieToe(AanwezigheidPerLesPerStudent enkeleLes){
        presentie.add(enkeleLes);
    }

    public void setKlas(Klas klas) {
        this.klas = klas;
    }

    public String getNaam() {
        return naam;
    }

    public int getStudentennummer() {
        return studentennummer;
    }

    public String getEmail() {
        return email;
    }

    public Klas getKlas() {
        return klas;
    }

    public double getRollCall() {
        return rollCall;
    }

    public void setRollCall(double rollCall) {
        this.rollCall = rollCall;
    }

    public String toString(){
        return "Student " + naam;
    }
}