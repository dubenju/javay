package javay.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javay.http.JavayHttp;
import javay.xml.Dbjcalc;
import javay.zip.UnpackZip;

public class Launcher {
	private static final Logger log = LoggerFactory.getLogger(Launcher.class);
	public static void main(String[] args) {
		log.debug("----- begin -----");
		Launcher launcher = new Launcher();
		launcher.run();
		log.debug("-----   end -----");
	}

	public void run() {
		log.debug("----- begin -----");
		// 检查检查更新选项
		Dbjcalc conf = getConf();
		if (conf.getAutoUpdate().getisAutoUpdate()) {
			// 检查版本
			String curVer = conf.getCalcultor().getCurrentVersion();
			JavayHttp http = new JavayHttp("https://raw.githubusercontent.com/dubenju/javay/master/ReleaseNotes.txt");
			String nextVer = http.getInfo(":");
			if (curVer.compareTo(nextVer) < 0) {
				int max = conf.getAutoUpdate().getRetry();
				int i = 0;
				while(i < max) {
					i ++;
					// 下载新版本
					http = new JavayHttp("https://github.com/dubenju/javay/raw/master/lib/slf4j-1.7.13.tar.gz");
					// 检查下载内容
					boolean res = http.getModule("./tmp/slf4j-1.7.13.tar.gz");
					if (res) {
						// 解压
						UnpackZip unpackZip = new UnpackZip();
						unpackZip.unpack("./tmp/slf4j-1.7.13.tar.zip", "./tmp", "");
						// jar更新
						// 配置文件更新
						conf.getCalcultor().setCurrentVersion(nextVer);
						saveConf(conf);
						break;
					}
				}
			}
		}
		// 启动
		log.debug("-----   end -----");
	}
	
	public static Dbjcalc getConf() {
		log.debug("----- begin -----");
		Dbjcalc conf = null;
        InputStream inStream = null;
		try {
			inStream = new FileInputStream("./conf/dbjcalc.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        conf = JAXB.unmarshal(inStream, Dbjcalc.class);
        try {
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        log.debug("-----   end -----");
        return conf;
	}
	public static void saveConf(Dbjcalc conf) {
		log.debug("----- begin -----");
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream("./conf/dbjcalc.xml");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		JAXB.marshal(conf, fs);
		try {
			fs.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        log.debug("-----   end -----");
	}
}
