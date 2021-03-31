package domeinLaag;

import java.util.ArrayList;

public class Student {
    private String naam;
    private int studentennummer;
    private String email;
    private boolean status;
    private String rollCall;
    private int pogingen;
    private String wachtwoord;
    private Klas klas;
    private ArrayList<AanwezigheidPerLesPerStudent> presentie;

    public static Student getAccount() {
        return huidigeAccount;
    }

    public static void setAccount(Student huidigeAccount) {
        Student.huidigeAccount = huidigeAccount;
    }

    private static Student huidigeAccount;

    public Student(String naam, int studentennummer, String email, boolean status, int pogingen, String rollCall, String wachtwoord){
        this.naam = naam;
        this.studentennummer = studentennummer;
        this.email = email;
        this.status = status;
        this.rollCall = rollCall + "%";
        this.pogingen = pogingen;
        this.wachtwoord = wachtwoord;
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

    public String getRollCall() {
        return rollCall;
    }

    public void setRollCall(String rollCall) {
        this.rollCall = rollCall;
    }

    public String toString(){
        return "Student " + naam;
    }
}