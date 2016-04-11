package cn.campusapp.rog.generator;

import java.util.List;
import java.util.Random;

/**
 * Created by kris on 16/3/28.
 * The character generator
 * feature: can generate random character
 * feature: support set max and min values
 * feature: support set value set
 */
public class CharGenerator implements IGenerator<Character>{


    private char mMaxBound = Character.MAX_VALUE;
    private char mMinBound = Character.MIN_VALUE;

    private List<Character> mValueSet;

    private Random mRandom = new Random();


    protected CharGenerator(){}

    /**
     * set up bound of values generated
     * @param maxBound
     */
    public void setMaxBound(char maxBound){
        mMaxBound = maxBound;
    }

    /**
     * set low bound of values genrated
     * @param minBound
     */
    public void setMinBound(char minBound){
        mMinBound = minBound;
    }

    /**
     * if the value set settled, the values generated will be gotton from this set.
     * @param valueSet
     */
    public void setValueSet(List<Character> valueSet){
        mValueSet = valueSet;
    }


    @Override
    public Character generate() {
        if(mValueSet != null && mValueSet.size() > 0){
            return generateCharFromValueSet(mValueSet);
        } else {
            return (char) generateIntValueInBounds(mMaxBound, mMinBound);
        }
    }

    @Override
    public Class<?> getClassToGenerate() {
        return char.class;
    }


    private int generateIntValueInBounds(char maxValue, char minValue){
        int maxIntValue = (int) maxValue;  // -> 1
        int minIntValue = (int) minValue; // -> 100
        return minIntValue + mRandom.nextInt(maxIntValue - minIntValue + 1);  //1 + [0, 100) -> [1, 101) -> [1, 100]
    }

    private char generateCharFromValueSet(List<Character> valueSet){
        if(valueSet == null || valueSet.size() == 0){
            throw new IllegalArgumentException("The value set can't be empty");
        }
        return valueSet.get(mRandom.nextInt(valueSet.size()));
    }

    public static class Builder{
        private char mMaxBound = Character.MAX_VALUE;
        private char mMinBound = Character.MIN_VALUE;

        private List<Character> mValueSet;

        /**
         * set up bound of values generated
         * @param maxBound
         */
        public Builder setMaxBound(char maxBound){
            mMaxBound = maxBound;
            return this;
        }

        /**
         * set low bound of values genrated
         * @param minBound
         */
        public Builder setMinBound(char minBound){
            mMinBound = minBound;
            return this;
        }

        /**
         * if the value set settled, the values generated will be gotton from this set.
         * @param valueSet
         */
        public Builder setValueSet(List<Character> valueSet){
            mValueSet = valueSet;
            return this;
        }

        public CharGenerator build(){
            CharGenerator generator = new CharGenerator();
            if(mMinBound > mMaxBound){
                throw new IllegalArgumentException("Min bound can't larger than max bound");
            }
            generator.setMaxBound(mMaxBound);
            generator.setMinBound(mMinBound);
            if(mValueSet != null && mValueSet.size() > 0){
                generator.setValueSet(mValueSet);
            }
            return generator;
        }


    }

}
