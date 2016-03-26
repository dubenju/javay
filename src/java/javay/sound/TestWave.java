package javay.sound;

import java.io.FileInputStream;
import java.io.InputStream;

public class TestWave {

	public static void main(String[] args) throws Exception {
		RiffChunkDescriptor riff = null;
		FormatSubChunk format = null;
		DataSubChunk data = null;
		ListChunk list = null;
		ListChunkInfo info = null;
		Id3Chunk id3 = null;
		Id3v23Header id3header = null;
		Id3v23Frame id3frame = null;

		int bytesum = 0;
		int byteread = 0;
		String in = "./classes/javay/sound/shangxinlei.wav";
		// String in = "./classes/javay/sound/Windows Shutdown.wav";
		InputStream inStream = new FileInputStream(in);
//		InputStream inStream = TestWave.class.getClassLoader().getResource("shangxinlei.wav");

		// RiffChunkDescriptor
		byte[] buffer = new byte[12];
		if ( (byteread = inStream.read(buffer)) == -1) {
			System.out.println("read error RiffChunk");
			inStream.close();
			System.out.println("read=" + bytesum);
			return ;
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
			return ;
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
			return ;
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
			return ;
		}
		bytesum  += byteread;
		

		// 播放
		PlayWave.print((float) (format.getSampleRate() * 1.0), (int) format.getBitsperSample(), (int) format.getNumChannel(), true, false, buffer);

		// ListChunk,没有的时候？？
		buffer = new byte[12];
		if ( (byteread = inStream.read(buffer)) == -1) {
			System.out.println("read error ListChunk");
			inStream.close();
			System.out.println("read=" + bytesum);
			return ;
		}
		bytesum  += byteread;
		list = new ListChunk(buffer);
		System.out.println("ListChunk=" + list);

		long listChunkSize = list.getChunkSize();
		System.out.println("size=" + listChunkSize);
		listChunkSize -= 4;
		while (listChunkSize > 0) {
			// InfoChunk
			buffer = new byte[8];
			if ( (byteread = inStream.read(buffer)) == -1) {
				System.out.println("read error InfoChunk");
				inStream.close();
				System.out.println("read=" + bytesum);
				return ;
			}
			bytesum  += byteread;
			listChunkSize -= byteread;
			info = new ListChunkInfo(buffer);
			System.out.println("ListChunkInfo=" + info);
			buffer = new byte[(int)info.getTextSize()];
			if ( (byteread = inStream.read(buffer)) == -1) {
				System.out.println("read error InfoChunk Text");
				inStream.close();
				System.out.println("read=" + bytesum);
				return ;
			}
			bytesum  += byteread;
			listChunkSize -= byteread;
			info.setText(buffer);
			System.out.println("ListChunkInfo=" + info);
		}
		
		// ID3Chunk
		buffer = new byte[8];
		if ( (byteread = inStream.read(buffer)) == -1) {
			System.out.println("read error ID3Chunk");
			inStream.close();
			System.out.println("read=" + bytesum);
			return ;
		}
		bytesum  += byteread;
		id3 = new Id3Chunk(buffer);
		System.out.println("ID3Chunk=" + id3);
		
		// ID3Header
		buffer = new byte[10];
		if ( (byteread = inStream.read(buffer)) == -1) {
			System.out.println("read error ID3Header");
			inStream.close();
			System.out.println("read=" + bytesum);
			return ;
		}
		bytesum  += byteread;
		id3header = new Id3v23Header(buffer);
		System.out.println("ID3Header=" + id3header);
		long id3Size = id3header.getId3Size();
		while(id3Size > 0) {
			buffer = new byte[10];
			if ( (byteread = inStream.read(buffer)) == -1) {
				System.out.println("read error ID3Header");
				inStream.close();
				System.out.println("read=" + bytesum);
				return ;
			}
			bytesum  += byteread;
			id3Size -= byteread;
			id3frame = new Id3v23Frame(buffer);
			System.out.println("ID3Frame=" + id3frame);
			buffer = new byte[(int)id3frame.getDataSize()];
			if ( (byteread = inStream.read(buffer)) == -1) {
				System.out.println("read error ID3Frame");
				inStream.close();
				System.out.println("read=" + bytesum);
				return ;
			}
			bytesum  += byteread;
			id3Size -= byteread;
			id3frame.setData(buffer);
			System.out.println("ID3Frame=" + id3frame);
			
		}
		
		inStream.close();
		System.out.println("read=" + bytesum);
	}

}
