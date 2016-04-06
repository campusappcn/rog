package cn.campusapp.lib.generator;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import cn.campusapp.lib.BaseUnitTest;
import cn.campusapp.lib.generator.classes.TestInferfaceClassGenerator;
import cn.campusapp.lib.generator.classes.TestInterface;
import cn.campusapp.lib.generator.classes.TestInterfaceClass;

/**
 * Created by kris on 16/4/6.
 */
@RunWith(AndroidJUnit4.class)
public class InterfaceOrAbstractClassGeneratorTest extends BaseUnitTest{


    @Test
    public void testSetObjectSet(){
        List<TestInterface>  objects = new ArrayList<>();
        for(int i=0;i<100;i++){
            objects.add(new TestInterfaceClass());
        }

        InterfaceOrAbstractClassGenerator<TestInterface> generator = new InterfaceOrAbstractClassGenerator
                .Builder<>(TestInterface.class)
                .setObjectSet(objects)
                .build();

        for(int i=0;i<1000;i++){
            TestInterface object = generator.generate();
            if(object != null) {
                Assert.assertTrue(objects.contains(object));
            }
        }
    }


    @Test
    public void testSetClassSet(){
        List<Class<? extends TestInterface>> classes = new ArrayList<>();
        classes.add(TestInterfaceClass.class);
        InterfaceOrAbstractClassGenerator<TestInterface> generator = new InterfaceOrAbstractClassGenerator.Builder<>(TestInterface.class)
                .setClassSet(classes)
                .build();
        for(int i=0;i<1000;i++){
            TestInterface object = generator.generate();
            Assert.assertTrue(object == null || object instanceof TestInterfaceClass);
        }

    }

    @Test
    public void testSetTypeGenerator(){
        List<Class<? extends TestInterface>> classes = new ArrayList<>();
        classes.add(TestInterfaceClass.class);
        InterfaceOrAbstractClassGenerator<TestInterface> generator = new InterfaceOrAbstractClassGenerator.Builder<>(TestInterface.class)
                .setClassSet(classes)
                .setClassGenerator(new TestInferfaceClassGenerator())
                .build();
        for(int i=0;i<1000;i++){
            TestInterface object = generator.generate();
            if(object != null && object instanceof TestInterfaceClass){
                Assert.assertTrue(((TestInterfaceClass) object).f1 == 10);
            }
        }
    }

    @Test
    public void testSetScaleOfNull(){
        List<Class<? extends TestInterface>> classes = new ArrayList<>();
        classes.add(TestInterfaceClass.class);
        InterfaceOrAbstractClassGenerator<TestInterface> generator = new InterfaceOrAbstractClassGenerator.Builder<>(TestInterface.class)
                .setClassSet(classes)
                .build();

        generator.setScaleOfNull(0);
        for(int i=0;i<1000;i++){
            Assert.assertTrue(generator.generate() != null);
        }

        generator.setScaleOfNull(1);
        for(int i=0;i<1000;i++){
            Assert.assertTrue(generator.generate() == null);
        }

        boolean hasGenerateNull = false;
        boolean hasGenerateNotNull = false;
        generator.setScaleOfNull(0.5f);
        for(int i=0;i<1000;i++){
            TestInterface object = generator.generate();
            if(object == null){
                hasGenerateNull = true;
            } else{
                hasGenerateNotNull = true;
            }
        }
        Assert.assertTrue(hasGenerateNotNull && hasGenerateNull);
    }


}
