package com.github.stuxuhai.jpinyin;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;

/**
 * 问题还是稍有间隙。如果是声音文件的问题则需要编辑声音文件。声音的加载，解码与播放要搞成多线程？
 * 另一个问题有个别失音现象。因为采样率不一样的原因。
 * @author DBJ
 *
 */
public class TestPy1 {

    public static void main(String[] args) throws Exception {
        String str = "你好世界";
        str = "在老家上班，虽然不用挤春运，可也少了过年那份回家的期待。那种期待感其实挺美好的。";
//        str = "洪洞";
//        str = "特别高，高到你无法想象！";
//        str = "快过年了还叫我出来出差，关键是要处理的东西完全没接触过，我做起来一脸懵逼，这对客户也不负责任吧。";
//        str = "曾小明小朋友，早上好"; // NG:céng,xiǎo,míng,xiǎo,péng,yǒu,，,zǎo,shàng,hǎo
//        str = "好";

        long st = System.nanoTime();
        String str1 = PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_MARK);
        long st1 = System.nanoTime();
        String str2 = PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_NUMBER);
        long st2 = System.nanoTime();
        String str3 = PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITHOUT_TONE);
        long st3 = System.nanoTime();

        System.out.println(str1 + ":" + (st1 - st) / 1000000); // nǐ,hǎo,shì,jiè
        System.out.println(str2 + ":" + (st2 - st1) / 1000000); // ni3,hao3,shi4,jie4
        System.out.println(str3 + ":" + (st3 - st2) / 1000000); // ni,hao,shi,jie
        System.out.println(PinyinHelper.getShortPinyin(str)); // nhsj





        FactoryRegistry r = FactoryRegistry.systemRegistry();
        AudioDevice audio = r.createAudioDevice();

        long l1 = System.nanoTime(), l2  = System.nanoTime(), l3 = System.nanoTime();
        String[] snd = str2.split(",");
        for (int i = 0; i < snd.length; i ++) {
            if (snd[i] == null || snd[i].equals("。") || snd[i].equals("，") || snd[i].equals("！")) {
                continue;
            }
            try {
                l1 = System.nanoTime();
                String filename = "./in/" + snd[i] + ".mp3";
                Bitstream bits = new Bitstream(new BufferedInputStream(new FileInputStream(filename)));
                Decoder decoder = new Decoder();
                audio.open(decoder);

                l2 = System.nanoTime();

                int frames = Integer.MAX_VALUE;
                boolean ret = true;
                while (frames-- > 0 && ret) {
                    Header h = bits.readFrame();
                    if (h == null) {
                        ret = false;
                    }
                    if (ret) {
                        // 解码
                        SampleBuffer output = (SampleBuffer) decoder.decodeFrame(h, bits);
                        audio.write(output.getBuffer(), 0, output.getBufferLength());
                        // 播放
                        bits.closeFrame();
                    }
                }
                // 保证最后一个的播放
                if (!ret) {
                    audio.flush();
                }
                l3 = System.nanoTime();
                System.out.println("-----load:" + (l2 - l1) / 1000000 + "ms,play:" + (l3 - l2) / 1000000 + "ms.");
                bits.close();
            } catch (FileNotFoundException ex){
                System.out.println(ex);
            }
        }
        audio.close();
    }
}
