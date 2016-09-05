package javay.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javay.util.UBytes;

public class Gzip {
    public static String compress(String str) throws IOException {
           if (str == null || str.length() == 0) {
               return str;
           }
           ByteArrayOutputStream out = new ByteArrayOutputStream();
           GZIPOutputStream gzip = new GZIPOutputStream(out);
           gzip.write(str.getBytes());
           gzip.close();
           gzip.finish();
//           System.out.println("压缩:" + UBytes.toHexString(out.toByteArray()));
//           System.out.println("aa:" + UBytes.toHexString(Ascii2Utf8(out.toByteArray())));
//           return new String(out.toString("ISO-8859-1").getBytes("UTF-8"), "UTF-8");
           return new String(Ascii2Utf8(out.toByteArray()), "UTF-8");
       }

    private static byte[] Ascii2Utf8(byte[] in) {
        if (in == null) { return null; }
        byte[] tmp = new byte[2 * in.length];
        int i = 0, j = 0;
        for (; i < in.length; i ++, j ++) {
            byte b = in[i];
            if (b >= 0) {
                tmp[j] = b;
            } else {
               // 110yyyyx
               tmp[j] = (byte) (0xC0 | (0x03 & b >>> 6));
               j ++;
               // 10xxxxxx
               tmp[j] = (byte) (0x80 | (0x3F & b));
            }
        }
        if (i == 0) {
            return tmp;
        } else {
            byte[] result = new byte[j + 1];
            System.arraycopy(tmp, 0, result, 0, j);
            return result;
        }
    }
    // 解压缩
    public static String uncompress(String str) throws IOException {
      if (str == null || str.length() == 0) {
        return str;
      }
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
      GZIPInputStream gunzip = new GZIPInputStream(in);
      byte[] buffer = new byte[256];
      int n;
      while ((n = gunzip.read(buffer))>=0) {
        out.write(buffer, 0, n);
      }
      // toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)
      return out.toString();
    }
}
