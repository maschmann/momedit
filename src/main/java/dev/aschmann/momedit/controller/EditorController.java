package dev.aschmann.momedit.controller;

import dev.aschmann.momedit.game.SaveGame;
import dev.aschmann.momedit.game.models.SaveGameEntryInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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

	private SaveGame saveGame;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		saveGame = new SaveGame();
		initCheckBoxesFromMap(saveGame.getAddressMap().abilities(), abilityBox);
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
		setValuesFromControls();
		saveGame.save();

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Game saved");
		alert.setHeaderText("Game saved");
		alert.setContentText("Your game has been saved :-)");
		alert.show();
	}

	private void setValuesToControls() {
		txtGold.setText(String.valueOf(saveGame.gold()));
		txtCasting.setText(String.valueOf(saveGame.castingSkill()));
		txtMana.setText(String.valueOf(saveGame.mana()));

		updateCheckboxesWithValues(saveGame.getAbilities(), abilityBox);

		initTable(saveGame.getLife(), tbl_life);
		initTable(saveGame.getDeath(), tbl_death);
		initTable(saveGame.getNature(), tbl_nature);
		initTable(saveGame.getSorcery(), tbl_sorcery);
		initTable(saveGame.getChaos(), tbl_chaos);
		initTable(saveGame.getArcane(), tbl_arcane);
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
		vbox.setFillWidth(false);
		map.forEach((name, offset) -> {
			CheckBox cb = new CheckBox(offset);
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

	private void initTable(List<SaveGameEntryInterface> map, TableView<SaveGameEntryInterface> table) {
		table.getColumns().removeAll(table.getColumns());

		TableColumn<SaveGameEntryInterface, String> column1 = new TableColumn<>("spell");
		column1.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<SaveGameEntryInterface, Integer> column2 = new TableColumn<>("value");
		column2.setCellValueFactory(new PropertyValueFactory<>("value"));
		column2.setCellFactory(ComboBoxTableCell.<SaveGameEntryInterface, Integer>forTableColumn(0, 1, 2, 3));
		column2.setOnEditCommit(event -> {
			SaveGameEntryInterface save = event.getTableView().getItems().get(event.getTablePosition().getRow());
			save.setValue(event.getNewValue());
			saveGame.writeSingleValOffset(save.getHexOffset(), event.getNewValue());
		});

		/*TableColumn<SaveGameEntryInterface, String> column3 = new TableColumn<>("offset");
		column3.setCellValueFactory(new PropertyValueFactory<>("hexOffset"));
		column3.setCellFactory(TextFieldTableCell.<SaveGameEntryInterface>forTableColumn());
		column3.setVisible(false); // just needed for debugging*/

		table.getColumns().add(column1);
		table.getColumns().add(column2);
		//table.getColumns().add(column3);
		table.getItems().addAll(map);
	}
}
