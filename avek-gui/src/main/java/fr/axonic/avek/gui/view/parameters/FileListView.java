package fr.axonic.avek.gui.view.parameters;

import fr.axonic.avek.gui.model.structure.UploadedFile;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

/**
 * Created by Nathaël N on 18/07/16.
 */
public class FileListView extends StackPane {
	private final static Logger logger = Logger.getLogger(FileListView.class);

	private static final URL FILELISTWIEW_FXML
			= ParametersPane.class.getClassLoader().getResource("fxml/parameters/fileListView.fxml");

	@FXML
	private ListView<UploadedFile> fileList;
	@FXML
	private Label dragDropPane;

	// should be public
	public FileListView() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(FILELISTWIEW_FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		logger.info("Loading fileListView.fxml");
		fxmlLoader.load();
	}

	@FXML
	public void initialize() {
		dragDropPane.setVisible(false);

		fileList.setCellFactory(new Callback<ListView<UploadedFile>, ListCell<UploadedFile>>() {
			@Override
			public FileListCell call(ListView<UploadedFile> list) {
				return new FileListCell();
			}
		});
	}

	@FXML
	private void onDragDropped(DragEvent event) {
		Dragboard db = event.getDragboard();
		boolean success = false;

		if (db.hasFiles()) {
			onAddFiles(db.getFiles());
			success = true;
		}
		event.setDropCompleted(success);
		event.consume();
		dragDropPane.setVisible(false);
	}

	@FXML
	private void onDragEntered(DragEvent event) {
		if (event.getDragboard().hasFiles())
			dragDropPane.setVisible(true);
	}

	@FXML
	private void onDragOver(DragEvent event) {
		if (event.getDragboard().hasFiles())
			event.acceptTransferModes(TransferMode.COPY);
		else
			event.consume();
	}

	@FXML
	private void onDragExited(DragEvent event) {
		dragDropPane.setVisible(false);
	}

	private void onAddFiles(List<File> list) {
		for (final File f : list) {
			UploadedFile uf = new UploadedFile(f);
			try {
				// Adding to the list on GUI
				uf.doUpload();
				Platform.runLater(() -> fileList.getItems().add(uf));
			} catch (FileNotFoundException e) {
				logger.error("File not found: " + f, e);
			} catch (FileAlreadyExistsException e) {
				logger.warn("File already added: " + f.getName(), e);
			}
		}
	}

	ListView<UploadedFile> getFileList() {
		return fileList;
	}
}