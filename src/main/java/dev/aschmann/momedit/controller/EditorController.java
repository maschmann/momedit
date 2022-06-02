package dev.aschmann.momedit.controller;

import dev.aschmann.momedit.game.SaveGame;
import dev.aschmann.momedit.game.models.Artifact;
import dev.aschmann.momedit.game.models.SaveGameEntryInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;

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
	public VBox abilityBox;

	private SaveGame saveGame;

	@FXML
	public TableView<SaveGameEntryInterface> tbl_base;

	@FXML
	public TableView<SaveGameEntryInterface> tbl_nature;

	@FXML
	public TableView<SaveGameEntryInterface> tbl_sorcery;

	@FXML
	public TableView<SaveGameEntryInterface> tbl_chaos;

	@FXML
	public TableView<SaveGameEntryInterface> tbl_life;

	@FXML
	public TableView<SaveGameEntryInterface> tbl_death;

	@FXML
	public TableView<SaveGameEntryInterface> tbl_arcane;

	@FXML
	public TableView<Artifact> tbl_artifacts;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		saveGame = new SaveGame();
		initCheckBoxesFromMap(saveGame.getAbilityMap(), abilityBox);
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

	public void onSave(ActionEvent event) {
		saveGame.save();

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Game saved");
		alert.setContentText("Your game has been saved :-)");
		alert.show();
	}

	private void setValuesToControls() {
		updateCheckboxesWithValues(saveGame.getAbilities(), abilityBox);

		initBaseValues(saveGame.getBase(), tbl_base);

		initTable(saveGame.getLife(), tbl_life);
		initTable(saveGame.getDeath(), tbl_death);
		initTable(saveGame.getNature(), tbl_nature);
		initTable(saveGame.getSorcery(), tbl_sorcery);
		initTable(saveGame.getChaos(), tbl_chaos);
		initTable(saveGame.getArcane(), tbl_arcane);
		intArtifactTable(saveGame.getArtifacts(), tbl_artifacts);
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
		vbox.setFillWidth(false);
		// clean up in case of reload
		vbox.getChildren().removeAll(vbox.getChildren());

		map.forEach((name, offset) -> {
			CheckBox cb = new CheckBox();
			cb.setId(offset);
			cb.setText(name);
			cb.setSelected(false);
			vbox.getChildren().add(cb);
		});
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

	private void initBaseValues(List<SaveGameEntryInterface> map, TableView<SaveGameEntryInterface> table) {
		// clean up in case of reload
		table.getColumns().removeAll(table.getColumns());
		table.setEditable(true);

		TableColumn<SaveGameEntryInterface, Integer> column2 = new TableColumn<>("value");
		column2.setCellValueFactory(new PropertyValueFactory<>("value"));
		column2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		column2.setOnEditCommit(event -> {
			SaveGameEntryInterface save = event.getTableView().getItems().get(event.getTablePosition().getRow());
			save.setValue(event.getNewValue());
			saveGame.writeOffset(save.getHexOffset(), 2, event.getNewValue());
		});
		column2.setEditable(true);

		table.getColumns().add(createStringColumn("name", "name"));
		table.getColumns().add(column2);
		table.getItems().addAll(map);
	}

	private void initTable(List<SaveGameEntryInterface> map, TableView<SaveGameEntryInterface> table) {
		// clean up in case of reload
		table.getColumns().removeAll(table.getColumns());

		TableColumn<SaveGameEntryInterface, Integer> column2 = new TableColumn<>("value");
		column2.setCellValueFactory(new PropertyValueFactory<>("value"));
		column2.setCellFactory(ComboBoxTableCell.<SaveGameEntryInterface, Integer>forTableColumn(0, 1, 2, 3));
		column2.setOnEditCommit(event -> {
			SaveGameEntryInterface save = event.getTableView().getItems().get(event.getTablePosition().getRow());
			save.setValue(event.getNewValue());
			saveGame.writeSingleValOffset(save.getHexOffset(), event.getNewValue());
		});

		table.getColumns().add(createStringColumn("spell", "name"));
		table.getColumns().add(column2);
		table.getItems().addAll(map);
	}

	private void intArtifactTable(List<Artifact> artifacts, TableView<Artifact> table) {
		// clean up in case of reload
		table.getColumns().removeAll(table.getColumns());

		table.getColumns().add(createArtifactStringColumn("name", "name"));
		table.getColumns().add(createArtifactStringColumn("type", "type"));
		table.getColumns().add(createArtifactIntegerColumn("attackBonus", "attackBonus"));
		table.getColumns().add(createArtifactIntegerColumn("hitBonus", "hitBonus"));
		table.getColumns().add(createArtifactIntegerColumn("defenseBonus", "defenseBonus"));
		table.getColumns().add(createArtifactIntegerColumn("movementBonus", "movementBonus"));
		table.getColumns().add(createArtifactIntegerColumn("resistanceBonus", "resistanceBonus"));
		table.getColumns().add(createArtifactIntegerColumn("spellSkill", "spellSkill"));
		table.getColumns().add(createArtifactIntegerColumn("spellSave", "spellSave"));
		table.getColumns().add(createArtifactIntegerColumn("spellCharges", "spellCharges"));
		table.getColumns().add(createArtifactIntegerColumn("manaPrice", "manaPrice"));

		table.getItems().addAll(artifacts);
	}

	private TableColumn<SaveGameEntryInterface, String> createStringColumn(String type, String name) {
		TableColumn<SaveGameEntryInterface, String> column = new TableColumn<>(type);
		column.setCellValueFactory(new PropertyValueFactory<>(name));

		return column;
	}

	private TableColumn<Artifact, Integer> createArtifactIntegerColumn(String type, String name) {
		TableColumn<Artifact, Integer> column = new TableColumn<>(type);
		column.setCellValueFactory(new PropertyValueFactory<>(name));
		column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		return column;
	}

	private TableColumn<Artifact, String> createArtifactStringColumn(String type, String name) {
		TableColumn<Artifact, String> column = new TableColumn<>(type);
		column.setCellValueFactory(new PropertyValueFactory<>(name));

		return column;
	}
}
