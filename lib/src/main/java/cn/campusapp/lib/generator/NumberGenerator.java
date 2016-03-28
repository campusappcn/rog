package cn.campusapp.lib.generator;

import java.util.List;
import java.util.Random;

/**
 * Created by kris on 16/3/28.
 */
public abstract class NumberGenerator <T extends Number> implements IGenerator {
    //is this generator generates negative value
    protected boolean mGenerateNegative = true;
    //is this generator generates zero value
    protected boolean mGenerateZero = true;
    //is this generator generates positive value
    protected boolean mGeneratePositive = true;

    protected T mMaxBound;

    protected T mMinBound;

    // if this set settled, the positive value will be gotten from this set
    protected List<T> mPositiveValueSet;
    // if this set settled, the negative value will be gotten from this set
    protected List<T> mNegativeValueSet;

    //if this set settled, the value will be gotten from this set
    protected List<T> mValueSet;

    protected float mScaleGeneratePositive = 0.7f;

    protected float mScaleGenerateNegative = 0.2f;

    protected float mScaleGenerateZero = 0.1f;

    protected Random mRandom = new Random();

    public void setGenerateNegative(boolean generateNegative){
        mGenerateNegative = generateNegative;
    }

    public void setGenerateZero(boolean generateZero){
        mGenerateZero = generateZero;
    }

    public void setGeneratePositive(boolean generatePositive){
        mGeneratePositive = generatePositive;
    }

    public void setMaxBound(T maxBound){
        mMaxBound = maxBound;
    }

    public void setMinBound(T minBound){
        mMinBound = minBound;
    }

    public void setPositiveValueSet(List<T> set){
        mPositiveValueSet = set;
    }

    public void setNegativeValueSet(List<T> set){
        mNegativeValueSet = set;
    }

    public void setValueSet(List<T> set){
        mValueSet = set;
    }

    /**
     * the scale to generate positive, zero, negative value, the default is 7:1:2
     * @param positiveScale
     * @param zeroScale
     * @param negativeScale
     */
    public void setGenerateScale(int positiveScale, int zeroScale, int negativeScale){
        if(positiveScale == 0 && zeroScale == 0 && negativeScale == 0){
            throw new IllegalArgumentException("The three scale can't be all zero");
        } else if(positiveScale < 0 || zeroScale < 0 || negativeScale < 0){
            throw new IllegalArgumentException("Scale can't be negative");
        }
        mScaleGenerateNegative = 1.0f * negativeScale / (positiveScale + zeroScale + negativeScale);
        mScaleGeneratePositive = 1.0f * positiveScale / (positiveScale + zeroScale + negativeScale);
        mScaleGenerateZero = 1.0f * positiveScale / (positiveScale + zeroScale + negativeScale);
    }




    public T generate(){
        if(mValueSet != null && mValueSet.size() != 0){
            return generateFromSet(mValueSet);
        } else {
            return generateRandom();
        }
    }


    private T generateRandom(){
        //value bounds and is generate influence the scale
        float negativeScale = mGenerateNegative && (smaller(mMinBound, getZero())) ? mScaleGenerateNegative : 0;
        float positiveScale = mGeneratePositive && (larger(mMaxBound, getZero()))? mScaleGeneratePositive : 0;
        float zeroScale = mGenerateZero && (smallerOrEqual(mMinBound, getZero())) && (largerOrEqual(mMaxBound, getZero())) ? mScaleGenerateZero : 0;
        if(negativeScale == 0 && positiveScale == 0 && zeroScale == 0){
            throw new RuntimeException("The negative scale, positive scale and zero scale can't be all zero");
        }
        negativeScale = negativeScale / (negativeScale + positiveScale + zeroScale);
        positiveScale = positiveScale / (negativeScale + positiveScale + zeroScale);
        zeroScale = zeroScale / (negativeScale + positiveScale + zeroScale);

        int type = getGenerateNumberType(negativeScale, zeroScale, positiveScale);
        if(type == -1){
            //generate a negative value
            if(largerOrEqual(mMinBound, getZero())){
                throw new RuntimeException("Need to generate negative value, but the min bound >= 0");
            }
            if(mNegativeValueSet != null && mNegativeValueSet.size() != 0){
                return generateFromSet(mNegativeValueSet);
            } else {
                return getRandomNegativeValue();
            }
        } else if(type == 0){
            return getZero();
        } else{
            if(smallerOrEqual(mMaxBound, getZero())){
                throw new RuntimeException("Need to generate positive value, but the max bound <= 0");
            }
            if(mPositiveValueSet != null && mPositiveValueSet.size() != 0){
                return generateFromSet(mPositiveValueSet);
            } else {
                return getRandomPositiveValue();
            }
        }
    }

    protected abstract T getZero();

    protected abstract T getRandomPositiveValue();

    protected abstract T getRandomNegativeValue();

    /**
     *
     * @param value1
     * @param value2
     * @return 1 value1 > value2, 0 value1 = value2, -1 value1 < value2
     */
    protected abstract int compare(T value1, T value2);


    private boolean smaller(T value1, T value2){
        return compare(value1, value2) == -1;
    }

