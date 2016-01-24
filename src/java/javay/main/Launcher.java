package javay.main;

import javay.http.JavayHttp;
import javay.util.UFile;
import javay.xml.Dbjcalc;
import javay.xml.Website;
import javay.zip.UnpackZip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.xml.bind.JAXB;

public class Launcher {
  private static final Logger log = LoggerFactory.getLogger(Launcher.class);

  /**
   * main.
   * @param args String[]
   */
  public static void main(String[] args) {
    log.debug("----- begin -----");
    Launcher launcher = new Launcher();
    launcher.run();
    log.debug("-----   end -----");
  }

  /**
   * run.
   */
  public void run() {
    log.debug("----- begin -----");
    // 检查检查更新选项
    Dbjcalc conf = getConf();
    if (conf.getAutoUpdate().getisAutoUpdate()) {
      // 检查版本
      String curVer = conf.getCalcultor().getCurrentVersion();
      String url = "";
      List<Website> list = conf.getAutoUpdate().getWebsites();
      for (Website site : list) {
        if (site.isSelected()) {
          url = site.getAddress();
          break;
        }
      }
      log.debug("当前版本：" + curVer + ",地址：" + url);
      if (url.length() > 0) {
        JavayHttp http = new JavayHttp(url + "ReleaseNotes.txt");
        String nextVer = http.getInfo(":");
        log.debug("版本：" + nextVer);
        if (curVer.compareTo(nextVer) < 0) {
          int max = conf.getAutoUpdate().getRetry();
          log.debug("max：" + max);
          int icnt = 0;
          while (icnt < max) {
            icnt ++;
            // 下载新版本
            http = new JavayHttp(url + "build/" + nextVer + ".zip");
            // 检查下载内容
            boolean res = http.getModule("./tmp/" + nextVer + ".zip");
            if (res) {
              // 解压
              UnpackZip unpackZip = new UnpackZip();
              unpackZip.unpack("./tmp/" + nextVer + ".zip", "./tmp", "");
              // jar更新
              UFile.copyFile("./tmp/" + nextVer + ".jar", "./lib/" + nextVer + ".jar");
              UFile.removeFile("./tmp/" + nextVer + ".jar");
              UFile.removeFile("./tmp/" + nextVer + ".zip");

              // 配置文件更新
              conf.getCalcultor().setCurrentVersion(nextVer);
              saveConf(conf);
              break;
            }
          }
        }
      }
    }
    // 启动
    SwingUtilities.invokeLater(()-> {
      UIManager.put("swing.boldMetal", Boolean.FALSE);
      Calculator.createAndShowGui();
    });
    log.debug("-----   end -----");
  }

  /**
   * getConf.
   * @return Dbjcalc
   */
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

  /**
   * saveConf.
   * @param conf Dbjcalc
   */
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
