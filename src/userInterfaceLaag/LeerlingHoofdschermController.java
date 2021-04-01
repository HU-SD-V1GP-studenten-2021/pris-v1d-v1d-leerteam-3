package userInterfaceLaag;

import domeinLaag.Klas;
import domeinLaag.Les;
import domeinLaag.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Properties;

public class LeerlingHoofdschermController {


    public static int lesnummer;
    public static Les les;
    public DatePicker datepickerid;
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


    public void initialize() throws SQLException {



        datepickerid.setValue(LocalDate.now());
        String s = student.getNaam();
        naamLabel.setText(s);   // in de klasse domeinLaag.Student de naam opvragen
        lesid.setCellValueFactory(new PropertyValueFactory<>("lesnummer"));
        datumid.setCellValueFactory(new PropertyValueFactory<>("datum"));
        docentid.setCellValueFactory(new PropertyValueFactory<>("docent"));
        tijdid.setCellValueFactory(new PropertyValueFactory<>("begintijd"));
//        aanwezigid.setCellValueFactory(new PropertyValueFactory<>("aanwezig"));
        aanwezigheidsTabel.setEditable(true);

        aanwezigheidsTabel.setItems(getLessen());


        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Aanwezig " + student.getRollCall() + "%", student.getRollCall()),
                new PieChart.Data("Afwezig " + (100 - student.getRollCall()) + "%", 100 - student.getRollCall()));
        rollCallAttendance.setData(pieChartData);
        rollCallAttendance.setLabelsVisible(false);
        rollCallAttendance.setLegendVisible(true);
        rollCallAttendance.setStartAngle(90);



    }

    public ObservableList<Les> getLessen() throws SQLException {
        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","ruben");
        Connection con = DriverManager.getConnection(url, props);
        Statement stmt = con.createStatement();
        ObservableList<Les> lessen = FXCollections.observableArrayList();
        for(Les les : student.getKlas().getLessen()){
            boolean tikker = false;
            if (les.getDatum().isAfter(datepickerid.getValue()) || les.getDatum().isEqual(datepickerid.getValue())){
                ResultSet rs = stmt.executeQuery("SELECT afwezig FROM afwezigheid WHERE studentnummer = " +
                        student.getStudentennummer() + " AND lesnummer = " + les.getLesnummer());
                while(rs.next()){
                    boolean afwezigbool = rs.getBoolean("afwezig");
                    if (afwezigbool){
                        tikker = true;
                    }
                }
                if(!tikker){
                    lessen.add(les);
                }
            }
        }
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

    public void popUpScherm(MouseEvent mouseEvent) throws IOException, SQLException{
        try{
            Les les = (Les) aanwezigheidsTabel.getSelectionModel().getSelectedItem();
            this.lesnummer = les.getLesnummer();
            this.les = les;
            if(!les.getDatum().isBefore(LocalDate.now())){
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
        catch(Exception ignored){
        }
    }

    public void getdatum(Event event) throws NullPointerException, SQLException {
        LocalDate datum = datepickerid.getValue();
        aanwezigheidsTabel.setItems(setLessen(datum));

    }

    public ObservableList<Les> setLessen(LocalDate datum) throws SQLException {
        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","ruben");
        Connection con = DriverManager.getConnection(url, props);
        Statement stmt = con.createStatement();
        ObservableList<Les> lessen = FXCollections.observableArrayList();
        for(Les les : student.getKlas().getLessen()){
            boolean tikker = false;
            if (les.getDatum().isAfter(datepickerid.getValue()) || les.getDatum().isEqual(datepickerid.getValue())){
                ResultSet rs = stmt.executeQuery("SELECT afwezig FROM afwezigheid WHERE studentnummer = " +
                        student.getStudentennummer() + " AND lesnummer = " + les.getLesnummer());
                while(rs.next()){
                    boolean afwezigbool = rs.getBoolean("afwezig");
                    if (afwezigbool){
                        tikker = true;
                     }
                }
                if(!tikker){
                    lessen.add(les);
                }
            }
        }
        System.out.println(lessen);
        return lessen;
    }
}
