package cn.campusapp.lib.factory;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import cn.campusapp.lib.BaseUnitTest;
import cn.campusapp.lib.generator.IGenerator;
import cn.campusapp.lib.generator.classes.TestIntegerGenerator;

/**
 * Created by kris on 16/4/6.
 */
@RunWith(AndroidJUnit4.class)
public class BasicTypeGeneratorFactoryTest extends BaseUnitTest{

    @Test
    public void testGetGenerator(){
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(Integer.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(int.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(Float.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(float.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(Character.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(char.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(Long.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(long.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(Short.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(short.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(Boolean.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(boolean.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(String.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(Byte.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(byte.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(Double.class) != null);
        Assert.assertTrue(BasicTypeGeneratorFactory.INSTANCE.getGenerator(double.class) != null);
    }

    @Test
    public void testSetGenerator(){
        BasicTypeGeneratorFactory factory = BasicTypeGeneratorFactory.INSTANCE;
        factory.setGenerator(new TestIntegerGenerator());
        IGenerator<Integer> generator = factory.getGenerator(Integer.class);
        for(int i=0;i<1000;i++){
            Assert.assertTrue(generator.generate() == 1);
        }

    }


}
