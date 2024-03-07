package ulb.views;

import javafx.scene.control.Alert;
import ulb.views.ViewController;

import java.net.URL;
import java.util.ResourceBundle;


public class MenuViewController implements ViewController {

	private Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Auto-generated method stub
	}

	public void consultProfile() {
		this.listener.loadConsultProfileView();
	}

	public void modifyProfile(){
		this.listener.loadModifyProfileView();
	}

	public void createActivity() {
		this.listener.loadCreateActivityView();
	}

	public void activityHistory(){this.listener.loadActivityHistoryView();}

	public void computeCalories() {
		showAlert("Calcul du nombre de calories");
		// TODO: implement this method
	}

	private void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Action sélectionnée");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void setListener(Object listener) {
		this.listener = (Listener) listener;
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
	}

	public interface Listener {
		void loadConsultProfileView();
		void loadCreateActivityView();
		void loadModifyProfileView();
		void loadCreateProfileView();
		void loadMenuView();
		void loadWelcomeView();
		void loadActivityHistoryView();
	}

}
