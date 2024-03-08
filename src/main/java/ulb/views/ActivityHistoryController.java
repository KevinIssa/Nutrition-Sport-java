/* (C)2024 */
package ulb.views;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import ulb.models.Activity;

public class ActivityHistoryController implements ViewController {
	private static final String FOLDERNAME = "activities";
	Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Specify the directory path
		File directory = new File(FOLDERNAME);

		// Get list of files in the directory
		File[] files = directory.listFiles();

		// Print the names of files
		if (files != null) {
			for (File file : files) {
				this.addActivity(listener.loadActivity(file.getPath()));
			}
		}
	}

	public void addActivity(Activity activity) {
		// TODO add the activity in the window, do not know how
		return;
	}

	public void returnHome() {
		this.listener.returnHome();
	}

	public void setListener(Object listener) {
		this.listener = (Listener) listener;
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
	}

	public interface Listener {
		Activity loadActivity(String filename);

		void returnHome();
	}
}
