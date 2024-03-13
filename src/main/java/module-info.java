module ulb {
	requires javafx.controls;
	requires javafx.fxml;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;
    requires java.desktop;

    exports ulb;
	exports ulb.models;
	exports ulb.models.enums;
	exports ulb.views;
	exports ulb.controllers;

	opens ulb.models to
			javafx.fxml;
	opens ulb.views to
			javafx.fxml;
	opens ulb.controllers to
			javafx.fxml;
}
