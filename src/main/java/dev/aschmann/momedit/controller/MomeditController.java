package dev.aschmann.momedit.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class MomeditController {
	//Exit the program
	public void handleExit(ActionEvent actionEvent) {
		System.exit(0);
	}

	//Help Menu button behavior
	public void handleHelp(ActionEvent actionEvent) {
		Alert alert = new Alert (Alert.AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText("");
		alert.show();
	}
}