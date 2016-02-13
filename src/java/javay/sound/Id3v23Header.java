package javay.sound;

import javay.util.UBytes;

public class Id3v23Header {
	private byte[] id;//3
	private byte[] ver; // 2
	private byte[] flags; //1
	private byte[] size; // 4
	private long id3Size;
	public Id3v23Header(byte[] in) {
		this.id = new byte[3];
		this.ver = new byte[2];
		this.flags = new byte[1];
		this.size = new byte[4];
		System.arraycopy(in, 0, this.id, 0, 3);
		System.arraycopy(in, 3, this.ver, 0, 2);
		System.arraycopy(in, 5, this.flags, 0, 1);
		System.arraycopy(in, 6, this.size, 0, 4);
		this.id3Size = getValue(this.size);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("Chunk ID:");
		buf.append(new String(this.id));
		buf.append(",Version:");
		buf.append(UBytes.toLong(this.ver, 1));
		buf.append(",Flags:");
		buf.append(UBytes.toHexString(this.flags));
		buf.append(",Size:");
		// buf.append(UBytes.toLong(this.size, 2));
		buf.append(getValue(this.size));
		return buf.toString();
	}
	/**
	 * @return the id3Size
	 */
	public long getId3Size() {
		return id3Size;
	}
	/**
	 * @param id3Size the id3Size to set
	 */
	public void setId3Size(long id3Size) {
		this.id3Size = id3Size;
	}

	public static int getValue(byte[] in) {
		int res = ( ( in[0] & 0x7F ) << 21 )
				+ ( ( in[1] & 0x7F ) << 14 )
				+ ( ( in[2] & 0x7F ) << 7 )
				+ ( ( in[3] & 0x7F ) << 0 );
		return res;
	}
}
