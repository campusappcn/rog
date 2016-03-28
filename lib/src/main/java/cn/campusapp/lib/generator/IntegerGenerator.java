package cn.campusapp.lib.generator;

import java.util.List;
import java.util.Random;

/**
 * Created by kris on 16/3/25.
 * A integer generator
 */
public class IntegerGenerator implements IGenerator{
    //is this generator generates negative value
    protected boolean mGenerateNegative = true;
    //is this generator generates zero value
    protected boolean mGenerateZero = true;
    //is this generator generates positive value
    protected boolean mGeneratePositive = true;

    protected int mMaxBound = Integer.MAX_VALUE;

    protected int mMinBound = Integer.MIN_VALUE;

    // if this set settled, the positive value will be gotten from this set
    protected List<Integer> mPositiveValueSet;
    // if this set settled, the negative value will be gotten from this set
    protected List<Integer> mNegativeValueSet;

    //if this set settled, the value will be gotten from this set
    protected List<Integer> mValueSet;

    protected float mScaleGeneratePositive = 0.7f;

    protected float mScaleGenerateNegative = 0.2f;

    protected float mScaleGenerateZero = 0.1f;

    protected Random mRandom = new Random();

    private IntegerGenerator(){}

    public void setGenerateNegative(boolean generateNegative){
        mGenerateNegative = generateNegative;
    }

    public void setGenerateZero(boolean generateZero){
        mGenerateZero = generateZero;
    }

    public void setGeneratePositive(boolean generatePositive){
        mGeneratePositive = generatePositive;
    }

    public void setMaxBound(int maxBound){
        mMaxBound = maxBound;
    }

    public void setMinBound(int minBound){
        mMinBound = minBound;
    }

    public void setPositiveValueSet(List<Integer> set){
        mPositiveValueSet = set;
    }

    public void setNegativeValueSet(List<Integer> set){
        mNegativeValueSet = set;
    }

    public void setValueSet(List<Integer> set){
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




    public Integer generate(){
        if(mValueSet != null && mValueSet.size() != 0){
            return generateFromSet(mValueSet);
        } else {
            return generateRandom();
        }
    }


    private int generateRandom(){
        //value bounds and is generate influence the scale
        float negativeScale = mGenerateNegative && (mMinBound < 0) ? mScaleGenerateNegative : 0;
        float positiveScale = mGeneratePositive && (mMaxBound > 0)? mScaleGeneratePositive : 0;
        float zeroScale = mGenerateZero && (mMinBound <= 0) && (mMaxBound >=0) ? mScaleGenerateZero : 0;
        if(negativeScale == 0 && positiveScale == 0 && zeroScale == 0){
            throw new RuntimeException("The negative scale, positive scale and zero scale can't be all zero");
        }
        negativeScale = negativeScale / (negativeScale + positiveScale + zeroScale);
        positiveScale = positiveScale / (negativeScale + positiveScale + zeroScale);
        zeroScale = zeroScale / (negativeScale + positiveScale + zeroScale);

        int type = getGenerateIntType(negativeScale, zeroScale, positiveScale);
        if(type == -1){
            //generate a negative value
            if(mMinBound >= 0){
                throw new RuntimeException("Need to generate negative value, but the min bound >= 0");
            }
            if(mNegativeValueSet != null && mNegativeValueSet.size() != 0){
                return generateFromSet(mNegativeValueSet);
            } else {
                return -mRandom.nextInt(-mMinBound);
            }
        } else if(type == 0){
            return 0;
        } else{
            if(mMaxBound <=0 ){
                throw new RuntimeException("Need to generate positive value, but the max bound <= 0");
            }
            if(mPositiveValueSet != null && mPositiveValueSet.size() != 0){
                return generateFromSet(mPositiveValueSet);
            } else {
                return mRandom.nextInt(mMaxBound);
            }
        }
    }

    /**
     * if user set the valueSet, the value will be fetched from the set randomly
     * @return
     */
    private int generateFromSet(List<Integer> valueSet){
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
    private int getGenerateIntType(float negativeScale, float zeroScale, float positiveScale){
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


    public static class Builder{
        //is this generator generates negative value
        protected boolean mGenerateNegative = true;
        //is this generator generates zero value
        protected boolean mGenerateZero = true;
        //is this generator generates positive value
        protected boolean mGeneratePositive = true;

        protected int mMaxBound = Integer.MAX_VALUE;

        protected int mMinBound = Integer.MIN_VALUE;

        // if this set settled, the positive value will be gotten from this set
        protected List<Integer> mPositiveValueSet;
        // if this set settled, the negative value will be gotten from this set
        protected List<Integer> mNegativeValueSet;

        //if this set settled, the value will be gotten from this set
        protected List<Integer> mValueSet;

        protected int mScaleOfPositive = -1;

        protected int mScaleOfNegative = -1;

        protected int mScaleOfZero = -1;



        public Builder setGenerateNegative(boolean generateNegative){
            mGenerateNegative = generateNegative;
            return this;
        }

        public Builder setGenerateZero(boolean generateZero){
            mGenerateZero = generateZero;
            return this;
        }

        public Builder setGeneratePositive(boolean generatePositive){
            mGeneratePositive = generatePositive;
            return this;
        }

        public Builder setMaxBound(int maxBound){
            mMaxBound = maxBound;
            return this;
        }

        public Builder setMinBound(int minBound){
            mMinBound = minBound;
            return this;
        }

        public Builder setPositiveValueSet(List<Integer> set){
            mPositiveValueSet = set;
            return this;
        }

        public Builder setNegativeValueSet(List<Integer> set){
            mNegativeValueSet = set;
            return this;
        }

        public Builder setValueSet(List<Integer> set){
            mValueSet = set;
            return this;
        }

        /**
         * the scale to generate positive, zero, negative value, the default is 7:1:2
         * @param positiveScale
         * @param zeroScale
         * @param negativeScale
         */
        public Builder setGenerateScale(int positiveScale, int zeroScale, int negativeScale){
            mScaleOfNegative = negativeScale;
            mScaleOfZero = zeroScale;
            mScaleOfPositive = positiveScale;
            return this;
        }



        public IntegerGenerator build(){
            IntegerGenerator generator = new IntegerGenerator();
            if(!mGenerateNegative && !mGenerateZero && !mGeneratePositive){
                throw new IllegalArgumentException("GenerateNegative GenerateZero GeneratePositive can't be all false");
            }
            generator.setGenerateNegative(mGenerateNegative);
            generator.setGenerateZero(mGenerateZero);
            generator.setGeneratePositive(mGeneratePositive);
            if(mMaxBound <= mMinBound){
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
