package javay.sound;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UWave {
    public static int[] getData(String in) throws IOException {
		RiffChunkDescriptor riff = null;
		FormatSubChunk format = null;
		DataSubChunk data = null;
//		ListChunk list = null;
//		ListChunkInfo info = null;
//		Id3Chunk id3 = null;
//		Id3v23Header id3header = null;
//		Id3v23Frame id3frame = null;

		int bytesum = 0;
		int byteread = 0;
//		String in = "./classes/javay/sound/shangxinlei.wav";
		// String in = "./classes/javay/sound/Windows Shutdown.wav";
		InputStream inStream = new FileInputStream(in);
//		InputStream inStream = TestWave.class.getClassLoader().getResource("shangxinlei.wav");

		// RiffChunkDescriptor
		byte[] buffer = new byte[12];
		if ( (byteread = inStream.read(buffer)) == -1) {
			System.out.println("read error RiffChunk");
			inStream.close();
			System.out.println("read=" + bytesum);
			return null;
		}
		bytesum  += byteread;
		riff = new RiffChunkDescriptor(buffer);
		System.out.println("RiffChunk=" + riff);
		
		// FormatSubChunk
		buffer = new byte[24];
		if ( (byteread = inStream.read(buffer)) == -1) {
			System.out.println("read error FormatChunk");
			inStream.close();
			System.out.println("read=" + bytesum);
			return null;
		}
		bytesum  += byteread;
		format = new FormatSubChunk(buffer);
		System.out.println("FormatChunk=" + format);
		
		// DataSubChunk
		buffer = new byte[8];
		if ( (byteread = inStream.read(buffer)) == -1) {
			System.out.println("read error DataChunk");
			inStream.close();
			System.out.println("read=" + bytesum);
			return null;
		}
		bytesum  += byteread;
		data = new DataSubChunk(buffer, format);
//		data.setFormat(format);
		System.out.println("DataChunk=" + data);
		
		// data
		buffer = new byte[(int) data.getChunkSize()];
		if ( (byteread = inStream.read(buffer)) == -1) {
			System.out.println("read error data");
			inStream.close();
			System.out.println("read=" + bytesum);
			return null;
		}
		bytesum  += byteread;

//		// 播放
//		PlayWave.play((float) (format.getSampleRate() * 1.0), (int) format.getBitsperSample(), (int) format.getNumChannel(), true, false, buffer);

		int[] res = PlayWave.b2i((float) (format.getSampleRate() * 1.0), (int) format.getBitsperSample(), (int) format.getNumChannel(), true, false, buffer, 1);
		inStream.close();
		System.out.println("read=" + bytesum);

		return res;
    }
}
