package ulb.controllers;

import javafx.scene.control.Alert;
import ulb.models.Model;

// ALL controller heritate from this class
public abstract class AbstractController {
    /**
     * class used to be heritate from all controller of fxml file
     */
    private Model model;
    public void setListener(Object listener) {
        return;
    }
    public void setModel(Model model) {
        this.model = model;
    }
    public Model getModel(){
        return model;
    }
    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
