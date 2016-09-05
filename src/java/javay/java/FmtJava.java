package javay.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;

public class FmtJava {

	public static void main(String[] args) throws Exception {
		FmtJava proc = new FmtJava();
		long a = System.currentTimeMillis();
		proc.run("C:/Users/DBJ/git/javay/src/java/", "C:/prj/javay1/");
		long b = System.currentTimeMillis();
		System.out.println(b - a);
	}

	public void run(String in, String out) throws Exception {
		File infile = new File(in);
		File[] files = infile.listFiles();
		if (files == null) {
			return;
		}
		for(int i = 0; i < files.length; i ++) {
			File fl = files[i];
			if (fl.isDirectory()) {
				String outPath = out + fl.getName() + "/";
				new File(outPath).mkdir();
				run(fl.getAbsolutePath() + "/", outPath);
			} // Directory
			if (fl.isFile() && fl.getName().toLowerCase().endsWith(".java")) {
				String outFile = out + fl.getName();
				OutputStream os = new FileOutputStream(outFile);

				BufferedReader br = new BufferedReader(new FileReader(fl));
				String strLn = null;
				long lineCnt = 0;
				int lineByte = 0;
				int cmnFlg = 0;
				LBL_LINE:while((strLn = br.readLine()) != null) {
					String outLine = strLn;
					lineCnt ++;
					outLine = outLine.replaceAll("\t", "    ");

					if (cmnFlg == 1) {
						int idz = outLine.indexOf("*/");
						if (idz >= 0) {
							cmnFlg = 0;
							outLine = outLine.substring(idz + 2);
						} else {
							outLine = "";
						}
					}

					int idx = outLine.indexOf("//");
					int ida = outLine.indexOf("http://");
					if (ida < 0) {
						ida = outLine.indexOf("https://");
					}
					if (idx  >= 0) {
						if (ida < 0) {
							outLine = outLine.substring(0, idx);
						} else {
							if (idx < ida) {
								outLine = outLine.substring(0, idx);
							}
						}
					}

					int idy = outLine.indexOf("/*");
					String tmp = "";
					int iPrev = 0;
					LBL_CMN:while(idy >= 0) {
						tmp = tmp + outLine.substring(iPrev, idy);
						int idz = outLine.indexOf("*/", idy + 2);
						if (idz >= 0) {
							iPrev = idz + 2;
							idy = outLine.indexOf("/*", iPrev);
						} else {
							cmnFlg = 1;
							outLine = tmp;
							iPrev = 1;
							break LBL_CMN;
						}
					}
					if (iPrev >= 0) {
						tmp = tmp + outLine.substring(iPrev);
					}
					outLine = tmp;

					outLine = outLine.trim();
					if (outLine.length() > 0) {
						os.write(outLine.getBytes());
						lineByte = lineByte + outLine.length();
						os.write((" /*LINECNT* " + lineCnt + " *LINECNT*/ ").getBytes());
						if (outLine.endsWith(":") || outLine.endsWith("{") || outLine.endsWith("}") || outLine.endsWith("*/")) {
							os.write("\r\n".getBytes());
						}
						if (lineCnt >= 80) {
							os.write("\r\n".getBytes());
							lineCnt = 0;
						}
					}
				} // LBL_LINE
			} // java file
		} // for
	}
}