    private boolean smallerOrEqual(T value1, T value2){
        int ret = compare(value1, value2);
        return  ret == -1 || ret == 0;
    }

    private boolean equal(T value1, T value2){
        return compare(value1, value2) == 0;
    }

    private boolean larger(T value1, T value2){
        return compare(value1, value2) == 1;
    }

    private boolean largerOrEqual(T value1, T value2){
        int ret = compare(value1, value2);
        return ret == 1 || ret == 0;
    }
    /**
     * if user set the valueSet, the value will be fetched from the set randomly
     * @return
     */
    private T generateFromSet(List<T> valueSet){
        //
        if(valueSet == null || valueSet.size() == 0){
            throw new RuntimeException("The value set is empty");
        } else {
            int random = mRandom.nextInt(valueSet.size());
            return valueSet.get(random);
        }
    }


    /**
     * the integer type
     * @return -1 negative 0 zero  1 positive
     */
    private int getGenerateNumberType(float negativeScale, float zeroScale, float positiveScale){
        int negativeUp = (int) (100 * negativeScale);
        int zeroScaleUp = negativeUp + (int) (100 * zeroScale);
        int positiveScaleUp = zeroScaleUp + (int) (100 * positiveScale);
        int r = mRandom.nextInt(positiveScaleUp);
        if(r < negativeUp){
            return -1;
        } else if(r < zeroScaleUp){
            return 0;
        } else{
            return 1;
        }
    }


    public abstract static class NumberBuilder<T extends Number> {
        //is this generator generates negative value
        protected boolean mGenerateNegative = true;
        //is this generator generates zero value
        protected boolean mGenerateZero = true;
        //is this generator generates positive value
        protected boolean mGeneratePositive = true;

        protected T mMaxBound;

        protected T mMinBound;

        // if this set settled, the positive value will be gotten from this set
        protected List<T> mPositiveValueSet;
        // if this set settled, the negative value will be gotten from this set
        protected List<T> mNegativeValueSet;

        //if this set settled, the value will be gotten from this set
        protected List<T> mValueSet;

        protected int mScaleOfPositive = -1;

        protected int mScaleOfNegative = -1;

        protected int mScaleOfZero = -1;


        public NumberBuilder(){
            mMaxBound = getDefaultMaxValue();
            mMinBound = getDefaultMinValue();
        }

        protected abstract T getDefaultMaxValue();

        protected abstract T getDefaultMinValue();

        public NumberBuilder setGenerateNegative(boolean generateNegative){
            mGenerateNegative = generateNegative;
            return this;
        }

        public NumberBuilder setGenerateZero(boolean generateZero){
            mGenerateZero = generateZero;
            return this;
        }

        public NumberBuilder setGeneratePositive(boolean generatePositive){
            mGeneratePositive = generatePositive;
            return this;
        }

        public NumberBuilder setMaxBound(T maxBound){
            mMaxBound = maxBound;
            return this;
        }

        public NumberBuilder setMinBound(T minBound){
            mMinBound = minBound;
            return this;
        }

        public NumberBuilder setPositiveValueSet(List<T> set){
            mPositiveValueSet = set;
            return this;
        }

        public NumberBuilder setNegativeValueSet(List<T> set){
            mNegativeValueSet = set;
            return this;
        }

        public NumberBuilder setValueSet(List<T> set){
            mValueSet = set;
            return this;
        }

        /**
         * the scale to generate positive, zero, negative value, the default is 7:1:2
         * @param positiveScale
         * @param zeroScale
         * @param negativeScale
         */
        public NumberBuilder setGenerateScale(int positiveScale, int zeroScale, int negativeScale){
            mScaleOfNegative = negativeScale;
            mScaleOfZero = zeroScale;
            mScaleOfPositive = positiveScale;
            return this;
        }


        protected abstract NumberGenerator<T> getNewGenerator();


        public NumberGenerator build(){
            NumberGenerator<T> generator = getNewGenerator();
            if(!mGenerateNegative && !mGenerateZero && !mGeneratePositive){
                throw new IllegalArgumentException("GenerateNegative GenerateZero GeneratePositive can't be all false");
            }
            generator.setGenerateNegative(mGenerateNegative);
            generator.setGenerateZero(mGenerateZero);
            generator.setGeneratePositive(mGeneratePositive);
            if(generator.smallerOrEqual(mMaxBound, mMinBound)){
                throw new IllegalArgumentException("Min bound can't be big than max bound");
            }
            generator.setMaxBound(mMaxBound);
            generator.setMinBound(mMinBound);
            generator.setPositiveValueSet(mPositiveValueSet);
            generator.setNegativeValueSet(mNegativeValueSet);
            generator.setValueSet(mValueSet);
            if(mScaleOfPositive >=0 && mScaleOfZero >= 0 && mScaleOfNegative >= 0 && (mScaleOfPositive + mScaleOfZero + mScaleOfNegative) > 0) {
                generator.setGenerateScale(mScaleOfPositive, mScaleOfZero, mScaleOfNegative);
            }
            return generator;
        }

    }


}
