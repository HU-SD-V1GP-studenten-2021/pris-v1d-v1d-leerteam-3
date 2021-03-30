package userInterfaceLaag;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LeerlingHoofdschermController {

    @FXML private Button loguitKnop;
    @FXML private Label naamLabel;
    @FXML private TableView aanwezigheidsTabel;
    @FXML private PieChart rollCallAttendance;



    public void initialize() {
        String s = "";
        naamLabel.setText(s);   // in de klasse domeinLaag.Student de naam opvragen
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
