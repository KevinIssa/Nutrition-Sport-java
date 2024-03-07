package ulb.views;

import javafx.scene.control.*;
import javafx.util.StringConverter;
import ulb.controllers.AbstractController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import ulb.models.enums.Sport;

public class AddActivityController extends AbstractController implements Initializable {
    @FXML
    private TextField sport;
    @FXML
    private Slider intensity_slider;
    @FXML
    private TextField intensity;
    @FXML
    private TextField duration;
    @FXML
    ComboBox<Sport> cbSport;
    private Listener listener;
    System.Logger logger = System.getLogger(AddActivityController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.log(System.Logger.Level.INFO, "AddActivityController initialized");
        cbSport.getItems().addAll(Sport.values());
        intensity_slider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double aDouble) {
                if (aDouble < 0.5) return "Slow";
                if (aDouble < 1.5) return "Moderate";
                if (aDouble < 2.5) return "Intense";

                return "Intense";
            }

            @Override
            public Double fromString(String s) {
                switch (s) {
                    case "Slow":
                        return 0d;
                    case "Moderate":
                        return 1d;
                    case "Intense":
                        return 2d;

                    default:
                        return 2d;
                }

            }
        });
        intensity_slider.setMin(0);
        intensity_slider.setMax(2);
        intensity_slider.setValue(1);
        intensity_slider.setMinorTickCount(0);
        intensity_slider.setSnapToTicks(true);
        intensity_slider.setShowTickMarks(true);
        intensity_slider.setShowTickLabels(true);
        intensity_slider.setMajorTickUnit(1);
        intensity_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue()==0) intensity.setText("Slow");
            if(newValue.intValue()==1) intensity.setText("Moderate");
            if(newValue.intValue()==2) intensity.setText("Intense");
        });
    }

    public void saveActivity() {
        try {
            LocalDateTime date= LocalDateTime.now();
            Sport selectedSport = cbSport.getValue();
            String selectedIntensity = intensity.getText();
            float selectedDuration = Float.parseFloat(duration.getText());
            safeSaveActivity(selectedSport, selectedIntensity, selectedDuration,date);
        } catch (NumberFormatException e) {
            logger.log(System.Logger.Level.ERROR, "Duration must be a number");
            return;
        }
        this.getModel().getController().switchFXML("/ulb/views/main.fxml", this.getModel());
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public void safeSaveActivity(Sport selectedSport, String selectedIntensity, float selectedDuration, LocalDateTime date) {
        if(listener == null) {
            logger.log(System.Logger.Level.ERROR, "Listener is null");
            return;
        }
        listener.saveActivity(selectedSport, selectedIntensity, selectedDuration, date);
    }
    public interface Listener {
        void saveActivity(Sport selectedSport, String selectedIntensity, float selectedDuration, LocalDateTime date);
    }
}