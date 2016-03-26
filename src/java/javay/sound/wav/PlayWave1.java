package javay.sound.wav;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class PlayWave1 {
    // 音名を定数で表したもの
    static int C=0,CS=1,D=2,DS=3,E=4,F=5,FS=6,G=7,GS=8,A=9,AS=10,B=11;
    // 音名から NoteNo を計算する
    static int getNoteNo(int octave, int noteName) {
        return octave * 12 + noteName;
    }
    // 周波数を計算してテーブルにセットする
    static double note_freq[] = new double[128];
    static void calcFrequency() {
        // 半音(2の12乗根)を計算
        double r = Math.pow(2.0, 1.0 / 12.0);
        // A(NoteNo=69) より上の音を計算
        note_freq[69] = 440.0; // A のノート
        for (int i = 70; i < 128; i++) {
            note_freq[i] = note_freq[i-1] * r;
        }
        // A(NoteNo=69) より下の音を計算
        for (int i = 68; i >= 0; i--) {
            note_freq[i] = note_freq[i+1] / r;
        }
        // 一覧を表示
        for (int i = 0; i < 128; i++) {
            System.out.println(i + "," + note_freq[i]);
        }
        return;
    }
    static double getNotenoToFreq(int octave, int noteName) {
        return note_freq[getNoteNo(octave, noteName)];
    }
    // 波形のパラメータなどを指定
    static int SAMPLE_RATE = 44100; // 44.1KHz
    static SourceDataLine line;
    public static void main(String[] args)
        throws LineUnavailableException {
        calcFrequency();//周波数を計算してテーブルにセットする
        // オーディオの初期化
        AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
        line = (SourceDataLine)AudioSystem.getLine(info);
        // デバイスを開く
        line.open();
        line.start();
        // 演奏
        writeNote(getNotenoToFreq(6,C));
        writeNote(getNotenoToFreq(6,D));
        writeNote(getNotenoToFreq(6,E));
        writeNote(getNotenoToFreq(6,F));
        writeNote(getNotenoToFreq(6,G));
        writeNote(getNotenoToFreq(6,A));
        writeNote(getNotenoToFreq(6,B));
        writeNote(getNotenoToFreq(7,C));
        line.drain(); // 終了まで待機
    }
    public static void writeNote(double frequency) {
        // バイト列に適当な矩形波を作成
        byte[] b = new byte[SAMPLE_RATE];
        double amplitude = SAMPLE_RATE / frequency; // 波長
        for (int i = 0; i < b.length; i++) {
            double r = i / amplitude;
            b[i] = (byte)((Math.round(r) % 2 == 0) ? 100 : -100);
        }
        // 再生(バイト列を line に書き込む)
        line.write(b, 0, b.length);
    }
}
