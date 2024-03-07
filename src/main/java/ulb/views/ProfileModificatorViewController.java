
package ulb.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import ulb.controllers.AbstractController;
import ulb.models.Profile;
import ulb.models.ProfileReader;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProfileModificatorViewController extends AbstractController{
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
    @FXML
    private RadioButton maleButton;
    @FXML
    private RadioButton femaleButton;
    private ProfileModificatorViewController.Listener listener;
    System.Logger logger = System.getLogger(ProfileModificatorViewController.class.getName());

    public void setDefaultValue() {
        this.firstname.setText(listener.getFirstName());
        this.lastname.setText(listener.getLastName());
        this.birthdate.setValue(listener.getBirthDate());
        this.height.setText(Float.toString(listener.getHeight()));
        this.weight.setText(Float.toString(listener.getWeight()));
        if (listener.getSex().equals("♂")){
            maleButton.setSelected(true);
        }
        else{
            femaleButton.setSelected(true);
        }
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
        this.getModel().getController().switchFXML("/ulb/views/main.fxml", this.getModel()); // switch to main.fxml after saving profile ?
    }

    private String getSavedSex() {
        RadioButton selectedRadioButton = (RadioButton) sex.getSelectedToggle();
        return selectedRadioButton.getText();
    }

    public DatePicker getBirthdate() {
        return birthdate;
    }

    public void setListener(Object listener) {
        this.listener = (ProfileModificatorViewController.Listener) listener;
        this.setDefaultValue();
    }

    public void safeSaveProfile(String lastname, String firstname, String savedSex, LocalDate selectedDate, float floatWeight, float floatHeight) {
        if (listener == null) {
            logger.log(System.Logger.Level.ERROR, "Listener is null");
            return;
        }
        listener.saveProfile(lastname, firstname, savedSex,selectedDate,  floatWeight,  floatHeight );

    }
    public interface Listener {
        String getFirstName();
        String getLastName();
        String getSex();
        LocalDate getBirthDate();
        float getHeight();
        float getWeight();
        public void saveProfile(String firstname, String lastname, String sex, LocalDate birthdate, float weight, float height );
        }

}

