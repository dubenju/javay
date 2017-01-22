package javay.code;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLDecoderTest {

    public static void main(String[] args) throws Exception {

       //将application/x-www-form-urlencoded字符串

       //转换成普通字符串

       //必须强调的是编码方式必须正确，如baidu的是gb2312，而google的是UTF-8

       //String keyWord = URLDecoder.decode("%E6%96%87%E6%A1%A3", "gb2312");
        String keyWord = URLDecoder.decode("%CE%C4%B5%B5", "gb2312");
       System.out.println(keyWord);



       //将普通字符串转换成

       //application/x-www-form-urlencoded字符串

       //必须强调的是编码方式必须正确，如baidu的是gb2312，而google的是UTF-8

       String urlStr = URLEncoder.encode("文档", "gb2312");

       System.out.println(urlStr);

    }

}