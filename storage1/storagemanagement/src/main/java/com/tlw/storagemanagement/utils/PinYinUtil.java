package com.tlw.storagemanagement.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {
    /**
     * @param china (字符串 汉字)
     * @return 汉字转拼音 其它字符不变
     */
    public static String getPinyin(String china){
        //设置拼音输出格式
        HanyuPinyinOutputFormat formart = new HanyuPinyinOutputFormat();
        //小写字母
        formart.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        //设置拼音的音调,WITHOUT_TONE不表示音调。如：wo
        formart.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //用字母v表示。如：lv
        formart.setVCharType(HanyuPinyinVCharType.WITH_V);
        char[] arrays = china.trim().toCharArray();
        String result = "";
        try {
            for (int i=0;i<arrays.length;i++) {
                char ti = arrays[i];
                if(Character.toString(ti).matches("[\\u4e00-\\u9fa5]")){ //匹配是否是中文
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(ti,formart);
                    result += temp[0];
                }else{
                    result += ti;
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Boolean isContainHanzi(String china){
        Boolean result = false;
        char[] arrays = china.trim().toCharArray();
        for (int i=0;i<arrays.length;i++) {
            char ti = arrays[i];
            if(Character.toString(ti).matches("[\\u4e00-\\u9fa5]")){ //匹配是否是中文
                result = true;
                break;
            }
        }
        return result;

    }
}
