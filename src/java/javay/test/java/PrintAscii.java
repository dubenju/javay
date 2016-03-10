package javay.test.java;

public class PrintAscii {

	public static void main(String[] args) {
		for (int i = 0; i < 256; i ++) {
			char ch = (char) i;
			System.out.println(ch + "=" + i + " 0x" + Integer.toHexString(i));
		}

	}

}
