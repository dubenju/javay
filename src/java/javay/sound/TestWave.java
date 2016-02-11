package javay.sound;

import java.io.FileInputStream;
import java.io.InputStream;

public class TestWave {

	public static void main(String[] args) throws Exception {
		RiffChunkDescriptor riff = null;
		FormatSubChunk format = null;
		DataSubChunk data = null;

		int bytesum = 0;
		int byteread = 0;
		String in = "./classes/javay/sound/shangxinlei.wav";
		InputStream inStream = new FileInputStream(in);
//		InputStream inStream = TestWave.class.getClassLoader().getResource("shangxinlei.wav");

		// RiffChunkDescriptor
		byte[] buffer = new byte[12];
		if ( (byteread = inStream.read(buffer)) == -1) {
			System.out.println("error");
			inStream.close();
			return ;
		}
		bytesum  += byteread;
		riff = new RiffChunkDescriptor(buffer);
		System.out.println("RiffChunk=" + riff);
		
		// FormatSubChunk
		buffer = new byte[24];
		if ( (byteread = inStream.read(buffer)) == -1) {
			System.out.println("error");
			inStream.close();
			return ;
		}
		bytesum  += byteread;
		format = new FormatSubChunk(buffer);
		System.out.println("FormatChunk=" + format);
		
		// DataSubChunk
		buffer = new byte[8];
		if ( (byteread = inStream.read(buffer)) == -1) {
			System.out.println("error");
			inStream.close();
			return ;
		}
		bytesum  += byteread;
		data = new DataSubChunk(buffer, format);
//		data.setFormat(format);
		System.out.println("DataChunk=" + data);

		inStream.close();
	}

}
