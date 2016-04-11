package cn.campusapp.rog.utils;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import cn.campusapp.rog.BaseUnitTest;

/**
 * Created by kris on 16/3/30.
 */
@RunWith(AndroidJUnit4.class)
public class RandomExtendUtilTest extends BaseUnitTest{

    Random mRandom = new Random();
    @Test
    public void testNextLong(){
        for(int i=0;i<10000;i++){
            long r = mRandom.nextLong();
            r = r < 0 ? -r : r;
            r = r == 0 ? r + 1 : r;
            long value = RandomExtendUtil.nextLong(mRandom, r);
            Assert.assertTrue(value < r && value >= 0);
        }
    }


    @Test
    public void testNextByte(){
        for(int i=0;i<10000;i++){
            byte r = (byte) (mRandom.nextInt(Byte.MAX_VALUE)+1);
            byte value = RandomExtendUtil.nextByte(mRandom, r);
            Assert.assertTrue(value < (r<0?-r:r) && value >= 0);
        }
    }

    @Test
    public void testNextShort(){
        for(int i=0;i<10000;i++){
            short r = (short) (mRandom.nextInt(Short.MAX_VALUE)+1);
            short value = RandomExtendUtil.nextShort(mRandom, r);
            Assert.assertTrue(value < (r < 0 ? -r : r) && value >= 0);
        }
    }

    @Test
    public void testNextFloat(){
        for(int i=0;i<10000;i++){
            float r = mRandom.nextFloat() + 1.4f;
            float value = RandomExtendUtil.nextFloat(mRandom, r);
            Assert.assertTrue(value < r && value >= 0);
        }
    }



    @Test
    public void testNextDouble(){
        for(int i=0;i<10000;i++){
            double r = mRandom.nextDouble() + 14.3d;
            double value = RandomExtendUtil.nextDouble(mRandom, r);
            Assert.assertTrue(value < r && value >= 0);
        }
    }





}
