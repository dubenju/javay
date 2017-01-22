package javay.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import javay.util.UBytes;

public class PlayWave {

    public static void play(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian, byte[] wave_data) {
        System.out.println("@PlayWave::play sampleRate=" + sampleRate + ",sampleSizeInBits=" + sampleSizeInBits + ",channels=" + channels + ",signed=" + signed + ",bigEndian=" + bigEndian);
        AudioFormat   frmt= new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
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
