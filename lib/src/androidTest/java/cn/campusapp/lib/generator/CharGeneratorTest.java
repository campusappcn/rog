package cn.campusapp.lib.generator;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import cn.campusapp.lib.BaseUnitTest;

/**
 * Created by kris on 16/4/5.
 */
@RunWith(AndroidJUnit4.class)
public class CharGeneratorTest extends BaseUnitTest{

    @Test
    public void testGenerateValue(){
        CharGenerator generator = new CharGenerator.Builder()
                .build();
        for(int i=0;i<10000;i++){
            generator.generate();
        }
    }


    @Test
    public void testSetMaxValue(){
        CharGenerator generator = new CharGenerator.Builder()
                .setMaxValue('A')
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator.generate() <= 'A');
        }
    }

    @Test
    public void testSetMinValue(){
        CharGenerator generator = new CharGenerator.Builder()
                .setMinValue('A')
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(generator.generate() >= 'A');
        }
    }

    @Test
    public void testSetValueSet(){
        List<Character> valueSet = new ArrayList<>();
        valueSet.add('A');
        valueSet.add('B');
        valueSet.add('C');
        CharGenerator generator = new CharGenerator.Builder()
                .setValueSet(valueSet)
                .build();
        for(int i=0;i<10000;i++){
            Assert.assertTrue(valueSet.contains(generator.generate()));
        }
    }






}
