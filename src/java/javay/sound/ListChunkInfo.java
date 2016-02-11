package javay.sound;

import javay.util.UBytes;

public class ListChunkInfo {
	private byte[] id; // 4List
	private byte[] size; //4
	private long textSize;
	private byte[] text;
	public ListChunkInfo(byte[] in) {
		this.id = new byte[4];
		this.size = new byte[4];
		this.text = null;
		System.arraycopy(in, 0, this.id, 0, 4);
		System.arraycopy(in, 4, this.size, 0, 4);
		this.textSize = UBytes.toLong(this.size, 1);
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("Chunk ID:");
		buf.append(new String(this.id));
		buf.append(",Size:");
		buf.append(UBytes.toLong(this.size, 1));
		if (this.text != null) {
			buf.append(",Text:");
			buf.append(new String(this.text));
		}
		return buf.toString();
	}
	/**
	 * @return the textSize
	 */
	public long getTextSize() {
		// All text must be word aligned.
		if (this.textSize % 2 != 0) {
			return ((this.textSize / 2 ) + 1) * 2;
		}
		return textSize;
	}
	/**
	 * @param textSize the textSize to set
	 */
	public void setTextSize(long textSize) {
		this.textSize = textSize;
	}
	public void setText(byte[] in) {
		this.text = new byte[(int)textSize];
		System.arraycopy(in, 0, this.text, 0, (int)textSize);
	}
}
