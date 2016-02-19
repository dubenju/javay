package javay.sound;

import java.util.HashMap;

import javay.util.UBytes;

/**
 * AAAAAAAA AAABBCCD EEEEFFGH IIJJKLMM
 * @author dubenju
 *
 */
public class Mp3FrameHeader {
	private byte[] head;
	private int sync; // 11//同步信息
	/**
	 * 0:MPEG Version 2.5
	 * 1:MPEG Version reserved
	 * 2:MPEG Version 2
	 * 3:MPEG Version 1
	 */
	private int version; // 2//版本
	public static String[] versName = {"MPEG Version 2.5", "MPEG Version reserved", "MPEG Version 2", "MPEG Version 1"};
	private int layer; // 2//层
	public static String[] laysName = {"Layer reserved", "Layer III", "Layer II", "Layer I"};
	/* 0 - Protected by CRC (16bit CRC follows header) */
	private int protection; // 1// CRC校验
	public static String[] procName = {"Protected by CRC", "Not protected"};

	private int bitrate; // 4//位率
	public static HashMap<String, String> bitrateIndex = new HashMap<String, String>();
	private int frequency; // 2//采样频率
	public static HashMap<String, String> frequencyIndex = new HashMap<String, String>();
	private int padding; // 1//帧长调节
	public static String[] paddName = {"frame is not padded", "frame is padded with one extra bit"};
	private int privatebit; // 1//保留字

	/**
	 * 0:Stereo
	 * 1:Joint stereo (Stereo)
	 * 2:Dual channel (Stereo)
	 * 3:Single channel (Mono)
	 */
	private int channelMode; // 2//声道模式
	public static String[] modeName = {"Stereo", "Joint stereo (Stereo)", "Dual channel (Stereo)", "Single channel (Mono)"};
	private int modeExtension; // 2//扩充模式
	public static String[] modExtName = {"Intensity stereo:Off,MS stereo:Off", "Intensity stereo:On,MS stereo:Off", "Intensity stereo:Off,MS stereo:On", "Intensity stereo:On,MS stereo:On"};
	private int copyright; // 1// 版权
	public static String[] rightName = {"无版权", "版权"};
	private int original; // 1//原版标志
	public static String[] origName = {"复制品", "原版"};
	private int emphasis; // 2//强调模式
	public static String[] emphName = {"none", "50/15 ms", "reserved", "CCIT J.17"};

