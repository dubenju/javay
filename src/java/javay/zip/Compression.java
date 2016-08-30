package javay.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩
 */
public class Compression {

  // 要压缩文件的路径
  private String srcPath;

  // 压缩后存放的路径
  private String dirPath;

  public Compression(String srcPath, String dirPath) {
    this.srcPath = srcPath;
    this.dirPath = dirPath;
  }

  /**
   * 压缩程序
   */
  public void compressionProgram() {
    ZipOutputStream zipOutputStream = null;
    File file = null;

    try {
      file = new File(srcPath);
      zipOutputStream = new ZipOutputStream(new FileOutputStream(dirPath));

      if(file.isDirectory()) {//文件夹
        compressionFolder(file, zipOutputStream, "");
      } else {//文件
        zipOutputStream.putNextEntry(new ZipEntry(""));
        compressionFile(file, zipOutputStream, "");
      }

    } catch (FileNotFoundException  e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if(zipOutputStream != null) {
         zipOutputStream.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } // try
 }

  /**
   *  压缩文件
   *  参数： 当前访问的文件file，  压缩流zipCompression
   */
  private void compressionFile(File file, ZipOutputStream zipOutputStream, String dir) {
    FileInputStream fileInputStream = null;
    try {

      zipOutputStream.putNextEntry(new ZipEntry(dir + file.getName()));

      fileInputStream = new FileInputStream(file);
      byte[] byteStr = new byte[1024 * 1024]; // 每次读取1M
      int strLength = -1;

      while((strLength = fileInputStream.read(byteStr) ) > 0) {
        zipOutputStream.write(byteStr, 0, strLength);
      }

    } catch (FileNotFoundException e) {
      new Throwable("文件不存在！");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if(fileInputStream != null) {
          fileInputStream.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } // try
 }

  /**
   *  压缩文件夹
   */
  private void compressionFolder(File file, ZipOutputStream zipOutputStream, String dir) throws FileNotFoundException {
    String path = dir + file.getName() + "/";
    try {
      zipOutputStream.putNextEntry(new ZipEntry(path));

      File[] files = file.listFiles(); //得到该文件夹下的所有文件
      for (File filesFile : files) {

        if(filesFile.isDirectory()) {
          compressionFolder(filesFile, zipOutputStream, path);
        } else {
          compressionFile(filesFile, zipOutputStream, path);
        }

      } // for

    } catch (IOException e) {
       e.printStackTrace();
    }
  }
}
