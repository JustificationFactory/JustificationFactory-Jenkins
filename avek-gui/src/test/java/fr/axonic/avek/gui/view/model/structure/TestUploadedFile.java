package fr.axonic.avek.gui.view.model.structure;

import fr.axonic.avek.gui.model.structure.UploadedFile;
import fr.axonic.avek.gui.view.MainController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nathaël N on 20/07/16.
 */
public class TestUploadedFile extends ApplicationTest {
	static {
		System.setProperty("testfx.headless", "true");
		System.setProperty("prism.text", "t2k");
		System.setProperty("prism.order", "sw");
		System.setProperty("testfx.robot", "glass");
		System.setProperty("java.awt.headless", "true");
	}

	private Parent root;

	@Override
	public void start(Stage stage) throws Exception {
		root = MainController.getRoot();
		Scene scene = new Scene(root, 200, 200);
		stage.setScene(scene);
		stage.show();
	}

	@Before
	public void before() throws IOException {
		File f = new File("./temp/yolo.txt"); // 31 bytes
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

	UploadedFile uf;
	@Test
	public void uploadedFileTest() throws FileNotFoundException, FileAlreadyExistsException {
		uf = new UploadedFile(new File("./temp/yolo.txt"));

		assertEquals(new File("./temp/yolo.txt"), uf.getOriginal());
		assertEquals(31, uf.getSize(), 1);
		uf.setUpdateListener(this::uploadedFileTest2);
		uf.doUpload();
	}

	private void uploadedFileTest2() {
		if(uf.getPct() < 1)
			return;

		uf.removeListener();

		List<File> fileList = new ArrayList<>();
		Collections.addAll(fileList, UploadedFile.uploadedFolder.listFiles());

		assertEquals(1, fileList.size());
		File f = fileList.get(0);
		assertEquals("yolo.txt", f.getName());
		assertEquals(31, f.length(), 1);
		assertTrue(f.isFile());
	}

	@Test
	public void uploadedFolderTest() throws FileNotFoundException, FileAlreadyExistsException {
		uf = new UploadedFile(new File("./temp/toto/"));

		assertEquals(new File("./temp/toto"), uf.getOriginal());
		assertEquals(25+49, uf.getSize(), 1);
		uf.setUpdateListener(this::uploadedFolderTest2);
		uf.doUpload();
	}

	private void uploadedFolderTest2() {
		if(uf.getPct() < 1)
			return;

		uf.removeListener();

		List<File> fileList = new ArrayList<>();
		Collections.addAll(fileList, UploadedFile.uploadedFolder.listFiles());

		assertEquals(1, fileList.size());
		File f = fileList.get(0);
		assertEquals("toto", f.getName());
		assertEquals(31, f.length(), 1);
		assertTrue(f.isDirectory());

		assertEquals(2, f.listFiles().length);
		File titi = f.listFiles()[0];
		File tonton = f.listFiles()[1];
		assertEquals("titi", titi.getName());
		assertEquals("tonton", tonton.getName());
		assertTrue(titi.isFile());
		assertTrue(tonton.isFile());

		try {
			List<String> l = new ArrayList<>();
			l.add("TATA TATa TAta Tata tata");
			assertEquals(l, Files.readAllLines(titi.toPath()));

			l.clear();
			l.add("tonton Tonton TOnton TONton");
			l.add("TONTon TONTOn TONTON");
			assertEquals(l, Files.readAllLines(tonton.toPath()));

		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		}
	}

	@After public void after() throws IOException {
		File f = new File("./temp/yolo.txt");
		delete(f);
		f = new File("./temp/toto");
		delete(f);
	}

	void delete(File f) throws IOException {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				delete(c);
		}
		if (!f.delete())
			throw new FileNotFoundException("Failed to delete file: " + f);
	}
}
