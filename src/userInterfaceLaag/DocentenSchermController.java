package userInterfaceLaag;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class DocentenSchermController {
    @FXML private Rectangle rectangleBoven;
    public Button loguitKnop;
    public Label naamLabel;


    public void loguitEnAfsluiten(ActionEvent actionEvent) {
        System.exit(0);
    }
}
