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
package ulb.dtos;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This is a record class named ConsumedMealDTO. A record is a special kind of class in Java that helps to model plain data aggregates with less ceremony than normal classes.
 * This record has two fields: consumedFoods and date.
 *
 * @param consumedFoods A list of ConsumedFoodDTO objects representing the foods consumed in a meal.
 * @param date The date and time when the meal was consumed.
 */
public record ConsumedMealDTO(List<ConsumedFoodDTO> consumedFoods, LocalDateTime date) {}
