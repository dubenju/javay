package javay.sound;

/**
 * 17 or 32
 * @author DBJ
 *
 */
public class Mp3FrameSideInfo {
	private byte[] side;
	private int main_data_begin; // 9; // 9
	private int private_bits; // 5; // 3
	private int scfsi; // 4; // 8
	private int  part2_3_length; // 12; // 24
	private int  big_values; // 9; // 18
	private int  global_gain; // 8; // 16
	private int  scalefac_compress; // 4; // 8
	private int  windows_switching_flag; // 1; // 2
	private int  block_type; // 2; // 4
	private int  mixed_block_flag; // 1; // 2
	private int  table_select; // 15; // 30
	private int  subblock_gain; // 9; // 18
	private int  region0_count; // 4; // 8
	private int  region1_count; // 3; // 6
	private int  preflag; // 1; // 2
	private int  scalefac_scale; // 1; // 2
	private int  count1table_select; // 1; // 2

	public Mp3FrameSideInfo(byte[] in) {

	}
}
