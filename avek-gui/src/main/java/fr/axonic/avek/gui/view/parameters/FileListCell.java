package fr.axonic.avek.gui.view.parameters.list;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Nathaël N on 19/07/16.
 */
public class FileListCell extends ListCell<File> {
	private static final HashMap<String, Image> mapOfFileExtToSmallIcon = new HashMap<>();

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
}
