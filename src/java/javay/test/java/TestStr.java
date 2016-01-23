package javay.test.java;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class TestStr {

  public static void main(String[] args) throws Exception {
    ByteArrayOutputStream bos=new ByteArrayOutputStream();
    DataOutputStream dataOs=new DataOutputStream(bos);
     
    String s = " 妈妈爱我，我爱我的祖国，123哈哈啊";
    try {
      dataOs.writeChars(s);
      dataOs.flush();
      s.getBytes();
      byte[] b3 = s.getBytes("unicode");
      printary(b3);
      System.out.println("length:"+bos.toByteArray().length+Arrays.toString(bos.toByteArray()));
      System.out.println("length:"+s.getBytes("unicode").length+Arrays.toString(s.getBytes("unicode")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    String str = "1";
    byte[] b = str.getBytes();
    byte[] b2 = str.getBytes("UTF16");
    printary(b);
    printary(b2);
  }

  public static void printary(byte[] b) {
    for(byte by : b) {
      System.out.print(Integer.toHexString(by));
    }
    System.out.println();
  }
}
