package ulb.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ulb.controllers.AppController;
import ulb.models.Activity;
import ulb.models.enums.Sport;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ActivityHistoryController implements ViewController {
    private static final String FOLDERNAME = "activities";
    Listener listener;

    @FXML
    ListView historylist;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void addActivity(Activity activity){
        Label label = new Label("Date: " + activity.getDate().toString() +
                                "   Activity: " + activity.getSport().name() +
                                "   intensity: " + activity.getIntensity().name() +
                                "   duration: " + activity.getDuration().toString());
        historylist.getItems().add(label);
    }
    public void returnHome(){
        this.listener.returnHome();
    }
    public void addActivities(){
        historylist.getItems().clear();
        // Specify the directory path
        File directory = new File(FOLDERNAME);

        // Get list of files in the directory
        File[] files = directory.listFiles();

        // Print the names of files
        if (files != null) {
            for (File file : files) {
                this.addActivity(listener.loadActivity(file.getPath()));
            }
        }
    }

    public void setListener(Object listener) {
        this.listener = (Listener) listener;
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }
        this.addActivities();
    }

    public interface Listener {
        Activity loadActivity(String filename);
        void returnHome();
    }
}
