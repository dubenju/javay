package javay.jvm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javay.util.UBytes;

public class Jjvm {

	public static void main(String[] args) {
		JjvmProc proc = new JjvmProc();
		proc.testa();
	}

}
class JjvmProc {
	public void testa() {
		System.out.println("This is a test.");
		String strFileName = "./classes/javay/math/BigNum.class";
	    int bytesum = 0;
	    int byteread = 0;
	    boolean blRes = false;
	    try {
	      InputStream inStream = new FileInputStream(strFileName);
//	      FileOutputStream fs = new FileOutputStream(out);
	      byte[] buffer = new byte[4096];
	      while ( (byteread = inStream.read(buffer)) != -1) {
	        bytesum  += byteread;
	        System.out.println(bytesum);
	        String str = UBytes.toHexString(buffer);
	        System.out.println(str);
//	        fs.write(buffer, 0, byteread);
	      }
	      inStream.close();
//	      fs.close();
	      blRes = true;
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		System.out.println(">> End <<.");
	}
}