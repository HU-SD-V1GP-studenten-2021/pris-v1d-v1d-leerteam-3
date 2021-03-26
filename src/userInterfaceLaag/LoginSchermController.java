package userInterfaceLaag;

import domeinLaag.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginSchermController {
    @FXML private Button loginKnop;
    @FXML private PasswordField wachtwoordVeld;
    @FXML private CheckBox docent;
    @FXML private TextField naamVeld;
    @FXML private Label Waarschuwing;

    private Student account = Student.getAccount();

    public void loginAccount(ActionEvent actionEvent) throws Exception  {
        String naam = naamVeld.getText();
        String wachtwoord = wachtwoordVeld.getText();
        Stage loginscherm = (Stage) loginKnop.getScene().getWindow();
        if(naam.equals("")){
            Waarschuwing.setText("Naamveld is verplicht!");
        }
        else if(wachtwoord.equals("")){
            Waarschuwing.setText("Wachtwoordveld is verplicht!");
        }

        else {
            if (!docent.isSelected()){
                naam += "@student.hu.nl";
                wachtwoord = wachtwoordVeld.getText();
                System.out.println("ingelogd als student. met naam: " + naam + " en wachtwoord: " + wachtwoord);
                //* if(naam.equals() && wachtwoord.equals()){}
                try{

                    loginscherm.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LeerlingHoofdscherm.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Leerlingen scherm");
                    stage.setScene(new Scene(root));
                    stage.show();


                }
                catch (Exception ignored){
                    System.out.println(ignored);
                }

            }
            else{
                naam += "@hu.nl";
                wachtwoord = wachtwoordVeld.getText();
                //* if(naam.equals() && wachtwoord.equals()){}
                System.out.println("ingelogd als docent. met naam: " + naam + " en wachtwoord: " + wachtwoord);
                try{
                    loginscherm.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DocentenScherm.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Docenten scherm");
                    stage.setScene(new Scene(root));
                    stage.show();

                }
                catch (Exception ignored){
                    System.out.println(ignored);
                }
            }
        }



    }

    public void setStatusDocent(ActionEvent actionEvent) {
    }
}