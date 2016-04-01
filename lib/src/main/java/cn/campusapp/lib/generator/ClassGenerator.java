package cn.campusapp.lib.generator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.campusapp.lib.cache.FieldCache;
import cn.campusapp.lib.factory.ITypeGeneratorFactory;
import cn.campusapp.lib.factory.TypeGeneratorFactory;
import cn.campusapp.lib.store.SubClassStore;

/**
 * Created by kris on 16/3/28.
 */
public class ClassGenerator<T> implements IGenerator<T>{
    private static int STRATEGY_STRICT = 0;  //strict,
    private static int STRATEGY_LAX = 1;
    private Class<T> mClazz;
    private ITypeGeneratorFactory mGeneratorFactory;
    private float mScaleOfNull = 0.1f;  //the scale of the generator generate null;
    private SubClassStore mStore;
    private int mStrategy = STRATEGY_STRICT;


    protected ClassGenerator(Class<T> clazz, ITypeGeneratorFactory factory){
        if(mClazz == null || mGeneratorFactory == null){
            throw new IllegalArgumentException("The class and factory parameter can't be null");
        }
        mClazz = clazz;
        mGeneratorFactory = factory;
    }

    public void putTypeGenerator(IGenerator generator){
        mGeneratorFactory.setGenerator(generator);
    }

    protected <E> IGenerator<E> getGenerator(Class<E> clazz){
        IGenerator<E> generator = mGeneratorFactory.getGenerator(clazz);
        if(generator == null){
            generator = createNewGenerator(clazz);
        }
        return generator;
    }

    @SuppressWarnings("unchecked")
    public <E> IGenerator<E> createNewGenerator(Class<E> clazz){
        IGenerator<E> generator = null;
        if(clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())){
            generator = new InterfaceOrAbstractClassGenerator.Builder<E>(clazz, mGeneratorFactory, mStore).build();
        } else if(clazz.isEnum()){
            generator = new EnumGenerator.Builder<>(clazz).build();
        } else if(clazz.isArray()){
            generator = (IGenerator<E>) new ArrayGenerator.Builder<>((Class<Object[]>) clazz).build();
        } else {
            generator = new ClassGenerator.Builder<>(clazz).build();
        }
        return generator;
    }

    public void setScaleOfNull(float scale){
        if(scale < 0.0f || scale > 1.0f){
            throw new IllegalArgumentException("The scale of null must be in the bounds of [0.0f, 1.0f]");
        }
        mScaleOfNull = scale;
    }

    public void setSubClassStore(SubClassStore store){
        mStore = store;
    }

    public void setStrategy(int strategy){
        mStrategy = strategy;
    }

    @Override
    public T generate() {
        if(mClazz.isInterface() || Modifier.isAbstract(mClazz.getModifiers())){
            putTypeGenerator(new InterfaceOrAbstractClassGenerator.Builder<T>(mClazz, mGeneratorFactory, mStore).setScaleOfNull(mScaleOfNull).build());
            return getGenerator(mClazz).generate();
        } else {
            //TODO normal class, find no parameter constructor to get a object
            T object = getNewClassInstance(mClazz);

            return setFieldValueOfObject(object);
        }
    }

    private T setFieldValueOfObject(T object){
        Class<?> clazz = object.getClass();
        List<Field> fields = listAllFields(clazz);
        for(Field  f: fields){
            if(!Modifier.isFinal(f.getModifiers())) {
                f.setAccessible(true);
                Object value = getGenerator(f.getType()).generate();
                try {
                    f.set(object, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Can't access to field " + f.getClass().toString());
                }
            }
        }

        return object;
    }

    private List<Field> listAllFields(Class<?> clazz){

        Class<?> tempClass = clazz;
        List<Field> fields = FieldCache.get(clazz);
        if(fields == null) {
            fields = new ArrayList<>();
            do {
                fields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
                tempClass = tempClass.getSuperclass();
            } while (tempClass != null);
        }
        FieldCache.cache(clazz, fields);
        return fields;
    }


    private T getNewClassInstance(Class<T> clazz){
        Constructor<T> constructor = null;
        try{
            constructor = mClazz.getConstructor();
        } catch (NoSuchMethodException e){
            throw new RuntimeException("The class don't have a default constructor");
        }
        constructor.setAccessible(true);
        T object = null;
        try{
            object = constructor.newInstance();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return object;
    }

    @Override
    public Class<?> getClassToGenerate() {
        return mClazz;
    }


    public static class Builder<E>{
        private Class<E> mClazz;
        private ITypeGeneratorFactory mGeneratorFactory;
        private float mScaleOfNull = 0.1f;  //the scale of the generator generate null;
        private SubClassStore mSubClassStore;
        private int mStrategy = STRATEGY_LAX;

        protected Builder(Class<E> clazz){
            this(clazz, null, null);
        }

        protected Builder(Class<E> clazz, ITypeGeneratorFactory factory){
            this(clazz, factory, null);
        }

        protected Builder(Class<E> clazz, SubClassStore subClassStore){
            this(clazz, null, subClassStore);
        }

        protected Builder(Class<E> clazz, ITypeGeneratorFactory factory, SubClassStore store){
            mClazz = clazz;
            mGeneratorFactory = factory != null ? factory : TypeGeneratorFactory.getFactory();
            mSubClassStore = store != null ? store : SubClassStore.getInstance();
        }


        public Builder<E> putTypeGenerator(IGenerator generator){
            mGeneratorFactory.setGenerator(generator);
            return this;
        }

        protected <S> IGenerator<S> getGenerator(Class<S> clazz){
            return mGeneratorFactory.getGenerator(clazz);
        }

        public Builder<E> setScaleOfNull(float scale){
            if(scale < 0.0f || scale > 1.0f){
                throw new IllegalArgumentException("The scale of null must be in the bounds of [0.0f, 1.0f]");
            }
            mScaleOfNull = scale;
            return this;
        }

        public Builder<E> setStrategy(int strategy){
            mStrategy = strategy;
            return this;
        }

        public ClassGenerator<E> build(){
            ClassGenerator<E> generator = new ClassGenerator<>(mClazz, mGeneratorFactory);
            generator.setScaleOfNull(mScaleOfNull);
            generator.setSubClassStore(mSubClassStore);
            generator.setStrategy(mStrategy);
            return generator;
        }
    }
}
