package userInterfaceLaag;

import domeinLaag.Klas;
import domeinLaag.Les;
import domeinLaag.Student;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class LeerlingHoofdschermController {


    public CheckBox checkBox;
    @FXML private Button loguitKnop;
    @FXML private Label naamLabel;
    @FXML private TableView aanwezigheidsTabel;
    @FXML private TableColumn<Les, String> lesid;
    @FXML private TableColumn<Klas, String> datumid;
    @FXML private TableColumn<Klas, String> docentid;
    @FXML private TableColumn<Klas, String> tijdid;
    @FXML private TableColumn<Klas, String> aanwezigid;
    @FXML private PieChart rollCallAttendance;

    private Student student = Student.getAccount();


    public void initialize() {
        String s = student.getNaam();
        naamLabel.setText(s);   // in de klasse domeinLaag.Student de naam opvragen
        lesid.setCellValueFactory(new PropertyValueFactory<>("lesnummer"));
        datumid.setCellValueFactory(new PropertyValueFactory<>("datum"));
        docentid.setCellValueFactory(new PropertyValueFactory<>("docent"));
        tijdid.setCellValueFactory(new PropertyValueFactory<>("begintijd"));
        aanwezigid.setCellValueFactory(new PropertyValueFactory<>("aanwezigheid"));
        checkBox.selectedProperty(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<?> observableValue, Object o, Object t1) {

            }
        });
        aanwezigheidsTabel.setItems(getLessen());

    }
    public ObservableList<Les> getLessen(){
        ObservableList<Les> lessen = FXCollections.observableArrayList();
        lessen.addAll(student.getKlas().getLessen());
        return lessen;
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
