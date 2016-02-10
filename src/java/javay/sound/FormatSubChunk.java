package javay.sound;

public class FormatSubChunk {
	private byte[] id; // 4ftm 
	private byte[] size; // 4
	private byte[] formatTag; // 2 编码方式，一般为0x0001
	private byte[] channels; // 2声道数目，1--单声道；2--双声道
	private byte[] samplesPerSec; // 4采样频率
	private byte[] avgBytesPerSec; //4每秒所需字节数
	private byte[] blockAlign; // 2数据块对齐单位(每个采样需要的字节数)
	private byte[] bitsPerSample; // 2每个采样需要的bit数
	/* 一般情况下Size为16，此时最后附加信息没有；如果为18则最后多了2个字节的附加信息。 */
	private byte[] add;// 2附加信息（可选，通过Size来判断有无）
}
