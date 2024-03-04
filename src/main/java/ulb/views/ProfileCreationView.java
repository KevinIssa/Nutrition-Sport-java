package ulb.views;

import javafx.scene.control.RadioButton;
import ulb.controllers.AbstractController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
public class ProfileCreationView extends AbstractController implements Initializable {
    @FXML
    private TextField firstname;
    @FXML
    private TextField surname;
    @FXML
    private DatePicker birthdate;
    @FXML
    private TextField height;
    @FXML
    private TextField weight;
    @FXML
    private ToggleGroup sex; // radio button

    System.Logger logger = System.getLogger(ProfileCreationView.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.log(System.Logger.Level.INFO, "ProfileCreationView initialized");
        this.birthdate.setValue(LocalDate.now());
    }

    public void saveProfile() {
        String savedSurname = this.surname.getText();
        String savedFirstname = this.firstname.getText();
        LocalDate selectedDate = this.birthdate.getValue();
        int day = selectedDate.getDayOfMonth();
        int month = selectedDate.getMonthValue();
        int year = selectedDate.getYear();

        String savedHeight = this.height.getText();
        String savedWeight = this.weight.getText();
        int intergerWeight;
        int intergerHeight;
        try {
            intergerHeight = Integer.parseInt(savedHeight);
            intergerWeight = Integer.parseInt(savedWeight);
        } catch (NumberFormatException e) {
            logger.log(System.Logger.Level.ERROR, "height and weight must be numbers");
            return;
        }
        RadioButton selectedRadioButton = (RadioButton) sex.getSelectedToggle();
        String savedSex = selectedRadioButton.getText();
        this.getModele().setProfil(savedSurname, savedFirstname, savedSex, selectedDate, intergerWeight, intergerHeight);
        this.getModele().getController().switchFXML("/ulb/views/main.fxml", this.getModele());
        logger.log(System.Logger.Level.INFO, "surname: " + savedSurname + " firstname: " + savedFirstname + " birthdate: " + day + "/" + month + "/" + year + " height: " + savedHeight + " weight: " + savedWeight +" sex:" + savedSex);
    }
}
