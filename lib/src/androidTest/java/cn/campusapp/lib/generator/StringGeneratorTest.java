package cn.campusapp.lib.generator;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.campusapp.lib.BaseUnitTest;

/**
 * Created by kris on 16/4/5.
 */
@RunWith(AndroidJUnit4.class)
public class StringGeneratorTest extends BaseUnitTest {

    @Test
    public void testGenerateNull(){
        StringGenerator generator = new StringGenerator.Builder()
                .setGenerateNull(false)
                .build();
        for(int i=0;i<1000;i++){
            Assert.assertTrue(generator.generate() != null);
        }
        StringGenerator generator1 = new StringGenerator.Builder()
                .build();
        boolean hasGenerateNull = false;
        for(int i=0;i<1000;i++){
            if(generator1.generate() == null){
                hasGenerateNull = true;
            }
        }
        Assert.assertTrue(hasGenerateNull);
    }


    @Test
    public void testGenerateEmpty(){
        StringGenerator generator = new StringGenerator.Builder()
                .setGenerateEmpty(false)
                .build();
        for(int i=0;i < 1000;i++){
            Assert.assertTrue(!"".equals(generator.generate()));
        }

        StringGenerator generator1 = new StringGenerator.Builder()
                .build();
        boolean hasGenerateEmpty = false;
        for(int i = 0;i<1000;i++){
            if("".equals(generator1.generate())){
                hasGenerateEmpty = true;
            }
        }
        Assert.assertTrue(hasGenerateEmpty);
    }


    @Test
    public void testGenerateNotEmpty(){
        StringGenerator generator = new StringGenerator.Builder()
                .setGenerateNotEmpty(false)
                .build();
        for(int i=0;i<1000;i++){
            String generated = generator.generate();
            Assert.assertTrue(generated == null || generated.equals(""));
        }

        StringGenerator generator1 = new StringGenerator.Builder()
                .build();
        boolean hasGenerateNotEmpty = false;
        for(int i=0;i<1000;i++){
            if(generator1.generate() != null && ! "".equals(generator1.generate())){
                hasGenerateNotEmpty = true;
            }
        }
        Assert.assertTrue(hasGenerateNotEmpty);
    }

    @Test
    public void testMaxLength(){
        Random random = new Random();
        for(int i= 0;i<10;i++){
            int maxLength = random.nextInt(1000);
            StringGenerator generator = new StringGenerator.Builder()
                    .setMaxLength(maxLength)
                    .setGenerateNull(false)
                    .build();
            for(int j=0;j<1000;j++){
                Assert.assertTrue(generator.generate().length() <= maxLength);
            }
        }
    }


    @Test
    public void testSetCharSet(){
        List<Character> charSet = new ArrayList<>();
        charSet.add('A');
        charSet.add('b');
        charSet.add('D');
        StringGenerator generator = new StringGenerator.Builder()
                .setCharSet(charSet)
                .setProportion(0, 0, 1)
                .build();
        for(int i=0;i<1000;i++){
            String generated = generator.generate();
            for(char c : generated.toCharArray()){
                Assert.assertTrue(charSet.contains(c));
            }
        }
    }

    @Test
    public void testSetMode(){
        StringGenerator randomGenerator = new StringGenerator.Builder()
                .build();
        for(int i=0;i<1000;i++){
            randomGenerator.generate();
        }

        StringGenerator normalGenerator = new StringGenerator.Builder()
                .setMode(StringGenerator.MODE_NORMAL)
                .build();
        for(int i=0;i<1000;i++){
            normalGenerator.generate();
        }

        StringGenerator asciiGenerator = new StringGenerator.Builder()
                .setMode(StringGenerator.MODE_ASCII)
                .build();
        for(int i=0;i<1000;i++){
            asciiGenerator.generate();
        }
    }

    @Test
    public void testSetScale(){
        StringGenerator generator0 = new StringGenerator.Builder()
                .setProportion(0, 0, 1)
                .build();

        for(int i=0;i<1000;i++){
            String generated = generator0.generate();
            Assert.assertTrue(generated != null && !"".equals(generated));
        }

        StringGenerator generator1 = new StringGenerator.Builder()
                .setProportion(0, 1, 0).build();
        for(int i=0;i<1000;i++){
            String generated = generator1.generate();
            Assert.assertTrue(generated.equals(""));
        }

        StringGenerator generator2 = new StringGenerator.Builder()
                .setProportion(1, 0, 0).build();
        for(int i=0;i<1000;i++){
            Assert.assertTrue(generator2.generate() == null);
        }

        StringGenerator generator3 = new StringGenerator.Builder()
                .setProportion(0, 1, 1).build();
        boolean hasGeneratedNotEmpty3 = false;
        boolean hasGeneratedEmpty3 = false;
        for(int i=0;i<1000;i++){
            String generated = generator3.generate();
            if(generated == null) {
                Assert.fail();
            } else if(generated.equals("")){
                hasGeneratedEmpty3 = true;
            } else{
                hasGeneratedNotEmpty3 = true;
            }
        }
        Assert.assertTrue(hasGeneratedEmpty3 && hasGeneratedNotEmpty3);

        StringGenerator generator4 = new StringGenerator.Builder()
                .setProportion(1, 1, 0).build();
        boolean hasGeneratedNull4 = false;
        boolean hasGeneratedEmpty4 = false;
        for(int i=0;i<1000;i++){
            String generated = generator4.generate();
            if(generated == null) {
                hasGeneratedNull4 = true;
            } else if(generated.equals("")){
                hasGeneratedEmpty4 = true;
            } else{
                Assert.fail();
            }
        }
        Assert.assertTrue(hasGeneratedEmpty4 && hasGeneratedNull4);

        StringGenerator generator5 = new StringGenerator.Builder()
                .setProportion(1, 0, 1).build();
        boolean hasGeneratedNull5 = false;
        boolean hasGeneratedNotEmpty5 = false;
        for(int i=0;i<1000;i++){
            String generated = generator5.generate();
            if(generated == null) {
                hasGeneratedNull5 = true;
            } else if(generated.equals("")){
                Assert.fail();
            } else{
                hasGeneratedNotEmpty5 = true;
            }
        }
        Assert.assertTrue(hasGeneratedNull5 && hasGeneratedNotEmpty5);


        StringGenerator generator6 = new StringGenerator.Builder()
                .setProportion(1, 1, 1).build();
        boolean hasGeneratedNull6 = false;
        boolean hasGeneratedNotEmpty6 = false;
        boolean hasGeneratedEmpty6 = false;
        for(int i=0;i<1000;i++){
            String generated = generator6.generate();
            if(generated == null) {
                hasGeneratedNull6 = true;
            } else if(generated.equals("")){
                hasGeneratedEmpty6 = true;
            } else{
                hasGeneratedNotEmpty6 = true;
            }
        }
        Assert.assertTrue(hasGeneratedNull6 && hasGeneratedNotEmpty6 && hasGeneratedEmpty6);
    }

    @Test
    public void testSetChineseScale(){
        StringGenerator generator = new StringGenerator.Builder()
                .setChineseProportion(1, 1)
                .build();
        for(int i=0;i<1000;i++){
            generator.generate();
        }
    }



}
