package ulb.views;

import javafx.scene.control.*;
import ulb.controllers.AbstractController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import ulb.models.Model;

public class ProfileCreationViewController extends AbstractController implements Initializable {
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private DatePicker birthdate;
    @FXML
    private TextField height;
    @FXML
    private TextField weight;
    @FXML
    private ToggleGroup sex; // radio button

    private ProfileCreationViewController.Listener listener;
    System.Logger logger = System.getLogger(ProfileCreationViewController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.log(System.Logger.Level.INFO, "ProfileCreationViewController initialized");
        this.birthdate.setValue(LocalDate.now());
    }

    public void saveProfile() {
        String savedLastName = lastname.getText();
        String savedFirstName = firstname.getText();
        try {
            String savedSex = getSavedSex();
            LocalDate selectedDate = birthdate.getValue();
            float floatHeight = Float.parseFloat(height.getText());
            float floatWeight = Float.parseFloat(weight.getText());
            safeSaveProfile(savedFirstName, savedLastName, savedSex, selectedDate, floatWeight, floatHeight);
        } catch (NumberFormatException e) {
            logger.log(System.Logger.Level.ERROR, "Height and weight must be numbers");
            this.showAlert("le poid et la taille doivent etre des nombres");
            return;
        }
        this.getModel().getController().switchFXML("/ulb/views/main.fxml", this.getModel());
    }

    private String getSavedSex() {
        RadioButton selectedRadioButton = (RadioButton) sex.getSelectedToggle();
        return selectedRadioButton.getText();
    }
    @Override
    public void setListener(Object listener) {
        this.listener = (ProfileCreationViewController.Listener) listener;
    }

    public void safeSaveProfile(String firstname, String lastname, String sex, LocalDate birthdate, float weight, float height) {
        if (listener == null) {
            logger.log(System.Logger.Level.ERROR, "Listener is null");
            return;
        }
        listener.saveProfile(firstname, lastname, sex, birthdate, weight, height );

    }
    public interface Listener {
        public void saveProfile(String firstname, String lastname, String sex, LocalDate birthdate, float weight, float height );
    }

}
