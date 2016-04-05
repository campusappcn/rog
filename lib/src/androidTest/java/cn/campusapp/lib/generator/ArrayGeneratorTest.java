package cn.campusapp.lib.generator;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import cn.campusapp.lib.BaseUnitTest;

/**
 * Created by kris on 16/4/5.
 */
@RunWith(AndroidJUnit4.class)
public class ArrayGeneratorTest extends BaseUnitTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testSetLength() {
        ArrayGenerator<Double> generator = new ArrayGenerator
                .Builder<Double>((Class<Double[]>) (new Double[10]).getClass())
                .setLength(10)
                .build();


        Double[] doubles = generator.generate();
        Assert.assertEquals(doubles.length, 10);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSetGenerator() {
        Class<TestObject[]> clazz = (Class<TestObject[]>) (new TestObject[10]).getClass();
        ArrayGenerator<TestObject> generator = new ArrayGenerator
                .Builder<TestObject>(clazz)
                .setGenerator(new TestGenerator())
                .build();
        TestObject[] tests = generator.generate();
        for (TestObject object : tests) {
            Assert.assertTrue(object.mTag.equals("HH"));
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSetMaxLength() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            // [ 1, 100]
            int maxLength = 1 + random.nextInt(100);

            ArrayGenerator<Double> generator = new ArrayGenerator
                    .Builder<>((Class<Double[]>) (new Double[10]).getClass())
                    .setMaxLength(maxLength)
                    .build();
            for (int j = 0; j < 100; j++) {
                Double[] doubles = generator.generate();
                Assert.assertTrue(doubles.length < maxLength);
            }
        }
    }


    private static class TestObject {
        public String mTag;

        public TestObject(String tag) {
            mTag = tag;
        }
    }

    private static class TestGenerator implements IGenerator<ArrayGeneratorTest.TestObject> {

        @Override
        public ArrayGeneratorTest.TestObject generate() {
            return new ArrayGeneratorTest.TestObject("HH");
        }

        @Override
        public Class<?> getClassToGenerate() {
            return ArrayGeneratorTest.TestObject.class;
        }
    }


}
