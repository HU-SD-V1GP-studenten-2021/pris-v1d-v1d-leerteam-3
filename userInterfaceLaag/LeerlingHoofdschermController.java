package userInterfaceLaag;

import domeinLaag.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class LeerlingHoofdschermController {

    @FXML private Button loguitKnop;
    @FXML private Label naamLabel;
    @FXML private TableView aanwezigheidsTabel;
    @FXML private PieChart rollCallAttendance;

    public void initialize() {
        String s = "";
        naamLabel.setText(s);
    }

    public void loguitEnSluiten(ActionEvent actionEvent) {
        System.exit(0);
    }
}
