/**
 *
 */
package javay.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.unzip.UnzipUtil;

/**
 * @author DBJ
 *
 */
public class UnpackZip {
    // private static Logger log = LoggerFactory.getLogger(UnpackZip.class);

    public boolean unpack(String zip, String unpackpath, String pwd) {
        boolean bres = false;

        try {
            // System.out.println("解嵃);
            ZipInputStream is = null;
            OutputStream os = null;

            // ZipFile zipFile = new ZipFile("C:\\prj\\jzip\\test\\zip4j\\AddFilesWithStandardZipEncryption.zip");
            ZipFile zipFile = new ZipFile(zip);
            String destinationPath = unpackpath;

            // If zip file is password protected then set the password
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(pwd);
            }

            List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
            for (int i = 0; i < fileHeaderList.size(); i++) {
                FileHeader fileHeader = (FileHeader)fileHeaderList.get(i);
                if (fileHeader != null) {

                    //Build the output file
                    String outFilePath = destinationPath + System.getProperty("file.separator") + fileHeader.getFileName();
                    File outFile = new File(outFilePath);

                    //Checks if the file is a directory
                    if (fileHeader.isDirectory()) {
                        //This functionality is up to your requirements
                        //For now I create the directory
                        outFile.mkdirs();
                        continue;
                    }

                    //Check if the directories(including parent directories)
                    //in the output file path exists
                    File parentDir = outFile.getParentFile();
                    if (!parentDir.exists()) {
                        parentDir.mkdirs();
                    }

                    //Get the InputStream from the ZipFile
                    is = zipFile.getInputStream(fileHeader);
                    //Initialize the output stream
                    os = new FileOutputStream(outFile);

                    int readLen = -1;
                    byte[] buff = new byte[4096];

                    //Loop until End of File and write the contents to the output stream
                    while ((readLen = is.read(buff)) != -1) {
                        os.write(buff, 0, readLen);
                    }

                    //Please have a look into this method for some important comments
                    if (os != null) {
                        os.close();
                        os = null;
                    }
                    if (is != null) {
                        is.close();
                        is = null;
                    }

                    //To restore File attributes (ex: last modified file time,
                    //read only flag, etc) of the extracted file, a utility class
                    //can be used as shown below
                    UnzipUtil.applyFileAttributes(fileHeader, outFile);

                    System.out.println("Done extracting: " + fileHeader.getFileName());
                    bres = true;
                } else {
                    System.err.println("fileheader is null. Shouldn't be here");
                }
            }
        } catch (Exception ex) {
            bres = false;
        }

        return bres;
    }

    public static void main(String[] args) {
        // log.info("==BEGIN==");
        System.out.println("OS=" + System.getProperty("os.name"));

        UnpackZip unpackZip = new UnpackZip();
        unpackZip.unpack("C:\\prj\\jzip\\test\\130925.zip", "C:\\prj\\jzip\\test\\unpack", "");

        // log.info("==END==");
    }
}
