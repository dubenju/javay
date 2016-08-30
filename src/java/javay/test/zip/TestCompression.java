package javay.test.zip;

import javay.zip.Compression;

public class TestCompression {

	public static void main(String[] args) {
		Compression proc = new Compression("C:/test/testzip/", "C:/test/output/testzip.zip");
		proc.compressionProgram();
	}

}
