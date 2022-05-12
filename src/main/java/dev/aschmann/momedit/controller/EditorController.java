package dev.aschmann.momedit.controller;

import dev.aschmann.momedit.game.SaveGame;
import dev.aschmann.momedit.game.models.SaveGameEntryInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.controlsfx.control.textfield.CustomTextField;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EditorController implements Initializable{
	@FXML
	private Label openSavegamePath;

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

	@FXML
	public VBox abilityBox;

	@FXML
	public VBox bookBox;

	@FXML
	public VBox natureBox;

	private SaveGame saveGame;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		saveGame = new SaveGame();
		initCheckBoxesFromMap(saveGame.getAddressMap().abilities(), abilityBox);
		initTextFromMap(saveGame.getAddressMap().books(), bookBox);
		initTextFromMap(saveGame.getAddressMap().nature(), natureBox);
	}

	@FXML
	public void onOpenSavegameClick(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();

		configureFileChooser(fileChooser);
		File file = fileChooser.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());
		if (file != null) {
			openSavegamePath.setText(file.getName());
			saveGame.load(file);
			setValuesToControls();
		}
	}

	public void handleCheckboxUpdate(ActionEvent event) {
		Object cb = event.getSource();
		if (cb instanceof CheckBox) {
			saveGame.writeSingleValOffset(
				((CheckBox) cb).getId(),
				((CheckBox) cb).isSelected() ? 1 : 0
			);
		}
	}

	public void handleTextUpdate(ActionEvent event) {
		int value = 0;
		//saveGame.getAbilities().
	}

	public void onSave(ActionEvent event) {
		setValuesFromControls();
		saveGame.save();
	}

	private void setValuesToControls() {
		txtGold.setText(String.valueOf(saveGame.gold()));
		txtCasting.setText(String.valueOf(saveGame.castingSkill()));
		txtMana.setText(String.valueOf(saveGame.mana()));

		updateCheckboxesWithValues(saveGame.getAbilities(), abilityBox);
		updateTextWithValues(saveGame.getNature(), natureBox);
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

	/**
	 * to have some initial fields in the editor. Makes it nicer
	 */
	private void initCheckBoxesFromMap(Map<String, String> map, VBox vbox) {
		map.forEach((name, offset) -> {
			CheckBox cb = new CheckBox(offset);
			cb.setText(name);
			cb.setSelected(false);
			vbox.getChildren().add(cb);
		});
	}

	private void initTextFromMap(Map<String, String> map, VBox vbox) {
		map.forEach((name, offset) -> {
			CustomTextField txt = new CustomTextField();
			txt.setId(offset);
			txt.setLeft(new Label(name));
			vbox.getChildren().add(txt);
		});
	}

	private void updateTextWithValues(List<SaveGameEntryInterface> list, VBox vbox) {
		for (int i = 0; i < list.size(); i++) {
			Object txt = vbox.getChildren().get(i);
			if (txt instanceof CustomTextField) {
				((CustomTextField) txt).setText(String.valueOf(list.get(i).getValue()));
				((CustomTextField) txt).setOnAction(this::handleTextUpdate);
			}
		}
	}

	private void updateCheckboxesWithValues(List<SaveGameEntryInterface> list, VBox vbox) {
		for (int i = 0; i < list.size(); i++) {
			Object cb = vbox.getChildren().get(i);
			if (cb instanceof CheckBox) {
				((CheckBox) cb).setSelected(list.get(i).getValue() == 1);
				((CheckBox) cb).setOnAction(this::handleCheckboxUpdate);
			}
		}
	}
}
