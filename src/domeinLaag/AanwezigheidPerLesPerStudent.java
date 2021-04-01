package domeinLaag;

import java.awt.*;

public class AanwezigheidPerLesPerStudent {
    private Les les;
    private Student student;
    private boolean afwezigheid;
    private String reden;


    public AanwezigheidPerLesPerStudent(Les les, Student student, boolean afwezigheid, String reden) {
        this.les = les;
        this.student = student;
        this.afwezigheid = afwezigheid;
        this.reden = reden;
    }

    public AanwezigheidPerLesPerStudent(Les les, Student student, boolean afwezigheid) {
        this.les = les;
        this.student = student;
        this.afwezigheid = afwezigheid;
    }

    public Student getStudent() {
        return student;
    }


    public Les getLes() {
        return les;
    }

    public boolean isAfwezigheid() {
        return afwezigheid;
    }

    public void setAfwezigheid(boolean afwezigheid) {
        this.afwezigheid = afwezigheid;
    }

    public String getReden() {
        return reden;
    }

    public String toString(){
        return les.getLesnaam() + " " + student.getNaam() + " " + afwezigheid + " " + reden;
    }
}
