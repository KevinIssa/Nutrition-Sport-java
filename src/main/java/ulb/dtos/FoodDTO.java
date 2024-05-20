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

import ulb.enums.Unit;

/**
 * This is a record class named FoodDTO. A record is a special kind of class in Java that helps to model plain data aggregates with less ceremony than normal classes.
 * This record has three fields: name, quantity, and unit.
 *
 * @param name The name of the food item.
 * @param quantity The quantity of the food item.
 * @param unit The unit of the food item.
 */
public record FoodDTO(String name, double quantity, Unit unit) {}
