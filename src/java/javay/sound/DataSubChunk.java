package javay.sound;

import javay.util.UBytes;

public class DataSubChunk {
	private byte[] id; // 4data
	private byte[] size; //4 == NumSamples * channels * bitsPerSample/8
	private long chunkSize;
	private FormatSubChunk format;
	private long numSamples;
	private byte[] data; // 4
	
	public DataSubChunk(byte[] in, FormatSubChunk fmt) {
		this.id = new byte[4];
		this.size = new byte[4];
		System.arraycopy(in, 0, this.id, 0, 4);
		System.arraycopy(in, 4, this.size, 0, 4);
		this.format = fmt;
		this.chunkSize = UBytes.toLong(this.size, 1);
		this.numSamples = (this.chunkSize * 8) / (format.getNumChannel() * format.getBitsperSample());
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
		buf.append(",NumSamples:");
		buf.append(this.numSamples);
		return buf.toString();
	}

	/**
	 * @return the format
	 */
	public FormatSubChunk getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(FormatSubChunk format) {
		this.format = format;
	}
}
