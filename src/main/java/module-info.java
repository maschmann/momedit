module dev.aschmann.momedit {
	requires javafx.controls;
	requires javafx.base;
	requires javafx.fxml;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires org.kordamp.bootstrapfx.core;
	requires com.google.common;

	requires com.fasterxml.jackson.dataformat.yaml;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.annotation;
	requires com.fasterxml.jackson.databind;

	opens dev.aschmann.momedit to javafx.fxml;
	opens dev.aschmann.momedit.game.models to com.fasterxml.jackson.databind;
	exports dev.aschmann.momedit;
    exports dev.aschmann.momedit.controller;
	exports dev.aschmann.momedit.game.models;
    opens dev.aschmann.momedit.controller to javafx.fxml;
}