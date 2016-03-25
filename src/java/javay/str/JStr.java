package javay.str;

/**
 * 字符数组
 * 字符－字符编码UTF8
 * 
 * @author dubenju
 *
 */
public class JStr {
	private final char[] val;
	
	public JStr() {
		val = new char[0];
	}
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(val);
		return buf.toString();
	}
	public static void main(String[] args) {
		JStr str = new JStr();
		System.out.println("str=[" + str + "]");
	}
}
