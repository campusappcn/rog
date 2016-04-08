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
public class ByteGeneratorTest extends BaseUnitTest {
    @Test
    public void testGenerateZero(){

        boolean hasGeneratePositive = false;
        boolean hasGenerateNegative = false;
        ByteGenerator generator = new ByteGenerator.Builder()
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

        ByteGenerator generator = new ByteGenerator.Builder()
                .setGenerateNegative(false)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator.generate() >= 0);
        }
    }

    @Test
    public void testGeneratePositive(){
        ByteGenerator generator = new ByteGenerator.Builder()
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
            byte maxBound = (byte)random.nextInt();
            ByteGenerator generator = new ByteGenerator.Builder()
                    .setMaxBound(maxBound)
                    .build();
            for(int j=0;j<1000;j++){
                byte generated = generator.generate();
                Assert.assertTrue(generated <= maxBound);
            }
        }
    }


    @Test
    public void testSetMinBound(){
        Random random = new Random();
        for(int i=0;i<100;i++){
            byte minBound = (byte)random.nextInt();
            ByteGenerator generator = new ByteGenerator.Builder()
                    .setMinBound(minBound)
                    .build();
            for(int j=0;j<1000;j++){
                Assert.assertTrue("MinBound "+ minBound, generator.generate() >=minBound);
            }
        }
    }

    @Test
    public void testSetPositiveValueSet(){
        List<Byte> mValueSet = new ArrayList<>();
        mValueSet.add((byte)1);
        mValueSet.add((byte)5);
        mValueSet.add((byte)10434);
        ByteGenerator generator = new ByteGenerator.Builder()
                .setPositiveValueSet(mValueSet)
                .build();
        for(int i=0;i<10000;i++){
            byte generated = generator.generate();
            if(generated > 0) {
                Assert.assertTrue(mValueSet.contains(generated));
            }
        }

    }


    @Test
    public void testSetNegativeValueSet(){
        List<Byte> mValueSet = new ArrayList<>();
        mValueSet.add((byte)-1);
        mValueSet.add((byte)-5);
        mValueSet.add((byte)-330);
        ByteGenerator generator = new ByteGenerator.Builder()
                .setNegativeValueSet(mValueSet)
                .build();
        for(int i=0;i<10000;i++){
            byte generated = generator.generate();
            if(generated <0) {
                Assert.assertTrue(mValueSet.contains(generated));
            }
        }
    }


    @Test
    public void testSetValueSet(){
        List<Byte> mValueSet = new ArrayList<>();
        Random random = new Random();
        for(int i =0;i<1000;i++){
            mValueSet.add((byte)random.nextInt());
        }

        ByteGenerator generator = new ByteGenerator.Builder()
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
        ByteGenerator generator = new ByteGenerator.Builder()
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

        ByteGenerator generator1 = new ByteGenerator.Builder()
                .setGenerateProportion(1, 0, 0)
                .build();
        for(int i=0;i<10000;i++){
            short generated = generator1.generate();
            Assert.assertTrue(generated > 0);
        }

        ByteGenerator generator2 = new ByteGenerator.Builder()
                .setGenerateProportion(0, 0, 1)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator2.generate() < 0);
        }

        ByteGenerator generator3 = new ByteGenerator.Builder()
                .setGenerateProportion(1, 1, 0)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator3.generate() >=0);
        }

        Assert.assertTrue(hasGenerateNegative && hasGenerateZero && hasGeneratePositive);
    }
}
