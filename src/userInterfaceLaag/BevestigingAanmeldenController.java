package userInterfaceLaag;

import domeinLaag.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BevestigingAanmeldenController {
    public Button Bevestiging;
    public Button Annuleren;

    public void bevestigenButten(ActionEvent actionEvent) throws SQLException {
        ObservableList<ObservableList> namen = FXCollections.observableArrayList();

        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","ruben");
        Connection con = DriverManager.getConnection(url, props);
        Statement stmt = con.createStatement();

        try {
            ObservableList<Student> student = DocentenSchermController.view1.getSelectionModel().getSelectedItems();
            namen.addAll(student);
            for (Student i : student) {
                int studentnummerNu = i.getStudentennummer();


                int lesnummerNu = DocentenSchermController.les.getLesnummer();

                stmt.executeUpdate("DELETE FROM afwezigheid " +
                        "WHERE studentnummer = " + studentnummerNu + " AND lesnummer = " + lesnummerNu);
            }

        }
        catch (NullPointerException ignored){
        }
        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    public void annulerenButten() {
        Stage stage = (Stage) Annuleren.getScene().getWindow();
        stage.close();
    }
}
