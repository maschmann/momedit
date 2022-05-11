package dev.aschmann.momedit.controller;

import dev.aschmann.momedit.game.SaveGame;
import dev.aschmann.momedit.game.models.Ability;
import dev.aschmann.momedit.game.models.AbilityModel;
import dev.aschmann.momedit.game.models.SaveGameEntryInterface;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EditorController implements Initializable{
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

	@FXML
	private TableView<AbilityModel> abilityTable;

	@FXML
	public TableColumn<AbilityModel, Integer> value;

	@FXML
	public TableColumn<AbilityModel, String> name;

	private SaveGame saveGame;

	@FXML
	private ObservableList<AbilityModel> abilities;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		name.setCellValueFactory(new PropertyValueFactory<>("getName"));
		value.setCellValueFactory(new PropertyValueFactory<>("getValue"));
		abilityTable.setEditable(true);
	}

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

		abilities = FXCollections.observableArrayList();
		saveGame.getAbilities().forEach((ability) -> {
			abilities.add(
				new AbilityModel(
					ability.getName(),
					ability.getValue()
				)
			);
		});

		abilityTable.setItems(abilities);
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
		//resourceText.setText(saveGame.getAbilities().toString());
	}
}