	static {
		// ver layer Bitrate   kbps
		// "free" 表示位率可变
		bitrateIndex.put("1_1_0", "free");
		bitrateIndex.put("1_2_0", "free");
		bitrateIndex.put("1_3_0", "free");
		bitrateIndex.put("2_1_0", "free");
		bitrateIndex.put("2_2_0", "free");
		bitrateIndex.put("2_3_0", "free");

		bitrateIndex.put("1_1_1", "32");
		bitrateIndex.put("1_2_1", "32");
		bitrateIndex.put("1_3_1", "32");
		bitrateIndex.put("2_1_1", "32");
		bitrateIndex.put("2_2_1", "32");
		bitrateIndex.put("2_3_1", "8");
		
		bitrateIndex.put("1_1_2", "64");
		bitrateIndex.put("1_2_2", "48");
		bitrateIndex.put("1_3_2", "40");
		bitrateIndex.put("2_1_2", "64");
		bitrateIndex.put("2_2_2", "48");
		bitrateIndex.put("2_3_2", "16");
		
		bitrateIndex.put("1_1_3", "96");
		bitrateIndex.put("1_2_3", "56");
		bitrateIndex.put("1_3_3", "48");
		bitrateIndex.put("2_1_3", "96");
		bitrateIndex.put("2_2_3", "56");
		bitrateIndex.put("2_3_3", "24");
		
		bitrateIndex.put("1_1_4", "128");
		bitrateIndex.put("1_2_4", "64");
		bitrateIndex.put("1_3_4", "56");
		bitrateIndex.put("2_1_4", "128");
		bitrateIndex.put("2_2_4", "64");
		bitrateIndex.put("2_3_4", "32");

		
		bitrateIndex.put("1_1_5", "160");
		bitrateIndex.put("1_2_5", "80");
		bitrateIndex.put("1_3_5", "64");
		bitrateIndex.put("2_1_5", "160");
		bitrateIndex.put("2_2_5", "80");
		bitrateIndex.put("2_3_5", "64");
		
		bitrateIndex.put("1_1_6", "192");
		bitrateIndex.put("1_2_6", "96");
		bitrateIndex.put("1_3_6", "80");
		bitrateIndex.put("2_1_6", "192");
		bitrateIndex.put("2_2_6", "96");
		bitrateIndex.put("2_3_6", "80");
		
		bitrateIndex.put("1_1_7", "224");
		bitrateIndex.put("1_2_7", "112");
		bitrateIndex.put("1_3_7", "40");
		bitrateIndex.put("2_1_7", "96");
		bitrateIndex.put("2_2_7", "112");
		bitrateIndex.put("2_3_7", "56");
		
		bitrateIndex.put("1_1_8", "256");
		bitrateIndex.put("1_2_8", "128");
		bitrateIndex.put("1_3_8", "112");
		bitrateIndex.put("2_1_8", "256");
		bitrateIndex.put("2_2_8", "128");
		bitrateIndex.put("2_3_8", "64");
		
		bitrateIndex.put("1_1_9", "288");
		bitrateIndex.put("1_2_9", "160");
		bitrateIndex.put("1_3_9", "128");
		bitrateIndex.put("2_1_9", "288");
		bitrateIndex.put("2_2_9", "160");
		bitrateIndex.put("2_3_9", "128");

		
		bitrateIndex.put("1_1_10", "320");
		bitrateIndex.put("1_2_10", "192");
		bitrateIndex.put("1_3_10", "160");
		bitrateIndex.put("2_1_10", "320");
		bitrateIndex.put("2_2_10", "192");
		bitrateIndex.put("2_3_10", "160");
		
		bitrateIndex.put("1_1_11", "352");
		bitrateIndex.put("1_2_11", "224");
		bitrateIndex.put("1_3_11", "192");
		bitrateIndex.put("2_1_11", "352");
		bitrateIndex.put("2_2_11", "224");
		bitrateIndex.put("2_3_11", "112");
		
		bitrateIndex.put("1_1_12", "384");
		bitrateIndex.put("1_2_12", "256");
		bitrateIndex.put("1_3_12", "224");
		bitrateIndex.put("2_1_12", "384");
		bitrateIndex.put("2_2_12", "256");
		bitrateIndex.put("2_3_12", "128");
		
		bitrateIndex.put("1_1_13", "416");
		bitrateIndex.put("1_2_13", "320");
		bitrateIndex.put("1_3_13", "256");
		bitrateIndex.put("2_1_13", "416");
		bitrateIndex.put("2_2_13", "320");
		bitrateIndex.put("2_3_13", "256");
		
		bitrateIndex.put("1_1_14", "448");
		bitrateIndex.put("1_2_14", "384");
		bitrateIndex.put("1_3_14", "320");
		bitrateIndex.put("2_1_14", "448");
		bitrateIndex.put("2_2_14", "384");
		bitrateIndex.put("2_3_14", "320");

		bitrateIndex.put("1_1_14", "bad");
		bitrateIndex.put("1_2_14", "bad");
		bitrateIndex.put("1_3_14", "bad");
		bitrateIndex.put("2_1_14", "bad");
		bitrateIndex.put("2_2_14", "bad");
		bitrateIndex.put("2_3_14", "bad");
		//  "bad"  表示不允许值

		// ver val
		frequencyIndex.put("0_0", "11025");
		frequencyIndex.put("2_0", "22050");
		frequencyIndex.put("3_0", "44100");
		
		frequencyIndex.put("0_1", "12000");
		frequencyIndex.put("2_1", "24000");
		frequencyIndex.put("3_1", "48000");
		
		frequencyIndex.put("0_2", "8000");
		frequencyIndex.put("2_2", "16000");
		frequencyIndex.put("3_2", "32000");
		
		frequencyIndex.put("0_3", "reserv");
		frequencyIndex.put("2_3", "reserv");
		frequencyIndex.put("3_3", "reserv");
	}
	public Mp3FrameHeader(byte[] in) {
		this.head = new byte[4];
		System.arraycopy(in, 0, head, 0, 4);
		this.sync = ((this.head[0] << 3) | (this.head[1] & 0xE0) >>> 5);
		this.version = (this.head[1] & 0x18) >>> 3;
		this.layer = (this.head[1] & 0x06) >>> 1;
		this.protection = this.head[1] & 0x01;

		this.bitrate = (this.head[2] & 0xF0) >>> 4;
		this.frequency = (this.head[2] & 0x0C) >>> 2;
		this.padding = (this.head[2] & 0x02) >>> 1;
		this.privatebit = (this.head[2] & 0x01);
		
		this.channelMode = (this.head[3] & 0xC0) >>> 6;
		this.modeExtension = (this.head[3] & 0x30) >>> 4;
		this.copyright = (this.head[3] & 0x08) >>> 3;
		this.original = (this.head[3] & 0x04) >>> 2;
		this.emphasis = (this.head[3] & 0x03);
	}

