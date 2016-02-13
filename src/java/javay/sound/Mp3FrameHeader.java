package javay.sound;

import javay.util.UBytes;

/**
 * AAAAAAAA AAABBCCD EEEEFFGH IIJJKLMM
 * @author dubenju
 *
 */
public class Mp3FrameHeader {
	private byte[] head;
	private int sync;
	private int version;
	private int layer;
	/* 0 - Protected by CRC (16bit CRC follows header) */
	private int protection;
	private int bitrate;
	private int frequency;
	private int padding;
	private int privatebit;
	private int channelMode;
	private int modeExtension;
	private int copyright;
	private int original;
	private int emphasis;

	public Mp3FrameHeader(byte[] in) {
		this.head = new byte[4];
		System.arraycopy(in, 0, head, 0, 4);
		this.sync = ((this.head[0] << 3) | (this.head[1] & 0xE0) >>> 5);
		this.version = (this.head[1] & 0x18) >>> 3;
		this.layer = (this.head[1] & 0x06) >>> 1;
		this.protection = this.head[1] & 0x01;
	}

	/* (non-Javadoc)
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
}
