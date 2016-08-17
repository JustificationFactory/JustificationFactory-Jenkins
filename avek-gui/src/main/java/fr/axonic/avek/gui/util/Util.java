package fr.axonic.avek.gui.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by Nathaël N on 26/07/16.
 */
public abstract class Util {
    private final static Logger LOGGER = Logger.getLogger(Util.class);

    public static String getFileContent(File f) {
        String res = "";
        try {
            List<String> ls = Files.readAllLines(f.toPath());
            for (String s : ls) {
                res += "\n" + s;
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get file content for " + f, e);
        }
        return res.substring(1); // Ignore first line break
    }
    public static String getFileContent(String path) {
        try {
            File f = new File(Util.class.getClassLoader()
                    .getResource(path).toURI());

            return getFileContent(f);
        } catch (URISyntaxException | NullPointerException e) {
            LOGGER.error("Impossible to find file at " + path, e);
        }
        return null;
    }
}
