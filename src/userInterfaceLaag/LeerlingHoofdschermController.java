package userInterfaceLaag;

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
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class LeerlingHoofdschermController {
    @FXML private Label naamLabel;
    @FXML private TableView aanwezigheidsTabel;
    @FXML private TableColumn<Les, String> lesid;
    @FXML private TableColumn<Klas, String> datumid;
    @FXML private TableColumn<Klas, String> docentid;
    @FXML private TableColumn<Klas, String> tijdid;
    @FXML private TableColumn<Klas, String> aanwezigid;
    @FXML private PieChart rollCallAttendance;
    private final Student student = Student.getAccount();
    public static int lesnummer;
    public static Les les;
    public DatePicker datepickerid;
    public Button volgendeDagButton;
    public Button toonVorigeDagButton;

    public void initialize() throws SQLException {
        datepickerid.setValue(LocalDate.now());
        String s = student.getNaam();
        naamLabel.setText(s);   // in de klasse domeinLaag.Student de naam opvragen
        lesid.setCellValueFactory(new PropertyValueFactory<>("lesnaam"));
        datumid.setCellValueFactory(new PropertyValueFactory<>("datum"));
        docentid.setCellValueFactory(new PropertyValueFactory<>("docent"));
        tijdid.setCellValueFactory(new PropertyValueFactory<>("begintijd"));
        aanwezigid.setCellValueFactory(new PropertyValueFactory<>("afwezigheid"));
        aanwezigheidsTabel.setEditable(true);

        aanwezigheidsTabel.setItems(getLessen());

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Aanwezig " + student.getRollCall() + "%", student.getRollCall()),
                new PieChart.Data("Afwezig " + (100 - student.getRollCall()) + "%", 100 - student.getRollCall()));
        rollCallAttendance.setData(pieChartData);
        rollCallAttendance.setLabelsVisible(false);
        rollCallAttendance.setLegendVisible(true);
        rollCallAttendance.setStartAngle(90);

        aanwezigheidsTabel.setRowFactory(tv -> new TableRow<Les>() {
            @Override
            protected void updateItem(Les item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || item.getAfwezigheid() == null)
                    setStyle("");
                else if (item.getAfwezigheid().equals("Afwezig"))
                    setStyle("-fx-background-color: #ffd7d1;");
                else
                    setStyle("");
            }
        });
        datumid.setSortType(TableColumn.SortType.ASCENDING);
        aanwezigheidsTabel.getSortOrder().add(datumid);
        aanwezigheidsTabel.sort();
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
                if (tikker){
                    les.setAfwezigheid("Afwezig");
                }else{
                    les.setAfwezigheid("Aanwezig");
                }
                lessen.add(les);
            }
        }
        return lessen;
    }

    public void loguitEnSluiten(ActionEvent actionEvent) {
        try {
            ((Node)actionEvent.getSource()).getScene().getWindow().hide();
            Stage stage = new Stage();
            Pane root = FXMLLoader.load(getClass().getResource("/userInterfaceLaag/LoginScherm.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Login scherm");
            stage.setScene(scene);
            stage.getIcons().add(new Image("HU.png"));
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void popUpScherm(MouseEvent mouseEvent){
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if (mouseEvent.getClickCount() == 2){
                try{
                    Les les = (Les) aanwezigheidsTabel.getSelectionModel().getSelectedItem();
                    lesnummer = les.getLesnummer();
                    LeerlingHoofdschermController.les = les;
                    if(!les.getDatum().isBefore(LocalDate.now())){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfmeldenScherm.fxml"));
                        Parent root = loader.load();
                        Stage newStage = new Stage();
                        newStage.setTitle("Afmelden");
                        newStage.setScene(new Scene(root));
                        newStage.getIcons().add(new Image("HU.png"));
                        newStage.initStyle(StageStyle.UNDECORATED);
                        newStage.initModality(Modality.APPLICATION_MODAL);
                        newStage.showAndWait();
                        initialize();
                    }
                }
                catch(Exception ignored){
                }
            }
        }
    }

    public void getdatum() throws NullPointerException, SQLException {
        aanwezigheidsTabel.setItems(setLessen());
        datumid.setSortType(TableColumn.SortType.ASCENDING);
        aanwezigheidsTabel.getSortOrder().add(datumid);
        aanwezigheidsTabel.sort();
        aanwezigheidsTabel.refresh();
    }

    public ObservableList<Les> setLessen() throws SQLException {
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
                if (tikker){
                    les.setAfwezigheid("Afwezig");
                }else{
                    les.setAfwezigheid("Aanwezig");
                }
                lessen.add(les);
            }
        }
        return lessen;
    }

    public void toonVorigeDag() {
        LocalDate dagEerder = datepickerid.getValue().minusDays(1);
        datepickerid.setValue(dagEerder);
    }

    public void toonVolgendeDag() {
        LocalDate dagLater = datepickerid.getValue().plusDays(1);
        datepickerid.setValue(dagLater);
    }
    public void toonVolgendeWeek() {
        LocalDate weekLater = datepickerid.getValue().plusDays(7);
        datepickerid.setValue(weekLater);
    }

    public void toonVorigeWeek() {
        LocalDate weekEerder = datepickerid.getValue().minusDays(7);
        datepickerid.setValue(weekEerder);
    }
}