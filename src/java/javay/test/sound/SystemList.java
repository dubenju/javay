package javay.test.sound;

import java.awt.GraphicsEnvironment;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

public class SystemList {

    public static void main(String[] args) {
	SystemList proc = new SystemList();
	proc.listCharsets();
	proc.listFonts();
	proc.listMixers();
    }

    public void listCharsets() {
	System.out.println("查看系统中的全部Charset");
	    SortedMap<String,Charset> charsets = Charset.availableCharsets();
	    Set<String> names = charsets.keySet();
	    for (Iterator<String> e = names.iterator(); e.hasNext();) {
	      String name = (String) e.next();
	      Charset charset = (Charset) charsets.get(name);
	      System.out.println(charset);
	      Set<String> aliases = charset.aliases();
	      for (Iterator<String> ee = aliases.iterator(); ee.hasNext();) {
	        System.out.println("    " + ee.next());
	      }
	    }
	    System.out.println();
    }
    public void listFonts() {
        System.out.println("查看系统中的全部字体");
        GraphicsEnvironment grapEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNameList = grapEnv.getAvailableFontFamilyNames();
        for (int i = 0; i < fontNameList.length; i++) {
            System.out.println(fontNameList[i]);
        }
        System.out.println();
    }

    public void listMixers() {
        System.out.println("查看系统中的全部混音器");
        Mixer.Info[] a = AudioSystem.getMixerInfo();
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i].getName());
        }
        System.out.println();
    }
}
