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
    int diff = (len - ln);
    StringBuffer buf = new StringBuffer();
    if (diff > 0) {
      for(int i = 0; i < diff; i ++) {
        buf.append(" ");
      }
      buf.append(in);
    }
    if (diff == 0) {
      buf.append(in);
    }
    if (diff < 0) {
      buf.append(in.substring(0 - diff));
    }
    return buf.toString();
  }
}
