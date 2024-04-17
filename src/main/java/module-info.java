module ulb {
	requires javafx.controls;
	requires javafx.fxml;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires java.desktop;
	requires org.slf4j;

	exports ulb;
	exports ulb.models;
	exports ulb.models.enums;
	exports ulb.views;
	exports ulb.controllers;
	exports ulb.controllers.dtos;
	exports ulb.widgets;
	exports ulb.exceptions;

	opens ulb.models to
			javafx.fxml,
			com.fasterxml.jackson.databind;
	opens ulb.views to
			javafx.fxml;
	opens ulb.controllers to
			javafx.fxml;
	opens ulb.widgets to
			javafx.fxml;
}
