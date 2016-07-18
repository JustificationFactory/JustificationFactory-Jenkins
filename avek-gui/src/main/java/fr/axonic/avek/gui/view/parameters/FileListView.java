package fr.axonic.avek.gui.view.parameters;

import fr.axonic.avek.gui.model.structure.Pointer;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nathaël N on 18/07/16.
 */
public class FileListView extends StackPane {
	private static final URL FILELISTWIEW_FXML
		= ParametersPane.class.getClassLoader().getResource("fxml/parameters/fileListView.fxml");

	private static final HashMap<String, Image> mapOfFileExtToSmallIcon = new HashMap<>();

	@FXML private ListView<File> fileList;
	@FXML private Label dragDropPane;
	@FXML private ProgressIndicator progressIndic;
	@FXML private BorderPane progressIndicPane;

	public FileListView() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(FILELISTWIEW_FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}

	@FXML
	public void initialize() {
		dragDropPane.setVisible(false);
		progressIndicPane.setVisible(false);

		fileList.setCellFactory(list1 -> new ListCell<File>() {
			@Override
			public void updateItem(File item, boolean empty) {
				super.updateItem(item, empty);
		        if (empty) {
			        setGraphic(null);
			        setText(null);
		        }
		        else {
					Image fxImage = getFileIcon(item);
					ImageView imageView = new ImageView(fxImage);
					setGraphic(imageView);
					setText(item.getName());
				}
			}
		});
	}

	private void onAddFiles(List<File> list) {
		onBeginAddingFiles();
		final Pointer<Long> maxLoad = new Pointer<>(0L);
		final Pointer<Long> loadPct = new Pointer<>(0L);

		for(final File f : list) {
			new Thread(() -> {
				try {
					if(fileList.getItems().contains(f))
						return;

					maxLoad.set(maxLoad.get()+f.length());

					Thread.sleep((int)(Math.random()*5000)); // simulate download TODO /////////////////

					// Adding to the list on GUI
					Platform.runLater(() -> {
						fileList.getItems().add(f);
						loadPct.set(loadPct.get()+f.length());
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}

		progressIndic.setProgress(0);
		new Thread(() -> {
			try {
				double progress = 0;
				do {
					double p = (double)loadPct.get() / (double)maxLoad.get();
					if(progress == p)
						Thread.sleep(100);
					else {
						progress = p;
						Platform.runLater(() -> progressIndic.setProgress(p));
					}
				} while(progress < 1);

				Thread.sleep(400);
				onEndAddingFiles();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	private void onBeginAddingFiles() {
		progressIndic.setProgress(-1);
		progressIndicPane.setVisible(true);
		dragDropPane.setVisible(false);
	}
	private void onEndAddingFiles() {
		progressIndicPane.setVisible(false);
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

	@FXML private void onDragOver(DragEvent event) {
		if (event.getDragboard().hasFiles())
			event.acceptTransferModes(TransferMode.COPY);
		else
			event.consume();
	}

	@FXML
	private void onDragExited(DragEvent event) {
		dragDropPane.setVisible(false);
	}

	private static String getFileExt(File file) {
        String ext = ".";
        int p = file.getName().lastIndexOf('.');
        if (p >= 0)
            ext = file.getName().substring(p);

        return ext.toLowerCase();
    }

    private static Icon getJSwingIconFromFileSystem(File file) {
		return FileSystemView.getFileSystemView().getSystemIcon(file);
    }

    private static Image getFileIcon(File file) {
        final String ext = getFileExt(file);

        Image fileIcon = mapOfFileExtToSmallIcon.get(ext);
        if (fileIcon == null) {

            Icon jswingIcon = null;

            if (file.exists())
                jswingIcon = getJSwingIconFromFileSystem(file);
            else {
                File tempFile = null;
                try {
                    tempFile = File.createTempFile("icon", ext);
                    jswingIcon = getJSwingIconFromFileSystem(tempFile);
                }
                catch (IOException ignored) {
                    // Cannot create temporary file.
                }
                finally {
                    if (tempFile != null) tempFile.delete();
                }
            }

            if (jswingIcon != null) {
                fileIcon = jswingIconToImage(jswingIcon);
                mapOfFileExtToSmallIcon.put(ext, fileIcon);
            }
        }

        return fileIcon;
    }

    private static Image jswingIconToImage(Icon jswingIcon) {
        BufferedImage bufferedImage = new BufferedImage(jswingIcon.getIconWidth(),
		                                                jswingIcon.getIconHeight(),
                                                        BufferedImage.TYPE_INT_ARGB);
        jswingIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
}