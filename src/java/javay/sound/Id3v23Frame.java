package javay.sound;

import java.io.UnsupportedEncodingException;

import javay.util.UBytes;

public class Id3v23Frame {
	private byte[] id; // 4
	private String frameId;
	private byte[] size; // 4
	private long dataSize;
	private byte[] flags; // 2
	private byte[] data;
	public Id3v23Frame(byte[] in) {
		this.id = new byte[4];
		this.size = new byte[4];
		this.flags = new byte[2];
		this.data = null;
		System.arraycopy(in, 0, this.id, 0, 4);
		System.arraycopy(in, 4, this.size, 0, 4);
		System.arraycopy(in, 8, this.flags, 0, 2);
		this.frameId = new String(this.id);
		this.dataSize = UBytes.toLong(this.size, 2);
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("Chunk ID:");
		buf.append(this.frameId);
		buf.append(",Size:");
		buf.append(UBytes.toLong(this.size, 2));
		buf.append(",Flags:");
		buf.append(UBytes.toHexString(this.flags));
		if (this.data != null) {
			buf.append(",Data:");
			if (this.frameId.startsWith("C") ) {
				// COMM
				// Text encoding 1byte
				// Language 3byte
				// Short content descrip. 4byte
				// The actual text
				byte[] tb = new byte[this.data.length - 8];
				System.arraycopy(this.data, 8, tb, 0, this.data.length - 8);
				System.out.println(UBytes.toHexString(tb));
				try {
					// System.out.println(new String(tb, "UTF-16BE"));
					String str = new String(tb, Id3v23FrameTextEncode.getName(this.data[0]));
					// System.out.println("str=" + str);
					buf.append(str);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			if (this.frameId.startsWith("T")) {
				byte[] tb = new byte[this.data.length - 1];
				System.arraycopy(this.data, 1, tb, 0, this.data.length - 1);
				try {
					String str = new String(tb, Id3v23FrameTextEncode.getName(this.data[0]));
					System.out.println("str=" + str);
					buf.append(str);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return buf.toString();
	}
	/**
	 * @return the dataSize
	 */
	public long getDataSize() {
		return dataSize;
	}
	/**
	 * @param dataSize the dataSize to set
	 */
	public void setDataSize(long dataSize) {
		this.dataSize = dataSize;
	}
	public void setData(byte[] in) {
		this.data = new byte[(int)dataSize];
		System.arraycopy(in, 0, this.data, 0, (int)dataSize);
	}
	/**
	 * @return the frameId
	 */
	public String getFrameId() {
		return frameId;
	}
	/**
	 * @param frameId the frameId to set
	 */
	public void setFrameId(String frameId) {
		this.frameId = frameId;
	}
}
