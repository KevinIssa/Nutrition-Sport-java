package ulb;

import ulb.models.Profil;
import ulb.views.AppView;
import ulb.controllers.AppController;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {

	private final AppView appView;
	private final AppController appController;

	public App() {
		this.appView = new AppView();
		this.appController = new AppController(this.appView);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.appView.setPrimaryStage(primaryStage);
		appView.loadMenu();
		primaryStage.setTitle("YOYOYO");
		primaryStage.show();
		System.out.println(Profil.load().toString());

	}

	public static void main(String[] args) {
		launch(args);
	}
}
