package cn.campusapp.rog.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kris on 16/3/28.
 */
public abstract class NumberGenerator <T extends Number> implements IGenerator <T> {
    //is this generator generates negative value
    protected boolean mGenerateNegative = true;
    //is this generator generates zero value
    protected boolean mGenerateZero = true;
    //is this generator generates positive value
    protected boolean mGeneratePositive = true;

    protected T mMaxBound;

    protected T mMinBound;

    // if this set settled, the positive value will be gotten from this set
    protected List<T> mPositiveValueSet = new ArrayList<>();
    // if this set settled, the negative value will be gotten from this set
    protected List<T> mNegativeValueSet = new ArrayList<>();

    //if this set settled, the value will be gotten from this set, mValueSet has higher priority than mPositiveValueSet and mNegativeValueSet
    protected List<T> mValueSet;


    protected float mProportionGeneratePositive = 0.7f;

    protected float mProportionGenerateNegative = 0.2f;

    protected float mProportionGenerateZero = 0.1f;

    protected Random mRandom = new Random();

    /**
     * set if the generator will generate negative values.
     * @param generateNegative
     */
    public void setGenerateNegative(boolean generateNegative){
        mGenerateNegative = generateNegative;
    }

    /**
     * set if the generator will generate zero
     * @param generateZero
     */
    public void setGenerateZero(boolean generateZero){
        mGenerateZero = generateZero;
    }

    /**
     * set if the generator will generate positive values
     * @param generatePositive
     */
    public void setGeneratePositive(boolean generatePositive){
        mGeneratePositive = generatePositive;
    }

    /**
     * set max bound of values generated
     * @param maxBound
     */
    public void setMaxBound(T maxBound){
        mMaxBound = maxBound;
    }

    /**
     * set min bound of values generated
     * @param minBound
     */
    public void setMinBound(T minBound){
        mMinBound = minBound;
    }

    /**
     * set positive value set, if this set settled, the positive value will be gotten from this set
     * @param set
     */
    public void setPositiveValueSet(List<T> set){
        if(set != null){
            for(T t : set){
                if(larger(t, getZero())) {
                    mPositiveValueSet.add(t);
                }
            }
        }
    }

    /**
     * set negative value set, if this set settled, the negative value will be gotten from this set
     * @param set
     */
    public void setNegativeValueSet(List<T> set){
        if(set != null){
            for(T t : set){
                if(smaller(t, getZero())) {
                    mNegativeValueSet.add(t);
                }
            }
        }
    }

    /**
     * if this set settled, the value will be gotten from this set, mValueSet has higher priority than mPositiveValueSet and mNegativeValueSet
     * @param set
     */
    public void setValueSet(List<T> set){
        mValueSet = set;
    }

