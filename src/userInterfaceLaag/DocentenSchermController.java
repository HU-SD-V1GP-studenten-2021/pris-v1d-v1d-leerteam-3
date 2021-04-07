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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DocentenSchermController {
    public Label waarschuwingid;
    public ImageView magister200id;
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
    @FXML private TableColumn<Student, String> aanwezigid;

    private Docent docent = Docent.getAccount();
    public static Les les;
    public static TableView view1;
    public TableView view2;

    public void initialize() throws Exception {
        String s = docent.getNaam();
        naamLabel.setText(s);
        tableView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        klasid.setCellValueFactory(new PropertyValueFactory<>("klas"));
        datumid.setCellValueFactory(new PropertyValueFactory<>("datum"));
        lesid.setCellValueFactory(new PropertyValueFactory<>("lesnaam"));

        naamid.setCellValueFactory(new PropertyValueFactory<>("naam"));
        studentid.setCellValueFactory(new PropertyValueFactory<>("studentennummer"));
        emailid.setCellValueFactory(new  PropertyValueFactory<>("email"));
        rollcall.setCellValueFactory(new PropertyValueFactory<>("rollCall"));
        aanwezigid.setCellValueFactory(new PropertyValueFactory<>("afwezigheid"));

        tableView1.setRowFactory(tv -> new TableRow<Student>() {
            @Override
            protected void updateItem(Student    item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || item.getAfwezigheid() == null)
                    setStyle("");
                else if (item.getAfwezigheid().equals("Afwezig"))
                    setStyle("-fx-background-color: #ffd7d1;");
                else
                    setStyle("");
            }
        });


        tableView2.setItems(getLessen());
        tableView1.setItems(getStudenten());
        this.view1 = tableView1;
    }

    public ObservableList<Les> getLessen(){
        ObservableList<Les> lessen = FXCollections.observableArrayList();
        lessen.addAll(docent.getLessen());
        return lessen;
    }


    public ObservableList<Student> getStudenten() throws SQLException {
        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","united");
        Connection con = DriverManager.getConnection(url, props);
        Statement stmt = con.createStatement();
        ObservableList<Student> students = FXCollections.observableArrayList();

        Les les = docent.getLessen().get(0);
        this.les = les;
        for (Student student : les.getKlas().getStudenten()){
            boolean afwezig = false; // hiermee zet je de afwezigheid standaard op false (dus aanwezig).
            ResultSet rs = stmt.executeQuery("SELECT afwezig FROM afwezigheid WHERE studentnummer = " +
                    student.getStudentennummer() + " AND lesnummer = " + les.getLesnummer()); //hiermee haal je voor elke
            //leerling (de for loop) de afwezigheid op, als deze niet bestaat is de leerling dus aanwezig.
            while(rs.next()){
                boolean afwezigbool = rs.getBoolean("afwezig"); //hier zet je mits je in de lijst voorkomt
                // (dus in de while loop komt) wordt er een boolean aan toegekend.
                if (afwezigbool){
                    afwezig = true;
                }
            }
            student.setAfwezigheid("Aanwezig");
            if (afwezig){
                student.setAfwezigheid("Afwezig");
            }
            students.add(student);
        }
        System.out.println(les);

//        students.addAll(les.getKlas().getStudenten());
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
            primaryStage.setTitle("Login scherm");
            primaryStage.getIcons().add(new Image("HU.png"));
            primaryStage.show();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadDataPerLes(MouseEvent mouseEvent){
        try {
            Les les = (Les) tableView2.getSelectionModel().getSelectedItem();
            System.out.println(les);
            System.out.println(les.getLesnummer());
            tableView1.setItems(getStudentenLoad(les));
            System.out.println("hoi" +les);
            this.les = les;
        }
        catch (NullPointerException | SQLException ignored){
        }
    }
    public ObservableList<Student> getStudentenLoad(Les les) throws SQLException {
        tableView1.refresh();
        waarschuwingid.setText("");
        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","united");
        Connection con = DriverManager.getConnection(url, props);
        Statement stmt = con.createStatement();
        ObservableList<Student> students = FXCollections.observableArrayList();
        for (Student student : les.getKlas().getStudenten()){
            boolean afwezig = false;
            ResultSet rs = stmt.executeQuery("SELECT afwezig FROM afwezigheid WHERE studentnummer = " +
                    student.getStudentennummer() + " AND lesnummer = " + les.getLesnummer());
            while(rs.next()){
                boolean afwezigbool = rs.getBoolean("afwezig");
                if (afwezigbool){
                    afwezig = true;
                }
            }
            student.setAfwezigheid("Aanwezig");
            if (afwezig){
                student.setAfwezigheid("Afwezig");
            }
            students.add(student);
        }

//        students.addAll(les.getKlas().getStudenten());
        return students;
    }

    public void handleButtonAfmelden(ActionEvent actionEvent) throws Exception {
        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","united");
        Connection con = DriverManager.getConnection(url, props);
        Statement stmt = con.createStatement();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BevestigingAfmelden.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();

        ObservableList<Student> student = tableView1.getSelectionModel().getSelectedItems();
        for (Student studentUpdate : student){
            ResultSet rollcallMaken = stmt.executeQuery("SELECT count(*) AS total FROM afwezigheid " +
                    "WHERE studentnummer = " + studentUpdate.getStudentennummer());
            rollcallMaken.next();
            int aantal = rollcallMaken.getInt("total");
            double totaal = 100 - (100 / studentUpdate.getKlas().getTotaalAantalLessen()) * aantal;
            studentUpdate.setRollCall(totaal);
        }
        getStudentenLoad(les);
    }


    public void handleButtonAanmelden(ActionEvent actionEvent) throws SQLException, IOException {
        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","united");
        Connection con = DriverManager.getConnection(url, props);
        Statement stmt = con.createStatement();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BevestigingAanmelden.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();

        ObservableList<Student> student = tableView1.getSelectionModel().getSelectedItems();
        for (Student studentUpdate : student){
            ResultSet rollcallMaken = stmt.executeQuery("SELECT count(*) AS total FROM afwezigheid " +
                    "WHERE studentnummer = " + studentUpdate.getStudentennummer());
            rollcallMaken.next();
            int aantal = rollcallMaken.getInt("total");
            double totaal = 100 - (100 / studentUpdate.getKlas().getTotaalAantalLessen()) * aantal;
            studentUpdate.setRollCall(totaal);
        }
        getStudentenLoad(les);
    }

}
