package fr.axonic.avek.gui.util;

import fr.axonic.avek.gui.Main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by Nathaël N on 26/07/16.
 */
public abstract class Util {

	public static String getFileContent(String path) {
		String res = "";
		try {
			File f = new File(Main.class.getClassLoader()
					.getResource(path).toURI());
			List<String> ls = Files.readAllLines(f.toPath());
			for (String s : ls)
				res += s;

		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return res;
	}

	public static void disableGraphics() {
		System.setProperty("testfx.robot", "glass");
		System.setProperty("prism.order", "sw");
		System.setProperty("testfx.headless", "true");
		System.setProperty("java.awt.headless", "true");
		System.setProperty("prism.text", "t2k");
	}
}
