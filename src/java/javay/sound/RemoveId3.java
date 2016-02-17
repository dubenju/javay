/**
 *
 */
package javay.sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author DBJ
 *
 */
public class RemoveId3 {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String str = "./in/01-Get Your Head Straight.mp3";
		remove(str);

	}

	public static void remove(String strFileName) throws IOException {
		File file = new File(strFileName);
		String fileName = file.getName();
		long fileSize = file.length();
		System.out.println("文件:" + strFileName +  "大小:" + fileSize);

		InputStream is = new FileInputStream(strFileName);

		Id3v23Header id3header = null;
		int bytesum = 0;
		int byteread = 0;
		byte[] buffer = new byte[10];
		if ( (byteread = is.read(buffer)) == -1) {
			System.out.println("read error ID3Header");
			is.close();
			System.out.println("read=" + bytesum);
			return ;
		}
		bytesum  += byteread;
		fileSize -= byteread;
		id3header = new Id3v23Header(buffer);
		System.out.println("ID3Header=" + id3header);
		long id3Size = id3header.getId3Size();

		buffer = new byte[(int) id3Size];
		if ( (byteread = is.read(buffer)) == -1) {
			System.out.println("read error ID3");
			is.close();
			System.out.println("read=" + bytesum);
			return ;
		}
		bytesum  += byteread;
		fileSize -= byteread;

		fileSize -= 128; // ID3v1
		OutputStream os = new FileOutputStream("./out/" + fileName.substring(0, fileName.indexOf(".")) + "_noId3.mp3");

		while (fileSize > 0) {
			buffer = new byte[4096];
			if (fileSize < 4096) {
				buffer = new byte[(int) fileSize];
			}
			if ( (byteread = is.read(buffer)) == -1) {
				System.out.println("read error mp3");
				os.close();
				is.close();
				System.out.println("read=" + bytesum);
				return ;
			}

			bytesum  += byteread;
			fileSize -= byteread;
			byte[] outbuf = new byte[byteread];
			System.arraycopy(buffer, 0, outbuf, 0, byteread);
			os.write(outbuf);
		}
		os.close();
		is.close();

	}
}
