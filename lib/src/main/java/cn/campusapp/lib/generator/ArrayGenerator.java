package cn.campusapp.lib.generator;

import java.lang.reflect.Array;
import java.util.Random;

import cn.campusapp.lib.factory.TypeGeneratorFactory;

/**
 * Created by kris on 16/3/29.
 */
public class ArrayGenerator<T> implements IGenerator<T[]> {


    Class<T[]> mClazz;
    Random mRandom = new Random();
    int mLength = -1; //if this field settled, the length of this array will be mLength
    float mScaleOfNull = 0.1f;
    int mMaxLength = 100;
    TypeGeneratorFactory mFactory = TypeGeneratorFactory.getFactory();



    private ArrayGenerator(Class<T[]> clazz){
        if(clazz == null){
            throw new IllegalArgumentException("clazz can't be null");
        }
        mClazz = clazz;
    }

    public void setGenerator(IGenerator<T> generator){
        mFactory.setGenerator(generator);
    }

    public void setMaxLength(int length){
        if(mMaxLength <= 0){
            throw new IllegalArgumentException("Max Length must be larger than zero");
        }
        mMaxLength = length;
    }

    public void setLength(int length){
        if(length<0){
            throw new IllegalArgumentException("length must larger than zero");
        }
        mLength = length;
    }

    public void setScaleOfNull(float scale){
        mScaleOfNull = scale;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T[] generate() {
        IGenerator<T> generator = (IGenerator<T>) mFactory.getGenerator(mClazz.getComponentType());
        if(generator == null){
            generator = new ClassGenerator
                    .Builder<T>((Class<T>) mClazz.getComponentType())
                    .setScaleOfNull(mScaleOfNull)
                    .build();
            mFactory.setGenerator(generator);
        }
        mLength = mLength != -1 ? mLength : mRandom.nextInt(mMaxLength);
        T[] array = (T[]) Array.newInstance(mClazz.getComponentType(), mLength);
        for(int i=0;i<mLength;i++){
            array[i] = generator.generate();
        }
        return array;
    }


    @Override
    public Class<?> getClassToGenerate() {
        return mClazz;
    }


    public static class Builder<E> {
        Class<E[]> mClazz;
        IGenerator<E> mGenerator;
        int mLength = -1; //if this field settled, the length of this array will be mLength
        float mScaleOfNull = 0.1f;
        int mMaxLength = 100;

        public Builder(Class<E[]> clazz){
            mClazz = clazz;
        }

        public Builder setGenerator(IGenerator<E> generator){
            mGenerator = generator;
            return this;
        }

        public Builder setLength(int length){
            mLength = length;
            return this;
        }

        public Builder setScaleOfNull(float scale){
            mScaleOfNull = scale;
            return this;
        }


        public Builder setMaxLength(int length){
            mMaxLength = length;
            return this;
        }

        public ArrayGenerator<E> build(){
            ArrayGenerator<E> generator = new ArrayGenerator<>(mClazz);
            generator.setScaleOfNull(mScaleOfNull);
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
