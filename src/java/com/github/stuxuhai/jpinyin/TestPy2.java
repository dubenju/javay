package com.github.stuxuhai.jpinyin;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import net.sourceforge.lame.mp3.Main;
import javay.sound.PlayWave;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;

/**
 *
 * @author DBJ
 *
 */
public class TestPy2 {

    public static void main(String[] args) throws Exception {
        String str = "你好世界";
        str = "在老家上班，虽然不用挤春运，可也少了过年那份回家的期待。那种期待感其实挺美好的。";
//        str = "洪洞";
//        str = "特别高，高到你无法想象！";
//        str = "快过年了还叫我出来出差，关键是要处理的东西完全没接触过，我做起来一脸懵逼，这对客户也不负责任吧。";
//        str = "曾小明小朋友，早上好"; // NG:céng,xiǎo,míng,xiǎo,péng,yǒu,，,zǎo,shàng,hǎo
//        str = "下班喽，明天回家";
//        str = "好";
        str = "产业链已经不止一次的传出消息，今年苹果要推出的iPhone 8要支持无线充电功能，这虽说这个功能已经在不少安卓手机上实现，但果粉对于它的实现还是兴奋不已。当然了，苹果的魅力在于，对于现有技术重新包装，改进使用体验，并最终推动整个行业的发展，比如iPhone 8上的无线充电功能，其最大的魅力在于真正的摆脱线材，实现无拘束的无线充电。";
//        str = "大年初一";
        str = "祝大家一帆风顺，二龙腾飞，三羊开泰，四季平安，五福临门，六六大顺，七星高照，八方来财，九九同心，十全十美，百事亨通，千事吉祥，万事如意!";

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


        AudioFormat output_fmt = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        long l1 = System.nanoTime(), l2  = System.nanoTime(), l3 = System.nanoTime();
        String[] snd = str2.split(",");
        for (int i = 0; i < snd.length; i ++) {
            System.out.println(snd[i]);
            if (snd[i] == null || snd[i].equals("。") || snd[i].equals("，")
             || snd[i].equals("！") || snd[i].equals("!")) {
                continue;
            }

            try {

                l1 = System.nanoTime();
                String filename = "./in/" + snd[i] + ".mp3";
                Bitstream bits = new Bitstream(new BufferedInputStream(new FileInputStream(filename)));
                Decoder decoder = new Decoder();

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
                        if (output_fmt == null) {
                            // float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian
                            output_fmt = new AudioFormat(output.getSampleFrequency(), 16, output.getChannelCount(), true, false);
                            byte[] obys = toByteArray(output.getBuffer(), 0, output.getBufferLength());
                            baos.write(obys, 0, output.getBufferLength() * 2);
                        } else {
                            if (output.getSampleFrequency() != output_fmt.getSampleRate()) {
                                AudioFormat old_fmt = new AudioFormat(output.getSampleFrequency(), 16, output.getChannelCount(), true, false);
//                                System.out.println("old fmt:" + old_fmt);
//                                System.out.println("new fmt:" + output_fmt);
                                if (AudioSystem.isConversionSupported(output_fmt, old_fmt)) {
                                    byte[] buf_tmp = toByteArray(output.getBuffer(), 0, output.getBufferLength());
                                    ByteArrayInputStream bais = new ByteArrayInputStream(buf_tmp);
                                    AudioInputStream sourceStream = new AudioInputStream(bais, old_fmt, buf_tmp.length / old_fmt.getFrameSize());

                                    AudioInputStream stream = AudioSystem.getAudioInputStream(output_fmt, sourceStream);
//                                    System.out.println(stream.available());
//                                    System.out.println(stream.getFormat());
                                    byte[] tnp = new byte[4096];
                                    int size = 0;
                                    while((size = stream.read(tnp, 0, tnp.length)) != -1) {
                                        baos.write(tnp, 0, size);
                                    }
                                } else {
                                    System.out.println("is NOT ConversionSupported");
                                }
                            } else {
                                byte[] obys = toByteArray(output.getBuffer(), 0, output.getBufferLength());
                                baos.write(obys, 0, output.getBufferLength() * 2);
                            }
                        }
                        bits.closeFrame();
                    }
                }
                l3 = System.nanoTime();
                System.out.println("-----load:" + (l2 - l1) / 1000000 + "ms,play:" + (l3 - l2) / 1000000 + "ms.");
                bits.close();
                baos.close();
            } catch (FileNotFoundException ex){
                System.out.println(ex);
            }
        }
        byte[] pbys = baos.toByteArray();
        // PlayWave.play(output_fmt.getSampleRate(), 16, output_fmt.getChannels(), true,  false, pbys);
        String filename = "./out/" + System.currentTimeMillis() + ".wav";
        PlayWave.write(output_fmt.getSampleRate(), 16, output_fmt.getChannels(), true,  false, pbys, filename);
        Main.main(new String[] {filename, filename + ".mp3"});
    }
    static byte[] toByteArray(short[] samples, int offs, int len)  {
        byte[] b = new byte[len * 2 + 1024];
        int idx = 0;
        short s;
        while (len-- > 0) {
            s = samples[offs++];
            b[idx++] = (byte) s;
            b[idx++] = (byte) (s>>>8);
        }
        return b;
    }
}
