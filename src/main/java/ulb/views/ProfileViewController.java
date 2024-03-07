package ulb.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import ulb.controllers.AbstractController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProfileViewController extends AbstractController implements Initializable {
    @FXML
    private Label firstname;
    @FXML
    private Label lastname;
    @FXML
    private Label sex;
    @FXML
    private Label birthdate;
    @FXML
    private Label height;
    @FXML
    private Label weight;

    @FXML
    private Button return_button;

    private ProfileViewController.Listener listener;


    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        // if we put setter here, the initialize will be executed before setListener so listener is null
    }
    @Override
    public void setListener(Object listener) {
        this.listener = (ProfileViewController.Listener) listener;
        this.setDefaultValue();
    }
    public void setDefaultValue(){
        this.firstname.setText(listener.getFirstName());
        this.lastname.setText(listener.getLastName());
        this.birthdate.setText(listener.getBirthDate().toString());
        this.height.setText(Float.toString(listener.getHeight()));
        this.weight.setText(Float.toString(listener.getWeight()));
        this.sex.setText(listener.getSex());
    }
    public void returnBack(){
        this.getModel().getController().switchFXML("/ulb/views/main.fxml", this.getModel());
    }
    public interface Listener {
        String getFirstName();
        String getLastName();
        String getSex();
        LocalDate getBirthDate();
        float getHeight();
        float getWeight();
    }

}
