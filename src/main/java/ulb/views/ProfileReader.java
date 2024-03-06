package ulb.views;

import java.time.LocalDate;

public class ProfileReader{
    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;
    private final float height;
    private final float weight;
    
    public ProfileReader(String firstName, String lastName, LocalDate birthDate, float height, float weight) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }

    public String getSex() {
        return "♂";
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
