package dev.aschmann.momedit.controller;

import com.google.common.io.BaseEncoding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
		try {
			Path path = Paths.get(file.getAbsolutePath());
			StringBuilder concatText = new StringBuilder();
			int bytesPerLine = 16;

			//Stream<String> lines = Files.lines(path, StandardCharsets.ISO_8859_1);
			byte[] fileBytes = Files.readAllBytes(path);
			//String str  = Integer.toHexString((b & 0xff)+256).substring(1);
			//resourceText.setText(fileBytes.toString());
			int iterations = (int)Math.round((float)fileBytes.length / bytesPerLine);

			for (int i = 0; i < iterations; i++) { // catch the rest of the byte array
				if ((iterations - i) == 1) {
					int restOfArray = fileBytes.length - (i * bytesPerLine);
					concatText.append(BaseEncoding.base16().lowerCase().encode(fileBytes, (i * bytesPerLine), restOfArray).toString());
				} else {
					concatText.append(BaseEncoding.base16().lowerCase().encode(fileBytes, (i * bytesPerLine), bytesPerLine).toString());
					concatText.append("\n");
				}

			}

			//resourceText.setText(concatText.toString());
			//resourceText.setText(BaseEncoding.base16().lowerCase().encode(fileBytes).toString());
			//resourceText.setText(lines.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
