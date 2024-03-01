module ulb {
	requires javafx.controls;
	requires javafx.fxml;
	exports ulb;
	opens ulb.controllers to javafx.fxml;
}