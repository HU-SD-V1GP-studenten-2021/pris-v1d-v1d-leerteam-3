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
    private String afwezigheid;
    private String reden;



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

    public String getReden() {
        return reden;
    }

    public void setReden(String reden) {
        this.reden = reden;
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

    public String getNaam() {
        return naam;
    }

    public int getStudentennummer() {
        return studentennummer;
    }

    public Klas getKlas() {
        return klas;
    }

    public double getRollCall() {
        return rollCall;
    }

    public String getEmail() {
        return email;
    }

    public void setRollCall(double rollCall) {
        String nu = String.format("%.2f", rollCall);
        nu = nu.replace(",", ".");
        this.rollCall = Double.parseDouble(nu);
    }

    public String toString(){
        return "Student " + naam;
    }
}