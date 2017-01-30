package javay.sound;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import javay.util.UBytes;

public class PlayWave {
    public static void write(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian, byte[] wave_data, String filename) {
        AudioFormat   frmt= new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        System.out.println(PlayWave.class.getName() + ":" + frmt);
        ByteArrayInputStream bais = new ByteArrayInputStream(wave_data);
        AudioInputStream ais  = new AudioInputStream(bais, frmt, wave_data.length / frmt.getFrameSize());
        //定义最终保存的文件名
        File file = null;
        //写入文件
        try {
//            //以当前的时间命名录音的名字
//            //将录音的文件存放到F盘下语音文件夹下
//            File filePath = new File("C:/sdfile");
//            if(!filePath.exists()) {
//                //如果文件不存在，则创建该目录
//                filePath.mkdir();
//            }
            file = new File(filename);
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //关闭流
            try {
                if(bais != null) {
                    bais.close();
                }
                if(ais != null) {
                    ais.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void play1(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian, byte[] wave_data) {
        // System.out.println("@PlayWave::play sampleRate=" + sampleRate + ",sampleSizeInBits=" + sampleSizeInBits + ",channels=" + channels + ",signed=" + signed + ",bigEndian=" + bigEndian);
        AudioFormat   frmt= new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        System.out.println(PlayWave.class.getName() + ":" + frmt);
        DataLine.Info info= new DataLine.Info(Clip.class, frmt);
        try {
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(frmt, wave_data, 0, wave_data.length);
            clip.start();
            long st = System.currentTimeMillis();
            Thread.sleep(100);
            while(clip.isRunning()) {
                Thread.sleep(100);
            }
            long en = System.currentTimeMillis() - st;
            System.out.println("@PlayWave::play 耗时:" + en + "ms");
        } catch(Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public static void play(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian, byte[] wave_data) {
        // System.out.println("@PlayWave::play sampleRate=" + sampleRate + ",sampleSizeInBits=" + sampleSizeInBits + ",channels=" + channels + ",signed=" + signed + ",bigEndian=" + bigEndian);
        AudioFormat   frmt= new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        System.out.println(PlayWave.class.getName() + ":" + frmt);
        DataLine.Info info= new DataLine.Info(SourceDataLine.class, frmt);
        try {
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(frmt);
            line.start();
            long st = System.currentTimeMillis();
            line.write(wave_data, 0, wave_data.length);
            long en = System.currentTimeMillis() - st;
            System.out.println("@PlayWave::play 耗时:" + en + "ms");
            line.drain();
            line.close();
        } catch(Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public static void print(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian, byte[] wave_data) {
        System.out.println("@PlayWave::print sampleRate=" + sampleRate + ",sampleSizeInBits=" + sampleSizeInBits + ",channels=" + channels + ",signed=" + signed + ",bigEndian=" + bigEndian);
        if (channels > 1) {
            System.out.println("每声道样本总数:" + (wave_data.length / channels) + (wave_data.length % channels));
        }
        int numPerChl = wave_data.length / channels;
        int byteSample = sampleSizeInBits / 8;
//        int step = byteSample * channels;
        byte[] sample = new byte[byteSample];
//        int max = numPerChl / byteSample;
        for (int i = 0; i < wave_data.length; i += byteSample) {
            if (i % channels == 0) {
                System.arraycopy(wave_data, i, sample, 0, byteSample);
                int s = UBytes.toInt(sample, 2);
                System.out.print(((short) s) + " ");
                if (i % 16 == 0) {
                    System.out.println();
                }
            }
            if (i % channels == 1) {
                System.arraycopy(wave_data, i, sample, 0, byteSample);
                int s = UBytes.toInt(sample, 2);
            }
        }
    }
    public static int[] b2i(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian, byte[] wave_data, int o) {
        System.out.println("@PlayWave::print sampleRate=" + sampleRate + ",sampleSizeInBits=" + sampleSizeInBits + ",channels=" + channels + ",signed=" + signed + ",bigEndian=" + bigEndian);
        if (channels > 1) {
            System.out.println("每声道样本总数:" + (wave_data.length / channels) + (wave_data.length % channels));
        }
        int numPerChl = wave_data.length / channels;
        int[] res = new int[numPerChl];
        int byteSample = sampleSizeInBits / 8;
//        int step = byteSample * channels;
        byte[] sample = new byte[byteSample];
//        int max = numPerChl / byteSample;
        int oi = 0;
        for (int i = 0; i < wave_data.length; i += byteSample) {
            if (i % channels == 0) {
                System.arraycopy(wave_data, i, sample, 0, byteSample);
                int s = UBytes.toInt(sample, 2);
                res[oi] = s;
                oi ++;
//                System.out.print(((short) s) + " ");
//                if (i % 16 == 0) {
//                    System.out.println();
//                }
            }
            if (i % channels == 1) {
                System.arraycopy(wave_data, i, sample, 0, byteSample);
                int s = UBytes.toInt(sample, 2);
            }
        }
        return res;
    }
}
