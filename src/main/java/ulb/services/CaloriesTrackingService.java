/*
 * Ce projet est une application de santé et de bien-être développée dans le cadre du cours INFO-F-307 à l'ULB.
 *
 * Groupe : 06
 * Étudiants :
 * - Kevin ISSA
 * - Hamza CAEYMAN
 * - Alexandru MELNIC
 * - Ze-Xuan XU
 * - Bao TRAN
 * - Hà Uyên TRAN
 * - Hugo CHARELS
 * - Hodo SOULEIMAN AHMED
 * - Kevin VANDERVAEREN
 * - Arthur INSTALLÉ
 *
 * Date : 2024
 */
package ulb.services;

import java.time.LocalDate;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.DateCalorieDTO;
import ulb.models.Activity;
import ulb.models.ConsumedMeal;
import ulb.repositories.ActivityRepository;
import ulb.repositories.ConsumeMealRepository;

/**
 * The CaloriesTrackingService class provides methods to track calories intake and burned calories.
 * It uses ActivityRepository and ConsumeMealRepository to fetch data.
 */
public class CaloriesTrackingService {
	private static final Logger logger = LoggerFactory.getLogger(CaloriesTrackingService.class);
	private final ActivityRepository activityRepository;
	private final ConsumeMealRepository consumeMealRepository;

	/**
	 * Constructor for the CaloriesTrackingService class.
	 * @param activityRepository the repository to fetch activities data.
	 * @param consumeMealRepository the repository to fetch consumed meals data.
	 */
	public CaloriesTrackingService(
			ActivityRepository activityRepository, ConsumeMealRepository consumeMealRepository) {
		this.activityRepository = activityRepository;
		this.consumeMealRepository = consumeMealRepository;
	}

	/**
	 * This method returns a list of DateCalorieDTO objects which represent the calories tracking for the past 31 days.
	 * @return a list of DateCalorieDTO objects.
	 */
	public List<DateCalorieDTO> getCaloriesTracking(int daysAgo) {
		Map<LocalDate, DateCalorie> dateCalories = getLocalDateDateCalorieMap(daysAgo);
		LocalDate firstUsableDate = LocalDate.now().minusDays(daysAgo);
		addCaloriesBurned(firstUsableDate, dateCalories);
		addCaloriesIntake(firstUsableDate, dateCalories);
		return getDateCalorieDTOS(dateCalories);
	}

	private static Map<LocalDate, DateCalorie> getLocalDateDateCalorieMap(int daysAgo) {
		Map<LocalDate, DateCalorie> dateCalories = new HashMap<>();
		for (int i = daysAgo; i >= 0; i--) {
			LocalDate date = LocalDate.now().minusDays(i);
			dateCalories.put(date, new DateCalorie(date));
		}
		return dateCalories;
	}

	private void addCaloriesBurned(
			LocalDate firstUsableDate, Map<LocalDate, DateCalorie> dateCalories) {
		List<Activity> activities = this.activityRepository.loadAll();
		for (Activity activity : activities) {
			if (activity.getDate().toLocalDate().isBefore(firstUsableDate)) {
				continue;
			}
			dateCalories
					.get(activity.getDate().toLocalDate())
					.addCaloriesBurned(activity.getBurnedCalories());
		}
	}

	private void addCaloriesIntake(
			LocalDate firstUsableDate, Map<LocalDate, DateCalorie> dateCalories) {
		List<ConsumedMeal> consumedMeals = this.consumeMealRepository.loadAll();
		for (ConsumedMeal consumedMeal : consumedMeals) {
			if (consumedMeal.getDate().toLocalDate().isBefore(firstUsableDate)) {
				continue;
			}
			dateCalories
					.get(consumedMeal.getDate().toLocalDate())
					.addCaloriesIntake(consumedMeal.getCaloriesConsumed());
		}
	}

	private static List<DateCalorieDTO> getDateCalorieDTOS(
			Map<LocalDate, DateCalorie> dateCalories) {
		List<DateCalorieDTO> dtos = new ArrayList<>();
		for (LocalDate date : dateCalories.keySet()) {
			dtos.add(dateCalories.get(date).buildDTO());
		}
		dtos.sort(Comparator.comparing(DateCalorieDTO::getDate));
		return dtos;
	}

	/**
	 * Retrieves the calorie difference for today's tracking.
	 *
	 * @return the calorie difference for today's tracking
	 */
	public double getTodayCalorieDelta() {
		DateCalorieDTO today = this.getCaloriesTracking(0).getFirst();
		return today.getCalorieDifference();
	}

	/**
	 * The DateCalorie class represents a date with its corresponding calories intake and burned calories.
	 */
	private static class DateCalorie {
		private final LocalDate date;
		private double caloriesIntake = 0;
		private double caloriesBurned = 0;

		/**
		 * Constructor for the DateCalorie class.
		 * @param date the date for which the object is created.
		 */
		public DateCalorie(LocalDate date) {
			this.date = date;
		}

		/**
		 * This method adds the specified number of calories to the calories intake.
		 * @param calories the number of calories to add.
		 */
		public void addCaloriesIntake(double calories) {
			this.caloriesIntake += calories;
		}

		/**
		 * This method adds the specified number of calories to the burned calories.
		 * @param calories the number of calories to add.
		 */
		public void addCaloriesBurned(double calories) {
			this.caloriesBurned += calories;
		}

		/**
		 * This method checks if the date of this object is the same as the specified date.
		 * @param date the date to compare with.
		 * @return true if the dates are the same, false otherwise.
		 */
		public boolean isSameDate(LocalDate date) {
			return this.date.equals(date);
		}

		/**
		 * This method builds a DateCalorieDTO object from this object.
		 * @return a DateCalorieDTO object.
		 */
		public DateCalorieDTO buildDTO() {
			return new DateCalorieDTO(this.date, this.caloriesIntake, this.caloriesBurned);
		}
	}
}
