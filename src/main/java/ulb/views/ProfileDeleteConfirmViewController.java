/* (C)2024 */
package ulb.views;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileDeleteConfirmViewController implements ViewController {

	private ProfileDeleteConfirmViewController.Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Auto-generated method stub
	}

	public void setYesButton() {
		this.listener.deleteProfile();

	}

	public void setNoButton() {
		this.listener.returnHome();
	}

	public void setListener(Object listener) {
		this.listener = (ProfileDeleteConfirmViewController.Listener) listener;
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
	}

	public interface Listener {
		void deleteProfile();

		void returnHome();
	}
}
