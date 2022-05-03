package dev.aschmann.momedit;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Momedit extends Application {

	private Stage primaryStage;

	private BorderPane rootLayout;

	private String SavegamePath;

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Master of Magic savegame editor");

		initRootLayout();
		showMainView();
	}

	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Momedit.class.getResource("root-layout.fxml"));
			rootLayout = loader.load();
			Scene scene = new Scene(rootLayout); //We are sending rootLayout to the Scene.

			MenuBar menuBar = new MenuBar();

			Menu menuFile = new Menu("File");
			MenuItem add = new MenuItem("Load Savegame");
			/*add.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					openSavegameDialog();
				}
			});*/

			menuFile.getItems().addAll(add);
			Menu menuEdit = new Menu("Help");
			menuBar.getMenus().addAll(menuFile, menuEdit);

			primaryStage.setScene(scene); //Set the scene in primary stage.
			//Third, show the primary stage
			primaryStage.show(); //Display the primary stage
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showMainView() {
		try {
			//First, load EmployeeOperationsView from EmployeeOperations.fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Momedit.class.getResource("main-view.fxml"));
			AnchorPane mainView = loader.load();
			// Set Employee Operations view into the center of root layout.
			rootLayout.setCenter(mainView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void openSavegameDialog() {
		final FileChooser fileChooser = new FileChooser();

		configureFileChooser(fileChooser);
		File file = fileChooser.showOpenDialog(primaryStage);
		if (file != null) {
			SavegamePath = file.getPath();
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
		/*try {
			desktop.open(file);
		} catch (IOException ex) {
			System.Logger.getLogger(Momedit.class.getName()).log(
					Level.SEVERE, null, ex
			);
		}*/
	}

	public static void main(String[] args) {
		launch(args);
	}
}