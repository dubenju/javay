package javay.sound;

import java.io.FileInputStream;
import java.io.InputStream;

import javay.util.UBytes;

public class TestMp3 {

	public static void main(String[] args) throws Exception {
		Id3v23Header id3header = null;
		Id3v23Frame id3frame = null;
		Mp3FrameHeader mp3header = null;
		Mp3FrameSideInfo mp3side = null;

		int bytesum = 1;
		int byteread = 0;
		String in = "./classes/javay/sound/test.mp3";
		in = "./out/01-Get Your Head Straight_noId3.mp3";
		in = "./out/01/f_00018.mp3";
		InputStream inStream = new FileInputStream(in);

		// ID3Header
		byte[] buffer = new byte[10];
		/*
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

			if (UBytes.isZero(buffer)) {
				// Padding
				buffer = new byte[(int) id3Size];
				if ( (byteread = inStream.read(buffer)) == -1) {
					System.out.println("read error Padding");
					inStream.close();
					System.out.println("read=" + bytesum);
					return ;
				}
				bytesum  += byteread;
				id3Size -= byteread;
				continue;
			}

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
		*/

		while (bytesum > 0) {
			// frame
			// MP3
			buffer = new byte[4];
			if ( (byteread = inStream.read(buffer)) == -1) {
				System.out.println("read error ID3Header");
				inStream.close();
				System.out.println("read=" + bytesum);
				return ;
			}
			bytesum  += byteread;
			mp3header = new Mp3FrameHeader(buffer);
			System.out.println("MP3Header=" + mp3header);
			int frameSize = mp3header.getSize();
			int crc = mp3header.getProtection();
			if (crc == 0) {
				buffer = new byte[2];
				if ( (byteread = inStream.read(buffer)) == -1) {
					System.out.println("read error ID3Header");
					inStream.close();
					System.out.println("read=" + bytesum);
					return ;
				}
				bytesum  += byteread;
				frameSize -= byteread;
				System.out.println(UBytes.toHexString(buffer));
			}
			// side information
			int channel = mp3header.getChannelMode();
			int version = mp3header.getVersion();
			buffer = null;
			if (3 != channel) {
				// Stereo
				if (version == 3) {
					// MPEG 1
					buffer = new byte[32];
				} 
				if (version == 0 || version == 2) {
					// MPEG 2/2.5 (LSF)
					buffer = new byte[17];
				}
			} else {
				// Mono
				if (version == 3) {
					// MPEG 1
					buffer = new byte[17];
				}
				if (version == 0 || version == 2) {
					// MPEG 2/2.5 (LSF)
					buffer = new byte[9];
				}
			}
			if ( (byteread = inStream.read(buffer)) == -1) {
				System.out.println("read error ID3Header");
				inStream.close();
				System.out.println("read=" + bytesum);
				return ;
			}
			bytesum  += byteread;
			frameSize -= byteread;
			System.out.println(UBytes.toHexString(buffer));
			mp3side = new Mp3FrameSideInfo(buffer);
			System.out.println("MP3Side=" + mp3side);

			if (frameSize > 0) {
				buffer = new byte[frameSize];
				if ( (byteread = inStream.read(buffer)) == -1) {
					System.out.println("read error ID3Header");
					inStream.close();
					System.out.println("read=" + bytesum);
					return ;
				}
				bytesum  += byteread;
			}
			// Frame
		}


		inStream.close();
		System.out.println("read=" + bytesum);
	}

}
