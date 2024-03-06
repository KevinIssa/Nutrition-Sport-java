package ulb.controllers;

import ulb.models.Model;
// ALL controller heritate from this class
public abstract class AbstractController {
    /**
     * class used to be heritate from all controller of fxml file
     */
    Model model;

    public void setModele(Model model){
        // get the model
        this.model = model;
    }
    public void giveData(Object data){
        // function to receive data if not null
        if (data != null){
            // do something with override in children class
        }
    }
    public Model getModele(){
        return this.model;
    }
}
