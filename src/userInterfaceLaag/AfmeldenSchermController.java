package userInterfaceLaag;

import domeinLaag.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;



public class AfmeldenSchermController {
    public Button Annuleren;
    public Button Afmelden;
    public TextArea redenid;
    private Student account = Student.getAccount();


    public void annuleren(ActionEvent actionEvent) {
        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    public void afmelden(ActionEvent actionEvent) throws SQLException {
        String reden = redenid.getText();

//        String url = "jdbc:postgresql://localhost/SDGP";
//        Properties props = new Properties();
//        props.setProperty("user","postgres");
//        props.setProperty("password","united");
//        Connection conn = DriverManager.getConnection(url, props);
//        Statement stmt = conn.createStatement();
//        stmt.executeUpdate("INSERT INTO afwezig, reden FROM Afwezigheid VALUES(true, reden) WHERE studentstudentnummer = " + account.getStudentennummer() +  " AND Lesnummer =");

        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }
}
