package userInterfaceLaag;

import domeinLaag.Docent;
import domeinLaag.Les;
import domeinLaag.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DocentenSchermController {
    @FXML private TableColumn<Student, String> naam;
    @FXML private TableColumn<Student, Integer> studentennummer;
    @FXML private TableColumn<Student, Integer> rollcall;
    @FXML private TableColumn<Student, Boolean> aanwezigheid;
    @FXML private TableColumn<Student, String> info;
    @FXML private TableColumn<Student, String> email;
    @FXML private TableView<Student> tablewiew1;
    @FXML private Rectangle rectangleBoven;
    @FXML private Button loguitKnop;
    @FXML public Label naamLabel;

    private Student student = Student.getAccount();

    private Docent docent = Docent.getAccount();

    public void initialize() {
        String s = docent.getNaam();
        naamLabel.setText(s);   // in de klasse domeinLaag.Student de naam opvragen
        naam.setCellValueFactory(new PropertyValueFactory<>("naam"));
        studentennummer.setCellValueFactory(new PropertyValueFactory<>("studentennumnmer"));
//        rollcall.setCellValueFactory(new PropertyValueFactory<>("rollcall"));
//        aanwezigheid.setCellValueFactory(new PropertyValueFactory<>("aanwizegheid"));
//        info.setCellValueFactory(new PropertyValueFactory<>("info"));
        email.setCellValueFactory(new  PropertyValueFactory<>("email"));

        tablewiew1.setItems(getStudenten());

    }
    public ObservableList<Student> getStudenten(){
        ObservableList<Student> students = FXCollections.observableArrayList();

        students.addAll(student.getKlas().getStudenten());

        return students;
    }

    public void loguitEnAfsluiten(ActionEvent actionEvent){
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
