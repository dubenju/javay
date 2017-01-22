package javay.code;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;

public class Base64c {

    public static void main(String[] args) throws Exception {
	final String charset="UTF-8";
	String input="Base64编/解码";
	String encoded= Base64.encodeBase64String(input.getBytes(charset));
	String urlSafeEncoded=Base64.encodeBase64URLSafeString(input.getBytes(charset));
	System.out.println(encoded);
	System.out.println(urlSafeEncoded);
	Assert.assertEquals(input, new String(Base64.decodeBase64(encoded), charset));
	Assert.assertEquals(input, new String(Base64.decodeBase64(urlSafeEncoded), charset));
    }

}
