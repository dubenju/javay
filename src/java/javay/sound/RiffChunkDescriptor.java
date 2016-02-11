package javay.sound;

import javay.util.UBytes;

/**
 * file size - 8
 * @author dubenju
 *
 */
public class RiffChunkDescriptor {
	private byte[] chunkId; // 4RIFF
	/* fileSize - 8*/
	private byte[] chunkSize; // 4
	private byte[] format; // 4WAVE
	
	public RiffChunkDescriptor(byte[] in) {
		this.chunkId = new byte[4];
		this.chunkSize = new byte[4];
		this.format = new byte[4];
		System.arraycopy(in, 0, this.chunkId, 0, 4);
		System.arraycopy(in, 4, this.chunkSize, 0, 4);
		System.arraycopy(in, 8, this.format, 0, 4);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("Chunk ID:");
		buf.append(new String(this.chunkId));
		buf.append(",Size:");
		buf.append(UBytes.toLong(this.chunkSize, 1));
//		System.out.println(UBytes.toHexString(this.chunkSize));
//		System.out.println(UBytes.toLong(this.chunkSize, 1));
		buf.append(",Format:");
		buf.append(new String(this.format));
		return buf.toString();
	}
}
