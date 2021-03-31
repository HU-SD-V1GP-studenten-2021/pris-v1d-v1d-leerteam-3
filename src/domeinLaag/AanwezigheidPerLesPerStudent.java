package domeinLaag;

import java.awt.*;

public class AanwezigheidPerLesPerStudent {
    private int lesnummer;
    private int studentStudentnummer;
    private boolean aanwezigheid;
    private String reden;
    private Les les;
    private Student student;

    public AanwezigheidPerLesPerStudent(int lesnummer, int studentStudentnummer, boolean aanwezigheid, String reden) {
        this.lesnummer = lesnummer;
        this.studentStudentnummer = studentStudentnummer;
        this.aanwezigheid = aanwezigheid;
        this.reden = reden;
    }

    public AanwezigheidPerLesPerStudent(int i, int studentStudentnummer, Checkbox ch) {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Les getLes() {
        return les;
    }

    public void setLes(Les les) {
        this.les = les;
    }

    public boolean isAanwezigheid() {
        return aanwezigheid;
    }

    public void setAanwezigheid(boolean aanwezigheid) {
        this.aanwezigheid = aanwezigheid;
    }
}
