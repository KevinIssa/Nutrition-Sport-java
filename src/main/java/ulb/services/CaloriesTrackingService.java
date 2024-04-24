package ulb.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.DateCalorieDTO;
import ulb.models.Activity;
import ulb.models.ConsumedMeal;
import ulb.repositories.ActivityRepository;
import ulb.repositories.ConsumeMealRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;

public class CaloriesTrackingService {
    static private final Logger logger = LoggerFactory.getLogger(CaloriesTrackingService.class);
    private final ActivityRepository activityRepository;
    private final ConsumeMealRepository consumeMealRepository;

    public CaloriesTrackingService(ActivityRepository activityRepository, ConsumeMealRepository consumeMealRepository) {
        this.activityRepository = activityRepository;
        this.consumeMealRepository = consumeMealRepository;
    }

    public List<DateCalorieDTO> getCaloriesTracking() {
        List<Activity> activities = activityRepository.loadAll();
        List<ConsumedMeal> consumedMeals = consumeMealRepository.loadAll();
        List<DateCalorie> dateCalories = this.generateCaloriesTrackingListSince(31); // 31 days ago at most
        for (DateCalorie dateCalorie: dateCalories) { // TODO : refactor this LinSFa
            for (Activity activity : activities) {
                if (dateCalorie.isSameDate(activity.getDate().toLocalDate())) {
                    dateCalorie.addCaloriesBurned(activity.getBurnedCalories());
                }
            }
            for (ConsumedMeal consumedMeal : consumedMeals) {
                if (dateCalorie.isSameDate(consumedMeal.getDate().toLocalDate())) {
                    dateCalorie.addCaloriesIntake(consumedMeal.getCaloriesConsumed());
                }
            }
        }
        return dateCalories.stream().map(DateCalorie::buildDTO).toList();
    }

    private List<DateCalorie> generateCaloriesTrackingListSince(int daysAgo) {
        List<DateCalorie> dateCalories = new ArrayList<>();
        LocalDate date = LocalDate.now().minusDays(daysAgo);
        while (date.isBefore(LocalDate.now().plusDays(1))) {
            dateCalories.add(new DateCalorie(date));
            date = date.plusDays(1);
        }
        return dateCalories;
    }

    private class DateCalorie{
        private final LocalDate date;
        private int caloriesIntake;
        private int caloriesBurned;

        public DateCalorie(LocalDate date){
            this.date = date;
            this.caloriesIntake = 0;
            this.caloriesBurned = 0;
        }

        public void addCaloriesIntake(int calories){
            this.caloriesIntake += calories;
        }

        public void addCaloriesBurned(int calories){
            this.caloriesBurned += calories;
        }

        public boolean isSameDate(LocalDate date){
            return this.date.equals(date);
        }

        public DateCalorieDTO buildDTO(){
            return new DateCalorieDTO(this.date, this.caloriesIntake,this.caloriesBurned);
        }
    }
}
