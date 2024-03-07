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

    public void createProfile() throws IOException {
        if (Profile.fileExist()){
            // doing nothing because the profile already created
        }else{
            this.getController().switchFXML("/ulb/views/profile_creation.fxml", this, this);
        }
    }

    public void saveProfile(String firstname, String lastname, String sex,float weight, float height , LocalDate birthdate){
        Profile profile = new Profile(firstname, lastname, sex, weight, height, birthdate);
        profile.save();
    }

    public SwitchController getController(){
        return this.controller;
    }
    public Profile getProfile(){
        return Profile.load();
    }
}
