package userInterfaceLaag;

import domeinLaag.AanwezigheidPerLesPerStudent;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DocentenSchermController {
    @FXML private Rectangle rectangleBoven;
    @FXML private Button loguitKnop;
    @FXML public Label naamLabel;
    @FXML private TableView tableView2;
    @FXML private TableColumn<Les, String> klasid;
    @FXML private TableColumn<Les, String> datumid;
    @FXML private TableColumn<Les, String> lesid;

    @FXML private TableView tableView1;
    @FXML private TableColumn<Student, String> naamid;
    @FXML private TableColumn<Student, String> studentid;
    @FXML private TableColumn<Student, String> emailid;
    @FXML private TableColumn<Student, String> rollcall;


    private Docent docent = Docent.getAccount();

    public void initialize() throws Exception {
        String s = docent.getNaam();
        naamLabel.setText(s);
        tableView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        klasid.setCellValueFactory(new PropertyValueFactory<>("klas"));
        datumid.setCellValueFactory(new PropertyValueFactory<>("datum"));
        lesid.setCellValueFactory(new PropertyValueFactory<>("lesnummer"));

        naamid.setCellValueFactory(new PropertyValueFactory<>("naam"));
        studentid.setCellValueFactory(new PropertyValueFactory<>("studentennummer"));
        emailid.setCellValueFactory(new  PropertyValueFactory<>("email"));
        rollcall.setCellValueFactory(new PropertyValueFactory<>("rollCall"));


        tableView2.setItems(getLessen());
        tableView1.setItems(getStudenten());
    }

    public ObservableList<Les> getLessen(){
        ObservableList<Les> lessen = FXCollections.observableArrayList();
        lessen.addAll(docent.getLessen());
        return lessen;
    }


    public ObservableList<Student> getStudenten(){
        ObservableList<Student> students = FXCollections.observableArrayList();

        Les les = docent.getLessen().get(0);

        students.addAll(les.getKlas().getStudenten());
        return students;
    }

//    public ObservableList<AanwezigheidPerLesPerStudent> getafwezigestudenten(){
//        AanwezigheidPerLesPerStudent aanwezigheidPerLesPerStudent =
//    }




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

    public void loadDataPerLes(MouseEvent mouseEvent){
        try {
            Les les = (Les) tableView2.getSelectionModel().getSelectedItem();
            int lesnummer = les.getLesnummer();
            tableView1.setItems(getStudentenLoad(lesnummer));
        }
        catch (NullPointerException ignored){
        }
    }
    public ObservableList<Student> getStudentenLoad(int lesnummer){
        ObservableList<Student> students = FXCollections.observableArrayList();
        ArrayList<Les> lessen = docent.getLessen();
        for (Les lesUitDeLijst : lessen){
            if (lesUitDeLijst.getLesnummer() == lesnummer){
                students.addAll(lesUitDeLijst.getKlas().getStudenten());
            }
        }
        return students;
    }
    public void handleButtonAfmelden(ActionEvent actionEvent) throws SQLException {
        ObservableList<ObservableList> namen = FXCollections.observableArrayList();

        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","omara");
        props.setProperty("password","Omar1994");
        Connection con = DriverManager.getConnection(url, props);
        Statement stmt = con.createStatement();

        try {
            ObservableList<Student> stuNummer = tableView1.getSelectionModel().getSelectedItems();
            namen.addAll(tableView1.getSelectionModel().getSelectedItems());
            for (Student i : stuNummer) {
                int studentennum = i.getStudentennummer();
                System.out.println(i.getStudentennummer());

                ObservableList<Les> lessen = FXCollections.observableArrayList();
                ResultSet afwezig = stmt.executeQuery(("INSERT afwezigheid (afwezig) Va WHERE studentnummer = " +
                        studentennum )); // hier moeten we nog de les ophalen net zoals in de student scherm!!!

            }
//


        }
        catch (NullPointerException e){
            System.out.println(e);
        }
    }


    public void handleButtonAanmelden(ActionEvent actionEvent) throws SQLException {
        ObservableList<ObservableList> namen = FXCollections.observableArrayList();

        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","omara");
        props.setProperty("password","Omar1994");
        Connection con = DriverManager.getConnection(url, props);
        Statement stmt = con.createStatement();

        try {
            ObservableList<Student> stuNummer = tableView1.getSelectionModel().getSelectedItems();
            namen.addAll(tableView1.getSelectionModel().getSelectedItems());
            for (Student i : stuNummer) {
                int sudentennum = i.getStudentennummer();
                System.out.println(i.getStudentennummer());

                ObservableList<Les> lessen = FXCollections.observableArrayList();
                ResultSet afwezig = stmt.executeQuery("DELETE  from afwezigheid WHERE studentnummer = " + sudentennum ); // hier moeten we nog de les ophalen net zoals in de student scherm!!!

            }

        }
        catch (NullPointerException e){
            System.out.println(e);
        }
    }

}

