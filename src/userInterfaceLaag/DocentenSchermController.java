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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class DocentenSchermController {
    public Label waarschuwingid;
    public ImageView magister200id;
    @FXML public Label naamLabel;
    public DatePicker datepicker;
    public Button vorigeid;
    public Button volgendeid;
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

    private final Docent docent = Docent.getAccount();
    public static Les les;
    public static TableView view1;

    public void initialize() throws Exception {
        datepicker.setValue(LocalDate.now());
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
        datumid.setSortType(TableColumn.SortType.ASCENDING);
        tableView2.getSortOrder().add(datumid);
        tableView2.sort();
        naamid.setSortType(TableColumn.SortType.ASCENDING);
        tableView1.getSortOrder().add(naamid);
        tableView1.sort();
    }

    public ObservableList<Les> getLessen(){
        ObservableList<Les> lessen = FXCollections.observableArrayList();
        for(Les les : docent.getLessen()){
            if (les.getDatum().isAfter(datepicker.getValue()) || les.getDatum().isEqual(datepicker.getValue())){
                lessen.add(les);
            }
        }
        return lessen;
    }


    public ObservableList<Student> getStudenten(){
        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","ruben");
        ObservableList<Student> students = FXCollections.observableArrayList();
        try {
            Connection con = DriverManager.getConnection(url, props);
            Statement stmt = con.createStatement();

            Les les = docent.getLessen().get(0);
            this.les = les;
            for (Student student : les.getKlas().getStudenten()) {
                boolean afwezig = false; // hiermee zet je de afwezigheid standaard op false (dus aanwezig).
                ResultSet rs = stmt.executeQuery("SELECT afwezig, reden FROM afwezigheid WHERE studentnummer = " +
                        student.getStudentennummer() + " AND lesnummer = " + les.getLesnummer()); //hiermee haal je voor elke
                //leerling (de for loop) de afwezigheid op, als deze niet bestaat is de leerling dus aanwezig.
                while (rs.next()) {
                    boolean afwezigbool = rs.getBoolean("afwezig"); //hier zet je mits je in de lijst voorkomt
                    // (dus in de while loop komt) wordt er een boolean aan toegekend. */
                    String redenop = rs.getString("reden");
                    System.out.println(redenop);
                    if (afwezigbool) {
                        afwezig = true;
                    }
                    System.out.println("joo");
                }
                student.setAfwezigheid("Aanwezig");
                if (afwezig) {
                    student.setAfwezigheid("Afwezig");
                }
                students.add(student);
            }
        }catch (Exception ignored){
        }
        return students;
    }




    public void loguitEnAfsluiten(ActionEvent actionEvent) throws IOException {
        ((Node)actionEvent.getSource()).getScene().getWindow().hide();
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("/userInterfaceLaag/LoginScherm.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login scherm");
        primaryStage.getIcons().add(new Image("HU.png"));
        primaryStage.show();
    }

    public void loadDataPerLes(){
        try {
            Les les = (Les) tableView2.getSelectionModel().getSelectedItem();
            tableView1.setItems(getStudentenLoad(les));
            this.les = les;
            naamid.setSortType(TableColumn.SortType.ASCENDING);
            tableView1.getSortOrder().add(naamid);
            tableView1.sort();
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
        props.setProperty("password","ruben");
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
        return students;
    }

    public void handleButtonAfmelden() throws Exception {
        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","ruben");
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
            double totaal = 100 - (100.0 / studentUpdate.getKlas().getTotaalAantalLessen()) * aantal;
            studentUpdate.setRollCall(totaal);
        }
        getStudentenLoad(les);
    }


    public void handleButtonAanmelden() throws SQLException, IOException {
        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","ruben");
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
            double totaal = 100 - (100.0 / studentUpdate.getKlas().getTotaalAantalLessen()) * aantal;
            studentUpdate.setRollCall(totaal);
        }
        getStudentenLoad(les);
    }

        public void toonVorigeDag() {
            LocalDate dagEerder = datepicker.getValue().minusDays(1);
            datepicker.setValue(dagEerder);
        }

        public void toonVolgendeDag() {
            LocalDate dagLater = datepicker.getValue().plusDays(1);
            datepicker.setValue(dagLater);
        }

    public void getdatum() throws NullPointerException, SQLException {
        tableView2.setItems(getLessen());
        tableView2.refresh();
        if(tableView2.getItems().size() == 0){
            tableView1.getItems().clear();
        }
        datumid.setSortType(TableColumn.SortType.ASCENDING);
        tableView2.getSortOrder().add(datumid);
        tableView2.sort();
        naamid.setSortType(TableColumn.SortType.ASCENDING);
        tableView1.getSortOrder().add(naamid);
        tableView1.sort();
        tableView1.refresh();
    }
}