package javay.sound.wav;
/**
 * RectWave.java
 * 矩形波を出力(繰り返して再生)
 *
 * $Id: RectWave.java 880 2004-11-24 06:43:26Z propella $
 *
 * @author YAMAMIYA Takasi
 * @version 0.1
 */

import javax.sound.sampled.*;
import java.io.IOException;

public class RectWave extends Thread {

    static int SAMPLE_RATE = 11025; // サンプルレート
    static AudioFormat FORMAT = new AudioFormat(SAMPLE_RATE, 8, 1, true, true); // 再生形式
    static int BUF_SIZE = SAMPLE_RATE / 10; // バッファサイズ(少ないほうが反応が良いが音飛びしやすい)

    byte[] data = new byte[BUF_SIZE]; // 波形データ
    float frequency;                // 周波数
    float amplitude;                // 波長(バイト数)
    SourceDataLine line = null; // 出力ライン

    boolean isRunning;          // 動作中なら真

    public RectWave (float _frequency) {
        setFrequency(_frequency);

        // ライン情報取得
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, FORMAT, BUF_SIZE);
        if(!AudioSystem.isLineSupported(info)){
            System.out.println("Line type not supported: "+info);
        }
        try {
            line = (SourceDataLine)AudioSystem.getLine(info); // 出力ライン取得
            line.open();
        } catch(LineUnavailableException e) {
            e.printStackTrace();
            return;
        }
    }

    int makeTone(byte[] _data) {            // 波形変更/作成 作成したデータの長さを返す
        int _amplitude = (int) amplitude;
        int length = (int) (_data.length / _amplitude * _amplitude); // 振幅の整数倍にする
        //      System.out.println(length);
        for(int i = 0; i < length; i++){
            if ((1.0 * i / _amplitude) - (i / _amplitude) > 0.5) {
                _data[i] = (byte)(120);
            } else {
                _data[i] = (byte)(-120);
            }
        }
        return length;
    }

    public void run() {         // 再生
        isRunning = true;
        line.start();
        while (isRunning) {
            int length = makeTone(data);            // 再生するたびにデータが壊れるので作り直す
            line.write(data, 0, length);
        }
        line.close();
    }

    void off() {                // 再生終了
        isRunning = false;
    }

    void setFrequency(float _frequency) { // 周波数を設定する
        frequency = _frequency;
        if (frequency <=0) return;
        amplitude = SAMPLE_RATE / frequency; // 振幅
    }

    void setVolume(float volume) {  // 音量設定 v は 0 から 1 までの小数
        // linearScalar = pow(10.0, gainDB/20.0) なのでややこしい
        FloatControl control = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
        double max = Math.pow(10.0, control.getMaximum()/20.0);
        double min = Math.pow(10.0, control.getMinimum()/20.0);
        double newValue = (max - min) * (volume * volume) + min;
        newValue = 20 * Math.log(newValue) / Math.log(10);
        control.setValue((float) newValue);
    }

    public static void main(String[] args) {

        RectWave tone = null;
        tone = new RectWave(440);
        tone.start();

        while (true) {
            System.out.println("周波数:"+ tone.frequency + " 波長:" + tone.amplitude + "/" + RectWave.SAMPLE_RATE);
            try {
                System.out.print("周波数を入力してください(Hz) [終了:リターン]> ");
                byte [] chars = new byte[256];
                int length = System.in.read(chars);
                tone.setFrequency(Integer.parseInt(new String(chars).substring(0, length - 1)));
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (NumberFormatException e) {
                break;
            }
        }
        tone.off();
        System.exit(0);
    }
}