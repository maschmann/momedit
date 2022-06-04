package dev.aschmann.momedit.controller;

import dev.aschmann.momedit.game.SaveGame;
import dev.aschmann.momedit.game.models.Artifact;
import dev.aschmann.momedit.game.models.SaveGameEntryInterface;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Pair;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class EditorController implements Initializable {
	@FXML
	private Label openSavegamePath;

	@FXML
	private Button openSavegame;

	@FXML
	private Button save;

	@FXML
	private Button addArtifactButton;

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
		addArtifactButton.setDisable(true);
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
			addArtifactButton.setDisable(false);
		}
	}

	@FXML
	public void onAddArtifactClick(ActionEvent event) {
		// Create the custom dialog.
		Dialog<Artifact> dialog = new Dialog<>();
		dialog.setTitle("Artifact");
		dialog.setHeaderText("Create or change an artifact");

		int newId = saveGame.getArtifacts().size(); // because the last id is size() -1
		Artifact artifact = new Artifact(newId);

		List<Integer> defaultChoices = new ArrayList<>();
		defaultChoices.add(0);
		defaultChoices.add(1);
		defaultChoices.add(2);
		defaultChoices.add(3);
		defaultChoices.add(4);
		defaultChoices.add(5);
		defaultChoices.add(6);

		//dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

		ButtonType storeButtonType = new ButtonType("Store", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(storeButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField name = new TextField();
		name.setPromptText("name");
		grid.add(new Label("name:"), 0, 0);
		grid.add(name, 1, 0);

		ChoiceBox<String> type = new ChoiceBox<>();
		type.getItems().addAll(artifact.getTypes().values().stream().toList());
		if (artifact.getTypeString() != null) {
			type.setValue(artifact.getTypeString());
		}
		grid.add(new Label("type:"), 0, 1);
		grid.add(type, 1, 1);
		type.setOnAction((cbEvent) -> artifact.setTypeString(type.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> attackBonus = createArtifactCheckBox(
			"attackBonus:", 2, grid, artifact.getAttackBonus(), defaultChoices
		);
		attackBonus.setOnAction((cbEvent) -> artifact.setAttackBonus(attackBonus.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> hitBonus = createArtifactCheckBox(
			"hitBonus:", 3, grid, artifact.getHitBonus(), defaultChoices
		);
		hitBonus.setOnAction((cbEvent) -> artifact.setHitBonus(hitBonus.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> defenseBonus = createArtifactCheckBox(
			"defenseBonus:", 4, grid, artifact.getDefenseBonus(), defaultChoices
		);
		defenseBonus.setOnAction((cbEvent) -> artifact.setDefenseBonus(defenseBonus.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> movementBonus = createArtifactCheckBox(
			"movementBonus:", 5, grid, artifact.getMovementBonus(), defaultChoices
		);
		movementBonus.setOnAction((cbEvent) -> artifact.setMovementBonus(movementBonus.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> resistanceBonus = createArtifactCheckBox(
			"resistanceBonus:", 6, grid, artifact.getResistanceBonus(), defaultChoices
		);
		resistanceBonus.setOnAction((cbEvent) -> artifact.setResistanceBonus(resistanceBonus.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> spellSkill = createArtifactCheckBox(
			"spellSkill:", 7, grid, artifact.getSpellSkill(), defaultChoices
		);
		spellSkill.setOnAction((cbEvent) -> artifact.setSpellSkill(spellSkill.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> spellSave = createArtifactCheckBox(
			"spellSave:", 8, grid, artifact.getSpellSave(), defaultChoices
		);
		spellSave.setOnAction((cbEvent) -> artifact.setSpellSave(spellSave.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> spellCharges = createArtifactCheckBox(
			"spellCharges:", 9, grid, artifact.getSpellSave(), defaultChoices
		);
		spellCharges.setOnAction((cbEvent) -> artifact.setSpellSave(spellCharges.getSelectionModel().getSelectedItem()));

		artifact.setManaPrice(3500); // set some nice default price, so you can get money

		//Node storeButton = dialog.getDialogPane().lookupButton(storeButtonType);
		//storeButton.setDisable(true);
		dialog.getDialogPane().setContent(grid);

		Platform.runLater(name::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == storeButtonType) {
				// saveGame.getArtifacts()
				return artifact;
			}

			return null;
		});

		Optional<Artifact> resultArtifact = dialog.showAndWait();

		resultArtifact.ifPresent(artifactToStore -> {
			// store artifact in byteArray
			saveGame.saveArtifact(artifactToStore);
		});
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

	private ChoiceBox<Integer> createArtifactCheckBox(
		String label,
		int position,
		GridPane grid,
		int value,
		List<Integer> choices
	) {
		ChoiceBox<Integer> choiceBox = new ChoiceBox<>();
		choiceBox.getItems().addAll(choices);
		choiceBox.setValue(value);
		grid.add(new Label(label), 0, position);
		grid.add(choiceBox, 1, position);

		return choiceBox;
	}
}
