package dev.aschmann.momedit.controller;

import dev.aschmann.momedit.game.SaveGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;

public class EditorController {
	@FXML
	private Label openSavegamePath;

	@FXML
	private TextArea resourceText;

	@FXML
	private Button openSavegame;

	@FXML
	public void onOpenSavegameClick(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();

		configureFileChooser(fileChooser);
		File file = fileChooser.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());
		if (file != null) {
			openSavegamePath.setText(file.getName());
			openFile(file);
		}
	}

	private static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("Load a savegame");
		fileChooser.setInitialDirectory(
			new File(System.getProperty("user.home"))
		);
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Savegames", "*.GAM")
		);
	}

	private void openFile(File file) {
		SaveGame save = new SaveGame(file);
		resourceText.setText(save.createHexMap().toString());
	}
}
