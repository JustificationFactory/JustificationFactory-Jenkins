package fr.axonic.avek.gui.model.structure;

import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Stack;

/**
 * Created by Nathaël N on 18/07/16.
 */
public class UploadedFile {
	private final static Logger logger = Logger.getLogger(UploadedFile.class);

	private static final File uploadedFolder;
	static {
		uploadedFolder = new File("./temp/uploaded/"+ Calendar.getInstance().getTimeInMillis());

		uploadedFolder.mkdirs();
		uploadedFolder.deleteOnExit();
		logger.debug("Temp folder created: " + uploadedFolder);
	}

	private final File origin;
	private final File uploaded;

	private long uploadedBytes;
	private Runnable listener;

	public UploadedFile(File origin) throws FileNotFoundException, FileAlreadyExistsException {
		if(!origin.exists())
			throw new FileNotFoundException(origin.getAbsolutePath());

		this.origin = origin;
		this.uploadedBytes = -1;

		File uploaded = null;

		// Checking if already uploade
		for(File f : uploadedFolder.listFiles())
			if(f.equals(origin)) {
				uploaded = f;
				break;
			}

		this.uploaded = uploaded==null ?
				  new File(uploadedFolder.getPath() + "/" + origin.getName())
				: uploaded;

		if(this.uploaded.exists())
			throw new FileAlreadyExistsException(this.uploaded.getPath());
	}

	public void doUpload() {
		Thread t = new Thread(() -> {
			long calc = getSize() / 100L + 1L;
			if (calc > 1024L * 1024L) // 1Mb max
				calc = 1024L * 1024L;
			if (calc < 1024L) // 1Kb min
				calc = 1024;

			final int bufSize = (int) calc;
			final String originPath = origin.getPath();
			final String uploadPath = uploaded.getPath();

			final Stack<File> stack = new Stack<>();
			stack.push(origin);

			do {
				File pop = stack.pop();
				if(pop.isDirectory()) {
					for(File f : pop.listFiles())
						stack.push(f);
				}
				else if(pop.isFile()) {
					final String fileName = pop.getPath().replace(originPath, uploadPath);
					doUploadOneFile(pop, new File(fileName), bufSize);
				}
			} while(!stack.isEmpty());

			if(uploaded.isDirectory())
				logger.info("All files treated for "+uploaded);

			uploadedBytes = getSize();
			if (listener != null)
				Platform.runLater(() -> listener.run());
		});
		t.setName(t.getName() + ":" + origin.getName());
		t.start();
	}

	private void doUploadOneFile(File pop, File newFile, int bufSize) {
		InputStream input = null;
		OutputStream output = null;
		try {
			newFile.delete();
			newFile.getParentFile().mkdirs();
			newFile.createNewFile();
			newFile.deleteOnExit();

			input = new FileInputStream(pop);
			output = new FileOutputStream(newFile);
			byte[] buf = new byte[bufSize];
			int bytesRead;

			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);

				uploadedBytes += bytesRead;

				if (listener != null)
					Platform.runLater(() -> listener.run());
			}

			logger.info("File uploaded: "+pop+" to "+newFile);
		} catch (Exception e) {
			logger.error("Upload of a file failed: "+newFile,e);
		} finally {
			try {
				if (input != null)
					input.close();
				if (output != null)
					output.close();
			} catch (IOException e) {
				logger.error("Impossible to close input/output stream: "+newFile, e);
			}
		}
	}

	public File getOriginal() {
		return origin;
	}

	private long size = -1;
	public long getSize() {
		if(size > 0)
			return size;

		Stack<File> stack = new Stack<>();
		stack.push(origin);

		do {
			File pop = stack.pop();

			if(pop.isDirectory())
				for(File f : pop.listFiles())
					stack.push(f);
			else if(pop.isFile())
				size+=pop.length();
		} while(!stack.isEmpty());

		logger.debug("Size calculated: "+ NumberFormat.getInstance().format(size)+" bytes for "+origin.getName());

		return size;
	}
	public double getPct() {
		return (double)uploadedBytes / (double)getSize();
	}



	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UploadedFile)
			return origin.equals(((UploadedFile)obj).origin);
		if(obj instanceof File)
			return origin.equals(obj) || uploaded.equals(obj);
		return super.equals(obj);
	}

	public void removeListener() {
		this.listener = null;
	}

	public void setUpdateListener(Runnable listener) {
		this.listener = listener;

		Platform.runLater(listener);
	}
}
