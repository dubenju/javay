package javay.sound;

import javay.util.UBytes;

/**
 * AAAAAAAA AAABBCCD EEEEFFGH IIJJKLMM
 * @author dubenju
 *
 */
public class Mp3FrameHeader {
	private byte[] head;
	private int sync; // 11
	private int version; // 2
	private int layer; // 2
	/* 0 - Protected by CRC (16bit CRC follows header) */
	private int protection; // 1

	private int bitrate; // 4
	private int frequency; // 2
	private int padding; // 1
	private int privatebit; // 1

	private int channelMode; // 2
	private int modeExtension; // 2
	private int copyright; // 1
	private int original; // 1
	private int emphasis; // 2

	public Mp3FrameHeader(byte[] in) {
		this.head = new byte[4];
		System.arraycopy(in, 0, head, 0, 4);
		this.sync = ((this.head[0] << 3) | (this.head[1] & 0xE0) >>> 5);
		this.version = (this.head[1] & 0x18) >>> 3;
		this.layer = (this.head[1] & 0x06) >>> 1;
		this.protection = this.head[1] & 0x01;

		this.channelMode = (this.head[3] & 0xC0) >>> 6;
		this.modeExtension = (this.head[3] & 0x30) >>> 4;
		this.copyright = (this.head[3] & 0x08) >>> 3;
		this.original = (this.head[3] & 0x04) >>> 2;
		this.emphasis = (this.head[3] & 0x03);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		System.out.println(UBytes.toHexString(this.head));
		StringBuffer buf = new StringBuffer();
		buf.append("sync:");
		buf.append(this.sync);
		buf.append(",version:");
		buf.append(this.version);
		buf.append(",layer:");
		buf.append(this.layer);
		buf.append(",protection:");
		buf.append(this.protection);
		
		buf.append(",channelMode:");
		buf.append(this.channelMode);
		buf.append(",modeExtension:");
		buf.append(this.modeExtension);
		buf.append(",copyright:");
		buf.append(this.copyright);
		buf.append(",original:");
		buf.append(this.original);
		buf.append(",emphasis:");
		buf.append(this.emphasis);
		return buf.toString();
	}

	/**
	 * @return the protection
	 */
	public int getProtection() {
		return protection;
	}

	/**
	 * @param protection the protection to set
	 */
	public void setProtection(int protection) {
		this.protection = protection;
	}

	/**
	 * @return the channelMode
	 */
	public int getChannelMode() {
		return channelMode;
	}

	/**
	 * @param channelMode the channelMode to set
	 */
	public void setChannelMode(int channelMode) {
		this.channelMode = channelMode;
	}
}
