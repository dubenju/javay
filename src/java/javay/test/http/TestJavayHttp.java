package javay.test.http;

import javay.http.JavayHttp;

public class TestJavayHttp {

	public static void main(String[] args) {
		String url = "https://github.com/dubenju/javay/blob/master/ReleaseNotes.txt"; // HTML
		url = "https://raw.githubusercontent.com/dubenju/javay/master/ReleaseNotes.txt";// 速度快，没有停顿
		//url = "http://raw.githubusercontent.com/dubenju/javay/master/ReleaseNotes.txt"; // NG
		//url = "https://github.com/dubenju/javay/raw/master/ReleaseNotes.txt"; // 速度慢有停顿
		JavayHttp http = new JavayHttp(url);
		System.out.println("@test:" + http.getVersion());
		
		url = "https://github.com/dubenju/javay/raw/master/lib/slf4j-1.7.13.tar.gz";
		http = new JavayHttp(url);
		System.out.println("@test:" + http.getModule("./tmp/slf4j-1.7.13.tar.gz"));
	}

}
