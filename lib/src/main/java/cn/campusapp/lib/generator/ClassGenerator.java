package cn.campusapp.lib.generator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cn.campusapp.lib.cache.FieldCache;
import cn.campusapp.lib.factory.BasicTypeGeneratorFactory;
import cn.campusapp.lib.factory.ITypeGeneratorFactory;
import cn.campusapp.lib.factory.TypeGeneratorFactory;
import cn.campusapp.lib.store.SubClassStore;
import timber.log.Timber;

/**
 * Created by kris on 16/3/28.
 * The main interface for users
 */
public class ClassGenerator<T> implements IGenerator<T> {
    private static int STRATEGY_STRICT = 0;  //strict,
    private static int STRATEGY_LAX = 1;
    private static int mMaxLayer = 5;
    Random mRandom = new Random();
    private Class<T> mClazz;
    private ITypeGeneratorFactory mGeneratorFactory;
    private float mProportionOfNull = 0.1f;  //the scale of the generator generate null;
    private SubClassStore mStore;
    private int mStrategy = STRATEGY_STRICT;


    protected ClassGenerator(Class<T> clazz, ITypeGeneratorFactory factory) {
        if (clazz == null || factory == null) {
            throw new IllegalArgumentException("The class and factory parameter can't be null");
        }
        mClazz = clazz;
        mGeneratorFactory = factory;
    }

    /**
     * set generator of type (generator.getClassToGenerate()). It will use the generator to generate
     * objects of this type afterwards.
     *
     * @param generator
     */
    public void setTypeGenerator(IGenerator generator) {
        mGeneratorFactory.setGenerator(generator);
    }

    /**
     * set the max layer of classes referencing tree. For example, Class1 references Class2, Class1's layer is 1 and Class2's layer is 1.
     * If max layer is 1. Then Class2 can't generate any more object except primitive type and its field of class types will be null.
     *
     * @param maxLayer
     * @return
     */
    public void setMaxLayer(int maxLayer) {
        mMaxLayer = maxLayer;
    }


    protected <E> IGenerator<E> getGenerator(Class<E> clazz) {
        IGenerator<E> generator = mGeneratorFactory.getGenerator(clazz);
        if (generator == null) {
            generator = createNewGenerator(clazz);
            setTypeGenerator(generator);
        }
        return generator;
    }


