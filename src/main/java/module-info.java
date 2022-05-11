module dev.aschmann.momedit {
	requires javafx.controls;
	requires javafx.base;
	requires javafx.fxml;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires org.kordamp.bootstrapfx.core;
	requires com.google.common;

	opens dev.aschmann.momedit to javafx.fxml;
	exports dev.aschmann.momedit;
    exports dev.aschmann.momedit.controller;
	exports dev.aschmann.momedit.game.models;
    opens dev.aschmann.momedit.controller to javafx.fxml;
}