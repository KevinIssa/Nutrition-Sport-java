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
import java.util.List;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.DateCalorieDTO;
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
	public List<DateCalorieDTO> getCaloriesTracking() {
		List<DateCalorie> dateCalories = this.generateCaloriesTrackingListSince(31);
		this.activityRepository
				.loadAll()
				.forEach(
						activity ->
								dateCalories.forEach(
										dateCalorie -> {
											if (dateCalorie.isSameDate(
													activity.getDate().toLocalDate())) {
												dateCalorie.addCaloriesBurned(
														activity.getBurnedCalories());
											}
										}));
		this.consumeMealRepository
				.loadAll()
				.forEach(
						consumedMeal ->
								dateCalories.forEach(
										dateCalorie -> {
											if (dateCalorie.isSameDate(
													consumedMeal.getDate().toLocalDate())) {
												dateCalorie.addCaloriesIntake(
														consumedMeal.getCaloriesConsumed());
											}
										}));
		return dateCalories.stream().map(DateCalorie::buildDTO).toList();
	}

	/**
	 * This method generates a list of DateCalorie objects for the past specified number of days.
	 * @param daysAgo the number of past days to generate the list for.
	 * @return a list of DateCalorie objects.
	 */
	private List<DateCalorie> generateCaloriesTrackingListSince(int daysAgo) {
		return IntStream.rangeClosed(0, daysAgo)
				.mapToObj(i -> new DateCalorie(LocalDate.now().minusDays(daysAgo - i)))
				.toList();
	}

	/**
	 * The DateCalorie class represents a date with its corresponding calories intake and burned calories.
	 */
	private static class DateCalorie {
		private final LocalDate date;
		private int caloriesIntake;
		private int caloriesBurned;

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
		public void addCaloriesIntake(int calories) {
			this.caloriesIntake += calories;
		}

		/**
		 * This method adds the specified number of calories to the burned calories.
		 * @param calories the number of calories to add.
		 */
		public void addCaloriesBurned(int calories) {
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
