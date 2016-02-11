package javay.sound;

import javay.util.UBytes;

public class Id3Chunk {
	private byte[] id; // 4List
	private byte[] size; //4
	private long chunkSize;
	public Id3Chunk(byte[] in) {
		this.id = new byte[4];
		this.size = new byte[4];
		System.arraycopy(in, 0, this.id, 0, 4);
		System.arraycopy(in, 4, this.size, 0, 4);
		this.chunkSize = UBytes.toLong(this.size, 1);
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
		return buf.toString();
	}
	/**
	 * @return the chunkSize
	 */
	public long getChunkSize() {
		return chunkSize;
	}
	/**
	 * @param chunkSize the chunkSize to set
	 */
	public void setChunkSize(long chunkSize) {
		this.chunkSize = chunkSize;
	}
}
