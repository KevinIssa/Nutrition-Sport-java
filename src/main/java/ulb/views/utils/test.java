package ulb.views.utils;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;


import java.net.URL;
import java.util.ResourceBundle;

public class test implements Initializable {
    @FXML public Label infoUser;
    @FXML public Button editButton;
    @FXML public Control field;

    protected void setDefault() {
        infoUser.setVisible(true);
        field.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDefault();
    }
}
