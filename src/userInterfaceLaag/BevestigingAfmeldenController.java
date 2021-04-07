package userInterfaceLaag;

import domeinLaag.Les;
import domeinLaag.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BevestigingAfmeldenController {
    @FXML private Label waarschuwing;
    public Les les;

    public void bevestigingButten(ActionEvent actionEvent) throws SQLException {
        ObservableList<ObservableList> namen = FXCollections.observableArrayList();

        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","united");
        Connection con = DriverManager.getConnection(url, props);
        Statement stmt = con.createStatement();

        try {
            ObservableList<Student> student = DocentenSchermController.view1.getSelectionModel().getSelectedItems();
            namen.addAll(student);

            try {
                for (Student i : student) {
                    int studentnummerNu = i.getStudentennummer();
                    int lesnummerNu = DocentenSchermController.les.getLesnummer();
                    stmt.executeUpdate("INSERT INTO afwezigheid (lesnummer, studentnummer, afwezig) " +
                            "VALUES (" + lesnummerNu + ", " + studentnummerNu + ", true )");

                }
                Button source = (Button)actionEvent.getSource();
                Stage stage = (Stage)source.getScene().getWindow();
                stage.close();
            }catch (Exception duplicatedKey){
                waarschuwing.setText("Deze student(en) is/zijn reeds afgemeld!");
            }
        }
        catch (NullPointerException ignored ){
        }
    }

    public void annulerenButten(ActionEvent actionEvent) {
        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }
}
