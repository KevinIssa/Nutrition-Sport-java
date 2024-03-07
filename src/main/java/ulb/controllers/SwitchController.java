package ulb.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ulb.models.Model;

import java.io.IOException;


public class SwitchController{

    /*Controller used to switch the Scene showed on the screen and at the time giving data to
     * the controller of the scene if needed
     * */
    Stage primaryStage;

    public SwitchController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void switchFXML(String fxmlFileName, Model model, Object listener) {
        /* the function switchFXML use the path to a fxml file to be able to switch to this scene
         * the Model are given to give the possibility to the controller of each fxml file to apply changes
         * Attention, the controller of each fxml file need to heritate from AbstractController
         * the Object data are given to the controller of each fxml if we give them one, the class SwitchController just pass
         * the object to the corresponding controller, because the type of the object is Object, the Controller of the fxml file
         * need to cast it into the right type.
         */
        try {
            // load the fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            // get the root
            Parent root = loader.load();
            // get the controller of the fxml file
            AbstractController loaderController = loader.getController();
            // give the model to the controller
            loaderController.setModel(model);
            loaderController.setListener(listener);
            // give an Object type data that need to be cast or null
            // create a scene using the root of the fxml file and use it as display
            primaryStage.setScene(new Scene(root, 300, 200));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }

    }

    public void switchFXML(String fxmlFileName, Model model) {
        switchFXML(fxmlFileName, model, null);
    }
}
