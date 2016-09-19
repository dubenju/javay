package javay.sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CatFile {


    /**
     * in merge mp3 dir
     * out output file
     * log log
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String inDir = "";
        String outDir = "";
        String logFile = "";

        for(String arg : args) {
            System.out.println(arg);
            if (arg.startsWith("in=")) {
                inDir = arg.substring(3);
                continue;
            }
            if (arg.startsWith("out=")) {
                outDir = arg.substring(4);
                continue;
            }
            if (arg.startsWith("log=")) {
                logFile = arg.substring(4);
                continue;
            }
        }
        System.out.println("inDir=" + inDir);
        System.out.println("outDir" + outDir);
        System.out.println("logFile=" + logFile);

        File inPath = new File(inDir);
        if (!inPath.exists()) {
            System.out.println("in isn't exists.");
            return ;
        }
        if ( !inPath.isDirectory()) {
            System.out.println("in isn't a Directory.");
            return ;
        }
        File outPath = new File(outDir);
        if (outPath.exists() && !outPath.isFile()) {
            System.out.println("out isn't a File.");
            return ;
        }
        File log = new File(logFile);
        if (log.exists() && !log.isFile()) {
            System.out.println("log isn't a File.");
            return ;
        }

        proc(inPath, outPath, log);
    }

    public static void proc(File in, File out, File log) throws Exception {
        File[] files = in.listFiles();
        // String outFile = out.getAbsolutePath() + "/" + fileName.substring(0, fileName.indexOf(".")) + "_" + Strings.format(String.valueOf(out_cnt), 5, '0') + ".mp3";
        OutputStream os = new FileOutputStream(out);
        for (File fl : files) {
//            if (fl.isDirectory()) {
//               System.out.println(fl.getName());
//               proc(fl, out, log);
//            }
            if (fl.isFile()) {
                System.out.println(fl.getAbsolutePath());
                byte[] buf = new byte[(int) fl.length()];
                InputStream is = new FileInputStream(fl);
                is.read(buf);
                is.close();
                os.write(buf);
            }
        }
        os.close();
    }

}
