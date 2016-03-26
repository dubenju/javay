package javay.sound.wav;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class PlaySquareWave {
    public static void main(String[] args) throws LineUnavailableException {
        // オーディオ形式を指定
        int SAMPLE_RATE = 44100; // 44.1KHz
        AudioFormat audio_format = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audio_format);
        SourceDataLine line = (SourceDataLine)AudioSystem.getLine(info);
        line.open();
        line.start();
        // バイト列に適当な矩形波を作成
        int frequency = 440; // ---------------------- (*1)
        byte[] b = new byte[SAMPLE_RATE];
        for (int i = 0; i < b.length; i++) {
            int r = i / (SAMPLE_RATE / frequency);
            b[i] = (byte)((r % 2 == 0) ? 100 : -100);
        }
        // 再生(バイト列を line に書き込む)
        line.write(b, 0, b.length);
        line.drain(); // 終了まで待機
    }
}
