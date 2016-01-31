package javay.mail;

import sun.misc.BASE64Encoder;

public class Base64Util {

	public static void main(String[] args) {
		String userName = args[0];
		String passWord = args[1];
		BASE64Encoder encoder = new BASE64Encoder();
		System.out.println("用户名" + encoder.encode(userName.getBytes()) + "密码" + encoder.encode(passWord.getBytes()));
	}
}
