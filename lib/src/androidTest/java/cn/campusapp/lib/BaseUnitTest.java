package cn.campusapp.lib;

import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import timber.log.Timber;

/**
 * Created by kris on 16/3/30.
 */
@RunWith(AndroidJUnit4.class)
public class BaseUnitTest {

    @org.junit.BeforeClass
    public static void setUp(){
        Timber.plant(new Timber.DebugTree());
    }


}
