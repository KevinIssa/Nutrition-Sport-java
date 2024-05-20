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

/**
 * This is a record class named ConsumedFoodDTO. A record is a special kind of class in Java that helps to model plain data aggregates with less ceremony than normal classes.
 * This record has four fields: name, quantity, calories, and unit.
 *
 * @param name The name of the consumed food item.
 * @param quantity The quantity of the consumed food item.
 * @param calories The caloric value of the consumed food item.
 * @param unit The unit of the consumed food item.
 */
public record ConsumedFoodDTO(String name, double quantity, double calories, String unit) {}
