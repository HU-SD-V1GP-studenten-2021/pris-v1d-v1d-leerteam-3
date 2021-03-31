package userInterfaceLaag;

import domeinLaag.Docent;
import domeinLaag.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.print.Doc;
import java.io.IOException;

public class DocentenSchermController {
    @FXML private Rectangle rectangleBoven;
    @FXML private Button loguitKnop;
    @FXML public Label naamLabel;

    private Docent docent = Docent.getAccount();

    public void initialize() {
        String s = docent.getNaam();
        naamLabel.setText(s);   // in de klasse domeinLaag.Student de naam opvragen
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

