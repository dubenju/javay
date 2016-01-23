package javay.test.http;

import java.io.FileInputStream;
import java.security.MessageDigest;

public class MD5CheckSumExample 
{
  public static void main(String[] args)throws Exception
  {
    MessageDigest md = MessageDigest.getInstance("MD5");
    FileInputStream fis = new FileInputStream("./build/javay.tar.gz");
    
    byte[] dataBytes = new byte[1024];
   
    int nread = 0; 
    while ((nread = fis.read(dataBytes)) != -1) {
      md.update(dataBytes, 0, nread);
    };
    fis.close();
    byte[] mdbytes = md.digest();
   
    //convert the byte to hex format method 1
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < mdbytes.length; i++) {
      sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
    }

    System.out.println("Digest(in hex format):: " + sb.toString());
    
    //convert the byte to hex format method 2
    StringBuffer hexString = new StringBuffer();
    for (int i=0;i<mdbytes.length;i++) {
      String hex=Integer.toHexString(0xff & mdbytes[i]);
          if(hex.length()==1) hexString.append('0');
          hexString.append(hex);
    }
    System.out.println("Digest(in hex format):: " + hexString.toString());
  }
}