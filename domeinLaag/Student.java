package domeinLaag;


public class Student {
    private String naam;
    private int studentennummer;
    private String email;
    private int status;
    private double percentage;
    private int pogingen;
    private String wachtwoord;

    public Student(String naam, int studentennummer, String email, int status, double percentage, int pogingen, String wachtwoord){
        this.naam = naam;
        this.studentennummer = studentennummer;
        this.email = email;
        this.status = status;
        this.percentage = percentage;
        this.pogingen = pogingen;
        this.wachtwoord = wachtwoord;
    }
}