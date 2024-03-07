package ulb.models;

import ulb.models.enums.Sex;
import ulb.views.ProfileViewController;

import java.time.LocalDate;

public class ProfileReader implements ProfileViewController.Listener {
    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;

    private final Sex sex;
    private final float height;
    private final float weight;
    
    public ProfileReader(Profile profile) {
        this.firstName = profile.getFirstName();
        this.lastName = profile.getLastName();
        this.birthDate = profile.getBirthDate();
        this.sex = profile.getSex();
        this.height = profile.getHeight();
        this.weight = profile.getWeight();

    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }

    public String getSex() {
       if (sex.equals(Sex.MALE)){
           return "♂";
       }
       else{
           return "♀";
       }
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public float getHeight() {
        return height;
    }
    
    public float getWeight() {
        return weight;
    }
}
