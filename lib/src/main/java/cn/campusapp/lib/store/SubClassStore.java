package cn.campusapp.lib.store;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

/**
 * Created by kris on 16/3/29.
 * used to store the map from interface or abstract class to their subclasses which aren't interface or abstract class.
 */
public class SubClassStore {
    private Map<Class<?>, List> mMap = new HashMap<>();
    private SubClassStore(){}
    public static SubClassStore getInstance(){
        return new SubClassStore();
    }

    public <T> void setInterfaceOrAbstractMap(Class<T> clazz, List<Class<? extends T>> classSet){
        if(clazz == null || classSet == null || classSet.size() == 0){
            Timber.e("clazz or classSet can't empty");
            return;
        }
        classSet.remove(null);
        List<Class<? extends T>> toRemove = new ArrayList<>();
        for(Class<? extends T> clz : classSet){
            if(clz.isInterface()){
                Timber.e("clz in classSet settled to map can't be a interface");
                toRemove.add(clz);
            } else if(Modifier.isAbstract(clz.getModifiers())){
                Timber.e("clz in classSet settled to map can't be a abstract class");
                toRemove.add(clz);
            }
        }
        classSet.removeAll(toRemove);
        mMap.put(clazz, classSet);
    }


    @SuppressWarnings("unchecked")
    public <E> List<Class<? extends E>> getSubClasses(Class<E> clazz){
        return mMap.get(clazz);
    }
}
