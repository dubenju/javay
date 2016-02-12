package javay.sound;

import java.io.FileInputStream;
import java.io.InputStream;

public class TestMp3 {

	public static void main(String[] args) throws Exception {
		Id3v23Header id3header = null;
		Id3v23Frame id3frame = null;
		
		int bytesum = 0;
		int byteread = 0;
		String in = "./classes/javay/sound/test.mp3";
		InputStream inStream = new FileInputStream(in);
		
		// ID3Header
		byte[] buffer = new byte[10];
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
				System.out.println("read error ID3Frame");
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
			if ("APIC".equals(id3frame.getFrameId())) {
				System.out.println("ID3Frame=" + id3frame);
			} else {
				id3frame.setData(buffer);
				System.out.println("ID3Frame=" + id3frame);
			}
			System.out.println("id3Size=" + id3Size);
		}
		
		inStream.close();
		System.out.println("read=" + bytesum);
	}

}
