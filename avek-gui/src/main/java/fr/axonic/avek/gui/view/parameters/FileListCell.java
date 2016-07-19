package fr.axonic.avek.gui.view.parameters;

import fr.axonic.avek.gui.model.structure.UploadedFile;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

/**
 * Created by Nathaël N on 19/07/16.
 */
class FileListCell extends ListCell<UploadedFile> {
	private static final HashMap<String, Image> mapOfFileExtToSmallIcon = new HashMap<>();
	private ProgressIndicator progressIndicator;


	private static Image getFileIcon(UploadedFile uploadedFile) {
		final String ext = getFileExt(uploadedFile.getOriginal());

		Image fileIcon = mapOfFileExtToSmallIcon.get(ext);
		if (fileIcon == null) {
			Icon jswingIcon = getJSwingIconFromFileSystem(uploadedFile.getOriginal());

			if (jswingIcon != null) {
				fileIcon = jswingIconToImage(jswingIcon);
				mapOfFileExtToSmallIcon.put(ext, fileIcon);
			}
		}

		return fileIcon;
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

	private static Image jswingIconToImage(Icon jswingIcon) {
		BufferedImage bufferedImage = new BufferedImage(jswingIcon.getIconWidth(),
				jswingIcon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		jswingIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
		return SwingFXUtils.toFXImage(bufferedImage, null);
	}


	@Override
	public void updateItem(UploadedFile item, boolean empty) {
		if(getItem() != null)
			getItem().removeListener();

		super.updateItem(item, empty);

		if (empty) {
			setGraphic(null);
			setText(null);
		}
		else {
			item.setUpdateListener(this::updateGraphics);
			setText(item.getOriginal().getName());
		}
	}

	private void updateGraphics() {
		UploadedFile item = FileListCell.this.getItem();

		if(item == null)
			return;

		if (item.getPct() == 1) {
			Image fxImage = getFileIcon(item);
			ImageView imageView = new ImageView(fxImage);

			setGraphic(imageView);
			progressIndicator = null; // throw the progress indicator (now unused)
		} else {
			if (progressIndicator == null) {
				progressIndicator = new ProgressIndicator();
				setGraphic(progressIndicator);
			}

			progressIndicator.setProgress(item.getPct());
		}
	}
}
