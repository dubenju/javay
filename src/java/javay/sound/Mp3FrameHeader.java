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
    public static final String[] versName = {"MPEG Version 2.5", "MPEG Version reserved保留", "MPEG Version 2 （ISO/IEC 13818-3）", "MPEG Version 1（ISO/IEC 11172-3）"};
    private int layer; // 2//层
    public static final String[] laysName = {"Layer reserved保留", "Layer III", "Layer II", "Layer I"};
    /* 0 - Protected by CRC (16bit CRC follows header) */
    private int protection; // 1// CRC校验
    public static final String[] procName = {"Protected by CRC紧跟帧头后有16位即2个字节用作CRC校验", "Not protected没有校验"};

    private int bitrate; // 4//位率
    public static final HashMap<String, String> bitrateIndex = new HashMap<String, String>();
    private int frequency; // 2//采样频率
    public static final HashMap<String, String> frequencyIndex = new HashMap<String, String>();
    private int padding; // 1//帧长调节
    public static final String[] paddName = {"frame is not padded没有填充", "frame is padded with one extra bit填充了一个额外的空位"};
    private int privatebit; // 1//保留字

    /**
     * 0:Stereo
     * 1:Joint stereo (Stereo)
     * 2:Dual channel (Stereo)
     * 3:Single channel (Mono)
     */
    private int channelMode; // 2//声道模式
    public static final String[] modeName = {"Stereo立体声", "Joint stereo (Stereo)联合立体声（立体声）", "Dual channel (Stereo)双声道（立体声）", "Single channel (Mono)单声道（单声）"};
    private int modeExtension; // 2//扩充模式
    public static final String[] modExtName = {"Intensity stereo:Off,MS stereo:Off", "Intensity stereo:On,MS stereo:Off", "Intensity stereo:Off,MS stereo:On", "Intensity stereo:On,MS stereo:On"};
    private int copyright; // 1// 版权
    public static final String[] rightName = {"无版权", "有版权"};
    private int original; // 1//原版标志
    public static final String[] origName = {"复制品", "原版原创"};
    private int emphasis; // 2//强调模式
    public static final String[] emphName = {"none无", "50/15 ms", "reserved保留", "CCIT J.17"};

    public static final HashMap<String, String> framesizeIndex = new HashMap<String, String>();
    static {
        /*
         * 索引值    MPEG 1            MPEG 2, 2.5 (LSF)
        LayerILayer IILayer III    Layer I    Layer II & III
0000    Free
0001     32     32     32     32     8
0010     64     48     40     48     16
0011     96     56     48     56     24
0100    128     64     56     64     32
0101    160     80     64     80     40
0110    192     96     80     96     48
0111    224    112     96    112     56
1000    256    128    112    128     64
1001    288    160    128    144     80
1010    320    192    160    160     96
1011    352    224    192    176    112
1100    384    256    224    192    128
1101    416    320    256    224    144
1110    448    384    320    256    160
1111    Bad
         */
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

        bitrateIndex.put("1_1_15", "bad");
        bitrateIndex.put("1_2_15", "bad");
        bitrateIndex.put("1_3_15", "bad");
        bitrateIndex.put("2_1_15", "bad");
        bitrateIndex.put("2_2_15", "bad");
        bitrateIndex.put("2_3_15", "bad");
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

        // ver lay val
        framesizeIndex.put("0_3", "384");
        framesizeIndex.put("0_2", "384");
        framesizeIndex.put("0_1", "384");
        framesizeIndex.put("2_3", "1152");
        framesizeIndex.put("2_2", "1152");
        framesizeIndex.put("2_1", "1152");
        framesizeIndex.put("3_3", "1152");
        framesizeIndex.put("3_2", "576");
        framesizeIndex.put("3_1", "576");
    }
    public Mp3FrameHeader(byte[] in) {
        this.head          = new byte[4];
        System.arraycopy(in, 0, head, 0, 4);
        // 1111 1111 111x xxxx
        this.sync          = ((this.head[0] << 3) | (this.head[1] & 0xE0) >>> 5);
        this.version       = (this.head[1] & 0x18) >>> 3;
        this.layer         = (this.head[1] & 0x06) >>> 1;
        this.protection    = this.head[1] & 0x01;

        this.bitrate       = (this.head[2] & 0xF0) >>> 4;
        this.frequency     = (this.head[2] & 0x0C) >>> 2;
        this.padding       = (this.head[2] & 0x02) >>> 1;
        this.privatebit    = (this.head[2] & 0x01);

        this.channelMode   = (this.head[3] & 0xC0) >>> 6;
        this.modeExtension = (this.head[3] & 0x30) >>> 4;
        this.copyright     = (this.head[3] & 0x08) >>> 3;
        this.original      = (this.head[3] & 0x04) >>> 2;
        this.emphasis      = (this.head[3] & 0x03);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        System.out.println(UBytes.toHexString(this.head));
        StringBuffer buf = new StringBuffer();
        buf.append("\n----- mp3 frame header -----\n");
        buf.append("  sync(帧同步（所有位置1）):");
        buf.append(this.sync);
        buf.append("\n" + Integer.toHexString(this.sync));

        buf.append(",\n★version(MPEG 音频版本(MPEG-1,2等)MPEG 音频版本ID):");
        buf.append(this.version);
        buf.append(":");
        buf.append(versName[this.version]);

        buf.append(",\n★layer(MPEG 层(LayerI,II,III 等)Layer描述):");
        buf.append(this.layer);
        buf.append(":");
        buf.append(laysName[this.layer]);

        buf.append(",\n  protection(保护(如果==0，校验和附在头后(CRC))校验位):");
        buf.append(this.protection);
        buf.append(":");
        buf.append(procName[this.protection]);

        buf.append(",\n★bitrate(比特率索引(查找表用于说明本 MPEG 版本和层的比特率)位率索引):");
        buf.append(this.bitrate);
        String ver = "";
        if (this.version == 3) {
            ver = "1";
        }
        if (this.version == 0 || this.version == 2) {
            ver = "2";
        }
        String lay = "" + this.layer;
        if (this.layer == 1) {
            lay = "3";
        }
        if (this.layer == 3) {
            lay = "1";
        }
        String k = ver + "_" + lay + "_" + this.bitrate;
        buf.append(",(" + k + ")");
        buf.append(bitrateIndex.get(k));

        buf.append("\n★frequency(采样率频率(44.1kHz 等，由查找表决定)采样频率（单位：Hz）):");
        buf.append(this.frequency);
        k = this.version + "_" + this.frequency;
        buf.append(frequencyIndex.get(k));

        buf.append(",\n★padding(补齐位(On/Off, 是否对未填满的帧进行补齐)填充位):");
        buf.append(paddName[this.padding]);
        buf.append(":");
        buf.append(this.padding);

        buf.append(",\n  privatebit(保留位(On/Off, 允许特定应用触发)私有bit):");
        buf.append(this.privatebit);

        buf.append(",\n  channelMode(声道模式(立体声，联合立体声，双声道、单声道)声道):");
        buf.append(this.channelMode);
        buf.append(":");
        buf.append(modeName[this.channelMode]);

        buf.append(",\n  modeExtension(扩展模式(只用于联合立体声，以联合声道数据)扩展模式（仅在联合立体声时有效）):");
        buf.append(this.modeExtension);
        buf.append(":");
        buf.append(modExtName[this.modeExtension]);

        buf.append(",\n  copyright(版权(On/Off)版权):");
        buf.append(this.copyright);
        buf.append(":");
        buf.append(rightName[this.copyright]);

        buf.append(",\n  original(原始(On/Off, Off 表示复制版，On 表示原创)):");
        buf.append(this.original);
        buf.append(":");
        buf.append(origName[this.original]);

        buf.append(",\n  emphasis(强调(尊重强调原始录制，现基本废弃)):");
        buf.append(this.emphasis);
        buf.append(":");
        buf.append(emphName[this.emphasis]);

        buf.append(",\n  frame size:");
        buf.append(this.getSize());

        buf.append(",\n  是否有效:");
        buf.append(this.isValid());
        buf.append("\n----- mp3 frame header -----\n");
        return buf.toString();
    }

    public boolean isValid() {
        //if (this.sync != 0x7FF) {
    	if (this.sync != -1) {
            return false;
        }
        if (this.version != 0 && this.version != 2 && this.version != 3) {
            return false;
        }
        if (this.layer != 1 && this.layer != 2 && this.layer != 3) {
            return false;
        }
        return true;
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
     * 帧大小即每帧采样数表示一帧中采样的个数，这是恒定值。
     *     MPEG 1    MPEG 2 (LSF)    MPEG 2.5 (LSF)
     * Layer   I     384     384     384
     * Layer  II    1152    1152    1152
     * Layer III    1152     576     576
     * 帧长度是压缩时每一帧的长度，包括帧头。它将填充的空位也计算在内。
     * LayerI的一个空位长4字节，LayerII和LayerIII的空位是1字节。
     * 当读取MPEG文件时必须计算该值以便找到相邻的帧。
     * 注意：因为有填充和比特率变换，帧长度可能变化。
     * 从头中读取比特率，采样频率和填充，LyaerI使用公式：
     * 帧长度（字节） = (( 每帧采样数 / 8 * 比特率 ) / 采样频率 ) + 填充 * 4
     * LyerII和LyaerIII使用公式：
     * 帧长度（字节）= (( 每帧采样数 / 8 * 比特率 ) / 采样频率 ) + 填充
     * 例：LayerIII 比特率 128000，采样频率 44100，填充0
     * =〉帧大小 417字节
     */
    /**
     * MP3帧长取决于位率和频率，计算公式为：
     * . mpeg1.0 layer1 : 帧长= (048000*bitrate)/sampling_freq + padding
     *          layer2&3: 帧长= (144000*bitrate)/sampling_freq + padding
     * . mpeg2.0 layer1 : 帧长= (024000*bitrate)/sampling_freq + padding
     *         layer2&3 : 帧长= (072000*bitrate)/sampling_freq + padding
     * 根据公式，位率为128kbps，采样频率为44.1kHz，padding（帧长调节）为0时，帧长为417字节。
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
        String lay = "" + this.layer;
        if (this.layer == 1) {
            lay = "3";
        }
        if (this.layer == 3) {
            lay = "1";
        }
        String k = ver + "_" + lay + "_" + this.bitrate;
        String strBitrate = bitrateIndex.get(k);
        int bitrate = 0;
        if (strBitrate!= null && !"free".equals(strBitrate) && !"bad".equals(strBitrate)) {
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

    /**
     * 2）每帧的持续时间
     * 每帧的持续时间可以通过计算获得，下面给出计算公式
     * 每帧持续时间(毫秒) = 每帧采样数 / 采样频率 * 1000
     * 如图 2.3中，其每帧时间为：
     * 1152 / 44.1K * 1000 = 26.12 (约等于26ms)
     * 如果是MPEG2 Layer III 采样率为16KHz的话那一帧要持续36毫秒，这个相差还是蛮大的，所以还是应该通过计算来获的。
     * @return
     */
    public int getTimeLength() {
        return 0;
    }
}
