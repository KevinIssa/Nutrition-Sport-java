package ulb;

import javafx.application.Application;
import javafx.stage.Stage;
import ulb.controllers.MainAppController;


public class App extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Fitness App");
		primaryStage.setResizable(true);
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		MainAppController controller = new MainAppController(primaryStage);
		controller.loadWelcomeView();
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
