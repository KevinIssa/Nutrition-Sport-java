module ulb {
	requires javafx.controls;
	requires javafx.fxml;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires java.desktop;
	requires org.slf4j;
	requires jdk.jshell;
    requires ch.qos.logback.core;

    exports ulb;
	exports ulb.controllers;
	exports ulb.dtos;
	exports ulb.enums;
	exports ulb.exceptions;
	exports ulb.models;
	exports ulb.repositories;
	exports ulb.services;
	exports ulb.views;
	exports ulb.widgets;

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
