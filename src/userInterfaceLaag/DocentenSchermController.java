package userInterfaceLaag;

import domeinLaag.Docent;
import domeinLaag.Klas;
import domeinLaag.Les;
import domeinLaag.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.print.Doc;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

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
    @FXML private TableView tableView2;
    @FXML private TableColumn<Klas, String> klasid;
    @FXML private TableColumn<Les, String> datumid;
    @FXML private TableColumn<Les, String> lesid;

    @FXML private TableView tableView1;
    @FXML private TableColumn<Student, String> naamid;
    @FXML private TableColumn<Student, String> studentid;
    @FXML private TableColumn<Student, String> emailid;
    @FXML private TableColumn<Student, String> rollcall;
    @FXML private TableColumn<Student, String> aanwezigid;
    @FXML private TableColumn<Student, String> infoid;

    private Docent docent = Docent.getAccount();

    public void initialize() throws Exception {
        int nummer = docent.getMedewerkersnummer();
        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","Galaxy");
        Connection conn = DriverManager.getConnection(url, props);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select lesnummer, klasnaam, datum from les\n" +
                "join docent on docent.docentnummer = les.docentdocentnummer\n" +
                "where docentnummer = " + nummer);

        ObservableList<String> data = FXCollections.observableArrayList();
        while (rs.next()) {
            String lesnummer = rs.getString("lesnummer");
            String klasnaam = rs.getString("klasnaam");
            String datum = rs.getString("datum");
            data.addAll(lesnummer,klasnaam,datum);
        }

        String s = docent.getNaam();
        naamLabel.setText(s);   // in de klasse domeinLaag.Student de naam opvragen
//        naam.setCellValueFactory(new PropertyValueFactory<>("naam"));
//        studentennummer.setCellValueFactory(new PropertyValueFactory<>("studentennumnmer"));
//        rollcall.setCellValueFactory(new PropertyValueFactory<>("rollcall"));
//        aanwezigheid.setCellValueFactory(new PropertyValueFactory<>("aanwizegheid"));
//        info.setCellValueFactory(new PropertyValueFactory<>("info"));
//        email.setCellValueFactory(new  PropertyValueFactory<>("email"));

//        tablewiew1.setItems(getStudenten());

    }
    public ObservableList<Docent> getDocenten(){
        ObservableList<Docent> docents = FXCollections.observableArrayList();

        docents.addAll(docent.getLessen());

        return students;
        klasid.setCellValueFactory(new PropertyValueFactory<>("klasnaam"));
        datumid.setCellValueFactory(new PropertyValueFactory<>("datum"));
        lesid.setCellValueFactory(new PropertyValueFactory<>("lesnummer"));
        System.out.println(data);
        tableView2.setItems(data);
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

