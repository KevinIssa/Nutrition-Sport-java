package ulb.views;

import javafx.scene.control.ComboBox;
import ulb.controllers.AbstractController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import ulb.models.enums.Sport;

public class AddActivityController extends AbstractController implements Initializable{
    @FXML
    private TextField sport;
    @FXML
    private TextField duration;
    @FXML
    ComboBox<Sport> cbSport;
    System.Logger logger = System.getLogger(AddActivityController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.log(System.Logger.Level.INFO, "AddActivityController initialized");
        cbSport.getItems().addAll(Sport.values());
    }
}
