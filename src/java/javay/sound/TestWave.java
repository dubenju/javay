package javay.sound;

import java.io.FileInputStream;
import java.io.InputStream;

public class TestWave {

	public static void main(String[] args) throws Exception {
		RiffChunkDescriptor riff = null;
		int bytesum = 0;
		int byteread = 0;
		 String in = "./classes/javay/sound/shangxinlei.wav";
		 InputStream inStream = new FileInputStream(in);

//		InputStream inStream = TestWave.class.getClassLoader().getResource("shangxinlei.wav");
		byte[] buffer = new byte[12];
		if ( (byteread = inStream.read(buffer)) == -1) {
			System.out.println("error");
			inStream.close();
			return ;
		}
		bytesum  += byteread;
		riff = new RiffChunkDescriptor(buffer);
		System.out.println("RiffChunk=" + riff);
		
		inStream.close();
	}

}
