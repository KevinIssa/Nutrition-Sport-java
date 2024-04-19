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
package ulb.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import ulb.models.Profile;

public class JSONProfileRepository implements ProfileRepository {
	private static final String FILE_NAME = "profile.json";
	private static final String IMAGE_PATH = "profile.png";

	@Override
	public void save(Profile profile) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.registerModule(new JavaTimeModule());
		try {
			mapper.writeValue(new File(FILE_NAME), profile);
		} catch (IOException e) {
			// TODO: Implement
			e.printStackTrace();
		}
	}

	@Override
	public void saveProfileImage(String imagePath) {
		if (!(imagePath.endsWith(".png")
				|| imagePath.endsWith(".jpg")
				|| imagePath.endsWith(".jpeg"))) {
			// TODO: Implement
			// logger.warn("Only PNG and JPG images are supported: {}", imagePath);
			// throw new IllegalImageFormatException("Only PNG and JPG images are supported");
		}

		try {
			Files.copy(
					new URL(imagePath).openStream(),
					Paths.get(IMAGE_PATH),
					java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO: Implement
			// logger.error("Failed to save image", e);
			// throw new InvalidImageException("Failed to save image");
			e.printStackTrace();
		}
	}

	@Override
	public Profile load() {
		File file = new File(FILE_NAME);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(file, Profile.class);
		} catch (IOException e) {
			// logger.error("Error loading profile from file: {}", FILE_NAME);
			return null;
		}
	}

	@Override
	public void update(Profile profile) {
		System.out.println(profile.toString());
		this.save(profile);
	}

	@Override
	public void delete() {
		new File(FILE_NAME).delete();
	}

	@Override
	public boolean isCreated() {
		return new File(FILE_NAME).exists() && this.load() != null;
	}

	@Override
	public String getImagePath() {
		return IMAGE_PATH;
	}

	@Override
	public float getWeight() {
		return Optional.ofNullable(this.load()).map(Profile::getWeight).orElse(0f);
	}
}