    @SuppressWarnings("unchecked")
    public <E> IGenerator<E> createNewGenerator(Class<E> clazz) {
        IGenerator<E> generator = null;
        if (! clazz.isArray() && (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers()))) {
            generator = new InterfaceOrAbstractClassGenerator.Builder<E>(clazz, mGeneratorFactory, mStore)
                    .setScaleOfNull(mProportionOfNull)
                    .build();
        } else if (clazz.isEnum()) {
            generator = new EnumGenerator.Builder<>(clazz)
                    .setProportionOfNull(mProportionOfNull)
                    .build();
        } else if (clazz.isArray()) {
            generator = (IGenerator<E>) new ArrayGenerator
                    .Builder<>((Class<Object[]>) clazz)
                    .setProportionOfNull(mProportionOfNull)
                    .setGenerator((IGenerator<Object>) getGenerator(clazz.getComponentType()))
                    .build();
        } else {
            generator = new ClassGenerator.Builder<>(clazz, mGeneratorFactory, mStore)
                    .setProportionOfNull(mProportionOfNull)
                    .setStrategy(mStrategy)
                    .setMaxLayer(mMaxLayer)
                    .build();
        }
        return generator;
    }

    /**
     * set proportion to generate null
     *
     * @param proportion
     */
    public void setProportionOfNull(float proportion) {
        if (proportion < 0.0f || proportion > 1.0f) {
            throw new IllegalArgumentException("The scale of null must be in the bounds of [0.0f, 1.0f]");
        }
        mProportionOfNull = proportion;
    }

    /**
     * If the class has field of interfaces or abstract classes. The generator must be told the subclasses of the interfaces or abstract classes.
     * And the SubClassStore consists this information.
     *
     * @param store
     */
    public void setSubClassStore(SubClassStore store) {
        mStore = store;
    }

    /**
     * @param strategy
     * @deprecated don't make effect yet.
     */
    @Deprecated
    public void setStrategy(int strategy) {
        mStrategy = strategy;
    }


    @Override
    public T generate() {
        return generate(0);
    }

    protected T generate(int level) {

        IGenerator<T> generator = getGenerator(mClazz);
        if (generator != null && !(generator instanceof ClassGenerator)) {
            return generator.generate();
        } else {
            if (isGenerateNull(mProportionOfNull)) {
                return null;
            } else {
                T object = getNewClassInstance(mClazz);

                return setFieldValueOfObject(object, level);
            }
        }
    }

    protected boolean isGenerateNull(float scaleOfNull) {
        float r = mRandom.nextFloat();
        if (r < scaleOfNull) {
            return true;
        } else {
            return false;
        }
    }

    private T setFieldValueOfObject(T object, int level) {
        Class<?> clazz = object.getClass();
        List<Field> fields = listAllFields(clazz);
        for (Field f : fields) {
            if (!Modifier.isFinal(f.getModifiers())) {
                try {
                    f.setAccessible(true);
                    Object value = null;
                    if (level < mMaxLayer) {
                        IGenerator<?> generator = getGenerator(f.getType());
                        if (generator instanceof ClassGenerator) {
                            value = ((ClassGenerator) generator).generate(++level);
                        } else if (generator instanceof InterfaceOrAbstractClassGenerator) {
                            value = ((InterfaceOrAbstractClassGenerator) generator).generate(++level);
                        } else {
                            value = generator.generate();
                        }
                    } else {
                        if (BasicTypeGeneratorFactory.INSTANCE.isBasicType(f.getType())) {
                            value = getGenerator(f.getType()).generate();
                        } else {
                            value = null;
                        }
                    }
                    if (value != null) {
                        f.set(object, value);
                    }
                } catch (SecurityException e) {
                    Timber.e(e, "");
                } catch (IllegalAccessException e) {
                    Timber.e(e, "Can't set the value of this field of %s", f.toString());
                }

            }
        }

        return object;
    }

    private List<Field> listAllFields(Class<?> clazz) {

        Class<?> tempClass = clazz;
        List<Field> fields = FieldCache.get(clazz);
        int level = 0;
        if (fields == null) {
            fields = new ArrayList<>();
            do {
                fields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
                tempClass = tempClass.getSuperclass();
                level++;
            } while (tempClass != null && tempClass != Object.class && level <= mMaxLayer);
            FieldCache.cache(clazz, fields);
        }

        return fields;
    }


    private T getNewClassInstance(Class<T> clazz) {
        Constructor<T> constructor = null;
        try {
            constructor = mClazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("The class don't have a default constructor");
        }
        constructor.setAccessible(true);
        T object = null;
        try {
            object = constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    @Override
    public Class<?> getClassToGenerate() {
        return mClazz;
    }


    public static class Builder<E> {
        private Class<E> mClazz;
        private ITypeGeneratorFactory mGeneratorFactory;
        private float mProportionOfNull = 0.1f;  //the scale of the generator generate null;
        private SubClassStore mSubClassStore;
        private int mStrategy = STRATEGY_LAX;
        private int mMaxLayer = -1;

        public Builder(Class<E> clazz) {
            this(clazz, null, null);
        }

        public Builder(Class<E> clazz, ITypeGeneratorFactory factory) {
            this(clazz, factory, null);
        }

        public Builder(Class<E> clazz, SubClassStore subClassStore) {
            this(clazz, null, subClassStore);
        }

        public Builder(Class<E> clazz, ITypeGeneratorFactory factory, SubClassStore store) {
            mClazz = clazz;
            mGeneratorFactory = factory != null ? factory : TypeGeneratorFactory.getFactory();
            mSubClassStore = store != null ? store : SubClassStore.getInstance();
        }


        /**
         * Set generator of type (generator.getClassToGenerate()). It will use the generator to generate objects of this type afterwards.
         * If the generator is a instance of ClassGenerator, it may not make effect. So implement a IGenerator yourself.
         *
         * @param generator
         * @return
         */
        public Builder<E> setTypeGenerator(IGenerator generator) {
            mGeneratorFactory.setGenerator(generator);
            return this;
        }

        /**
         * set subClasses of clazz. When the generator needs to generate this interface or abstract class, it will
         * generate object of one of the classes in the subClass set.
         *
         * @param clazz      should be a interface or abstract class.
         * @param subClasses
         * @param <S>
         * @return
         */
        public <S> Builder<E> setSubClass(Class<S> clazz, List<Class<? extends S>> subClasses) {
            mSubClassStore.setInterfaceOrAbstractMap(clazz, subClasses);
            return this;
        }


        /**
         * set proportion to generate null
         *
         * @param proportion
         */
        public Builder<E> setProportionOfNull(float proportion) {
            if (proportion < 0.0f || proportion > 1.0f) {
                throw new IllegalArgumentException("The proportion of null must be in the bounds of [0.0f, 1.0f]");
            }
            mProportionOfNull = proportion;
            return this;
        }

        /**
         * do not effect now
         *
         * @param strategy
         * @return
         */
        public Builder<E> setStrategy(int strategy) {
            mStrategy = strategy;
            return this;
        }

        /**
         * set the max layer of classes referencing tree. For example, Class1 references Class2, Class1's layer is 1 and Class2's layer is 1.
         * If max layer is 1. Then Class2 can't generate any more object except primitive type and its field of class types will be null.
         *
         * @param maxLayer
         * @return
         */
        public Builder<E> setMaxLayer(int maxLayer) {
            mMaxLayer = maxLayer;
            return this;
        }

        public ClassGenerator<E> build() {
            ClassGenerator<E> generator = new ClassGenerator<>(mClazz, mGeneratorFactory);
            generator.setProportionOfNull(mProportionOfNull);
            generator.setSubClassStore(mSubClassStore);
            generator.setStrategy(mStrategy);
            if (mMaxLayer >= 0) {
                generator.setMaxLayer(mMaxLayer);
            }
            return generator;
        }
    }
}
