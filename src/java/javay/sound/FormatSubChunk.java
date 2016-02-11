package javay.sound;

import javay.util.UBytes;
/**
 * 24 - 8 = 16
 * @author dubenju
 *
 */
public class FormatSubChunk {
	private byte[] id;   // 4ftm 
	private byte[] size; // 4
	private byte[] formatTag; // 2 编码方式，一般为0x0001。AudioFormatPCM = 1 (i.e. Linear quantization) Values other than 1 indicate some form of compression.
	private byte[] channels; // 2声道数目，1--单声道；2--双声道
	private long numChannel;
	private byte[] samplesPerSec; // 4采样频率
	private byte[] avgBytesPerSec; //4每秒所需字节数== samplesPerSec * channels * bitsPerSample/8
	private byte[] blockAlign; // 2数据块对齐单位(每个采样需要的字节数)== channels * bitsPerSample/8
	private long blockalign;
	private byte[] bitsPerSample; // 2每个采样需要的bit数
	private long bitsperSample;
	/* 一般情况下Size为16，此时最后附加信息没有；如果为18则最后多了2个字节的附加信息。 */
	private byte[] ExtraParamSize;// 2附加信息（可选，通过Size来判断有无）
	
	public FormatSubChunk(byte[] in) {
		this.id = new byte[4];
		this.size = new byte[4];
		this.formatTag = new byte[2];
		this.channels = new byte[2];
		this.samplesPerSec = new byte[4];
		this.avgBytesPerSec = new byte[4];
		this.blockAlign = new byte[2];
		this.bitsPerSample = new byte[2];
		System.arraycopy(in, 0, this.id, 0, 4);
		System.arraycopy(in, 4, this.size, 0, 4);
		System.arraycopy(in, 8, this.formatTag, 0, 2);
		System.arraycopy(in, 10, this.channels, 0, 2);
		System.arraycopy(in, 12, this.samplesPerSec, 0, 4);
		System.arraycopy(in, 16, this.avgBytesPerSec, 0, 4);
		System.arraycopy(in, 20, this.blockAlign, 0, 2);
		System.arraycopy(in, 22, this.bitsPerSample, 0, 2);
		
		this.numChannel = UBytes.toLong(this.channels, 1);
		this.blockalign = UBytes.toLong(this.blockAlign, 1);
		this.bitsperSample = UBytes.toLong(this.bitsPerSample, 1);
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
		buf.append(",formatTag:");
		buf.append(UBytes.toLong(this.formatTag, 1));
		buf.append(",音频通道数:");
		buf.append(UBytes.toLong(this.channels, 1));
		buf.append(",采样速率:");
		buf.append(UBytes.toLong(this.samplesPerSec, 1));
		buf.append(",avgBytesPerSec:");
		buf.append(UBytes.toLong(this.avgBytesPerSec, 1));
		buf.append(",blockAlign:");
		buf.append(UBytes.toLong(this.blockAlign, 1));
		buf.append(",位数／样本:");
		buf.append(UBytes.toLong(this.bitsPerSample, 1));
		return buf.toString();
	}

	/**
	 * @return the numChannel
	 */
	public long getNumChannel() {
		return numChannel;
	}

	/**
	 * @param numChannel the numChannel to set
	 */
	public void setNumChannel(long numChannel) {
		this.numChannel = numChannel;
	}

	/**
	 * @return the bitsperSample
	 */
	public long getBitsperSample() {
		return bitsperSample;
	}

	/**
	 * @param bitsperSample the bitsperSample to set
	 */
	public void setBitsperSample(long bitsperSample) {
		this.bitsperSample = bitsperSample;
	}
}
