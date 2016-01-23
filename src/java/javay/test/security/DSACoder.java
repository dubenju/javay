package javay.test.security;

import java.security.Key;   
import java.security.KeyFactory;   
import java.security.KeyPair;   
import java.security.KeyPairGenerator;   
import java.security.PrivateKey;   
import java.security.PublicKey;   
import java.security.SecureRandom;   
import java.security.Signature;   
import java.security.spec.PKCS8EncodedKeySpec;   
import java.security.spec.X509EncodedKeySpec;   
import java.util.HashMap;   
import java.util.Map;   
  
  
/**  
* DSA安全编码组件  
*   
*/  
public abstract class DSACoder extends Coder {   
  /**  
   * 可以使用DSA方式获得签名，也可以使用RSA方式获得签名，注意成对儿出现。  
   *   
   * <code>  
   * public static final String KEY_ALGORITHM = "RSA";  
   * public static final String SIGNATURE_ALGORITHM = "MD5withRSA";  
   * </code>  
   **/  
  public static final String KEY_ALGORITHM = "DSA";   
  public static final String SIGNATURE_ALGORITHM = "DSA";   
  
  /**  
   * 默认种子  
   */  
  private static final String DEFAULT_SEED = "0f22507a10bbddd07d8a3082122966e3";   
  
  private static final String PUBLIC_KEY = "DSAPublicKey";   
  private static final String PRIVATE_KEY = "DSAPrivateKey";   
  
  /**  
   * 用私钥对信息生成数字签名  
   *   
   * @param data  
   *      加密数据  
   * @param privateKey  
   *      私钥  
   * @return  
   * @throws Exception  
   */  
  public static String sign(byte[] data, String privateKey) throws Exception {   
    // 解密由base64编码的私钥   
    byte[] keyBytes = decryptBASE64(privateKey);   
  
    // 构造PKCS8EncodedKeySpec对象   
    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);   
  
    // KEY_ALGORITHM 指定的加密算法   
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);   
  
    // 取私钥匙对象   
    PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);   
  
    // 用私钥对信息生成数字签名   
    Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);   
    signature.initSign(priKey);   
    signature.update(data);   
  
    return encryptBASE64(signature.sign());   
  }   
  
  /**  
   * 校验数字签名  
   * @param data  
   *      加密数据  
   * @param publicKey  
   *      公钥  
   * @param sign  
   *      数字签名  
   *   
   * @return 校验成功返回true 失败返回false  
   * @throws Exception  
   *   
   */  
  public static boolean verify(byte[] data, String publicKey, String sign)   
      throws Exception {   
  
    // 解密由base64编码的公钥   
    byte[] keyBytes = decryptBASE64(publicKey);   
  
    // 构造X509EncodedKeySpec对象   
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);   
  
    // KEY_ALGORITHM 指定的加密算法   
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);   
  
    // 取公钥匙对象   
    PublicKey pubKey = keyFactory.generatePublic(keySpec);   
  
    Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);   
    signature.initVerify(pubKey);   
    signature.update(data);   
  
    // 验证签名是否正常   
    return signature.verify(decryptBASE64(sign));   
  }   
  
  /**  
   * 生成密钥  
   *   
   * @param seed  
   *      种子  
   * @return 密钥对象  
   * @throws Exception  
   */  
  public static Map<String, Object> initKey(String seed) throws Exception {   
    KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);   
    // 初始化随机产生器   
    SecureRandom secureRandom = new SecureRandom();   
    secureRandom.setSeed(seed.getBytes());   
    keygen.initialize(1024, secureRandom);   
  
    KeyPair keys = keygen.genKeyPair();   
  
    PublicKey publicKey = keys.getPublic();   
    PrivateKey privateKey = keys.getPrivate();   
  
    Map<String, Object> map = new HashMap<String, Object>(2);   
    map.put(PUBLIC_KEY, publicKey);   
    map.put(PRIVATE_KEY, privateKey);   
  
    return map;   
  }   
  
  /**  
   * 默认生成密钥  
   *   
   * @return 密钥对象  
   * @throws Exception  
   */  
  public static Map<String, Object> initKey() throws Exception {   
    return initKey(DEFAULT_SEED);   
  }   
  
  /**  
   * 取得私钥  
   *   
   * @param keyMap  
   * @return  
   * @throws Exception  
   */  
  public static String getPrivateKey(Map<String, Object> keyMap)   
      throws Exception {   
    Key key = (Key) keyMap.get(PRIVATE_KEY);   
  
    return encryptBASE64(key.getEncoded());   
  }   
  
  /**  
   * 取得公钥  
   *   
   * @param keyMap  
   * @return  
   * @throws Exception  
   */  
  public static String getPublicKey(Map<String, Object> keyMap)   
      throws Exception {   
    Key key = (Key) keyMap.get(PUBLIC_KEY);   
  
    return encryptBASE64(key.getEncoded());   
  }   
}
