package cn.campusapp.lib.cache;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import cn.campusapp.lib.BaseUnitTest;
import cn.campusapp.lib.generator.StringGenerator;

/**
 * Created by kris on 16/4/1.
 */
@RunWith(AndroidJUnit4.class)
public class FieldCacheTest extends BaseUnitTest {


    @Test
    public void testCache(){
        List<Field> fields = Arrays.asList(StringGenerator.class.getFields());
        FieldCache.cache(StringGenerator.class, fields);
        Assert.assertEquals(fields.size() , FieldCache.get(StringGenerator.class).size());
    }


}
