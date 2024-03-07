package ulb.models;

import ulb.controllers.SwitchController;
import ulb.views.ProfileCreationViewController;

import java.io.*;
import java.time.LocalDate;


public class Model implements ProfileCreationViewController.Listener {
    SwitchController controller;

    public Model(SwitchController controller){
        this.controller = controller;
    }
    public void createProfil() throws IOException {
        if (Profil.fileExist()){
            // doing nothing because the profil already created
        }else{
            this.getController().switchFXML("/ulb/views/profil_creation.fxml", this, this);
        }
    }
    public void saveProfile(String firstname, String lastname, String sex,float weight, float height , LocalDate birthdate){
        Profil profil = new Profil(firstname, lastname, sex, weight, height, birthdate);
        profil.save();
    }
    public SwitchController getController(){
        return this.controller;
    }
    public Profil getProfil(){
        return Profil.load();
    }
}
