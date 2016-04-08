package cn.campusapp.rog.sample;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import cn.campusapp.lib.factory.BasicTypeGeneratorFactory;
import cn.campusapp.lib.generator.ArrayGenerator;
import cn.campusapp.lib.generator.BooleanGenerator;
import cn.campusapp.lib.generator.ByteGenerator;
import cn.campusapp.lib.generator.CharGenerator;
import cn.campusapp.lib.generator.ClassGenerator;
import cn.campusapp.lib.generator.DoubleGenerator;
import cn.campusapp.lib.generator.EnumGenerator;
import cn.campusapp.lib.generator.FloatGenerator;
import cn.campusapp.lib.generator.IntegerGenerator;
import cn.campusapp.lib.generator.InterfaceOrAbstractClassGenerator;
import cn.campusapp.lib.generator.LongGenerator;
import cn.campusapp.lib.generator.ShortGenerator;
import cn.campusapp.lib.generator.StringGenerator;
import cn.campusapp.lib.generator.classes.TestClass;
import cn.campusapp.lib.generator.classes.TestClass2;
import cn.campusapp.lib.generator.classes.TestClass2Generator;
import cn.campusapp.lib.generator.classes.TestEnum;
import cn.campusapp.lib.generator.classes.TestInferfaceClassGenerator;
import cn.campusapp.lib.generator.classes.TestIntegerGenerator;
import cn.campusapp.lib.generator.classes.TestInterface;
import cn.campusapp.lib.generator.classes.TestInterfaceClass;

/**
 * Created by kris on 16/4/7.
 */
@RunWith(AndroidJUnit4.class)
public class Samples{
    /**
     * set basic type generator
     */
    @Test
    public void testSetBasicTypeGenerator(){
        // Rog provide primitive type generators. But you can also change it with your implementation.
        BasicTypeGeneratorFactory.INSTANCE.setGenerator(new TestIntegerGenerator());
        //Then the Integer's generator will be TestIntegerGenerator
        //Or you can change the properties of the default primitive type generators
        IntegerGenerator generator = new IntegerGenerator.Builder()
                .setMinBound(0)
                .build();
        BasicTypeGeneratorFactory.INSTANCE.setGenerator(generator);
        //Then the values generated will be larger or equal to 0
    }


    @Test
    public void testArrayGenerator(){
        ArrayGenerator<TestClass2> generator = new ArrayGenerator.Builder<>( TestClass2[].class)
                .setGenerator(new TestClass2Generator())   //set the generator for the parameter class
                .setMaxLength(10)    //set the max length of array generated, default to 100
                .setLength(10)  //set a specific length for the array generated, default randomly
                .setProportionOfNull(0.1f)       //set proportion to generate null
                .build();
        TestClass2[] object = generator.generate();
    }


    @Test
    public void testBooleanGenerator(){
        BooleanGenerator generator = new BooleanGenerator.Builder()
                //.setGenerateTrue()        //set if the generator generates true, default to true
                //.setGenerateFalse(false)   //set if the generator generates false, default to false
                .setProportion(1, 1)   //set the proportion to generate true and false
                .build();
        boolean value = generator.generate();
    }

    @Test
    public void testByteGenerator(){
        List<Byte> valueSet = new ArrayList<>();
        valueSet.add((byte) 1);
        List<Byte> negativeSet = new ArrayList<>();
        negativeSet.add((byte) -1);
        ByteGenerator generator = new ByteGenerator.Builder()
                //.setGenerateNegative(false)  //set if the generator generates negative values, default to true
                //.setGeneratePositive(false)  //set if the generator generates positive values, default to true
                .setGenerateZero(false)  //set if the generator generates zero
                .setGenerateProportion(1, 1, 1)  //set proportion to generate positive, zero and negative values
                //.setValueSet(valueSet)  //values generated will fetched from the list
                .setNegativeValueSet(negativeSet)  //negative values generated will be fetched from the list
                .setPositiveValueSet(valueSet)   //positive values generated will be fetched from the list
                .setMaxBound((byte) 123)   //set the max bound of values generated
                .setMinBound((byte) -4)  //set the min bound of values generated
                .build();
        byte value = generator.generate();

    }

    @Test
    public void testCharGenerator(){
        List<Character> valuesSet = new ArrayList<>();
        valuesSet.add('A');
        valuesSet.add('B');
        CharGenerator generator = new CharGenerator.Builder()
                .setValueSet(valuesSet)  //values generated will be fetched from this set
                .setMinBound((char) 112)  //set the up bound of values generated
                .setMaxBound((char)1)   //set the low bound of values generated
                .build();
    }


