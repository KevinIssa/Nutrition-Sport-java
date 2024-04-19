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

import java.time.LocalDate;

/**
 * This record represents a Profile Data Transfer Object (DTO).
 * It is used to transfer data between processes or components.
 *
 * @param firstName The first name of the user.
 * @param lastName The last name of the user.
 * @param sex The sex of the user.
 * @param weight The weight of the user in kilograms.
 * @param height The height of the user in meters.
 * @param birthDate The birth date of the user.
 * @param imagePath The path to the image of the user.
 */
public record ProfileDTO(
		String firstName,
		String lastName,
		String sex,
		float weight,
		float height,
		LocalDate birthDate,
		String imagePath) {}