	/**
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
		buf.append(":");
		buf.append(versName[this.version]);
		buf.append(",layer:");
		buf.append(this.layer);
		buf.append(":");
		buf.append(laysName[this.layer]);
		buf.append(",protection:");
		buf.append(this.protection);
		buf.append(":");
		buf.append(procName[this.protection]);
		buf.append("\n");
		
		buf.append(",bitrate:");
		buf.append(this.bitrate);
		String ver = "";
		if (this.version == 3) {
			ver = "1";
		}
		if (this.version == 0 || this.version == 2) {
			ver = "2";
		}
		String k = ver + "_" + this.layer + "_" + this.bitrate;
		buf.append(bitrateIndex.get(k));
		buf.append(",frequency:");
		buf.append(this.frequency);
		k = this.version + "_" + this.frequency;
		buf.append(frequencyIndex.get(k));
		buf.append("\n");
		buf.append(",padding:");
		buf.append(paddName[this.padding]);
		buf.append(":");
		buf.append(this.padding);
		buf.append(",privatebit:");
		buf.append(this.privatebit);

		buf.append(",channelMode:");
		buf.append(this.channelMode);
		buf.append(":");
		buf.append(modeName[this.channelMode]);
		buf.append(",modeExtension:");
		buf.append(this.modeExtension);
		buf.append(":");
		buf.append(modExtName[this.modeExtension]);
		buf.append(",copyright:");
		buf.append(this.copyright);
		buf.append(":");
		buf.append(rightName[this.copyright]);
		buf.append(",original:");
		buf.append(this.original);
		buf.append(":");
		buf.append(origName[this.original]);
		buf.append(",emphasis:");
		buf.append(this.emphasis);
		buf.append(":");
		buf.append(emphName[this.emphasis]);
		buf.append(",frame size:");
		buf.append(this.getSize());
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

	/**
	 *
	 * 0:Stereo
	 * 1:Joint stereo (Stereo)
	 * 2:Dual channel (Stereo)
	 * 3:Single channel (Mono)
	 *
	 * @return the channelMode
	 */
	public int getChannelMode() {
		return channelMode;
	}

	/**
	 * @param channelMode the channelMode to set
	 */
	public void setChannelMode(int channelMode) {
		this.channelMode = channelMode;
	}
	
	/**
	 * MP3帧长取决于位率和频率，计算公式为：
. mpeg1.0 layer1 : 帧长= (48000*bitrate)/sampling_freq + padding
         layer2&3: 帧长= (144000*bitrate)/sampling_freq + padding
. mpeg2.0 layer1 : 帧长= (24000*bitrate)/sampling_freq + padding
        layer2&3 : 帧长= (72000*bitrate)/sampling_freq + padding
根据公式，位率为128kbps，采样频率为44.1kHz，padding（帧长调节）为0时，帧长为417字节。
	 * FrameSize = (((MpegVersion == MPEG1 ? 144 : 72) * Bitrate) / SamplingRate) + PaddingBit
	 * @return
	 */
	public int getSize() {
		int a = this.layer == 3 ? (this.version == 3 ? 48 : 24) : (this.version == 3 ? 144 : 72);
		String ver = "";
		if (this.version == 3) {
			ver = "1";
		}
		if (this.version == 0 || this.version == 2) {
			ver = "2";
		}
		String k = ver + "_" + this.layer + "_" + this.bitrate;
		String strBitrate = bitrateIndex.get(k);
		int bitrate = 0;
		if (!"free".equals(strBitrate) && !"bad".equals(strBitrate)) {
			bitrate = Integer.valueOf(strBitrate) * 1000;
		}
		k = this.version + "_" + this.frequency;
		String strSamplingRate = frequencyIndex.get(k);
		int samplingRate = 0;
		if (!"reserv".equals(strSamplingRate)) {
			samplingRate = Integer.valueOf(strSamplingRate);
		}
		int frameSize = a * bitrate / samplingRate + this.padding;
		System.out.println("a=" + a);
		System.out.println("bitrate=" + bitrate);
		System.out.println("samplingRate=" + samplingRate);
		System.out.println("padding=" + padding);
		System.out.println("frameSize=" + frameSize);
		return frameSize;
	}

	/**
	 * 0:MPEG Version 2.5
	 * 1:MPEG Version reserved
	 * 2:MPEG Version 2
	 * 3:MPEG Version 1
	 * 
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}
}
