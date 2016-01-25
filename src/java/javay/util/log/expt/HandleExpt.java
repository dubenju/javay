package javay.util.log.expt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HandleExpt {
  private static String getFileName() {
    Date date = new Date();
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    String dteSuffix = dateformat.format(date);
    return "./logs/" + dteSuffix + ".trc";
  }

  /**
   * handle.
   * @param th Throwable
   */
  public static void handle(Throwable th) {
    String trcFileName = getFileName();
    System.out.println(trcFileName);
    File fl = new File(trcFileName);
    BufferedWriter bw = null;
    try {
      bw = new BufferedWriter(new FileWriter(fl));
      StringBuilder builder = new StringBuilder();
      for (StackTraceElement ste : th.getStackTrace()) {
        builder.append(ste.toString());
        builder.append("¥n");
      }
      bw.write(builder.toString());
      bw.flush();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    if (bw != null) {
      try {
        bw.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    handle(new ArithmeticException("Division by zero"));
  }
}
