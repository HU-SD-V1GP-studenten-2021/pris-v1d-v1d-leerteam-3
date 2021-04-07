package userInterfaceLaag;

import domeinLaag.AanwezigheidPerLesPerStudent;
import domeinLaag.Les;
import domeinLaag.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class AfmeldenSchermController {
    public Button Annuleren;
    public Button Afmelden;
    public TextArea redenid;
    public Label waarschuwingid;
    private final Student account = Student.getAccount();

    public void initialize() {
        waarschuwingid.setText("");
    }

    public void annuleren(ActionEvent actionEvent) {
        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    public void afmelden(ActionEvent actionEvent) throws SQLException{

        String reden = redenid.getText();
        int studentnummer = account.getStudentennummer();
        int lesnummer = LeerlingHoofdschermController.lesnummer;

        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","omara");
        props.setProperty("password","Omar1994");
        Connection conn = DriverManager.getConnection(url, props);
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO afwezigheid (lesnummer, studentnummer, afwezig, reden) VALUES (" + lesnummer + ", " + studentnummer + ", true, '"
                    + reden + "')");
        }
        catch (Exception duplicateKey){
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE afwezigheid SET reden = ('" + reden + "') " +
                    "WHERE studentnummer = " + studentnummer + " AND lesnummer = " + lesnummer);
        }

        Statement stmt1 = conn.createStatement();
        ResultSet rollcallMaken = stmt1.executeQuery("SELECT count(*) AS total FROM afwezigheid " +
                "WHERE studentnummer = " + studentnummer);
        rollcallMaken.next();
        int aantal = rollcallMaken.getInt("total");
        double totaal = 100 - (100.0 / account.getKlas().getTotaalAantalLessen()) * aantal;
        account.setRollCall(totaal);
        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }
}
