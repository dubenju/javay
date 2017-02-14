package javay.test.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;






import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import javay.util.UBytes;

public class DES3demo {

  private byte[] keys = {
      0x3b,0x38,(byte)0x98,0x37, 0x15,0x20,(byte)0xf7,0x5e,
      (byte)0x92,0x2f,(byte)0xb5,0x10,(byte)0xc7,0x1f,0x43,0x6e,
      0x3b,0x38,(byte)0x98,0x37,0x15,0x20,(byte)0xf7,0x5e
  };
  // KeyGenerator 提供对称密钥生成器的功能，支持各种算法
  private KeyGenerator keygen;
  // SecretKey 负责保存对称密钥
  private SecretKey deskey;
  // Cipher负责完成加密或解密工作
  private Cipher c;
  // 该字节数组负责保存加密的结果
  private byte[] cipherByte;

  public DES3demo() throws NoSuchAlgorithmException, NoSuchPaddingException {
    Security.addProvider(new com.sun.crypto.provider.SunJCE());
//    // 实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
//    keygen = KeyGenerator.getInstance("DESede");
//    // 生成密钥
//    deskey = keygen.generateKey();
    deskey= new SecretKeySpec(keys, "DESede");
    System.out.println("密钥:" + UBytes.toHexString(deskey.getEncoded()));
    // 生成Cipher对象,指定其支持的DES算法
    c = Cipher.getInstance("DESede/ECB/NOPADDING");
  }

  /**
   * 对字符串加密
   *
   * @param str
   * @return
   * @throws InvalidKeyException
   * @throws IllegalBlockSizeException
   * @throws BadPaddingException
   */
  public byte[] Encrytor(String str) throws InvalidKeyException,
      IllegalBlockSizeException, BadPaddingException {
    // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
    c.init(Cipher.ENCRYPT_MODE, deskey);
    byte[] src = str.getBytes();
    System.out.println("明文:" + UBytes.toHexString(src));
    // 加密，结果保存进cipherByte
    cipherByte = c.doFinal(src);
    System.out.println("密文:" + UBytes.toHexString(cipherByte));
    return cipherByte;
  }

  /**
   * 对字符串解密
   *
   * @param buff
   * @return
   * @throws InvalidKeyException
   * @throws IllegalBlockSizeException
   * @throws BadPaddingException
   */
  public byte[] Decryptor(byte[] buff) throws InvalidKeyException,
      IllegalBlockSizeException, BadPaddingException {
    // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
    c.init(Cipher.DECRYPT_MODE, deskey);
    cipherByte = c.doFinal(buff);
    return cipherByte;
  }

  /**
   * @param args
   * @throws NoSuchPaddingException
   * @throws NoSuchAlgorithmException
   * @throws BadPaddingException
   * @throws IllegalBlockSizeException
   * @throws InvalidKeyException
   */
  public static void main(String[] args) throws Exception {
    DES3demo des = new DES3demo();
    String msg ="abcdefgh";
    byte[] encontent = des.Encrytor(msg);
    byte[] decontent = des.Decryptor(encontent);
    System.out.println("解密:" + UBytes.toHexString(decontent));
    System.out.println("明文是:" + msg);
    System.out.println("加密后:" + new String(encontent));
    System.out.println("解密后:" + new String(decontent));

  }

}
