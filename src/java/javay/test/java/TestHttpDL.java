package javay.test.java;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class TestHttpDL {

	public static void main(String[] args) {
		//"http://github.com/dubenju/encv/blob/master/lib/encv-0.0.1.jar"
		String file = "http://downloads.sourceforge.net/project/encv/0.0.1/0.0.1-bin.zip?r=http%3A%2F%2Fsourceforge.net%2Fprojects%2Fencv%2Ffiles%2F0.0.1%2F&ts=1453067846&use_mirror=heanet";
		if (httpDownload(file, "./encv-0.0.1.zip")) {
			System.out.println("OK");
		} else {
			System.out.println("NG");
		}

	}
	/**http下载*/  
	public static boolean httpDownload(String httpUrl,String saveFile) {
	       // 下载网络文件  
	       int bytesum = 0;  
	       int byteread = 0;  

	       URL url = null;  
	       try {  
	    	   url = new URL(httpUrl);  
	       } catch (MalformedURLException e1) {  
	    	   e1.printStackTrace();  
	    	   return false;  
	       }
	  
	       try {  
	           URLConnection conn = url.openConnection();  
	           InputStream inStream = conn.getInputStream();  
	           FileOutputStream fs = new FileOutputStream(saveFile);  
	  
	           byte[] buffer = new byte[1204];  
	           while ((byteread = inStream.read(buffer)) != -1) {  
	               bytesum += byteread;  
	               System.out.println(bytesum);  
	               fs.write(buffer, 0, byteread);  
	           }
	           fs.close();
	           return true;  
	       } catch (FileNotFoundException e) {  
	           e.printStackTrace();  
	           return false;  
	       } catch (IOException e) {  
	           e.printStackTrace();  
	           return false;  
	       }  
	   }
	
	public static boolean hpdl() throws Exception {
        // 创建URL对象
        URL myURL = new URL("https://www.sun.com");
        // 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
        HttpsURLConnection httpsConn = (HttpsURLConnection) myURL.openConnection();
        // 取得该连接的输入流，以读取响应内容
        InputStreamReader insr = new InputStreamReader(httpsConn .getInputStream());
        // 读取服务器的响应内容并显示
        int respInt = insr.read();
        while (respInt != -1) {
            System.out.print((char) respInt);
            respInt = insr.read();
        }
        return true;
	}
	public static boolean hpdl2() throws Exception {
		 // 创建SSLContext对象，并使用我们指定的信任管理器初始化
        TrustManager[] tm = { new MyX509TrustManager() };
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new java.security.SecureRandom());
        // 从上述SSLContext对象中得到SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        // 创建URL对象
        URL myURL = new URL("https://ebanks.gdb.com.cn/sperbank/perbankLogin.jsp");
        // 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
        HttpsURLConnection httpsConn = (HttpsURLConnection) myURL.openConnection();
        httpsConn.setSSLSocketFactory(ssf);
        // 取得该连接的输入流，以读取响应内容
        InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream());
        // 读取服务器的响应内容并显示
        int respInt = insr.read();
        while (respInt != -1) {
            System.out.print((char) respInt);
            respInt = insr.read();
        }
        return true;
	}
	
//	URL url = new URL("https://hostname:port/file.txt");
//	URLConnection connection = url.openConnection();
//	InputStream is = connection.getInputStream();
//	// .. then download the file
//	
//	// Create a new trust manager that trust all certificates
//	TrustManager[] trustAllCerts = new TrustManager[]{
//	    new X509TrustManager() {
//	        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//	            return null;
//	        }
//	        public void checkClientTrusted(
//	            java.security.cert.X509Certificate[] certs, String authType) {
//	        }
//	        public void checkServerTrusted(
//	            java.security.cert.X509Certificate[] certs, String authType) {
//	        }
//	    }
//	};
//
//	// Activate the new trust manager
//	try {
//	    SSLContext sc = SSLContext.getInstance("SSL");
//	    sc.init(null, trustAllCerts, new java.security.SecureRandom());
//	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//	} catch (Exception e) {
//	}
//
//	// And as before now you can use URL and URLConnection
//	URL url = new URL("https://hostname:port/file.txt");
//	URLConnection connection = url.openConnection();
//	InputStream is = connection.getInputStream();
//	// .. then download the file
//	
//	public HttpServletResponse download(String path, HttpServletResponse response) {
//		try {
//		// path是指欲下载的文件的路径。
//		File file = new File(path);
//		// 取得文件名。
//		String filename = file.getName();
//		// 取得文件的后缀名。
//		String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
//		// 以流的形式下载文件。
//		InputStream fis = new BufferedInputStream(new FileInputStream(path));
//		byte[] buffer = new byte[fis.available()];
//		fis.read(buffer);
//		fis.close();
//		// 清空response
//		response.reset();
//		// 设置response的Header
//		response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
//		response.addHeader("Content-Length", "" + file.length());
//		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
//		response.setContentType("application/octet-stream");
//		toClient.write(buffer);
//		toClient.flush();
//		toClient.close();
//		} catch (IOException ex) {
//		ex.printStackTrace();
//		}
//		return response;
//		}
//		public void downloadLocal(HttpServletResponse response) throws FileNotFoundException {
//		// 下载本地文件
//		String fileName = "Operator.doc".toString(); // 文件的默认保存名
//		// 读到流中
//		InputStream inStream = new FileInputStream("c:/Operator.doc");// 文件的存放路径
//		// 设置输出的格式
//		response.reset();
//		response.setContentType("bin");
//		response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
//		// 循环取出流中的数据
//		byte[] b = new byte[100];
//		int len;
//		try {
//		while ((len = inStream.read(b)) > 0)
//		response.getOutputStream().write(b, 0, len);
//		inStream.close();
//		} catch (IOException e) {
//		e.printStackTrace();
//		}
//		}
//		public void downloadNet(HttpServletResponse response) throws MalformedURLException {
//		// 下载网络文件
//		int bytesum = 0;
//		int byteread = 0;
//		URL url = new URL("windine.blogdriver.com/logo.gif");
//		try {
//		URLConnection conn = url.openConnection();
//		InputStream inStream = conn.getInputStream();
//		FileOutputStream fs = new FileOutputStream("c:/abc.gif");
//		byte[] buffer = new byte[1204];
//		int length;
//		while ((byteread = inStream.read(buffer)) != -1) {
//		bytesum += byteread;
//		System.out.println(bytesum);
//		fs.write(buffer, 0, byteread);
//		}
//		} catch (FileNotFoundException e) {
//		e.printStackTrace();
//		} catch (IOException e) {
//		e.printStackTrace();
//		}
//		}
//		//支持在线打开文件的一种方式
//		public void downLoad(String filePath, HttpServletResponse response, boolean isOnLine) throws Exception {
//		File f = new File(filePath);
//		if (!f.exists()) {
//		response.sendError(404, "File not found!");
//		return;
//		}
//		BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
//		byte[] buf = new byte[1024];
//		int len = 0;
//		response.reset(); // 非常重要
//		if (isOnLine) { // 在线打开方式
//		URL u = new URL("file:///" + filePath);
//		response.setContentType(u.openConnection().getContentType());
//		response.setHeader("Content-Disposition", "inline; filename=" + f.getName());
//		// 文件名应该编码成UTF-8
//		} else { // 纯下载方式
//		response.setContentType("application/x-msdownload");
//		response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
//		}
//		OutputStream out = response.getOutputStream();
//		while ((len = br.read(buf)) > 0)
//		out.write(buf, 0, len);
//		br.close();
//		out.close();
//		}
}
