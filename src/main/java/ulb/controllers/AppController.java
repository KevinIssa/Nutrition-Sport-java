package ulb.controllers;

import ulb.views.AppView;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Controller {

	private final AppView appView;

	public AppController(AppView appView) {
		this.appView = appView;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

	}

}
