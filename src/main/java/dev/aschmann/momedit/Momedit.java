package dev.aschmann.momedit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;

public class Momedit extends Application {

	private Stage primaryStage;

	private BorderPane rootLayout;

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
			primaryStage.setScene(scene); //Set the scene in primary stage.

			primaryStage.show(); //Display the primary stage
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showMainView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Momedit.class.getResource("editor-view.fxml"));
			AnchorPane mainView = loader.load();
			rootLayout.setCenter(mainView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeApp() {
		primaryStage.close();
	}

	public static void main(String[] args) {
		launch(args);
	}
}