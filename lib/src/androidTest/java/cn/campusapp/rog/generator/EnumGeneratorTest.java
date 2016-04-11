package cn.campusapp.rog.generator;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import cn.campusapp.rog.BaseUnitTest;

/**
 * Created by kris on 16/4/5.
 */
@RunWith(AndroidJUnit4.class)
public class EnumGeneratorTest extends BaseUnitTest{


    @Test
    public void testGenerateValue(){
        EnumGenerator<TestEnum> generator = new EnumGenerator.Builder<TestEnum>(TestEnum.class)
                .build();
        boolean hasGeneratedNotNull = false;
        boolean hasGeneratedNull = false;
        for(int i=0;i<1000;i++){
            TestEnum value = generator.generate();
            if(value == null ){
                hasGeneratedNull = true;
            } else{
                hasGeneratedNotNull = true;
            }
        }
        Assert.assertTrue(hasGeneratedNotNull && hasGeneratedNull);
    }

    @Test
    public void testSetValueSet(){
        List<TestEnum> testEnums = new ArrayList<>();
        testEnums.add(TestEnum.T1);
        testEnums.add(TestEnum.T3);
        testEnums.add(TestEnum.T5);
        EnumGenerator<TestEnum> generator = new EnumGenerator.Builder<TestEnum>(TestEnum.class)
                .setValueSet(testEnums)
                .setProportionOfNull(0.0f)
                .build();
        for(int i=0;i<1000;i++){
            Assert.assertTrue(testEnums.contains(generator.generate()));
        }
    }


    @Test
    public void testSetScaleOfNull(){
        EnumGenerator<TestEnum> generator = new EnumGenerator.Builder<>(TestEnum.class)
                .setProportionOfNull(0.0f)
                .build();
        for(int i=0;i<1000;i++){
            Assert.assertTrue(generator.generate() != null);
        }
    }



    enum TestEnum{
        T1, T2, T3, T4, T5;
    }



}
