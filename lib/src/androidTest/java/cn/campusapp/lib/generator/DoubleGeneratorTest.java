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
public class DoubleGeneratorTest extends BaseUnitTest {



    @Test
    public void testGenerateZero(){

        boolean hasGeneratePositive = false;
        boolean hasGenerateNegative = false;
        DoubleGenerator generator = new DoubleGenerator.Builder()
                .setGenerateZero(false)
                .build();
        for(int i=0;i<10000;i++){
            double generated = generator.generate();
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

        DoubleGenerator generator = new DoubleGenerator.Builder()
                .setGenerateNegative(false)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator.generate() >= 0);
        }
    }

    @Test
    public void testGeneratePositive(){
        DoubleGenerator generator = new DoubleGenerator.Builder()
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
            double maxBound = random.nextDouble();
            DoubleGenerator generator = new DoubleGenerator.Builder()
                    .setMaxBound(maxBound)
                    .build();
            for(int j=0;j<10000;j++){
                double generated = generator.generate();
                Assert.assertTrue(generated <= maxBound);
            }
        }
    }


    @Test
    public void testSetMinBound(){
        Random random = new Random();
        for(int i=0;i<100;i++){
            double minBound = random.nextDouble();
            DoubleGenerator generator = new DoubleGenerator.Builder()
                    .setMinBound(minBound)
                    .build();
            for(int j=0;j<10000;j++){
                Assert.assertTrue(generator.generate() >=minBound);
            }
        }
    }

    @Test
    public void testSetPositiveValueSet(){
        List<Double> mValueSet = new ArrayList<>();
        mValueSet.add(1d);
        mValueSet.add(5d);
        mValueSet.add(10434d);
        DoubleGenerator generator = new DoubleGenerator.Builder()
                .setPositiveValueSet(mValueSet)
                .build();
        for(int i=0;i<10000;i++){
            double generated = generator.generate();
            if(generated > 0) {
                Assert.assertTrue(mValueSet.contains(generated));
            }
        }

    }


    @Test
    public void testSetNegativeValueSet(){
        List<Double> mValueSet = new ArrayList<>();
        mValueSet.add(-1d);
        mValueSet.add(-5d);
        mValueSet.add(-330d);
        DoubleGenerator generator = new DoubleGenerator.Builder()
                .setNegativeValueSet(mValueSet)
                .build();
        for(int i=0;i<10000;i++){
            double generated = generator.generate();
            if(generated <0) {
                Assert.assertTrue(mValueSet.contains(generated));
            }
        }
    }


    @Test
    public void testSetValueSet(){
        List<Double> mValueSet = new ArrayList<>();
        Random random = new Random();
        for(int i =0;i<1000;i++){
            mValueSet.add(random.nextDouble());
        }

        DoubleGenerator generator = new DoubleGenerator.Builder()
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
        DoubleGenerator generator = new DoubleGenerator.Builder()
                .setGenerateScale(1, 1, 1)
                .build();
        for(int i=0;i< 10000;i++){
            double generated = generator.generate();
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

        DoubleGenerator generator1 = new DoubleGenerator.Builder()
                .setGenerateScale(1, 0, 0)
                .build();
        for(int i=0;i<10000;i++){
            double generated = generator1.generate();
            Assert.assertTrue(generated > 0);
        }

        DoubleGenerator generator2 = new DoubleGenerator.Builder()
                .setGenerateScale(0, 0, 1)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator2.generate() < 0);
        }

        DoubleGenerator generator3 = new DoubleGenerator.Builder()
                .setGenerateScale(1, 1, 0)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator3.generate() >=0);
        }

        Assert.assertTrue(hasGenerateNegative && hasGenerateZero && hasGeneratePositive);
    }

}