    @Test
    public void testDoubleGenerator(){
        List<Double> valueSet = new ArrayList<>();
        valueSet.add(1.0d);
        List<Double> negativeSet = new ArrayList<>();
        negativeSet.add( -1.0d);
        DoubleGenerator generator = new DoubleGenerator.Builder()
                //.setGenerateNegative(false)  //set if the generator generates negative values, default to true
                //.setGeneratePositive(false)  //set if the generator generates positive values, default to true
                .setGenerateZero(false)  //set if the generator generates zero
                .setGenerateProportion(1, 1, 1)  //set proportion to generate positive, zero and negative values
                        //.setValueSet(valueSet)  //values generated will fetched from the list
                .setNegativeValueSet(negativeSet)  //negative values generated will be fetched from the list
                .setPositiveValueSet(valueSet)   //positive values generated will be fetched from the list
                .setMaxBound( 123.1d)   //set the max bound of values generated
                .setMinBound( -4.0d)  //set the min bound of values generated
                .build();
        double value = generator.generate();
    }
    @Test
    public void testEnumGenerate(){
        List<TestEnum> valueSet = new ArrayList<>();
        valueSet.add(TestEnum.v1);
        EnumGenerator<TestEnum> generator = new EnumGenerator.Builder<>(TestEnum.class)
                .setValueSet(valueSet)  //values generated will fetched from this set
                .setProportionOfNull(0.5f)  //the proportion to generate null
                .build();
        TestEnum value = generator.generate();
    }

    @Test
    public void testFloatGenerator(){
        List<Float> valueSet = new ArrayList<>();
        valueSet.add(1.0f);
        List<Float> negativeSet = new ArrayList<>();
        negativeSet.add(-1.0f);
        FloatGenerator generator = new FloatGenerator.Builder()
                //.setGenerateNegative(false)  //set if the generator generates negative values, default to true
                //.setGeneratePositive(false)  //set if the generator generates positive values, default to true
                .setGenerateZero(false)  //set if the generator generates zero
                .setGenerateProportion(1, 1, 1)  //set proportion to generate positive, zero and negative values
                        //.setValueSet(valueSet)  //values generated will fetched from the list
                .setNegativeValueSet(negativeSet)  //negative values generated will be fetched from the list
                .setPositiveValueSet(valueSet)   //positive values generated will be fetched from the list
                .setMaxBound(123.1f)   //set the max bound of values generated
                .setMinBound(-4.0f)  //set the min bound of values generated
                .build();
        float value = generator.generate();
    }


    @Test
    public void testIntegerGenerator(){
        List<Integer> valueSet = new ArrayList<>();
        valueSet.add(1);
        List<Integer> negativeSet = new ArrayList<>();
        negativeSet.add(-1);
        IntegerGenerator generator = new IntegerGenerator.Builder()
                //.setGenerateNegative(false)  //set if the generator generates negative values, default to true
                //.setGeneratePositive(false)  //set if the generator generates positive values, default to true
                .setGenerateZero(false)  //set if the generator generates zero
                .setGenerateProportion(1, 1, 1)  //set proportion to generate positive, zero and negative values
                        //.setValueSet(valueSet)  //values generated will fetched from the list
                .setNegativeValueSet(negativeSet)  //negative values generated will be fetched from the list
                .setPositiveValueSet(valueSet)   //positive values generated will be fetched from the list
                .setMaxBound(123)   //set the max bound of values generated
                .setMinBound(-4)  //set the min bound of values generated
                .build();
        int value = generator.generate();
    }


    @Test
    public void testInterfaceOrAbstractClassGenerator(){
        List<Class<? extends TestInterface>> classes = new ArrayList<>();
        classes.add(TestInterfaceClass.class);
        List<TestInterface> objects = new ArrayList<>();
        objects.add(new TestInterfaceClass());
        InterfaceOrAbstractClassGenerator<TestInterface> generator = new InterfaceOrAbstractClassGenerator.Builder<>(TestInterface.class)
                //set the subclasses of TestInterface.
                // If the framework can't find subclasses of any interface or abstract classes,
                // it will throw RuntimeException
                .setClassSet(classes)
                .setTypeGenerator(new TestInferfaceClassGenerator())   //set the generator for TestInterfaceClass
                .setScaleOfNull(0.1f)   //set proportion  to generate null, default to 0.1f
                .setObjectSet(objects)  //the values gerated will be fetched from this set
                .build();
        TestInterface value = generator.generate();
    }


