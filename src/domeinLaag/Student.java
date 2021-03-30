package domeinLaag;

import java.util.ArrayList;

public class Student {
    private String naam;
    private int studentennummer;
    private String email;
    private boolean status;
    private double percentage;
    private int pogingen;
    private String wachtwoord;
    private Klas klas;

    public static Student getAccount() {
        return huidigeAccount;
    }

    public void setAccount(Student huidigeAccount) {
        this.huidigeAccount = huidigeAccount;
    }

    private static Student huidigeAccount;

    public Student(String naam, int studentennummer, String email, boolean status, double percentage, int pogingen, String wachtwoord, Klas klas){
        this.naam = naam;
        this.studentennummer = studentennummer;
        this.email = email;
        this.status = status;
        this.percentage = percentage;
        this.pogingen = pogingen;
        this.wachtwoord = wachtwoord;
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
}