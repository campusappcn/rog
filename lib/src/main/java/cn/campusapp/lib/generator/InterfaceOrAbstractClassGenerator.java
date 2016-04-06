package cn.campusapp.lib.generator;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Random;

import cn.campusapp.lib.exception.NoInterfaceLimitationException;
import cn.campusapp.lib.factory.ITypeGeneratorFactory;
import cn.campusapp.lib.factory.TypeGeneratorFactory;
import cn.campusapp.lib.store.SubClassStore;

/**
 * Created by kris on 16/3/29.
 * the generator of interface or abstract class
 *
 *
 */
public class InterfaceOrAbstractClassGenerator<T> implements IGenerator<T> {


    private Class<T> mClazz;
    private List<T> mObjectSet;
    private List<Class<? extends T>> mClazzSet;
    private float mScaleOfNull = 0.1f;
    private ITypeGeneratorFactory mFactory;
    private SubClassStore mSubClassStore;
    Random mRandom = new Random();

    protected InterfaceOrAbstractClassGenerator(Class<T> clz, ITypeGeneratorFactory factory){
        if(clz == null || (! clz.isInterface() && ! Modifier.isAbstract(clz.getModifiers()))|| factory == null){
            throw new IllegalArgumentException("The clazz must be a interface or abstract class!");
        }
        mClazz = clz;
        mFactory = factory;
    }

    public void setObjectSet(List<T> objectSet){
        mObjectSet = objectSet;
    }

    public void setClassSet(List<Class<? extends T>> clazzSet){
        mClazzSet = clazzSet;
    }

    public void setClassGenerator(IGenerator generator){
        mFactory.setGenerator(generator);
    }


    public void setSubClassStore(SubClassStore store){
        mSubClassStore = store;
    }

    protected <E> IGenerator<E> getGenerator(Class<E> clazz){
        IGenerator<E> generator = mFactory.getGenerator(clazz);
        if(generator == null){
            generator = new ClassGenerator.Builder<E>(clazz, mFactory, mSubClassStore).setScaleOfNull(mScaleOfNull).build();
            setClassGenerator(generator);
        }
        return generator;
    }

    public void setScaleOfNull(float scale){
        if(scale < 0.0f || scale > 1.0f){
            throw new IllegalArgumentException("The scale must be in the bound of [0.0f, 1.0f]");
        }
        mScaleOfNull = scale;
    }


    @Override
    public T generate() {
        return generate(0);
    }


    public T generate(int level){
        T ret = null;
        if(!isGenerateNull(mScaleOfNull)){
            if(mObjectSet != null && mObjectSet.size() > 0){
                ret = generateObjectFromSet(mRandom, mObjectSet);
            } else if(mClazzSet != null && mClazzSet.size() > 0){
                ret = generateObjectOfClasses(mRandom, mClazzSet, level);
            } else {
                //TODO find all the subclass of the interface and pick one to give the object
                ret = generateObjectOfClasses(mRandom, mSubClassStore.getSubClasses(mClazz), level);
            }
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    protected T generateObjectOfClasses(Random random, List<Class<? extends T>> classes, int level){
        if(random == null || classes == null || classes.size() == 0){
            throw new IllegalArgumentException("Random cant be null and classes can't be empty");
        }
        Class<? extends T> clazz = classes.get(random.nextInt(classes.size()));
        IGenerator<? extends T> generator = getGenerator(clazz);
        if(generator instanceof ClassGenerator){
            return  ((ClassGenerator<? extends T>) generator).generate(level);
        } else {
            return generator.generate();
        }
    }

    protected T generateObjectFromSet(Random random, List<T> objectSet){
        if(random == null || objectSet == null || objectSet.size() == 0){
            throw new IllegalArgumentException("Random can't be null and classed can't empty");
        }
        return objectSet.get(random.nextInt(objectSet.size()));
    }



    protected boolean isGenerateNull(float scale){
        float r = mRandom.nextFloat();
        return r < scale;
    }

    @Override
    public Class<T> getClassToGenerate() {
        return mClazz;
    }


    public static class Builder<E>{
        private Class<E> mClazz;
        private List<E> mObjectSet;
        private List<Class<? extends E>> mClazzSet;
        private float mScaleOfNull = 0.1f;
        private ITypeGeneratorFactory mFactory;
        private SubClassStore mSubClassStore;
        Random mRandom = new Random();

        public Builder(Class<E> clazz) {
            this(clazz, null, null);
        }

        public Builder(Class<E> clazz, ITypeGeneratorFactory factory){
            this(clazz, factory, null);
        }

        public Builder(Class<E> clazz, SubClassStore subClassStore){
            this(clazz, null, subClassStore);
        }

        public Builder(Class<E> clazz, ITypeGeneratorFactory factory, SubClassStore store){
            mClazz = clazz;
            mFactory = factory != null ? factory : TypeGeneratorFactory.getFactory();
            mSubClassStore = store != null ? store : SubClassStore.getInstance();
        }


        public Builder<E> setObjectSet(List<E> objectSet){
            mObjectSet = objectSet;
            return this;
        }

        public Builder<E> setClassSet(List<Class<? extends E>> clazzSet){
            mClazzSet = clazzSet;
            return this;
        }


        public Builder<E> setClassGenerator(IGenerator generator){
            mFactory.setGenerator(generator);
            return this;
        }

        public Builder<E> setScaleOfNull(float scale){
            if(scale < 0.0f || scale > 1.0f){
                throw new IllegalArgumentException("The scale must be in the bound of [0.0f, 1.0f]");
            }
            mScaleOfNull = scale;
            return this;
        }

        public InterfaceOrAbstractClassGenerator<E> build(){
            InterfaceOrAbstractClassGenerator<E> generator = new InterfaceOrAbstractClassGenerator<>(mClazz, mFactory);
            if((mObjectSet == null || mObjectSet.size() == 0) && (mClazzSet == null || mClazzSet.size() == 0) && (mSubClassStore.getSubClasses(mClazz) == null|| mSubClassStore.getSubClasses(mClazz).size() == 0)){
                throw new NoInterfaceLimitationException();
            }
            generator.setClassSet(mClazzSet);
            generator.setObjectSet(mObjectSet);
            generator.setScaleOfNull(mScaleOfNull);
            generator.setSubClassStore(mSubClassStore);
            return generator;
        }
    }


}
