package cn.campusapp.lib.generator;

import java.lang.reflect.Array;
import java.util.Random;

import cn.campusapp.lib.factory.TypeGeneratorFactory;

/**
 * Created by kris on 16/3/29.
 */
public class ArrayGenerator<T> implements IGenerator<T[]> {


    Class<? extends T[]> mClazz;
    Random mRandom = new Random();
    int mLength = -1; //if this field settled, the length of this array will be mLength
    float mProportionOfNull = 0.1f;
    int mMaxLength = 100;
    TypeGeneratorFactory mFactory = TypeGeneratorFactory.getFactory();

    protected ArrayGenerator(Class<? extends T[]> clazz){
        if(clazz == null){
            throw new IllegalArgumentException("clazz can't be null");
        }
        mClazz = clazz;
    }

    /**
     * if generator settled, it will use the generator to generate component in the array
     *
     * @param generator
     */
    public void setGenerator(IGenerator<T> generator){
        mFactory.setGenerator(generator);
    }

    /**
     * set max length of array generated
     * @param length
     */
    public void setMaxLength(int length){
        if(mMaxLength <= 0){
            throw new IllegalArgumentException("Max Length must be larger than zero");
        }
        mMaxLength = length;
    }


    /**
     * set the length of array generated
     * @param length
     */
    public void setLength(int length){
        if(length<0){
            throw new IllegalArgumentException("length must larger than zero");
        }
        mLength = length;
    }


    /**
     * set the proportion of null in the array
     * @param proportion
     */
    public void setProportionOfNull(float proportion){
        mProportionOfNull = proportion;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T[] generate() {
        if(isGenerateNull()){
            return null;
        } else {
            IGenerator<T> generator = (IGenerator<T>) mFactory.getGenerator(mClazz.getComponentType());
            if (generator == null) {
                generator = new ClassGenerator
                        .Builder<T>((Class<T>) mClazz.getComponentType())
                        .build();
                mFactory.setGenerator(generator);
            }
            mLength = mLength != -1 ? mLength : mRandom.nextInt(mMaxLength);
            T[] array = (T[]) Array.newInstance(mClazz.getComponentType(), mLength);
            for (int i = 0; i < mLength; i++) {
                array[i] = generator.generate();
            }
            return array;
        }
    }

    private boolean isGenerateNull(){
        float r = mRandom.nextFloat();
        return r < mProportionOfNull;
    }


    @Override
    public Class<?> getClassToGenerate() {
        return mClazz;
    }


    public static class Builder<E> {
        Class<? extends E[]> mClazz;
        IGenerator<E> mGenerator;
        int mLength = -1; //if this field settled, the length of this array will be mLength
        float mProportionOfNull = 0.1f;
        int mMaxLength = 100;

        public Builder(Class<? extends E[]> clazz){
            mClazz = clazz;
        }

        /**
         * if generator settled, it will use the generator to generate component in the array
         *
         * @param generator
         */
        public Builder<E> setGenerator(IGenerator<E> generator){
            mGenerator = generator;
            return this;
        }


        /**
         * set the length of array generated
         * @param length
         */
        public Builder<E> setLength(int length){
            mLength = length;
            return this;
        }


        /**
         * set the proportion of null in the array
         * @param proportion
         */
        public Builder<E> setProportionOfNull(float proportion){
            mProportionOfNull = proportion;
            return this;
        }


        /**
         * set max length of array generated
         * @param length
         */
        public Builder<E> setMaxLength(int length){
            mMaxLength = length;
            return this;
        }

        public ArrayGenerator<E> build(){
            ArrayGenerator<E> generator = new ArrayGenerator<>(mClazz);
            generator.setProportionOfNull(mProportionOfNull);
            if(mGenerator != null) {
                generator.setGenerator(mGenerator);
            }
            if(mLength > 0) {
                generator.setLength(mLength);
            }
            generator.setMaxLength(mMaxLength);
            return generator;
        }
    }
}
