package fr.axonic.avek.gui.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by Nathaël N on 26/07/16.
 */
public abstract class Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

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

        // Ignore first line break
        return res.substring(1);
    }
}
