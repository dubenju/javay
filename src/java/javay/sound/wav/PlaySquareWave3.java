package javay.sound.wav;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class PlaySquareWave3 {
    static int SAMPLE_RATE = 44100; // 44.1KHz
    static AudioFormat audio_format;
    static DataLine.Info info;
    static SourceDataLine line;
    public static void main(String[] args)
            throws LineUnavailableException {
        // オーディオ形式を指定
        audio_format = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
        info = new DataLine.Info(
                SourceDataLine.class, audio_format);
        line = (SourceDataLine)AudioSystem.getLine(info);
        line.open();
        line.start();
        // 演奏
        writeNote(523.25); // C
        writeNote(587.33); // D
        writeNote(659.26); // E
    }
    public static void writeNote(double frequency) {
        byte[] b = new byte[SAMPLE_RATE];
        for (int i = 0; i < b.length; i++) {
            double r = i / (SAMPLE_RATE / frequency);
            b[i] = (byte)((Math.round(r) % 2 == 0) ? 100 : -100);
        }
        line.write(b, 0, b.length);
        line.drain(); // 終了まで待機
    }
}
