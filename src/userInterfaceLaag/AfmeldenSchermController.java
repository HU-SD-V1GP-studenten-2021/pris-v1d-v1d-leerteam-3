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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class AfmeldenSchermController {
    public Button Annuleren;
    public Button Afmelden;
    public TextArea redenid;
    public Label waarschuwingid;
    private Student account = Student.getAccount();

    public void initialize() {
    waarschuwingid.setText("");
    }

    public void annuleren(ActionEvent actionEvent) {
        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }




    public void afmelden(ActionEvent actionEvent) throws SQLException, InterruptedException {

        String reden = redenid.getText();
        int studentnummer = account.getStudentennummer();
        Les les = LeerlingHoofdschermController.les;


        int lesnummer = LeerlingHoofdschermController.lesnummer;
        System.out.println(lesnummer);

        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","united");
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

        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();

        stage.close();
    }
}
