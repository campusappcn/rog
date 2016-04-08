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
public class LongGeneratorTest extends BaseUnitTest {

    @Test
    public void testGenerateZero(){

        boolean hasGeneratePositive = false;
        boolean hasGenerateNegative = false;
        LongGenerator generator = new LongGenerator.Builder()
                .setGenerateZero(false)
                .build();
        for(int i=0;i<10000;i++){
            long generated = generator.generate();
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

        LongGenerator generator = new LongGenerator.Builder()
                .setGenerateNegative(false)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator.generate() >= 0);
        }
    }

    @Test
    public void testGeneratePositive(){
        LongGenerator generator = new LongGenerator.Builder()
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
            long maxBound = random.nextLong();
            LongGenerator generator = new LongGenerator.Builder()
                    .setMaxBound(maxBound)
                    .build();
            for(int j=0;j<10000;j++){
                Assert.assertTrue(generator.generate() < maxBound);
            }
        }
    }


    @Test
    public void testSetMinBound(){
        Random random = new Random();
        for(int i=0;i<100;i++){
            long minBound = random.nextLong();
            LongGenerator generator = new LongGenerator.Builder()
                    .setMinBound(minBound)
                    .build();
            for(int j=0;j<10000;j++){
                Assert.assertTrue(generator.generate() >=minBound);
            }
        }
    }

    @Test
    public void testSetPositiveValueSet(){
        List<Long> mValueSet = new ArrayList<>();
        mValueSet.add(1l);
        mValueSet.add(5l);
        mValueSet.add(10434l);
        LongGenerator generator = new LongGenerator.Builder()
                .setPositiveValueSet(mValueSet)
                .build();
        for(int i=0;i<10000;i++){
            long generated = generator.generate();
            if(generated > 0) {
                Assert.assertTrue(mValueSet.contains(generated));
            }
        }

    }


    @Test
    public void testSetNegativeValueSet(){
        List<Long> mValueSet = new ArrayList<>();
        mValueSet.add(-1l);
        mValueSet.add(-5l);
        mValueSet.add(-330l);
        LongGenerator generator = new LongGenerator.Builder()
                .setNegativeValueSet(mValueSet)
                .build();
        for(int i=0;i<10000;i++){
            long generated = generator.generate();
            if(generated <0) {
                Assert.assertTrue(mValueSet.contains(generated));
            }
        }
    }


    @Test
    public void testSetValueSet(){
        List<Long> mValueSet = new ArrayList<>();
        Random random = new Random();
        for(int i =0;i<1000;i++){
            mValueSet.add(random.nextLong());
        }

        LongGenerator generator = new LongGenerator.Builder()
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
        LongGenerator generator = new LongGenerator.Builder()
                .setGenerateProportion(1, 1, 1)
                .build();
        for(int i=0;i< 10000;i++){
            long generated = generator.generate();
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

        LongGenerator generator1 = new LongGenerator.Builder()
                .setGenerateProportion(1, 0, 0)
                .build();
        for(int i=0;i<10000;i++){
            long generated = generator1.generate();
            Assert.assertTrue(generated > 0);
        }

        LongGenerator generator2 = new LongGenerator.Builder()
                .setGenerateProportion(0, 0, 1)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator2.generate() < 0);
        }

        LongGenerator generator3 = new LongGenerator.Builder()
                .setGenerateProportion(1, 1, 0)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator3.generate() >=0);
        }

        Assert.assertTrue(hasGenerateNegative && hasGenerateZero && hasGeneratePositive);
    }


}
