package javay.sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javay.util.Strings;

public class MergeMp3 {

    /**
     * in merge mp3 dir
     * out output file
     * log log
     * @param args
     */
    public static void main(String[] args) {
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
        if (outPath.exists() && !outPath.isDirectory()) {
            System.out.println("out isn't a Directory.");
            return ;
        }
        File log = new File(logFile);
        if (log.exists() && !log.isFile()) {
            System.out.println("log isn't a File.");
            return ;
        }

        proc(inPath, outPath, log);
    }

    public static void proc(File in, File out, File log) {
        File[] files = in.listFiles();
        for (File fl : files) {
            if (fl.isDirectory()) {
               System.out.println(fl.getName());
               proc(fl, out, log);
            }
            if (fl.isFile()) {
                System.out.println(fl.getAbsolutePath());
                splitMp3(fl, out, log);
            }
        }
    }

    public static void splitMp3(File in, File out, File log) {
        byte[] buffer;
        byte[] buf2;
        int byteread;
        long bytesum = 0;
        Id3v23Header id3header;
        Mp3FrameHeader mp3header;
        int nextsize = 0;
        long out_cnt = 0;
        long fileLength = in.length();
        try {
            System.out.println(in.getAbsolutePath() + ",size=" + fileLength);
            InputStream inStream = new FileInputStream(in);
            while((fileLength - bytesum) > 0) {
                buffer = new byte[4];
                if ( (byteread = inStream.read(buffer)) == -1) {
                    System.out.println("read error Mp3FrameHeader(4bytes)");
                    inStream.close();
                    System.out.println("read=" + bytesum);
                    return ;
                }
                bytesum  += byteread;
                mp3header = new Mp3FrameHeader(buffer);
                System.out.println("MP3Header=" + mp3header);

                if (!mp3header.isValid()) {
                   buf2 = new byte[6];
                    if ( (byteread = inStream.read(buf2)) == -1) {
                        System.out.println("read error ID3Frame(10bytes)");
                        inStream.close();
                        System.out.println("read=" + bytesum);
                        return ;
                    }
                    bytesum  += byteread;
                    byte[] tmp = new byte[10];
                    System.arraycopy(buffer, 0, tmp, 0, buffer.length);
                    System.arraycopy(buf2, 0, tmp, buffer.length, buf2.length);
                    id3header = new Id3v23Header(tmp);
                    System.out.println("ID3Header=" + id3header);
                    nextsize = (int) id3header.getId3Size();
                } else {
                    nextsize = mp3header.getSize() - 4;
                }

                buf2 = new byte[nextsize];
                if ( (byteread = inStream.read(buf2)) == -1) {
                    System.out.println("read error Mp3FrameHeader(4bytes)");
                    inStream.close();
                    System.out.println("read=" + bytesum);
                    return ;
                }
                bytesum  += byteread;
                if (mp3header.isValid()) {
                    byte[] tmp = new byte[nextsize + 4];
                    System.arraycopy(buffer, 0, tmp, 0, buffer.length);
                    System.arraycopy(buf2, 0, tmp, buffer.length, buf2.length);
                    String fileName = in.getName();
                    String outFile = out.getAbsolutePath() + "/" + fileName.substring(0, fileName.indexOf(".")) + "_" + Strings.format(String.valueOf(out_cnt), 5, '0') + ".mp3";
                    OutputStream os = new FileOutputStream(outFile);
                    os.write(tmp);
                    os.close();
                    out_cnt ++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("bytesum=" + bytesum);
    }
}
