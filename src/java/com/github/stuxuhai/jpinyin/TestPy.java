package com.github.stuxuhai.jpinyin;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;
/**
 * 问题间隔时间太长。
 * 原因：每个文件的读取耗时。
 * @author DBJ
 *
 */
public class TestPy {

    public static void main(String[] args) throws Exception {
        String str = "你好世界";
        str = "在老家上班，虽然不用挤春运，可也少了过年那份回家的期待。那种期待感其实挺美好的。";
//        str = "洪洞";
//        str = "特别高，高到你无法想象！";
//        str = "快过年了还叫我出来出差，关键是要处理的东西完全没接触过，我做起来一脸懵逼，这对客户也不负责任吧。";

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

        long l1 = System.nanoTime(), l2  = System.nanoTime(), l3 = System.nanoTime();
        String[] snd = str2.split(",");
        for (int i = 0; i < snd.length; i ++) {
            if (snd[i] == null || snd[i].equals("。") || snd[i].equals("，") || snd[i].equals("！")) {
                continue;
            }

            l1 = System.nanoTime();
            String filename = "./in/" + snd[i] + ".mp3";
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
            Player player = new Player(buffer);

            l2 = System.nanoTime();

            player.play();
            l3 = System.nanoTime();
            System.out.println("-----load:" + (l2 - l1) / 1000000 + "ms,play:" + (l3 - l2) / 1000000 + "ms.");
        }

//        PlayerMusic p1 = new PlayerMusic("./in/ni3.mp3");
//        PlayerMusic p2 = new PlayerMusic("./in/hao3.mp3");
//        PlayerMusic p3 = new PlayerMusic("./in/shi4.mp3");
//        PlayerMusic p4 = new PlayerMusic("./in/jie4.mp3");
//
//        p1.play();
//        p2.play();
//        p3.play();
//        p4.play();
    }
}
