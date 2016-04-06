package cn.campusapp.lib.factory;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import cn.campusapp.lib.BaseUnitTest;
import cn.campusapp.lib.generator.classes.TestClass2;
import cn.campusapp.lib.generator.classes.TestClass2Generator;

/**
 * Created by kris on 16/4/6.
 */
@RunWith(AndroidJUnit4.class)
public class TypeGeneratorFactoryTest extends BaseUnitTest{


    @Test
    public void testSetGenerator(){
        TypeGeneratorFactory factory = TypeGeneratorFactory.getFactory();
        factory.setGenerator(new TestClass2Generator());
        Assert.assertTrue(factory.getGenerator(TestClass2.class) instanceof TestClass2Generator);
    }
}
