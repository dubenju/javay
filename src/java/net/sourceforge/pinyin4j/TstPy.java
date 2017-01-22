package net.sourceforge.pinyin4j;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

public class TstPy {

    public static void main(String[] args) throws Exception {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

     // UPPERCASE：大写  (ZHONG)
     // LOWERCASE：小写  (zhong)
     format.setCaseType(HanyuPinyinCaseType.LOWERCASE);

     // WITHOUT_TONE：无音标  (zhong)
     // WITH_TONE_NUMBER：1-4数字表示英标  (zhong4)
     // WITH_TONE_MARK：直接用音标符（必须WITH_U_UNICODE否则异常）  (zhòng)
     format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);

     // WITH_V：用v表示ü  (nv)
     // WITH_U_AND_COLON：用"u:"表示ü  (nu:)
     // WITH_U_UNICODE：直接用ü (nü)
     format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

     String[] pinyin = PinyinHelper.toHanyuPinyinStringArray('重', format);
    }

}
