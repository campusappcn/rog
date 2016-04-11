package cn.campusapp.rog.generator;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.campusapp.rog.BaseUnitTest;

/**
 * Created by kris on 16/4/1.
 */
@RunWith(AndroidJUnit4.class)
public class IntegerGeneratorTest extends BaseUnitTest{




    @Test
    public void testGenerateZero(){

        boolean hasGeneratePositive = false;
        boolean hasGenerateNegative = false;
        IntegerGenerator generator = new IntegerGenerator.Builder()
                .setGenerateZero(false)
                .build();
        for(int i=0;i<10000;i++){
            int generated = generator.generate();
            Assert.assertTrue(generated !=0 );
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

        IntegerGenerator generator = new IntegerGenerator.Builder()
                .setGenerateNegative(false)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator.generate() >= 0);
        }
    }

    @Test
    public void testGeneratePositive(){
        IntegerGenerator generator = new IntegerGenerator.Builder()
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
            int maxBound = random.nextInt();
            IntegerGenerator generator = new IntegerGenerator.Builder()
                    .setMaxBound(maxBound)
                    .build();
            for(int j=0;j<1000;j++){
                Assert.assertTrue(generator.generate() <= maxBound);
            }
        }
    }


    @Test
    public void testSetMinBound(){
        Random random = new Random();
        for(int i=0;i<100;i++){
            int minBound = random.nextInt();
            IntegerGenerator generator = new IntegerGenerator.Builder()
                    .setMinBound(minBound)
                    .build();
            for(int j=0;j<1000;j++){
                Assert.assertTrue(generator.generate() >=minBound);
            }
        }
    }

    @Test
    public void testSetPositiveValueSet(){
        List<Integer> mValueSet = new ArrayList<>();
        mValueSet.add(1);
        mValueSet.add(5);
        mValueSet.add(10434);
        IntegerGenerator generator = new IntegerGenerator.Builder()
                .setPositiveValueSet(mValueSet)
                .build();
        for(int i=0;i<10000;i++){
            int generated = generator.generate();
            if(generated > 0) {
                Assert.assertTrue(mValueSet.contains(generated));
            }
        }

    }


    @Test
    public void testSetNegativeValueSet(){
        List<Integer> mValueSet = new ArrayList<>();
        mValueSet.add(-1);
        mValueSet.add(-5);
        mValueSet.add(-330);
        IntegerGenerator generator = new IntegerGenerator.Builder()
                .setNegativeValueSet(mValueSet)
                .build();
        for(int i=0;i<10000;i++){
            int generated = generator.generate();
            if(generated <0) {
                Assert.assertTrue(mValueSet.contains(generated));
            }
        }
    }


    @Test
    public void testSetValueSet(){
        List<Integer> mValueSet = new ArrayList<>();
        Random random = new Random();
        for(int i =0;i<1000;i++){
            mValueSet.add(random.nextInt());
        }

        IntegerGenerator generator = new IntegerGenerator.Builder()
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
        IntegerGenerator generator = new IntegerGenerator.Builder()
                .setGenerateProportion(1, 1, 1)
                .build();
        for(int i=0;i< 10000;i++){
            int generated = generator.generate();
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

        IntegerGenerator generator1 = new IntegerGenerator.Builder()
                .setGenerateProportion(1, 0, 0)
                .build();
        for(int i=0;i<10000;i++){
            int generated = generator1.generate();
            Assert.assertTrue(generated > 0);
        }

        IntegerGenerator generator2 = new IntegerGenerator.Builder()
                .setGenerateProportion(0, 0, 1)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator2.generate() < 0);
        }

        IntegerGenerator generator3 = new IntegerGenerator.Builder()
                .setGenerateProportion(1, 1, 0)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator3.generate() >=0);
        }

        Assert.assertTrue(hasGenerateNegative && hasGenerateZero && hasGeneratePositive);
    }




}
