package ulb.models;

import ulb.controllers.SwitchController;

public class Modele {
    SwitchController controller;

    public Modele(SwitchController controller){
        this.controller = controller;
    }
    public SwitchController getController(){
        return this.controller;
    }
}
