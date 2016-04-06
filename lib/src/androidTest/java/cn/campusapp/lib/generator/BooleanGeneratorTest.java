package cn.campusapp.lib.generator;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import cn.campusapp.lib.BaseUnitTest;

/**
 * Created by kris on 16/4/1.
 */
@RunWith(AndroidJUnit4.class)
public class BooleanGeneratorTest extends BaseUnitTest {
    @Test
    public void testSetGenerateFalse(){
        IGenerator<Boolean> generator = new BooleanGenerator.Builder()
                .setGenerateFalse(false)
                .build();
        for(int i=0;i<1000;i++){
            Assert.assertTrue(generator.generate());
        }
    }

    @Test
    public void testSetGenerateTrue(){
        IGenerator<Boolean> generator = new BooleanGenerator.Builder()
                .setGenerateTrue(false)
                .build();

        for(int i=0;i<1000;i++){
            Assert.assertFalse(generator.generate());
        }
    }


    @Test
    public void testSetScale(){
        IGenerator<Boolean> generator = new BooleanGenerator.Builder()
                .setScale(0, 1)
                .build();
        for(int i=0;i<1000;i++){
            Assert.assertTrue(generator.generate());
        }

        IGenerator<Boolean> generator1 = new BooleanGenerator.Builder()
                .setScale(1, 0).build();
        for(int i=0;i<1000;i++){
            Assert.assertFalse(generator1.generate());
        }
        boolean hasGenerateFalse = false;
        boolean hasGenerateTrue = false;

        IGenerator<Boolean> generator2 = new BooleanGenerator.Builder()
                .setScale(1, 1).build();
        for(int i=0;i<1000;i++){
            if(generator2.generate()){
                hasGenerateTrue = true;
            } else {
                hasGenerateFalse = true;
            }
        }
        Assert.assertTrue(hasGenerateFalse && hasGenerateTrue);
    }

}
