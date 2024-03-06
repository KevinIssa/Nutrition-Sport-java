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

    private Listener listener;
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
            safeSaveProfile(savedLastName, savedFirstName, savedSex, selectedDate, floatWeight, floatHeight);
        } catch (NumberFormatException e) {
            logger.log(System.Logger.Level.ERROR, "Height and weight must be numbers");
            return;
        }
        this.getModele().getController().switchFXML("/ulb/views/main.fxml", this.getModele()); // switch to main.fxml after saving profile ?
    }

    private String getSavedSex() {
        RadioButton selectedRadioButton = (RadioButton) sex.getSelectedToggle();
        return selectedRadioButton.getText();
    }
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void safeSaveProfile(String surname, String firstname, String savedSex, LocalDate selectedDate, float floatWeight, float floatHeight) {
        if (listener == null) {
            logger.log(System.Logger.Level.ERROR, "Listener is null");
            return;
        }
        listener.saveProfile(surname, firstname, savedSex, selectedDate, floatWeight, floatHeight);

    }
    public interface Listener {
        void saveProfile(String surname, String firstname, String savedSex, LocalDate selectedDate, float floatWeight, float floatHeight);
    }
}
