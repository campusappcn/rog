package cn.campusapp.lib.utils;

import java.util.Random;

import timber.log.Timber;

/**
 * Created by kris on 16/3/25.
 */
public class StringUtil {

    private static final String ENGLISH_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 随机生成一个中文字
     *
     * 原理是从汉字区位码找到汉字。在汉字区位码中分高位与底位， 且其中简体又有繁体。位数越前生成的汉字繁体的机率越大。
     * 所以在本例中高位从171取，底位从161取， 去掉大部分的繁体和生僻字。但仍然会有！！
     *
     *
     * @return
     */
    public static String generateRandomChineseWord() {

        String str = null;
        int hightPos, lowPos; // 定义高低位
        Random random = new Random();
        hightPos = (176 + Math.abs(random.nextInt(39)));//获取高位值
        lowPos = (161 + Math.abs(random.nextInt(93)));//获取低位值
        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos).byteValue());
        b[1] = (Integer.valueOf(lowPos).byteValue());
        try {
            str = new String(b, "GBk");//转成中文
        } catch (Exception e) {
            Timber.e(e, "随机生成中文字符出错");
        }
        return str;
    }

    public static Character generateRandomEnglishChar(){
        Random random = new Random();
        return ENGLISH_CHARS.charAt(random.nextInt(ENGLISH_CHARS.length()));
    }

}
