package fr.axonic.avek.gui.util;

import fr.axonic.avek.gui.model.UploadedFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Nathaël N on 25/07/16.
 */
public class UtilForTests {
    private static void delete(File f) throws FileNotFoundException {
        if (f.isDirectory()) {
            for (File c : f.listFiles()) {
                delete(c);
            }
        }

        if (!f.delete()) {
            throw new FileNotFoundException("Failed to delete file: " + f);
        }
    }

    public static void deleteFileArchi() {
        try {
            File f = new File("./temp/test.txt");
            delete(f);
            f = new File("./temp/toto");
            delete(f);
            for (File ff : UploadedFile.uploadedFolder.listFiles()) {
                delete(ff);
            }
        } catch (FileNotFoundException ignored) {
        }
    }

    public static void createFileArchi() throws IOException {
        File f = new File("./temp/test.txt"); // 31 bytes
        f.createNewFile();

        PrintWriter writer = new PrintWriter(f, "UTF-8");
        writer.println("The first line");
        writer.println("The second line");
        writer.close();

        f = new File("./temp/toto/titi.txt"); // 25 bytes
        f.getParentFile().mkdirs();
        f.createNewFile();

        writer = new PrintWriter(f, "UTF-8");
        writer.println("TATA TATa TAta Tata tata");
        writer.close();

        f = new File("./temp/toto/tonton.txt"); // 49 bytes
        f.createNewFile();

        writer = new PrintWriter(f, "UTF-8");
        writer.println("tonton Tonton TOnton TONton");
        writer.println("TONTon TONTOn TONTON");
        writer.close();
    }

    public static void disableGraphics() {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("prism.order", "sw");
        System.setProperty("testfx.headless", "true");
        System.setProperty("java.awt.headless", "true");
        System.setProperty("prism.text", "t2k");
    }
}
