package userInterfaceLaag;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class BevestigingenSchermDecentAanmeldenController {

    @FXML
    private Button bevestigenButton;
    @FXML private Button annulerernButton;




    public void setBevestigenButton(javafx.event.ActionEvent actionEvent) {

        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    public void setAnnulerernButton(javafx.event.ActionEvent actionEvent) {
        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }
}
