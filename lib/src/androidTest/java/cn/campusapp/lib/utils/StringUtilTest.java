package cn.campusapp.lib.utils;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import cn.campusapp.lib.BaseUnitTest;
import timber.log.Timber;

/**
 * Created by kris on 16/3/30.
 */
@RunWith(AndroidJUnit4.class)
public class StringUtilTest extends BaseUnitTest {


    /**
     * test to generate chinese word
     */
    @Test
    public void testGenerateRandomChineseWord(){
        String str = "";
        for(int i=0;i< 1000;i++){
            String ret = StringUtil.generateRandomChineseWord();
            str += ret;
        }
        Timber.i(str);
        Assert.assertTrue(str.length() == 1000);


    }

    @Test
    public void testGenerateEnglishChar(){
        String str = "";
        for(int i=0;i<1000;i++){
           str += StringUtil.generateRandomEnglishChar();
        }
        Timber.i(str);
        Assert.assertTrue(str.length() == 1000);

    }


    @Test
    public void testGenerateRandomChar(){
        String str = "";
        for(int i=0;i<1000;i++){
            str += StringUtil.generateRandomChar();
        }
        Timber.i(str);
        Assert.assertTrue(str.length() == 1000);
    }




}
