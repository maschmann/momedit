package dev.aschmann.momedit.controller;

import dev.aschmann.momedit.game.SaveGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	private Button save;

	@FXML
	private TextField txtMana;

	@FXML
	private TextField txtCasting;

	@FXML
	private TextField txtGold;

	private SaveGame saveGame;

	@FXML
	public void onOpenSavegameClick(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();

		configureFileChooser(fileChooser);
		File file = fileChooser.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());
		if (file != null) {
			openSavegamePath.setText(file.getName());
			openFile(file);
			setValuesToControls();
		}
	}

	public void onSave(ActionEvent event) {
		setValuesFromControls();
		saveGame.save();
	}

	private void setValuesToControls() {
		txtGold.setText(String.valueOf(saveGame.gold()));
		txtCasting.setText(String.valueOf(saveGame.castingSkill()));
		txtMana.setText(String.valueOf(saveGame.mana()));
	}

	private void setValuesFromControls() {
		saveGame.setGold(Integer.parseInt(txtGold.getText()));
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
		saveGame = new SaveGame(file);
		//resourceText.setText(save.createHexMap().toString());
		resourceText.setText(saveGame.getAbilities().toString());
	}
}
