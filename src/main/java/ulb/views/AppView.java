package ulb.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AppView implements View {

	private Stage primaryStage;

	public AppView() {
	}

	public AppView(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void loadWelcome() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/ulb/views/welcome.fxml"));
		primaryStage.setScene(new Scene(root));
	}

	public void loadMenu() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/ulb/views/menu.fxml"));
		primaryStage.setScene(new Scene(root));
	}

	public void loadProfile() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/ulb/views/profile.fxml"));
		primaryStage.setScene(new Scene(root));
	}

	public void loadAddActivity() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/ulb/views/add_activity.fxml"));
		primaryStage.setScene(new Scene(root));
	}

}
