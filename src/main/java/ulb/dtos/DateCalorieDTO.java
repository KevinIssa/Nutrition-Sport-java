package ulb.dtos;

import java.time.LocalDate;


public record DateCalorieDTO(LocalDate date, int calorieIntake, int calorieBurned) {

        public int getCalorieDifference() {
            return calorieIntake - calorieBurned;
        }
    }