module ulb {
	requires javafx.controls;
	requires javafx.fxml;
	requires com.fasterxml.jackson.databind;
	exports ulb;
	opens ulb.controllers to javafx.fxml;
	opens ulb.views to javafx.fxml;
}