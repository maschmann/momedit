package dev.aschmann.momedit.controller;

import dev.aschmann.momedit.game.SaveGame;
import dev.aschmann.momedit.game.models.Artifact;
import dev.aschmann.momedit.game.models.SaveGameEntryInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class EditorController implements Initializable {
	@FXML
	private Label openSavegamePath;

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
		name.textProperty().addListener((observable, oldValue, newValue) -> artifact.setName(newValue.trim()));

		ChoiceBox<String> type = new ChoiceBox<>();
		type.getItems().addAll(artifact.getTypes().values().stream().toList());
		if (artifact.getTypeString() != null) {
			type.setValue(artifact.getTypeString());
		}
		grid.add(new Label("type:"), 0, 1);
		grid.add(type, 1, 1);
		type.setOnAction((cbEvent) -> artifact.setTypeString(type.getSelectionModel().getSelectedItem()));



		TextField imageVal = new TextField();
		imageVal.setPromptText("image");
		grid.add(imageVal, 0, 2);
		imageVal.textProperty().addListener((observable, oldValue, newValue) -> {
			artifact.setGraphics(Integer.parseInt(newValue.trim()));
			try {
				ImageView imageView = getImageForId(artifact.getGraphics());
				grid.add(imageView, 1, 2);
			} catch (FileNotFoundException e) {
				// There be dragons...
			}
		});

		try {
			ImageView imageView = getImageForId(artifact.getGraphics());
			grid.add(imageView, 1, 2);
		} catch (FileNotFoundException e) {
			// There be dragons...
		}

		ChoiceBox<Integer> attackBonus = createArtifactChoiceBox(
			"attackBonus:", 3, grid, artifact.getAttackBonus(), Arrays.asList(0, 1, 2, 3, 4, 5, 6)
		);
		attackBonus.setOnAction((cbEvent) -> artifact.setAttackBonus(attackBonus.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> hitBonus = createArtifactChoiceBox(
			"hitBonus:", 4, grid, artifact.getHitBonus(), Arrays.asList(0, 1, 2, 3)
		);
		hitBonus.setOnAction((cbEvent) -> artifact.setHitBonus(hitBonus.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> defenseBonus = createArtifactChoiceBox(
			"defenseBonus:", 5, grid, artifact.getDefenseBonus(), Arrays.asList(0, 1, 2, 3, 4, 5, 6)
		);
		defenseBonus.setOnAction((cbEvent) -> artifact.setDefenseBonus(defenseBonus.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> movementBonus = createArtifactChoiceBox(
			"movementBonus:", 6, grid, artifact.getMovementBonus(), Arrays.asList(0, 1, 2, 3, 4)
		);
		movementBonus.setOnAction((cbEvent) -> artifact.setMovementBonus(movementBonus.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> resistanceBonus = createArtifactChoiceBox(
			"resistanceBonus:", 7, grid, artifact.getResistanceBonus(), Arrays.asList(0, 1, 2, 3, 4, 5, 6)
		);
		resistanceBonus.setOnAction((cbEvent) -> artifact.setResistanceBonus(resistanceBonus.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> spellSkill = createArtifactChoiceBox(
			"spellSkill:", 8, grid, artifact.getSpellSkill(), Arrays.asList(0, 5, 10, 15, 20)
		);
		spellSkill.setOnAction((cbEvent) -> artifact.setSpellSkill(spellSkill.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> spellSave = createArtifactChoiceBox(
			"spellSave:", 9, grid, artifact.getSpellSave(), Arrays.asList(0, 1, 2, 3, 4)
		);
		spellSave.setOnAction((cbEvent) -> artifact.setSpellSave(spellSave.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> spellCharges = createArtifactChoiceBox(
			"spellCharges:", 10, grid, artifact.getSpellSave(), Arrays.asList(0, 1, 2, 3, 4)
		);
		spellCharges.setOnAction((cbEvent) -> artifact.setSpellSave(spellCharges.getSelectionModel().getSelectedItem()));

		ChoiceBox<Integer> vaultStorage = createArtifactChoiceBox(
			"vaultSpace:", 11, grid, artifact.getVaultStorage(), Arrays.asList(0, 1, 2, 3, 4)
		);
		vaultStorage.setOnAction((cbEvent) -> artifact.setVaultStorage(vaultStorage.getSelectionModel().getSelectedItem()));

		artifact.setManaPrice(3500); // set some nice default price, so you can get money
		// No spells here, need to jump a lot of hoops to build them in

		// ugly workaround
		final AtomicInteger ePosition = new AtomicInteger(12);
		final AtomicInteger eColumn = new AtomicInteger(0);

		// add a LOT of checkboxes
		saveGame.getEnchantments().forEach((eName, eOffset) -> {
			CheckBox cb = new CheckBox();
			cb.setId(eOffset);
			cb.setText(eName);
			cb.setSelected(false);

			// do the odd/Even thing
			if (eColumn.get() == 1) {
				grid.add(cb, eColumn.getAndDecrement(), ePosition.getAndIncrement());
			} else {
				grid.add(cb, eColumn.getAndIncrement(), ePosition.get());
			}

			cb.setOnAction(aEvent -> {
				Object currCb = aEvent.getSource();
				if (currCb instanceof CheckBox) {
					if (((CheckBox) currCb).isSelected()) {
						artifact.addEnchantment(((CheckBox) currCb).getId());
					} else {
						artifact.removeEnchantment(((CheckBox) currCb).getId());
					}
				}
			});
		});

		//Node storeButton = dialog.getDialogPane().lookupButton(storeButtonType);
		//storeButton.setDisable(true);
		dialog.getDialogPane().setContent(grid);

		Platform.runLater(name::requestFocus);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == storeButtonType) {
				return artifact;
			}

			return null;
		});

		Optional<Artifact> resultArtifact = dialog.showAndWait();

		resultArtifact.ifPresent(artifactToStore -> {
			// store artifact in byteArray
			saveGame.saveArtifact(artifactToStore);
			// reload artifact table to show changes
			intArtifactTable(saveGame.getArtifacts(), tbl_artifacts);
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
		table.getColumns().add(createArtifactStringColumn("type", "typeString"));
		table.getColumns().add(createArtifactIntegerColumn("attackBonus", "attackBonus"));
		table.getColumns().add(createArtifactIntegerColumn("hitBonus", "hitBonus"));
		table.getColumns().add(createArtifactIntegerColumn("defenseBonus", "defenseBonus"));
		table.getColumns().add(createArtifactIntegerColumn("movementBonus", "movementBonus"));
		table.getColumns().add(createArtifactIntegerColumn("resistanceBonus", "resistanceBonus"));
		table.getColumns().add(createArtifactIntegerColumn("spellSkill", "spellSkill"));
		table.getColumns().add(createArtifactIntegerColumn("spellSave", "spellSave"));
		table.getColumns().add(createArtifactIntegerColumn("spellCharges", "spellCharges"));
		table.getColumns().add(createArtifactIntegerColumn("spell", "spell"));
		table.getColumns().add(createArtifactIntegerColumn("manaPrice", "manaPrice"));

		// try later
		/*TableColumn<Artifact, Boolean> hasEnchantments = new TableColumn<>("enchanted");
		hasEnchantments.setCellValueFactory(new PropertyValueFactory<>("hasEnchantment"));
		hasEnchantments.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
		table.getColumns().add(hasEnchantments);*/

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

	private ImageView getImageForId(int id) throws FileNotFoundException {
		String artifactGfxId = String.valueOf(id);
		Image image = new Image(
			new FileInputStream(
				"src/main/resources/images/ITEMS_" + String
					.format("%1$" + 3 + "s", artifactGfxId)
					.replace(' ', '0') + "_000.bmp"
			)
		);
		ImageView imageView = new ImageView(image);
		imageView.setX(100);
		imageView.setY(25);
		imageView.setFitHeight(34);
		imageView.setFitWidth(34);
		imageView.setPreserveRatio(true);

		return imageView;
	}

	private ChoiceBox<Integer> createArtifactChoiceBox(
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
