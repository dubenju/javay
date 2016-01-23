package javay.test.security;

import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SEC {

  public static void main(String[] args) throws Exception {
    String msg = "郭XX-精品相声技术";  
    SEC md5 = new SEC();  
    byte[] resultBytes = md5.encryptMD5(msg.getBytes());  
      
    System.out.println("密文是：" + new String(resultBytes));  
    System.out.println("明文是：" + msg); 
    
    resultBytes = md5.encryptSHA(msg.getBytes());  
    System.out.println("明文是：" + msg);  
    System.out.println("密文是：" + new String(resultBytes));  

  }
  public static byte[] encryptMD5(byte[] data) throws Exception {

      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(data);

     return md5.digest(); 

  }
  public static byte[] encryptSHA(byte[] data) throws Exception { 

      MessageDigest sha = MessageDigest.getInstance("SHA");
     sha.update(data);

     return sha.digest();

     }
  /**
  * 初始化HMAC密钥
  */
  public static String initMacKey() throws Exception {
      KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
  
      SecretKey secretKey = keyGenerator.generateKey();
      return encryptBASE64(secretKey.getEncoded());
  }
  
  /**
  * HMAC加密
  */
  public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
  
       SecretKey secretKey = new SecretKeySpec(
           decryptBASE64(key), "HmacMD5");
      Mac mac = Mac.getInstance(secretKey.getAlgorithm());
      mac.init(secretKey);
  
     return mac.doFinal(data); 
  
  }
  /** 
   * BASE64解密 
   *  
   * @param key 
   * @return 
   * @throws Exception 
   */  
  public static byte[] decryptBASE64(String key) throws Exception {  
    return (new BASE64Decoder()).decodeBuffer(key);  
  }  
    
  /** 
   * BASE64加密 
   *  
   * @param key 
   * @return 
   * @throws Exception 
   */  
  public static String encryptBASE64(byte[] key) throws Exception {  
    return (new BASE64Encoder()).encodeBuffer(key);  
  } 
}