    /**
     * the proportion to generate positive, zero, negative value, the default is 7:1:2
     * @param proportionOfPositive
     * @param proportionOfZero
     * @param proportionOfNegative
     */
    public void setProportion(int proportionOfPositive, int proportionOfZero, int proportionOfNegative){
        if(proportionOfPositive == 0 && proportionOfZero == 0 && proportionOfNegative == 0){
            throw new IllegalArgumentException("The three scale can't be all zero");
        } else if(proportionOfPositive < 0 || proportionOfZero < 0 || proportionOfNegative < 0){
            throw new IllegalArgumentException("Scale can't be negative");
        }
        mProportionGenerateNegative = 1.0f * proportionOfNegative / (proportionOfPositive + proportionOfZero + proportionOfNegative);
        mProportionGeneratePositive = 1.0f * proportionOfPositive / (proportionOfPositive + proportionOfZero + proportionOfNegative);
        mProportionGenerateZero = 1.0f * proportionOfZero / (proportionOfPositive + proportionOfZero + proportionOfNegative);
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
        float negativeProportion = mGenerateNegative && (smaller(mMinBound, getZero())) ? mProportionGenerateNegative : 0;
        float positiveProportion = mGeneratePositive && (larger(mMaxBound, getZero()))? mProportionGeneratePositive : 0;
        float zeroProportion = mGenerateZero && (smallerOrEqual(mMinBound, getZero())) && (largerOrEqual(mMaxBound, getZero())) ? mProportionGenerateZero : 0;
        if(negativeProportion == 0 && positiveProportion == 0 && zeroProportion == 0){
            throw new RuntimeException("The negative scale, positive scale and zero scale can't be all zero");
        }
        negativeProportion = negativeProportion / (negativeProportion + positiveProportion + zeroProportion);
        positiveProportion = positiveProportion / (negativeProportion + positiveProportion + zeroProportion);
        zeroProportion = zeroProportion / (negativeProportion + positiveProportion + zeroProportion);

        int type = getGenerateNumberType(negativeProportion, zeroProportion, positiveProportion);
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
    private int getGenerateNumberType(float negativeProportion, float zeroProportion, float positiveProportion){
        int negativeUp = (int) (100 * negativeProportion);
        int zeroProportionUp = negativeUp + (int) (100 * zeroProportion);
        int positiveProportionUp = zeroProportionUp + (int) (100 * positiveProportion);
        int r = mRandom.nextInt(positiveProportionUp);
        if(r < negativeUp){
            return -1;
        } else if(r < zeroProportionUp){
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

        protected int mProportionOfPositive = -1;

        protected int mProportionOfNegative = -1;

        protected int mProportionOfZero = -1;


        public NumberBuilder(){
            mMaxBound = getDefaultMaxValue();
            mMinBound = getDefaultMinValue();
        }

        protected abstract T getDefaultMaxValue();

        protected abstract T getDefaultMinValue();

        /**
         * set if the generator will generate negative values.
         * @param generateNegative
         */
        public NumberBuilder setGenerateNegative(boolean generateNegative){
            mGenerateNegative = generateNegative;
            return this;
        }

        /**
         * set if the generator will generate zero
         * @param generateZero
         */
        public NumberBuilder setGenerateZero(boolean generateZero){
            mGenerateZero = generateZero;
            return this;
        }

        /**
         * set if the generator will generate positive values
         * @param generatePositive
         */
        public NumberBuilder setGeneratePositive(boolean generatePositive){
            mGeneratePositive = generatePositive;
            return this;
        }

        /**
         * set max bound of values generated
         * @param maxBound
         */
        public NumberBuilder setMaxBound(T maxBound){
            mMaxBound = maxBound;
            return this;
        }

        /**
         * set min bound of values generated
         * @param minBound
         */
        public NumberBuilder setMinBound(T minBound){
            mMinBound = minBound;
            return this;
        }

        /**
         * set positive value set, if this set settled, the positive value will be gotten from this set
         * @param set
         */
        public NumberBuilder setPositiveValueSet(List<T> set){
            mPositiveValueSet = set;
            return this;
        }

        /**
         * set negative value set, if this set settled, the negative value will be gotten from this set
         * @param set
         */
        public NumberBuilder setNegativeValueSet(List<T> set){
            mNegativeValueSet = set;
            return this;
        }

        /**
         * if this set settled, the value will be gotten from this set, mValueSet has higher priority than mPositiveValueSet and mNegativeValueSet
         * @param set
         */
        public NumberBuilder setValueSet(List<T> set){
            mValueSet = set;
            return this;
        }

        /**
         * the scale to generate positive, zero, negative value, the default is 7:1:2
         * @param positiveProportion
         * @param zeroProportion
         * @param negativeProportion
         */
        public NumberBuilder setGenerateProportion(int positiveProportion, int zeroProportion, int negativeProportion){
            mProportionOfNegative = negativeProportion;
            mProportionOfZero = zeroProportion;
            mProportionOfPositive = positiveProportion;
            return this;
        }


        protected abstract NumberGenerator<T> getNewGenerator();


        public NumberGenerator<T> build(){
            NumberGenerator<T> generator = getNewGenerator();
            if(!mGenerateNegative && !mGenerateZero && !mGeneratePositive){
                throw new IllegalArgumentException("GenerateNegative GenerateZero GeneratePositive can't be all false");
            }
            generator.setGenerateNegative(mGenerateNegative);
            generator.setGenerateZero(mGenerateZero);
            generator.setGeneratePositive(mGeneratePositive);
            if(generator.smaller(mMaxBound, mMinBound)){
                throw new IllegalArgumentException(String.format("Min bound can't be big than max bound: %s, %s", mMaxBound.toString(), mMinBound.toString()));
            }
            generator.setMaxBound(mMaxBound);
            generator.setMinBound(mMinBound);
            generator.setPositiveValueSet(mPositiveValueSet);
            generator.setNegativeValueSet(mNegativeValueSet);
            generator.setValueSet(mValueSet);
            if(mProportionOfPositive >=0 && mProportionOfZero >= 0 && mProportionOfNegative >= 0 && (mProportionOfPositive + mProportionOfZero + mProportionOfNegative) > 0) {
                generator.setProportion(mProportionOfPositive, mProportionOfZero, mProportionOfNegative);
            }
            return generator;
        }

    }


}
