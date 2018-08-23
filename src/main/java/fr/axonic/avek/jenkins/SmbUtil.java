package fr.axonic.avek.jenkins;

import jcifs.smb.SmbFile;
import org.eclipse.core.internal.localstore.SafeFileInputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

/**
 * Created by cduffau on 05/09/17.
 */
public class SmbUtil {
    private SmbFile distantDir;
    private File localFile;

    public SmbUtil(String distantDir,String newPath, String filePath) throws MalformedURLException {
        this.distantDir=new SmbFile(distantDir+"/"+newPath);
        this.localFile=new File(filePath);
    }

    public SmbFile getDistantFile() throws MalformedURLException, UnknownHostException {
        return new SmbFile(distantDir.getPath()+"/"+localFile.getName());
    }

    public void copy() throws IOException {
        SmbFile smbDir= distantDir;
        if (!smbDir.exists()) {
            smbDir.mkdirs();
        }
        SmbFile smbFile=getDistantFile();
        if (!smbFile.exists()) {
            smbFile.createNewFile();
        }
        copy(localFile,smbFile);

    }



    private static void copy(File from, SmbFile to) throws IOException {

        OutputStream os = to.getOutputStream();
        InputStream is = new SafeFileInputStream(from);

        int bufferSize = 5096;

        byte[] b = new byte[bufferSize];
        int noOfBytes = 0;
        while ((noOfBytes = is.read(b)) != -1) {
            os.write(b, 0, noOfBytes);
        }
        os.close();
        is.close();
    }

    public static SmbUtil getSmbUtil(String distantDir, String argumentationSystemName, String patternId, String stepId, String supportId, String artifactPath) throws MalformedURLException {
       return new SmbUtil(distantDir,argumentationSystemName+"/"+patternId+"/"+stepId+"/"+supportId,artifactPath);

    }
}
