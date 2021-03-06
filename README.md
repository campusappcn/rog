# Random object generator
Rog is an object generator designed for android test. It can create objects and set random values to their fields.

[![](https://jitpack.io/v/campusappcn/rog.svg)](https://jitpack.io/#campusappcn/rog)
[![Build Status](https://travis-ci.org/campusappcn/rog.svg?branch=master)](https://travis-ci.org/campusappcn/rog)
[![codecov.io](https://codecov.io/github/campusappcn/rog/coverage.svg?branch=master)](https://codecov.io/github/campusappcn/rog?branch=master)

## 中文设计文档
[Android随机对象生成器的设计与实现](http://sixwolf.net/blog/2016/04/08/Android%E9%9A%8F%E6%9C%BA%E5%AF%B9%E8%B1%A1%E7%94%9F%E6%88%90%E5%99%A8%E7%9A%84%E8%AE%BE%E8%AE%A1%E4%B8%8E%E5%AE%9E%E7%8E%B0/)
## Purpose
It's annoied to make fake data when writing test cases. So Rog is the rescue. You can use it to get random data in test. And combined with gson and mock, you can get fake result without the server. Then you can do test work easily without server's implementation.   

## Features  

- The main feature of Rog is to create objects with random values of their fields.
- Primitive types can be generated with default generators. These generators' behavior can be customized, and even replaced by your own implementations.
- Special types including enum, interface, abstract class and array are also well supported.


## Usage
### ClassGenerator
You can use ClassGenerator to generate any class you want even it's an array or enum, the special types we metioned above. 

```java
		List<Class<? extends TestInterface>> subClasses = new ArrayList<>();
        subClasses.add(TestInterfaceClass.class);
        ClassGenerator<TestClass> generator = new ClassGenerator.Builder<>(TestClass.class)
                .setProportionOfNull(0.1f)  //Set the proportion to generate null, default 0.1f
                //Set generator of type (generator.getClassToGenerate()).
                //It will use the generator to generate objects of this type afterwards.
                .setTypeGenerator(new TestClass2Generator())
                //Set the max layer of classes referencing tree. 
                .setMaxLayer(4)
                //Set sub classes of interface or abstract class. 
                .setSubClass(TestInterface.class, subClasses)
                .build();
        TestClass value = generator.generate();
```
#### Attention
Function ```setMaxLayer(int)``` can be used to set max depth of fields to generate.

The generator generates fields recusively, so the generating process may never stop until stack overflows.  For example, if TestClass has a field of itself, then it needs to generates another object of TestClass and the new object also needs to generate a new object. Never stop. So it needs to add a limition of layer and it should not be too large.

And function ```setSubClass()``` should be used to set the sub classes when the class or its fields's types are interfaces or abstract classes or itself is an interface or abstract classes. Otherwise, RuntimeException will be thrown for it doesn't know which class to generate for the interface or abstract class.

```SetTypeGenerator()``` won't make effect if the parameter is an instance of ClassGenerator. It's a shortcoming of rog. And I will support it in the later version.

### Default Generator
Besides ClassGenerator, there are other generators for special types and primitive types. You don't need to use them because you can use ClassGenerator to generate objects of any types you want. It supports to change these generator's behaviors. And it's all right if you want to use them directly.
#### Changing Behavior
You can change the default generator's behaviors.

```java
        CharGenerator charGenerator = new CharGenerator.Builder()
                .setValueSet(valuesSet)  //Values generated will be fetched from this set
                .setMinBound((char) 112)  //Set the up bound of values generated
                .setMaxBound((char)1)   //Set the low bound of values generated
                .build();
        ClassGenerator<TestClass> generator = new ClassGenerator.Builder<>(TestClass.class)
                .setTypeGenerator(charGenerator)
                .build();
```

The example change the char's default generator for the object of ClassGenerator. And if the type is one of the primitive types. You can replace it in the whole process. 

``` java
        // Rog provides generators for primitive types. And you can also replace it with your implementation.
        BasicTypeGeneratorFactory.INSTANCE.setGenerator(new TestIntegerGenerator());
        //Then the Integer's generator will be TestIntegerGenerator
        //Or you can change the properties of the default primitive type generators
        IntegerGenerator generator = new IntegerGenerator.Builder()
                .setMinBound(0)
                .build();
        BasicTypeGeneratorFactory.INSTANCE.setGenerator(generator);
        //Then the values generated will be larger or equal to 0
```

```java
public class TestIntegerGenerator implements IGenerator<Integer>{
    @Override
    public Integer generate() {
        return 1;
    }

    @Override
    public Class<?> getClassToGenerate() {
        return Integer.TYPE;
    }

}

```

### ArrayGenerator 
```java
        ArrayGenerator<TestClass2> generator = new ArrayGenerator.Builder<>( TestClass2[].class)
                .setGenerator(new TestClass2Generator())
                .setMaxLength(10)    //Set the max length of array generated, default 100
                .setLength(10)  //Set a specific length for the array generated, default randomly
                .setProportionOfNull(0.1f)       //Set proportion to generate null
                .build();
        TestClass2[] object = generator.generate();
```

### EnumGenerator
Generator to genrate enum. You

```java
        List<TestEnum> valueSet = new ArrayList<>();
        valueSet.add(TestEnum.v1);
        EnumGenerator<TestEnum> generator = new EnumGenerator.Builder<>(TestEnum.class)
                .setValueSet(valueSet)  //Values generated will fetched from this set
                .setProportionOfNull(0.5f)  //Set the proportion to generate null
                .build();
        TestEnum value = generator.generate();
```

### InterfaceOrAbstractClassGenerator

```java
        List<Class<? extends TestInterface>> classes = new ArrayList<>();
        classes.add(TestInterfaceClass.class);
        List<TestInterface> objects = new ArrayList<>();
        objects.add(new TestInterfaceClass());
        InterfaceOrAbstractClassGenerator<TestInterface> generator = new InterfaceOrAbstractClassGenerator.Builder<>(TestInterface.class)
                //Set the subClasses of TestInterface.
                .setClassSet(classes)
                .setTypeGenerator(new TestInferfaceClassGenerator())   
                .setScaleOfNull(0.1f)   //Set proportion  to generate null, default 0.1f
                .setObjectSet(objects)  //The values gerated will be fetched from this set
                .build();
        TestInterface value = generator.generate();
```

### StringGenerator
```java
        List<Character> charSet = new ArrayList<>();
        charSet.add('A');
        List<String> valueSet = new ArrayList<>();
        valueSet.add("A");
        StringGenerator generator = new StringGenerator.Builder()
                .setCharSet(charSet)  //The characters of strings generated will be fetched from this set
                .setValueSet(valueSet)  //Strings generated will be fetched from this set
                .setMaxLength(11)  //Set the max length of strings generated
                //.setGenerateNotEmpty(false) //Set if the generator generates not empty strings, default true
                .setGenerateNull(false)  //Set if the generator generates null, default true
                .setGenerateEmpty(false)  //Set if the generator generates empty string, default true
                .setChineseProportion(1, 1)  //Set the proportion of chinese to english characters when the mode is MODE_NORMAL
                //MODE_RANDOM = 0;//Generate unicode char randomly
                //MODE_ASCII = 1;  //Generate only ascii char
                //MODE_NORMAL = 2; //Generate only ascii char and chinese
                .setMode(StringGenerator.MODE_NORMAL)  //Set generating mode, default MODE_NORMAL
                .build();
        String value = generator.generate();
```

### BooleanGenerator
```java
		BooleanGenerator generator = new BooleanGenerator.Builder()
                //.setGenerateTrue()        //Set if the generator generates true, default true.
                //.setGenerateFalse(false)   //Set if the generator generates false, default true.
                .setProportion(1, 1)   //Set the proportion to generate true and false
                .build();
        boolean value = generator.generate();
```
### CharGenerator
```java
        List<Character> valuesSet = new ArrayList<>();
        valuesSet.add('A');
        valuesSet.add('B');
        CharGenerator generator = new CharGenerator.Builder()
                .setValueSet(valuesSet)  //Values generated will be fetched from this set
                .setMinBound((char) 112)  //Set the up bound of values generated
                .setMaxBound((char)1)   //Set the low bound of values generated
                .build();
```

### ByteGenerator
```java
        List<Byte> valueSet = new ArrayList<>();
        valueSet.add((byte) 1);
        List<Byte> negativeSet = new ArrayList<>();
        negativeSet.add((byte) -1);
        ByteGenerator generator = new ByteGenerator.Builder()
                //.setGenerateNegative(false)  //Set if the generator generates negative values, default true
                //.setGeneratePositive(false)  //Set if the generator generates positive values, default true
                .setGenerateZero(false)  //Set if the generator generates zero
                .setGenerateProportion(1, 1, 1)  //Set proportion to generate positive, zero and negative values
                //.setValueSet(valueSet)  //Values generated will fetched from the list
                .setNegativeValueSet(negativeSet)  //Negative values generated will be fetched from the list.
                .setPositiveValueSet(valueSet)   //Positive values generated will be fetched from the list.
                .setMaxBound((byte) 123)   //Set the up bound of values generated
                .setMinBound((byte) -4)  //Set the low bound of values generated
                .build();
        byte value = generator.generate();
```

The other genertors of number in primitive types, including int, short, byte, double, float and long, have the same function with ByteGenerator. So I won't give their examples. You can find them in [Samples](https://github.com/campusappcn/rog/tree/master/sample/src/androidTest/java/cn/campusapp/rog/sample/Samples.java)

## Test Coverage
![Test Coverage](https://img.alicdn.com/imgextra/i2/754328530/TB2TQ62mFXXXXXdXXXXXXXXXXXX_!!754328530.png)

## Install
First, add jitpack.io to your repositories.

```
allprojects {
 repositories {
    jcenter()
    maven { url "https://jitpack.io" }
 }
}
```

Then, add the dependency. 

```
dependencies {
    androidTestCompile 'com.github.campusappcn:rog:1.0.1'   //Dependency rog in instucmentation test.
    //compile 'com.github.campusappcn:rog:1.0.1'       //if you want to use rog everywhere.
    //testCompile 'com.github.campusappcn:rog:1.0.1'   //If you want to use rog in Java unit test.  
}
```

Note: do not add the jitpack.io repository under buildscript

## Feedback
If you have any problems, welcome, and please share any issues.

## License
```
Copyright 2015 杭州树洞网络科技有限公司

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
