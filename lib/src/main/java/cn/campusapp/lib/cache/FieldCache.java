package cn.campusapp.lib.cache;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kris on 16/3/30.
 */
public enum FieldCache {
    INSTANCE;

    HashMap<Class<?>, List<Field>> mMap = new HashMap<>();


    public static List<Field> get(Class<?> clazz){
        return INSTANCE.mMap.get(clazz);
    }

    public static void cache(Class<?> clazz, List<Field> fields){
        INSTANCE.mMap.put(clazz, fields);
    }
}
