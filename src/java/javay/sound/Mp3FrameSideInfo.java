package javay.sound;

import javay.util.UBytes;

/**
 * 17 or 32
 * @author DBJ
 *
 */
public class Mp3FrameSideInfo {
    private byte[] side;
    private int main_data_begin;         // 9; // 9
    private int private_bits;            // 5; // 3
    private int scfsi_1;                   // 4; // 8Share
    private int scfsi_2;

    private int  part2_3_length_1;         // 12; // 24
    private int  part2_3_length_2;
    private int  big_values_1;             // 9; // 18
    private int  big_values_2;
    private int  global_gain;            // 8; // 16
    private int  scalefac_compress;      // 4; // 8
    private int  windows_switching_flag; // 1; // 2

    private int  block_type;             // 2; // 4
    private int  mixed_block_flag;       // 1; // 2

    private int  table_select;           // 15; // 30

    private int  subblock_gain;          // 9; // 18

    private int  region0_count;          // 4; // 8
    private int  region1_count;          // 3; // 6
    private int  preflag;                // 1; // 2
    private int  scalefac_scale;         // 1; // 2
    private int  count1table_select;     // 1; // 2

    public Mp3FrameSideInfo(byte[] in, Mp3FrameHeader header) {
        this.side = new byte[in.length];
        System.arraycopy(in, 0, this.side, 0, in.length);
        System.out.println("bin:" + UBytes.toBinaryString(this.side));
        int nch = header.getChannelMode() == 3 ? 1 : 2;

        if (header.getVersion() == 3) {
            // MPEG1
            // 1111 1111 1
            this.main_data_begin = ( (this.side[0] & 0xFF) << 1) | ((this.side[1] & 0x80) >> 7);
            if (nch == 1) {
                // 1222 2233 0111 1100
                this.private_bits = ((this.side[1] & 0x7C) >> 2);
                // 1222 2233 3344 4444
                this.scfsi_1 = (this.side[1] & 0x0F);
            } else {
                // 1222 3333
                this.private_bits = ((this.side[1] & 0x70) >> 4);
                // 3333 4444
                this.scfsi_1 = (this.side[1] & 0x0F);
                this.scfsi_2 = ((this.side[2] & 0xF0) >> 4);
            }
        } else {
            // 1111 1111
            this.main_data_begin = ( this.side[0] & 0xFF);
        }


        if (in.length == 32) {


            // 4444 4444
            this.part2_3_length_1 = (((this.side[2] & 0x0F) << 8 ) | (this.side[3])) & 0x0FFF;
            // 4444 4444 4444
            this.part2_3_length_2 = ((this.side[4] << 4) | ((this.side[5] & 0xF0) >> 4)) & 0x0FFF;
            this.big_values_1 = ( ( (this.side[5] & 0x0F) << 5) | ( ( this.side[6] & 0xF8) >> 3) ) & 0x01FF;
            this.big_values_2 = (((this.side[6] & 0x03) << 6) | ((this.side[7] & 0xFC) >> 2) ) & 0x01FF;
        }

        if (in.length == 17) {
            // 1222 2233
            this.private_bits = (this.side[1] & 0x7C) >>> 2;

        }

        if (in.length == 32) {

        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        System.out.println("side length:" + this.side.length);
        StringBuffer buf = new StringBuffer();
        buf.append("\n----- mp3 frame sideinfo -----\n");
        buf.append("main_data_begin(9 bits):");
        buf.append(this.main_data_begin);

        buf.append(",\nprivate_bits(5 bits, 3 bits):");
        buf.append(this.private_bits);

        buf.append(",\nscfsi_1(4 bits, 8 bits):");
        buf.append(this.scfsi_1);
        buf.append(",\nscfsi_2(4 bits, 8 bits):");
        buf.append(this.scfsi_2);

        buf.append(",\npart2_3_length_1(12 bits, 24 bits):");
        buf.append(this.part2_3_length_1);
        buf.append(",\npart2_3_length_2:");
        buf.append(this.part2_3_length_2);
        buf.append(",\nbig_values_1(9 bits, 18 bits):");
        buf.append(this.big_values_1);
        buf.append(",\nbig_values_2:");
        buf.append(this.big_values_2);
        buf.append(",\nglobal_gain (8 bits, 16 bits):");
        buf.append(",\nwindows_switching_flag (1 bit, 2 bits):");
        buf.append(",\nblock_type (2 bits, 4 bits):");
        buf.append(",\nmixed_blockflag (1 bit, 2 bits):");
        buf.append(",\ntable_select ( (10 bits, 20 bits) or (15 bits, 30 bits) ):");
        buf.append(",\nsubblock_gain (9 bits, 18 bits):");
        buf.append(",\nregion0_count (4 bits, 8 bits), region1_count (3 bits, 6 bits):");
        buf.append(",\npreflag (1 bit, 2bits):");
        buf.append(",\nscalfac_scale (1 bit, 2bits):");
        buf.append(",\ncount1table_select (1 bit, 2bits):");
        buf.append("\n----- mp3 frame sideinfo -----\n");
        return buf.toString();
    }
}

/**
1. 数据结构
属性变量 window_switching_flag, block, subblock, block_type, mixed_block_flag, subblock_gain[], SBI, l/s, ro, lr, scfsi, linbit, region_count 等是干什么用的？
如何从整体上理解它们？看来从理解数据结构开始可能会顺利些。
结构的结构：
  III_side_info_t : temporaire[2] : gr_info_s[2]
  Sftable : l[5],  s[3]                        // 比例系数 ?
  SBI :     l[23], s[14]
宏常量：
  private static final int SSLIMIT = 18;       // 每个子带样条限制数
  private static final int SBLIMIT = 32;       // 子带限制数
帧结构：
  Header(32) + CRC(0,16) + Side Information(136,256) + Main Data
  注意：因为蓄水池的作用，主数据不必从本帧开始。

表1 缩放因子数据表格
  缩放因子带(21)     0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20
  ----------------- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
  缩放因子比特数(l)  3  3  3  3  3  3  3  3  3  3  3  3  3  3  3  3  3  3  3  3  3
  缩放因子      (s)  1  1  0  0  1  1  4  5  3  7  5  3  0  3  0  1  0  7  5  0  3

si :                                           // : 边信息的数据结构 si 由 ... 组成
  main_data_begin            : 9               // 读 9bits 的边信息的主数据开始
  private_bits               : 3               // 读取 3bits 的私有位，对多声道
  ch* :                                        // 为每个声道读取 4bits 的比例系数选择信息scfsi
    scfsi*4                  : 1
  gr* :                                        // * 由多个颗粒 gr 数组组成
    ch* :                                      // * 由多个声道 ch 数组组成
      part2_3_length         : 12              // : n 主数据由 n 个比特位组成
      big_values             : 9               //
      global_gain            : 8               // 全局增益
      scalefac_compress      : 4               // 比例系数压缩
      window_switching_flag  : 1               // 窗口切换开关位
      block_type             : 2               // 块类型 window_switching_flag!=0
      mixed_block_flag       : 1               // 混合块标记
      table_select[0]        : 5               // 表选择
      table_select[1]        : 5
      subblock_gain[0]       : 3               // 子块增益
      subblock_gain[1]       : 3
      subblock_gain[2]       : 3
      region0_count          = 8 | 7           // block_type==2 && mixed_block_flag==1
      region1_count          = 20 - region0_count;
      preflag                : 1               // ?
      scalefac_scale         : 1
      count1table_select     : 1

SCFSI 表示 "比例系数选择信息 SCale Factor Selection Information"，它是一个在 MPEG音频数据帧中的
技术标记，用以表示用于帧中两个颗粒 granule 数据公用的比例系数。
增加 frame-discard 表示引入两个标记仍然没有足够的主数据，命名为 'bytes_to_discard' 和 'sig'。此
外还包括变量 main_data_end1 以保持对主数据结束的跟踪。
在边信息块中增加变量 subblock_gain 和 mixed_block_flag，用于当 window_switching_flag 为 0时，以
避免当该条件满足时程序瘫痪。
当边信息确定后，在 main_data_start 的计算之后，移动 start(2) 和 start(1) 计算。增加变量 'new'以
替换 start(1) 函数，替换的 start(1) 而非 start(2)。须注意那些帧1中 main_data_begin 非 0 的文件。

2. 算法投影概念
MP3 解码算法含 MP1, MP2, MP3-1, MP3-2BC, MP3-2.5LSF 等版本，另处理又可根据模式分为Mono, Stereo,
Joint Stereo, Dual Channel 等,  如何针对其中一种进行算法投影？或如何获取算法视图？

3. Miscs
Run the eleMentSoft.com website, IP: 219.146.62.234
Bugs found in GBHZ.js simple and traditional chinese translation match table. 回 -> 迴
New words, terms and commands
sftable - Scale Factor Table 比例系数表
ancillary data 辅助数据
*/