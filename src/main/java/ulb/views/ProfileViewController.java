package ulb.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProfileViewController implements Initializable {
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

    private ProfileListener listener;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        this.firstname.setText(listener.getFirstName());
        this.lastname.setText(listener.getLastName());
        this.birthdate.setText(listener.getBirthDate().toString());
        this.height.setText(Float.toString(listener.getHeight()));
        this.weight.setText(Float.toString(listener.getWeight()));
        this.sex.setText(listener.getSex());
    }

    public void setListener(ProfileListener listener) {
        this.listener = listener;
    }

    public interface ProfileListener {
        String getFirstName();
        String getLastName();
        String getSex();
        LocalDate getBirthDate();
        float getHeight();
        float getWeight();
    }

}
