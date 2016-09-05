package javay.test.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javay.util.UBytes;
import javay.zip.Gzip;

public class TestGzip {

	public static void main(String[] args) throws Exception {
		String str1 = "abacadaer找好吃的时候要保持姿势正确，以前是起来重睡，现在是直接piupiu了。真人版的就是好啊，赶紧回去把家里的那几个破气球撇了吧。";

		// test(str1);

		String strg = Gzip.compress(str1);
		System.out.println("in=[" + str1 + "],out=[" + strg + "]");
		System.out.println("压缩:" + UBytes.toHexString(strg.getBytes("UTF-8")  ));
		String str2 = Gzip.uncompress(strg);
		System.out.println("in=[" + strg + "],out=[" + str2 + "]");
	}
    public static void test(String s) throws IOException {
        // String s ="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        //我这里用字节流输出的，所以转正byte[]
        byte[] b = s.getBytes();
        System.out.println("压缩前："+b.length);
        //缓存数据用的字节数组流
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //压缩用的是这个流
        GZIPOutputStream gout = new GZIPOutputStream(bout);
        gout.write(b);//把b写入到缓冲区中，也就是ByteArrayOutputStream
        gout.close();//关闭流，也就是把数据全都刷到字节数组流中
        b = bout.toByteArray();//这个字节数组流关闭之后还能用，不用担心，从他里面把压缩好的数据拿出来，还是放在byte[]中
        System.out.println("压缩后："+b.length);



        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        GZIPInputStream gis =new GZIPInputStream(bis);
        int len = -1;
        byte [] b1 =new byte[1024];
//        StringBuilder sb = new StringBuilder();
        while((len = gis.read(b1)) != -1){
            bos.write(b1, 0, len);
        }
        bos.close();
        System.out.println("解压之后："+bos.toByteArray().length);
        System.out.println(bos.toString());
    }
}
