package cn.campusapp.lib.generator;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.campusapp.lib.BaseUnitTest;
import cn.campusapp.lib.generator.classes.TestArrayClass;
import cn.campusapp.lib.generator.classes.TestClass;
import cn.campusapp.lib.generator.classes.TestClass2;
import cn.campusapp.lib.generator.classes.TestClass2Generator;
import cn.campusapp.lib.generator.classes.TestClass3;
import cn.campusapp.lib.generator.classes.TestClass4;
import cn.campusapp.lib.generator.classes.TestInterface;
import cn.campusapp.lib.generator.classes.TestInterfaceClass;

/**
 * Created by kris on 16/4/6.
 */
@RunWith(AndroidJUnit4.class)
public class ClassGeneratorTest extends BaseUnitTest{



    @Test
    public void testGenerateTestClass(){
        ClassGenerator<TestClass> generator = new ClassGenerator.Builder<>(TestClass.class)
                .build();
        for(int i=0;i<10;i++) {
            TestClass object = generator.generate();
        }
    }


    @Test
    public void testGenerateTestClass3(){
        ClassGenerator<TestClass3> generator1 = new ClassGenerator.Builder<>(TestClass3.class)
                .build();
        TestClass3 object = generator1.generate();
    }


    @Test
    public void testSetTypeGenerator(){
        ClassGenerator<TestClass2> generator = new ClassGenerator.Builder<>(TestClass2.class)
                .setTypeGenerator(new TestClass2Generator())
                .build();
        for(int i=0;i<10;i++){
            String tag = generator.generate().tag;
            Assert.assertTrue(tag, tag == null || "HAHA".equals(tag));
        }
    }

    @Test
    public void testSetSubClass(){
        List<Class<? extends TestInterface>> subClasses = new ArrayList<>();
        subClasses.add(TestInterfaceClass.class);
        ClassGenerator<TestInterface> generator = new ClassGenerator.Builder<>(TestInterface.class)
                .setSubClass(TestInterface.class, subClasses)
                .build();
        for(int i=0;i<1000;i++){
            TestInterface object = generator.generate();
            if(object != null) {
                Assert.assertTrue(object instanceof TestInterfaceClass);
            }
        }

        ClassGenerator<TestClass4> generator1 = new ClassGenerator.Builder<>(TestClass4.class)
                .setProportionOfNull(0)
                .setSubClass(TestInterface.class, subClasses)
                .build();

        for(int i=0;i<1000;i++){
            TestInterface class3 = generator1.generate().f1;
            Assert.assertTrue(class3 == null || class3 instanceof TestInterfaceClass);
        }
    }

    @Test
    public void testSetScaleOfNull(){
        ClassGenerator<TestClass2> generator = new ClassGenerator.Builder<>(TestClass2.class)
                .setProportionOfNull(0)
                .build();
        for(int i=0;i<1000;i++){
            Assert.assertTrue(generator.generate() != null);
        }


        ClassGenerator<TestClass2> generator1 = new ClassGenerator.Builder<>(TestClass2.class)
                .setProportionOfNull(1)
                .build();
        for(int i=0;i<1000;i++){
            Assert.assertTrue(generator1.generate() == null);
        }



        boolean hasGenerateNotNull = false;
        boolean hasGenerateNull = false;
        ClassGenerator<TestClass2> generator2 = new ClassGenerator.Builder<>(TestClass2.class)
                .setProportionOfNull(0.5f)
                .build();
        for(int i=0;i<1000;i++){
            TestClass2 generated = generator2.generate();
            if(generated != null){
                hasGenerateNotNull = true;
            } else {
                hasGenerateNull = true;
            }
        }
        Assert.assertTrue(hasGenerateNotNull && hasGenerateNull);
    }

    @Test
    public void testSetMaxLevel(){
        ClassGenerator<TestClass3> generator = new ClassGenerator.Builder<>(TestClass3.class)
                .setProportionOfNull(0)
            .build();
        Random random = new Random();
        for(int i=0;i<10;i++){
            int maxLevel = random.nextInt(5);
            generator.setMaxLayer(maxLevel);
            TestClass3 object = generator.generate();
            for(int j=0;j<=maxLevel;j++){
                object = object.clazz;
            }

            Assert.assertTrue(object == null);
        }
    }


    @Test
    public void testUseToGenerateEnum(){
        ClassGenerator<EnumGeneratorTest.TestEnum> generator = new ClassGenerator.Builder<>(EnumGeneratorTest.TestEnum.class)
                .setProportionOfNull(0.0f)
                .build();
        for(int i=0;i<1000;i++){
            Assert.assertTrue(generator.generate() != null);
        }
    }

    @Test
    public void testUseToGenerateArray(){
        ArrayGenerator<TestClass2> arrayGenerator = new ArrayGenerator.Builder<>(TestClass2[].class)
                .setProportionOfNull(0.0f)
                .setLength(11)
                .build();
        ClassGenerator<TestClass2[]> generator = new ClassGenerator.Builder<>(TestClass2[].class)
                .setTypeGenerator(arrayGenerator)
                .build();
        for(int i=0;i<100;i++){
            TestClass2[] values = generator.generate();
            Assert.assertTrue(values.length == 11);
        }
        ClassGenerator<TestClass2[]> generator1 = new ClassGenerator.Builder<>(TestClass2[].class)
                .build();
        for(int i=0;i<100;i++){
            TestClass2[] generated = generator1.generate();
            Assert.assertTrue(generated == null || generated.getClass().isArray());
        }


        ClassGenerator<TestArrayClass> generator2 = new ClassGenerator.Builder<>(TestArrayClass.class)
                .build();
        boolean hasGeneratedNotEmptyArray = false;
        for(int i=0;i<100;i++){
            TestArrayClass arrayClass = generator2.generate();
            if(arrayClass!= null && arrayClass.a != null && arrayClass.a.length > 0 && arrayClass.a[0] != null){
                hasGeneratedNotEmptyArray = true;
            }
        }
        Assert.assertTrue(hasGeneratedNotEmptyArray);


    }






}
