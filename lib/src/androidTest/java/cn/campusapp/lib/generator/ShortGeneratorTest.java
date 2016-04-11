package cn.campusapp.lib.generator;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.campusapp.lib.BaseUnitTest;

/**
 * Created by kris on 16/4/5.
 */
@RunWith(AndroidJUnit4.class)
public class ShortGeneratorTest extends BaseUnitTest {
    @Test
    public void testGenerateZero(){

        boolean hasGeneratePositive = false;
        boolean hasGenerateNegative = false;
        ShortGenerator generator = new ShortGenerator.Builder()
                .setGenerateZero(false)
                .build();
        for(int i=0;i<10000;i++){
            short generated = generator.generate();
            Assert.assertTrue(generated != 0);
            if(generated < 0){
                hasGenerateNegative = true;
            }
            if(generated > 0){
                hasGeneratePositive = true;
            }
        }
        Assert.assertTrue(hasGenerateNegative && hasGeneratePositive);
    }


    @Test
    public void testGenerateNegative(){

        ShortGenerator generator = new ShortGenerator.Builder()
                .setGenerateNegative(false)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator.generate() >= 0);
        }
    }

    @Test
    public void testGeneratePositive(){
        ShortGenerator generator = new ShortGenerator.Builder()
                .setGeneratePositive(false)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator.generate() <= 0);
        }
    }

    @Test
    public void testSetMaxBound(){
        Random random  = new Random();
        for(int i=0;i<100;i++) {
            short maxBound = (short)random.nextInt();
            ShortGenerator generator = new ShortGenerator.Builder()
                    .setMaxBound(maxBound)
                    .build();
            for(int j=0;j<1000;j++){
                short generated = generator.generate();
                Assert.assertTrue(generated <= maxBound);
            }
        }
    }


    @Test
    public void testSetMinBound(){
        Random random = new Random();
        for(int i=0;i<100;i++){
            short minBound = (short)random.nextInt();
            ShortGenerator generator = new ShortGenerator.Builder()
                    .setMinBound(minBound)
                    .build();
            for(int j=0;j<1000;j++){
                Assert.assertTrue(generator.generate() >=minBound);
            }
        }
    }

    @Test
    public void testSetPositiveValueSet(){
        List<Short> mValueSet = new ArrayList<>();
        mValueSet.add((short)1);
        mValueSet.add((short)5);
        mValueSet.add((short)10434);
        ShortGenerator generator = new ShortGenerator.Builder()
                .setPositiveValueSet(mValueSet)
                .build();
        for(int i=0;i<10000;i++){
            short generated = generator.generate();
            if(generated > 0) {
                Assert.assertTrue(mValueSet.contains(generated));
            }
        }

    }


    @Test
    public void testSetNegativeValueSet(){
        List<Short> mValueSet = new ArrayList<>();
        mValueSet.add((short)-1);
        mValueSet.add((short)-5);
        mValueSet.add((short)-330);
        ShortGenerator generator = new ShortGenerator.Builder()
                .setNegativeValueSet(mValueSet)
                .build();
        for(int i=0;i<10000;i++){
            short generated = generator.generate();
            if(generated <0) {
                Assert.assertTrue(mValueSet.contains(generated));
            }
        }
    }


    @Test
    public void testSetValueSet(){
        List<Short> mValueSet = new ArrayList<>();
        Random random = new Random();
        for(int i =0;i<1000;i++){
            mValueSet.add((short)random.nextInt());
        }

        ShortGenerator generator = new ShortGenerator.Builder()
                .setValueSet(mValueSet)
                .build();

        for(int i=0;i<10000;i++){
            Assert.assertTrue(mValueSet.contains(generator.generate()));
        }
    }


    @Test
    public void testSetGenerateScale(){
        boolean hasGenerateZero = false;
        boolean hasGeneratePositive = false;
        boolean hasGenerateNegative = false;
        ShortGenerator generator = new ShortGenerator.Builder()
                .setGenerateProportion(1, 1, 1)
                .build();
        for(int i=0;i< 10000;i++){
            short generated = generator.generate();
            if(generated < 0){
                hasGenerateNegative = true;
            }
            if(generated == 0){
                hasGenerateZero = true;
            }
            if(generated > 0){
                hasGeneratePositive = true;
            }
        }

        ShortGenerator generator1 = new ShortGenerator.Builder()
                .setGenerateProportion(1, 0, 0)
                .build();
        for(int i=0;i<10000;i++){
            short generated = generator1.generate();
            Assert.assertTrue(generated > 0);
        }

        ShortGenerator generator2 = new ShortGenerator.Builder()
                .setGenerateProportion(0, 0, 1)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator2.generate() < 0);
        }

        ShortGenerator generator3 = new ShortGenerator.Builder()
                .setGenerateProportion(1, 1, 0)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator3.generate() >=0);
        }

        Assert.assertTrue(hasGenerateNegative && hasGenerateZero && hasGeneratePositive);
    }
}
