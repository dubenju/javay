package javay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;

public class UFile {
  private static final Logger log = LoggerFactory.getLogger(UFile.class);

  /**
   * copyFile.
   * @param in String
   * @param out String
   * @return boolean
   */
  public static boolean copyFile(String in, String out) {
    log.info("copy " + in + " to " + out);
    int bytesum = 0;
    int byteread = 0;
    boolean blRes = false;
    try {
      InputStream inStream = new FileInputStream(in);
      FileOutputStream fs = new FileOutputStream(out);
      byte[] buffer = new byte[4096];
      while ( (byteread = inStream.read(buffer)) != -1) {
        bytesum  += byteread;
        System.out.println(bytesum);
        fs.write(buffer, 0, byteread);
      }
      inStream.close();
      fs.close();
      blRes = true;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    log.info("copy " + in + " to " + out + " : " + (blRes ? "成功" : "失败"));
    return blRes;
  }


  /**
   * removeFile.
   * @param in String
   * @return boolean
   */
  public static boolean removeFile(String in) {
    log.info("delete " + in);
    boolean blRes = false;
    File file = new File(in);
    if (file.isFile() && file.exists()) {
      file.delete();
    }
    blRes = true;
    log.info("delete " + in + " : " + (blRes ? "成功" : "失败"));
    return blRes;
  }
}
