module dev.aschmann.momedit {
	requires javafx.controls;
	requires javafx.fxml;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires org.kordamp.bootstrapfx.core;

	opens dev.aschmann.momedit to javafx.fxml;
	exports dev.aschmann.momedit;
    exports dev.aschmann.momedit.controller;
    opens dev.aschmann.momedit.controller to javafx.fxml;
}