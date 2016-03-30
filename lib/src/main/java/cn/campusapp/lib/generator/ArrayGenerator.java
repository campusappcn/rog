package cn.campusapp.lib.generator;

import java.lang.reflect.Array;
import java.util.Random;

/**
 * Created by kris on 16/3/29.
 */
public class ArrayGenerator<T> implements IGenerator<T[]> {


    Class<T[]> mClazz;
    IGenerator<T> mGenerator;
    Random mRandom = new Random();
    int mLength = -1; //if this field settled, the length of this array will be mLength
    float mScaleOfNull = 0.1f;

    private ArrayGenerator(Class<T[]> clazz){
        if(mClazz == null){
            throw new IllegalArgumentException("clazz can't be null");
        }
        mClazz = clazz;
    }

    public void setGenerator(IGenerator<T> generator){
        mGenerator = generator;
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
        if(mGenerator == null){
            mGenerator = new ClassGenerator
                    .Builder<T>((Class<T>) mClazz.getComponentType())
                    .setScaleOfNull(mScaleOfNull)
                    .build();
        }
        mLength = mLength != -1 ? mLength : mRandom.nextInt();
        T[] array = (T[]) Array.newInstance(mClazz.getComponentType(), mLength);
        for(int i=0;i<mLength;i++){
            array[i] = mGenerator.generate();
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


        public ArrayGenerator<E> build(){
            ArrayGenerator<E> generator = new ArrayGenerator<>(mClazz);
            generator.setScaleOfNull(mScaleOfNull);
            generator.setGenerator(mGenerator);
            generator.setLength(mLength);
            return generator;
        }
    }
}
