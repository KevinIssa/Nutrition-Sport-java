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
    
    public ProfileReader(Profil profil) {
        this.firstName = profil.getFirstName();
        this.lastName = profil.getLastName();
        this.birthDate = profil.getBirthDate();
        this.sex = profil.getSex();
        this.height = profil.getHeight();
        this.weight = profil.getWeight();

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
