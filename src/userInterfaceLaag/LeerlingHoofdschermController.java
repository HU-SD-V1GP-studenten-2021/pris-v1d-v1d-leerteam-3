package userInterfaceLaag;

import domeinLaag.Klas;
import domeinLaag.Les;
import domeinLaag.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class LeerlingHoofdschermController {


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
    Les les;

    public void initialize() {
        String s = student.getNaam();
        naamLabel.setText(s);   // in de klasse domeinLaag.Student de naam opvragen
        lesid.setCellValueFactory(new PropertyValueFactory<>("lesnummer"));
        datumid.setCellValueFactory(new PropertyValueFactory<>("datum"));
        docentid.setCellValueFactory(new PropertyValueFactory<>("docent"));
        tijdid.setCellValueFactory(new PropertyValueFactory<>("begintijd"));
//        aanwezigid.setCellValueFactory(new PropertyValueFactory<>("aanwezigheid"));

        aanwezigheidsTabel.setItems(getLessen());

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        new PieChart.Data("Aanwezig " + student.getRollCall() + "%", student.getRollCall()),
        new PieChart.Data("Afwezig " + (100 - student.getRollCall()) + "%", 100 - student.getRollCall()));
        rollCallAttendance.setData(pieChartData);
        rollCallAttendance.setLabelsVisible(false);
        rollCallAttendance.setLegendVisible(true);
        rollCallAttendance.setStartAngle(90);



    }
    public ObservableList<Les> getLessen(){
        ObservableList<Les> lessen = FXCollections.observableArrayList();

            lessen.addAll(student.getKlas().getLessen());
        return lessen;
    }





    public void loguitEnSluiten(ActionEvent actionEvent) {
        try {
            ((Node)actionEvent.getSource()).getScene().getWindow().hide();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/userInterfaceLaag/LoginScherm.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("iets");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void popUpScherm(MouseEvent mouseEvent) throws IOException {

        Les les = (Les) aanwezigheidsTabel.getSelectionModel().getSelectedItem();
        int lesnummer = les.getLesnummer();
        System.out.println(lesnummer);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfmeldenScherm.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Afmelden");
        newStage.setScene(new Scene(root));
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();
        initialize();
    }
}
