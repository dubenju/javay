package javay.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;

public class JavayHttp {
  private static final Logger log = LoggerFactory.getLogger(JavayHttp.class);
  private String urlAddr = "";

  public JavayHttp(String url ) {
    log.debug("url = " + url);
    this.urlAddr = url;
  }

  /**
   * getInfo.
   * @param separator String
   * @return String
   */
  public String getInfo(String separator) {
    String strRes = "";
    int byteread = 0;
    long filesize = 0;
    URL url = null;
    try {
      url = new URL(this.urlAddr);
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return strRes;
    }
    URLConnection conn = null;
    try {
      conn = url.openConnection();
    } catch (IOException e) {
      e.printStackTrace();
      return strRes;
    }
    if (conn instanceof HttpsURLConnection) {
      System.out.println("read by https.");
      HttpsURLConnection httpsUrlConnection = (HttpsURLConnection) conn;
      int responseCode = -1;
      try {
        responseCode = httpsUrlConnection.getResponseCode();
        System.out.println("responseCode=" + responseCode);
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      System.out.println(" " + httpsUrlConnection.getContentLengthLong());
      System.out.println(" " + httpsUrlConnection.getHeaderField("Content-Length"));
      InputStream inStream = null;
      try {
        inStream = conn.getInputStream();
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (inStream != null) {
        byte[] buffer = new byte[1204];
        try {
          while ((byteread = inStream.read(buffer)) != -1) {
            filesize = filesize + byteread;
            String line = new String(buffer);
            System.out.println("read internet:" + line + "byte:" + byteread);
            int pos = line.indexOf(separator);
            if (pos >= 0) {
              strRes = line.substring(pos + 1);
              break;
            }
          }
          int pos = strRes.indexOf(0x0A);
          if (pos > 0) {
            strRes = strRes.substring(0, pos);
          }
          pos = strRes.indexOf(0x0D);
          if (pos > 0) {
            strRes = strRes.substring(0, pos);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
        System.out.println(" " + filesize);
      }
      if (strRes.length() > 0) {
        System.out.println("return:" + strRes);
        return strRes;
      }
    }
    if (conn instanceof HttpURLConnection) {
      System.out.println("read by http.");
      HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
      int responseCode = -1;
      try {
        responseCode = httpUrlConnection.getResponseCode();
        System.out.println("responseCode=" + responseCode);
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      System.out.println(" " + httpUrlConnection.getContentLengthLong());
      System.out.println(" " + httpUrlConnection.getHeaderField("Content-Length"));
      InputStream inStream = null;
      try {
        inStream = conn.getInputStream();
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (inStream != null) {
        byte[] buffer = new byte[1204];
        try {
          while ((byteread = inStream.read(buffer)) != -1) {
            filesize = filesize + byteread;
            String line = new String(buffer);
            System.out.println("read internet:" + line + "byte:" + byteread);
            int pos = line.indexOf(separator);
            if (pos >= 0) {
              strRes = line.substring(pos + 1);
              break;
            }
          }
          int pos = strRes.indexOf(0x0A);
          if (pos > 0) {
            strRes = strRes.substring(0, pos);
          }
          pos = strRes.indexOf(0x0D);
          if (pos > 0) {
            strRes = strRes.substring(0, pos);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
        System.out.println(" " + filesize);
      } else {
        System.out.println("inStream is null.");
      }
      log.debug("out=" + strRes);
      return strRes;
    }
    return strRes;
  }

  /**
   * getModule.
   * @param output String output
   * @return boolean
   */
  public boolean getModule(String output) {
    boolean blRes = false;
    int byteread = 0;
    long filesize = 0;
    URL url = null;
    try {
      log.debug("url=" + this.urlAddr);
      url = new URL(this.urlAddr);
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return blRes;
    }
    URLConnection conn = null;
    try {
      conn = url.openConnection();
    } catch (IOException e) {
      e.printStackTrace();
      return blRes;
    }
    if (conn instanceof HttpsURLConnection) {
      System.out.println("read by https.");
      HttpsURLConnection httpsUrlConnection = (HttpsURLConnection) conn;
      int responseCode = -1;
      try {
        responseCode = httpsUrlConnection.getResponseCode();
        System.out.println("responseCode=" + responseCode);
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      if (responseCode != 200) {
        log.debug("ResponseCode=" + responseCode);
        return blRes;
      }
      System.out.println(" " + httpsUrlConnection.getContentLengthLong());
      System.out.println(" " + httpsUrlConnection.getHeaderField("Content-Length"));
      InputStream inStream = null;
      try {
        inStream = conn.getInputStream();
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (inStream != null) {
        FileOutputStream fs = null;
        try {
          fs = new FileOutputStream(output);
        } catch (FileNotFoundException e1) {
          e1.printStackTrace();
          return blRes;
        }
        byte[] buffer = new byte[1204];
        String chk = "";
        try {
          MessageDigest md = MessageDigest.getInstance("MD5");
          while ((byteread = inStream.read(buffer)) != -1) {
            filesize = filesize + byteread;
            fs.write(buffer, 0, byteread);
            if (!blRes) {
              blRes = true;
            }
            md.update(buffer, 0, byteread);
          }
          byte[] mdbytes = md.digest();
          chk = toHexString(mdbytes);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
        }
        System.out.println(" " + filesize);
        try {
          fs.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        JavayHttp httpc = new JavayHttp(this.getUrl() + ".md5");
        String net = httpc.getInfo("=");
        if (chk.compareTo(net.trim()) != 0) {
          blRes = false;
        }
      }
      return blRes;
    }
    if (conn instanceof HttpURLConnection) {
      System.out.println("read by http.");
      HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
      int responseCode = -1;
      try {
        responseCode = httpUrlConnection.getResponseCode();
        System.out.println("responseCode=" + responseCode);
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      if (responseCode != 200) {
        log.debug("ResponseCode=" + responseCode);
        return blRes;
      }
      System.out.println(" " + httpUrlConnection.getContentLengthLong());
      System.out.println(" " + httpUrlConnection.getHeaderField("Content-Length"));
      InputStream inStream = null;
      try {
        inStream = conn.getInputStream();
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (inStream != null) {
        FileOutputStream fs = null;
        try {
          fs = new FileOutputStream(output);
        } catch (FileNotFoundException e1) {
          e1.printStackTrace();
          return blRes;
        }
        byte[] buffer = new byte[1204];
        String chk = "";
        try {
          MessageDigest md = MessageDigest.getInstance("MD5");
          while ((byteread = inStream.read(buffer)) != -1) {
            filesize = filesize + byteread;
            fs.write(buffer, 0, byteread);
            if (!blRes) {
              blRes = true;
            }
            md.update(buffer, 0, byteread);
          }
          byte[] mdbytes = md.digest();
          chk = toHexString(mdbytes);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
        }
        System.out.println(" " + filesize);
        try {
          fs.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        JavayHttp httpc = new JavayHttp(this.getUrl() + ".md5");
        String net = httpc.getInfo("=");
        if (chk.compareTo(net.trim()) != 0) {
          blRes = false;
        }
      } else {
        System.out.println("inStream is null.");
      }
    }
    return blRes;
  }
  /**
   * getUrl.
   * @return the url
   */
  public String getUrl() {
    return urlAddr;
  }
  /**
   * setUrl.
   * @param url the url to set
   */
  public void setUrl(String url) {
    this.urlAddr = url;
  }

  /**
   * toHexString.
   * @param in byte[]
   * @return String
   */
  public static String toHexString(byte[] in) {
    StringBuffer buf = new StringBuffer();
    for (byte by : in) {
      String hex = Integer.toHexString(by);
      int len = hex.length();
      if (len > 2) {
        hex = hex.substring(len - 2, len);
      }
      if (len < 2) {
        buf.append("0");
      }
      buf.append(hex);
    }
    return buf.toString();
  }
}
