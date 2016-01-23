package javay.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javay.math.BigNumRound;

public class Jaxb {

  /**
   * main.
   * @param args String[]
   */
  public static void main(String[] args) {
    final Dbjcalc dbjcalc = new Dbjcalc();
    Calcultor calc = new Calcultor();
    calc.setCurrentVersion("dbjcalc-0.0.0");
    calc.setDecimalLength(40);
    calc.setRoundMode(BigNumRound.HALF_EVENT);

    final AutoUpdate update = new AutoUpdate();
    final List<Website> websites = new ArrayList<Website>();
    Website github = new Website();
    github.setName("github");
    github.setAddress("https://github.com/dubenju/javay/raw/master/build/javay-0.0.1.zip");
    github.setSelected(true);

    Website sourceforge = new Website();
    sourceforge.setName("sourceforge");
    sourceforge.setAddress("https://github.com/dubenju/javay/raw/master/build/javay-0.0.1.zip");

    Website oschina = new Website();
    oschina.setName("oschina");
    oschina.setAddress("https://github.com/dubenju/javay/raw/master/build/javay-0.0.1.zip");

    websites.add(github);
    websites.add(sourceforge);
    websites.add(oschina);
    update.setisAutoUpdate(true);
    update.setWebsites(websites);
    update.setRetry(2);
    dbjcalc.setCalcultor(calc);
    dbjcalc.setAutoUpdate(update);
    javax.xml.bind.JAXB.marshal(dbjcalc, System.out);

    InputStream inStream = null;
    try {
      inStream = new   FileInputStream("./conf/dbjcalc.xml");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    Dbjcalc conf = javax.xml.bind.JAXB.unmarshal(inStream, Dbjcalc.class);
    System.out.println(conf);
  }

}
