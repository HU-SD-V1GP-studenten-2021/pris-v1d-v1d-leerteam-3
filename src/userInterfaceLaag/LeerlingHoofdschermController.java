package userInterfaceLaag;

import domeinLaag.Klas;
import domeinLaag.Les;
import domeinLaag.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class LeerlingHoofdschermController {

    @FXML private Button loguitKnop;
    @FXML private Label naamLabel;
    @FXML private TableView aanwezigheidsTabel;
    @FXML private TableColumn<Les, String> lesid;
    @FXML private TableColumn<Klas, String> datumid;
    @FXML private TableColumn<Klas, String> docentid;
    @FXML private PieChart rollCallAttendance;

    private Student student = Student.getAccount();


    public void initialize() {
        String s = student.getNaam();
        naamLabel.setText(s);   // in de klasse domeinLaag.Student de naam opvragen
        lesid.setCellValueFactory(new PropertyValueFactory<>("lesnummer"));
        datumid.setCellValueFactory(new PropertyValueFactory<>("datum"));
        docentid.setCellValueFactory(new PropertyValueFactory<>("docent"));

        aanwezigheidsTabel.setItems(getLessen());

    }
    public ObservableList<Les> getLessen(){
        ObservableList<Les> lessen = FXCollections.observableArrayList();
        lessen.addAll(student.getKlas().getLessen());
        return lessen;
    }




    public void loguitEnSluiten(ActionEvent actionEvent) {
        System.exit(0);
    }
}
