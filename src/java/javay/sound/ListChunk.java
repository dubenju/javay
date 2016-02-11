package javay.sound;

import javay.util.UBytes;

public class ListChunk {
	private byte[] id; // 4List
	private byte[] size; //4
	private long chunkSize;
	private byte[] listTypeId; //4
	private byte[] data;
	public ListChunk(byte[] in) {
		this.id = new byte[4];
		this.size = new byte[4];
		this.listTypeId = new byte[4];
		System.arraycopy(in, 0, this.id, 0, 4);
		System.arraycopy(in, 4, this.size, 0, 4);
		System.arraycopy(in, 8, this.listTypeId, 0, 4);
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
		buf.append(",ListTypeID:");
		buf.append(new String(this.listTypeId));
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
