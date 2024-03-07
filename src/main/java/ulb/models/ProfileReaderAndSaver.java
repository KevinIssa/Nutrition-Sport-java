package ulb.models;

import ulb.views.ProfileModificatorViewController;

import java.time.LocalDate;

public class ProfileReaderAndSaver extends ProfileReader implements ProfileModificatorViewController.Listener {
    public ProfileReaderAndSaver(Profile profile){
        super(profile);
    }
    public void saveProfile(String firstname, String lastname, String sex, LocalDate birthdate, float weight, float height ){
        Profile profile = new Profile(firstname, lastname, sex, weight, height, birthdate);
        profile.save();
    }
}
