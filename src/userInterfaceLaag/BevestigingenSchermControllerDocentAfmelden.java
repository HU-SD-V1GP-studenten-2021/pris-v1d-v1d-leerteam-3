package userInterfaceLaag;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;



public class BevestigingenSchermControllerDocentAfmelden {


    @FXML private Button bevestigenButton;
    @FXML private Button annulerernButton;



    public void setBevestigenButton(ActionEvent actionEvent) {


        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    public void setAnnulerernButton(ActionEvent actionEvent) {
        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }
}
