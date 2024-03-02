package ulb.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ulb.models.Modele;

import java.io.IOException;


public class SwitchController {
    // used to switch between different fxml file while keeping the same modele all the time and be able to send a object type
    Stage primaryStage;
    public SwitchController(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public void switchFXML(String fxmlFileName, Modele modele, Object data) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            AbstractController loaderController = loader.getController();
            loaderController.setModele(modele);
            loaderController.giveData(data);
            // Replace the scene's root with the new FXML content
            primaryStage.setScene(new Scene(root, 300, 200));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
    public void switchFXML(String fxmlFileName, Modele modele) {
        this.switchFXML(fxmlFileName, modele,  null);
    }
}
