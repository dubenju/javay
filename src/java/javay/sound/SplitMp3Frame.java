package javay.sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javay.util.Strings;
import javay.util.UBytes;

public class SplitMp3Frame {

	public static void main(String[] args) throws IOException {
		byte[] sp = new byte[2];
		sp[0] = (byte) 0xFF;
		sp[1] = (byte) 0xFB;
		split("./out/01-Get Your Head Straight_noId3.mp3", sp);

	}
	public static void split(String strFileName, byte[] spliter) throws IOException {
		File file = new File(strFileName);
		String fileName = file.getName();
		long fileSize = file.length();
		System.out.println("文件:" + strFileName +  "大小:" + fileSize);

		int bytesum = 0;
		int byteread = 0;
		InputStream is = new FileInputStream(strFileName);
		byte[] buf = new byte[(int) fileSize];
		if ( (byteread = is.read(buf)) == -1) {
			System.out.println("read error mp3");
			is.close();
			System.out.println("read=" + bytesum);
			return ;
		}
		bytesum  += byteread;

		long cnt = 0;
		String outFile = "";
		OutputStream os = null;

		boolean bNewFile = false;
		for (int idx = 0; idx < bytesum; idx ++) {
			if (UBytes.comp(buf, idx, spliter, 0, spliter.length) == 0) {
				bNewFile = true;
			}
			if (bNewFile) {
				if (os != null) {
					os.close();
				}
				cnt ++;
				outFile = "./out/" + fileName.substring(0, 2) + "/f_" + Strings.format(String.valueOf(cnt), 5, '0') + ".mp3";
				os = new FileOutputStream(outFile);
				System.out.println("文件" + outFile + "is writing...");
				bNewFile = false;
			}
			os.write(buf[idx]);
		}
		if (os != null) {
			os.close();
		}
		is.close();
		System.out.println("cnt=" + cnt);
	}
}
