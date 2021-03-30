package userInterfaceLaag;

import domeinLaag.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.util.List;

public class LeerlingHoofdschermController {

    @FXML private Button loguitKnop;
    @FXML private Label naamLabel;
    @FXML private TableView aanwezigheidsTabel;
    @FXML private PieChart rollCallAttendance;
    @FXML private TableView<Student> tableView;
    @FXML private TableColumn<Student, String> les;
    @FXML private TableColumn<Student, String> Datum;
    @FXML private TableColumn<Student, String> Docent;
    @FXML private TableColumn<Student, String> Lokaal;
    @FXML private TableColumn<Student, String> Aanwezigheid;
    @FXML private List<Student> student;




    public void initialize() {
//        String s = "";
//        naamLabel.setText(s);   // in de klasse domeinLaag.Student de naam opvragen

        try {
            Datum.setCellValueFactory(new PropertyValueFactory<Student, String>("datum"));
            les.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
            Docent.setCellValueFactory(new PropertyValueFactory<Student, String>("active"));
            Lokaal.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
            Aanwezigheid.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
            tableView.getItems().setAll((student));
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void loguitEnSluiten(ActionEvent actionEvent) {
        try {
            ((Node)actionEvent.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/userInterfaceLaag/LoginScherm.fxml"));

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
