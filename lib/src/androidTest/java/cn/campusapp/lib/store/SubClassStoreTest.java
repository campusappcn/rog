package cn.campusapp.lib.store;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import cn.campusapp.lib.BaseUnitTest;
import cn.campusapp.lib.generator.IGenerator;
import cn.campusapp.lib.generator.ShortGenerator;
import cn.campusapp.lib.generator.StringGenerator;

/**
 * Created by kris on 16/4/1.
 */
@RunWith(AndroidJUnit4.class)
public class SubClassStoreTest extends BaseUnitTest{


    @Test
    public void testSetInterfaceOrAbstractMap(){
        List<Class<? extends IGenerator>>  classes = new ArrayList<>();
        classes.add(ShortGenerator.class);
        classes.add(StringGenerator.class);
        SubClassStore store = SubClassStore.getInstance();
        store.setInterfaceOrAbstractMap(IGenerator.class, classes);
        List<Class<? extends IGenerator>> generators = store.getSubClasses(IGenerator.class);
        Assert.assertEquals(generators.size(), 2);

    }



}
