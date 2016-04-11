package cn.campusapp.rog;

import timber.log.Timber;

/**
 * Created by kris on 16/3/30.
 */
public class BaseUnitTest {

    @org.junit.BeforeClass
    public static void setUp(){
        Timber.plant(new Timber.DebugTree());
    }
}
