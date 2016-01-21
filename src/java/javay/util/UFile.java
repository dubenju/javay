package javay.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UFile {

	public static boolean copyFile(String in, String out) {
        int bytesum = 0;
        int byteread = 0;
        boolean bRes = false;
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
            bRes = true;
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

        }

        return bRes;
	}
	public static boolean removeFile(String in) {
		boolean bRes = false;
		File file = new File(in);
        if(file.isFile() && file.exists()){
            file.delete();
        }
        bRes = true;
        return bRes;
	}
}
