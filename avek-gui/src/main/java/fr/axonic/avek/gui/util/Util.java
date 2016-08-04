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

    public static String getFileContent(String path) {
        String res = "";
        try {
            File f = new File(Util.class.getClassLoader()
                    .getResource(path).toURI());
            List<String> ls = Files.readAllLines(f.toPath());
            for (String s : ls) {
                res += s;
            }

        } catch (IOException | URISyntaxException e) {
            LOGGER.error("Impossible to get file content for " + path, e);
        }
        return res;
    }
}
