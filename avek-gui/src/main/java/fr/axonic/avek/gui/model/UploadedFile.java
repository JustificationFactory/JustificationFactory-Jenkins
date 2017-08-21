package fr.axonic.avek.gui.model;

import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Stack;

/**
 * Created by Nathaël N on 18/07/16.
 */
public class UploadedFile {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadedFile.class);

    // 1Mb
    private static final long MAX_BUF_LENGTH = 1024L * 1024L;

    // 1kb
    private static final long MIN_BUF_LENGTH = 1024L;

    public static final File uploadedFolder;

    static {
        uploadedFolder = new File("./temp/uploaded/" + Calendar.getInstance().getTimeInMillis());

        uploadedFolder.mkdirs();
        uploadedFolder.deleteOnExit();
        LOGGER.debug("Temp folder created: " + uploadedFolder);
    }

    private final File origin;
    private final File uploaded;

    private long uploadedBytes;
    private Runnable listener;

    public UploadedFile(File origin) {
        this.origin = origin;
        this.uploadedBytes = 0;
        this.uploaded = new File(uploadedFolder.getPath() + "/" + origin.getName());
    }

    public void doUpload() throws FileAlreadyExistsException, FileNotFoundException {
        if (!origin.exists()) {
            throw new FileNotFoundException(origin.getAbsolutePath());
        }
        if (this.uploaded.exists()) {
            throw new FileAlreadyExistsException(this.uploaded.getPath());
        }
        if (uploading) {
            throw new RuntimeException("Upload already started");
        }

        new Thread(this::doUploadMultiFiles).start();
    }

    private boolean uploading = false;

    private void doUploadMultiFiles() {
        uploading = true;

        // to update for each % of upload finished
        long calc = getSize() / 100L + 1L;
        if (calc > MAX_BUF_LENGTH) {
            calc = MAX_BUF_LENGTH;
        }
        if (calc < MIN_BUF_LENGTH) {
            calc = MIN_BUF_LENGTH;
        }

        final int bufSize = (int) calc;
        final String originPath = origin.getPath();
        final String uploadPath = uploaded.getPath();

        final Stack<File> stack = new Stack<>();
        stack.push(origin);

        while (!stack.isEmpty()) {
            File pop = stack.pop();
            if (pop.isDirectory()) {
                //noinspection ConstantConditions
                for (File f : pop.listFiles()) {
                    stack.push(f);
                }
            } else if (pop.isFile()) {
                final String fileName = pop.getPath().replace(originPath, uploadPath);
                doUploadOneFile(pop, new File(fileName), bufSize);
            }
        }

        if (uploaded.isDirectory()) {
            LOGGER.info("All json treated for " + uploaded);
        }

        uploadedBytes = getSize();
        if (listener != null) {
            Platform.runLater(listener);
        }

        uploading = false;
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

                if (listener != null) {
                    Platform.runLater(listener);
                }
            }

            LOGGER.info("File uploaded: " + pop + " to " + newFile);
        } catch (Exception e) {
            LOGGER.error("Upload of a file failed: " + newFile, e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                LOGGER.error("Impossible to close input/output stream: " + newFile, e);
            }
        }
    }

    public File getOriginal() {
        return origin;
    }

    private long size = 0;

    public long getSize() {
        if (size > 0) {
            return size;
        }

        Stack<File> stack = new Stack<>();
        stack.push(origin);

        while (!stack.isEmpty()) {
            File pop = stack.pop();

            if (pop.isDirectory()) {
                //noinspection ConstantConditions
                for (File f : pop.listFiles()) {
                    stack.push(f);
                }
            } else if (pop.isFile()) {
                size += pop.length();
            }
        }

        LOGGER.debug("Size calculated: " + NumberFormat.getInstance().format(size) + " bytes for " + origin.getName());

        return size;
    }

    public double getPct() {
        return (double) uploadedBytes / (double) getSize();
    }

    public void removeListener() {
        this.listener = null;
    }

    public void setUpdateListener(Runnable listener) {
        this.listener = listener;
        Platform.runLater(listener);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UploadedFile) {
            return origin.equals(((UploadedFile) obj).origin);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return (origin.hashCode() * 17 + uploaded.hashCode())*13;
    }
}
