package ulb.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;



//define this file in the fx:controller of the fxml
public class ProfileCreationController extends AbstractController implements Initializable {
    //@fxml to precise that this variable are connected to the item with fx:id the same as variable name
    @FXML
    private TextField surname;
    @FXML
    private TextField firstname;
    @FXML
    private DatePicker birthdate;

    // overriding the initialize to launch a function after the initialisation of the window
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Perform initialization tasks here
        System.out.println("creation profil window launched");
        this.birthdate.setValue(LocalDate.now());
    }
    public void makeProfil(){
        String surname = this.surname.getText();
        String firstname = this.firstname.getText();
        LocalDate selectedDate = this.birthdate.getValue();
        int day = selectedDate.getDayOfMonth();
        int month = selectedDate.getMonthValue();
        int year = selectedDate.getYear();

    }

}
