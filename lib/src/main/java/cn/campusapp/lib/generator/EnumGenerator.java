package cn.campusapp.lib.generator;

import java.util.List;
import java.util.Random;

/**
 * Created by kris on 16/3/29.
 * pick the value from a enum randomly
 *
 */
public class EnumGenerator<E> implements IGenerator<E> {

    private List<E> mValueSet;
    private Class<E> mClazz;
    private float mProportionOfNull = 0.1f;  //the scale of this generator generating null value


    private Random mRandom = new Random();

    protected EnumGenerator(Class<E> clazz){
        mClazz = clazz;
    }

    /**
     * if the value set settled, the values generated will be fetched from this set.
     * @param values
     */
    public void setValueSet(List<E> values){
        mValueSet = values;
    }

    /**
     * set proportion to generate null of this generator
     * @param proportion
     */
    public void setProportionOfNull(float proportion){
        mProportionOfNull = proportion;
    }

    @Override
    public E generate() {
        if(isGenerateNull(mProportionOfNull)){
            return null;
        } else {
            if (mValueSet != null && mValueSet.size() != 0) {
                return generateFromValueSet(mValueSet);
            } else {
                return generateAccordingToClass(mClazz);
            }
        }
    }

    @Override
    public Class<?> getClassToGenerate() {
        return mClazz;
    }


    private boolean isGenerateNull(float proportionOfNull){
        if(proportionOfNull <0.0f || proportionOfNull >1.0f){
            throw new IllegalArgumentException("The scale must in the bound of [0.0f, 1.0f]");
        }
        float r = mRandom.nextFloat();
        return r< proportionOfNull;
    }

    /**
     * @param clazz
     * @return return null when cant find any value from this enum
     */
    @SuppressWarnings("unchecked")
    private E generateAccordingToClass(Class<E> clazz){
        if(!clazz.isEnum()){
            throw new IllegalArgumentException("The class must be enum");
        }
        try {
            E [] values = clazz.getEnumConstants();
            if(values.length == 0){
                return null;
            }
            return values[mRandom.nextInt(values.length)];
        } catch (Exception e){
            throw new IllegalArgumentException(e);
        }

    }


    private E generateFromValueSet(List<E> valueSet){
        if(valueSet == null || valueSet .size() == 0){
            throw new IllegalArgumentException("The value set can't be empty");
        }
        return valueSet.get(mRandom.nextInt(valueSet.size()));
    }

    public static class Builder<T> {
        private List<T> mValueSet;
        private Class<T> mClazz;
        private float mProportionOfNull = 0.1f;
        public Builder(Class<T> clazz){
            if(!clazz.isEnum()){
                throw new IllegalArgumentException("The class must be enum");
            }
            mClazz = clazz;
        }

        /**
         * if the value set settled, the values generated will be fetched from this set.
         * @param values
         */
        public Builder<T> setValueSet(List<T> values){
            mValueSet = values;
            return this;
        }

        /**
         * set proportion to generate null of this generator
         * @param proportion must [0.0f, 1.0f]
         */
        public Builder<T> setProportionOfNull(float proportion){
            if(proportion <0.0f || proportion >1.0f){
                throw new IllegalArgumentException("The scale must in the bound of [0.0f, 1.0f]");
            }
            mProportionOfNull = proportion;
            return this;
        }

        public EnumGenerator<T> build(){
            EnumGenerator<T> generator = new EnumGenerator<>(mClazz);
            if(mValueSet != null && mValueSet.size() != 0){
                generator.setValueSet(mValueSet);
            }
            generator.setProportionOfNull(mProportionOfNull);
            return generator;
        }




    }




}
