/**
 *
 */
package javay.util;

/**
 * @author DBJ
 *
 */
public class Strings {

	/**
	 *
	 */
	public Strings() {
	}

	public static boolean equals(String str1, String str2) {
		if (str1 == null && str2 != null) return false;
		if (str1 != null && str2 == null) return false;
		if (str1.length() != str2.length()) return false;
		return str1.indexOf(str2) == 0;
	}
	public static String format(String in, int len) {
		int ln = in.length();
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < (len - ln); i ++) {
			buf.append("  ");
		}
		buf.append(in);
		return buf.toString();
	}
}
