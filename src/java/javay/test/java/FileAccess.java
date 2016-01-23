package javay.test.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileAccess
{

 public static boolean Move(File srcFile, String destPath)
 {
    // Destination directory
    File dir = new File(destPath);
    
    // Move file to new directory
    boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));
    
    return success;
  }
 
 public static boolean Move(String srcFile, String destPath)
 {
    // File (or directory) to be moved
    File file = new File(srcFile);
    
    // Destination directory
    File dir = new File(destPath);
    
    // Move file to new directory
    boolean success = file.renameTo(new File(dir, file.getName()));
    
    return success;
  }
 
 public  static   void   Copy(String   oldPath,   String   newPath)   
   {   
      try   {   
          int   bytesum   =   0;   
          int   byteread   =   0;   
          File   oldfile   =   new   File(oldPath);   
          if   (oldfile.exists())   {   
              InputStream   inStream   =   new   FileInputStream(oldPath);  
              FileOutputStream   fs   =   new   FileOutputStream(newPath);   
              byte[]   buffer   =   new   byte[1444];   
              int   length;   
              while   (   (byteread   =   inStream.read(buffer))   !=   -1)   {   
                  bytesum   +=   byteread;     
                  System.out.println(bytesum);   
                  fs.write(buffer,   0,   byteread);   
              }   
              inStream.close();   
          }   
      }   
      catch   (Exception   e)   {   
          System.out.println( "error  ");   
          e.printStackTrace();   
      }   
  }  
   public   static  void   Copy(File   oldfile,   String   newPath)   
   {   
      try   {   
          int   bytesum   =   0;   
          int   byteread   =   0;   
          //File   oldfile   =   new   File(oldPath);   
          if   (oldfile.exists())   {   
              InputStream   inStream   =   new   FileInputStream(oldfile);  
              FileOutputStream   fs   =   new   FileOutputStream(newPath);   
              byte[]   buffer   =   new   byte[1444];   
              while   (   (byteread   =   inStream.read(buffer))   !=   -1)   {   
                  bytesum   +=   byteread;     
                  System.out.println(bytesum);   
                  fs.write(buffer,   0,   byteread);   
              }   
              inStream.close();   
          }   
      }   
      catch   (Exception   e)   {   
          System.out.println( "error  ");   
          e.printStackTrace();   
      }   
  }  
}