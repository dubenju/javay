package javay.util;

public class UBytes {
	public static String toHexString(byte[] in) {
		StringBuffer buf = new StringBuffer();
		for (byte by : in) {
			//System.out.println("by" + by);
			String str = Strings.format(Integer.toHexString(by), 2);
			buf.append(str);
		}
		return buf.toString();
	}
	/**
	 * 
	 * @param in
	 * @param endian 1:Little endian,2:big endian
	 * @return
	 */
	public static long toLong(byte[] in, int endian) {
		long res = 0;
		int pos = in.length - 1;
		int step = -1;
		if (endian == 2) {
			pos = 0;
			step = 1;
		}
		//System.out.println("(2 << 7)=" + (2 << 8));
		for (int i = pos; (endian == 2 ? i < in.length : i >= 0) ; i = i + step) {
			//System.out.println(in[i]);
			res = res * 256 + Byte.toUnsignedInt(in[i]);
		}
		
		return res;
	}
}
