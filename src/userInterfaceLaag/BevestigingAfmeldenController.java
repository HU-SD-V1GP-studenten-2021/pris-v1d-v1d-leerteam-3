package userInterfaceLaag;

import domeinLaag.Docent;
import domeinLaag.Les;
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

public class BevestigingAfmeldenController {

    public DocentenSchermController docentenSchermController;
    private Docent docent = Docent.getAccount();
    public Les les;

    public void bevestigingButten(ActionEvent actionEvent) throws SQLException {
        ObservableList<ObservableList> namen = FXCollections.observableArrayList();

        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","omara");
        props.setProperty("password","Omar1994");
        Connection con = DriverManager.getConnection(url, props);
        Statement stmt = con.createStatement();

        try {
            ObservableList<Student> student = DocentenSchermController.view1.getSelectionModel().getSelectedItems();
            namen.addAll(student);


            for (Student i : student) {
                int studentnummerNu = i.getStudentennummer();
                int lesnummerNu = DocentenSchermController.les.getLesnummer();
                stmt.executeUpdate("INSERT INTO afwezigheid (lesnummer, studentnummer, afwezig) " +
                        "VALUES ("+ lesnummerNu + ", " + studentnummerNu + ", true )");

            }
            docentenSchermController.getStudentenLoad(this.docentenSchermController.les);
        }
        catch (NullPointerException e){
            System.out.println(e);
        }

        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    public void annulerenButten(ActionEvent actionEvent) {
        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }
}