    @Test
    public void testLongGenerator(){
        List<Long> valueSet = new ArrayList<>();
        valueSet.add(1l);
        List<Long> negativeSet = new ArrayList<>();
        negativeSet.add(-1l);
        LongGenerator generator = new LongGenerator.Builder()
                //.setGenerateNegative(false)  //set if the generator generates negative values, default to true
                //.setGeneratePositive(false)  //set if the generator generates positive values, default to true
                .setGenerateZero(false)  //set if the generator generates zero
                .setGenerateProportion(1, 1, 1)  //set proportion to generate positive, zero and negative values
                        //.setValueSet(valueSet)  //values generated will fetched from the list
                .setNegativeValueSet(negativeSet)  //negative values generated will be fetched from the list
                .setPositiveValueSet(valueSet)   //positive values generated will be fetched from the list
                .setMaxBound(123l)   //set the max bound of values generated
                .setMinBound(-4l)  //set the min bound of values generated
                .build();
        long value = generator.generate();
    }

    @Test
    public void testShortGenerator(){
        List<Short> valueSet = new ArrayList<>();
        valueSet.add((short) 1);
        List<Short> negativeSet = new ArrayList<>();
        negativeSet.add((short) -1);
        ShortGenerator generator = new ShortGenerator.Builder()
                //.setGenerateNegative(false)  //set if the generator generates negative values, default to true
                //.setGeneratePositive(false)  //set if the generator generates positive values, default to true
                .setGenerateZero(false)  //set if the generator generates zero
                .setGenerateProportion(1, 1, 1)  //set proportion to generate positive, zero and negative values
                        //.setValueSet(valueSet)  //values generated will fetched from the list
                .setNegativeValueSet(negativeSet)  //negative values generated will be fetched from the list
                .setPositiveValueSet(valueSet)   //positive values generated will be fetched from the list
                .setMaxBound((short) 123)   //set the max bound of values generated
                .setMinBound((short) -4)  //set the min bound of values generated
                .build();
        short value = generator.generate();
    }


    @Test
    public void testStringGenerator(){
        List<Character> charSet = new ArrayList<>();
        charSet.add('A');
        List<String> valueSet = new ArrayList<>();
        valueSet.add("A");
        StringGenerator generator = new StringGenerator.Builder()
                .setCharSet(charSet)  //the character of String generated will be fetched from this set
                .setValueSet(valueSet)  //string generated will be fetched from the set
                .setMaxLength(11)  //set the maxLength of the String generated
                //.setGenerateNotEmpty(false) //set if the generator generates not empty string, default true
                .setGenerateNull(false)  //set if the generator generates null, default true
                .setGenerateEmpty(false)  //set if the generates empty string, default true
                .setChineseProportion(1, 1)  //set the proportion of chinese to english when the mode is MODE_NORMAL
                //MODE_RANDOM = 0;//generate unicode char randomly
                //MODE_ASCII = 1;  //generate only ascii char
                //MODE_NORMAL = 2; //generate only ascii char and chinese
                .setMode(StringGenerator.MODE_NORMAL)  //set generate mode, default to MODE_NORMAL
                .build();
        String value = generator.generate();
    }


    @Test
    public void testClassGenerator(){
        List<Class<? extends TestInterface>> subClasses = new ArrayList<>();
        subClasses.add(TestInterfaceClass.class);
        ClassGenerator<TestClass> generator = new ClassGenerator.Builder<>(TestClass.class)
                .setProportionOfNull(0.1f)  //set the proportion to generate null, default to 0.1f
                //Set generator of type (generator.getClassToGenerate()).It will use the generator to generate objects of this type afterwards.
                //If the generator is a instance of ClassGenerator, it may not make effect. So implement a IGenerator yourself.
                .setTypeGenerator(new TestClass2Generator())
                //set the max layer of classes referencing tree. For example, Class1 references Class2, Class1's layer is 1 and Class2's layer is 1.
                //If max layer is 1. Then Class2 can't generate any more object except primitive type and its field of class types will be null.
                //Max level ensures the process of object generating will stop and avoids stack overflow.
                .setMaxLevel(4)
                //set subClasses of clazz.When the generator needs to generate this interface or abstract class, it will
                //generate object of one of the classes in the subClass set.
                .setSubClass(TestInterface.class, subClasses)
                .build();
        TestClass value = generator.generate();



        //you can also use ClassGenerator to generate object of primitive type, enum, interface, abstract class and array.
        ClassGenerator<Byte> generator1 = new ClassGenerator.Builder<>(Byte.class)
                .build();

        ClassGenerator<Byte[]> generator2 = new ClassGenerator.Builder<>(Byte[].class)
                .build();

        ClassGenerator<TestEnum> generator3 = new ClassGenerator.Builder<>(TestEnum.class)
                .build();

        ClassGenerator<TestInterface> generator4 = new ClassGenerator.Builder<>(TestInterface.class)
                .setSubClass(TestInterface.class, subClasses)
                .build();
    }






}
