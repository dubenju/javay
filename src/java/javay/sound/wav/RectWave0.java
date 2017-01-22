package javay.sound.wav;
/**
 * RectWave0.java
 * 矩形波を出力(一回だけ出力)
 *
 * $Id: RectWave0.java 880 2004-11-24 06:43:26Z propella $
 *
 * @author YAMAMIYA Takasi
 * @version 0.1
 */

import javax.sound.sampled.*;
import java.io.IOException;

public class RectWave0 {

    static int SAMPLE_RATE = 11025; // サンプルレート
    static AudioFormat FORMAT = new AudioFormat(SAMPLE_RATE, 8, 1, true, true); // 再生形式

    public static void main(String[] args) {

        // 準備
        if (args.length < 1) {
            System.out.println("java RectWave0 freqency (Hz)");
            System.exit(1);
        }

        byte[] data = new byte[SAMPLE_RATE]; // 波形データ
        int freqency = Integer.parseInt(args[0]);
        int amplitude = SAMPLE_RATE / freqency; // 振幅
        System.out.println("freqency: "+freqency +" amplitude: "+ amplitude);

        // ライン情報取得(意味は良く分からないがお約束)
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, FORMAT);
        if(!AudioSystem.isLineSupported(info)){
            System.out.println("Line type not supported: "+info);
        }
        SourceDataLine line = null; // 出力ライン
        try {
            line = (SourceDataLine)AudioSystem.getLine(info);
            line.open();
        } catch(LineUnavailableException e) {
            System.out.println(e);
            System.exit(1);
        }

        // 波形作成
        for(int i = 0; i < data.length; i++){
            if ((1.0 * i / amplitude) - (i / amplitude) > 0.5) {
                data[i] = (byte)(120);
            } else {
                data[i] = (byte)(-120);
            }
        }

        line.start();
        line.write(data, 0, data.length);
        line.drain();
        System.exit(0);
    }
}