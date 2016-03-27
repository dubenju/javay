package javay.util;

public class UBytes {
	public static String toHexString(byte[] in) {
		StringBuffer buf = new StringBuffer();
		for (byte by : in) {
			//System.out.println("by" + by);
			String str = Strings.format(Integer.toHexString(by).toUpperCase(), 2, '0');
			buf.append(str);
			buf.append(" ");
		}
		return buf.toString();
	}
	public static String toBinaryString(byte[] in) {
		StringBuffer buf = new StringBuffer();
		for (byte by : in) {
			//System.out.println("by" + by);
			String str = Strings.format(Integer.toBinaryString(by), 8, '0');
			buf.append(str);
			buf.append(" ");
		}
		return buf.toString();
	}
	/**
	 *
	 * @param in
	 * @param endian 1:Little endian,2:big endian
	 * @return
	 */
	public static int toInt(byte[] in, int endian) {
		int res = 0;
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
//			System.out.println(res);
		}
//		System.out.println(res);
		return res;
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
	public static boolean isZero(byte[] in) {
		boolean bRes = true;
		for(byte by : in) {
			if (by != 0x00) {
				bRes = false;
				break;
			}
		}
		return bRes;
	}
	public static int comp (byte[] a, int posa, byte[] b, int posb, int len) {
		int lena = a.length - posa;
		int lenb = b.length - posb;
		if (lena < lenb) {
			return -1;
		}

		int maxLen = len;
		if (lenb < maxLen) {
			maxLen = lenb;
		}
		int iRes = 0;
		for (int i = 0; i < maxLen; i ++) {
			if (a[posa + i] != b[posb + i]) {
				iRes = a[posa + i] > b[posb + i] ? 1 : -1;
				break;
			}
		}
		return iRes;
	}

}
