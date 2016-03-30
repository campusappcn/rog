package cn.campusapp.lib.generator;

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


    private char mMaxValue = Character.MAX_VALUE;
    private char mMinValue = Character.MIN_VALUE;

    private List<Character> mValueSet;

    private Random mRandom = new Random();


    protected CharGenerator(){}

    public void setMaxValue(char maxValue){
        mMaxValue = maxValue;
    }

    public void setMinValue(char minValue){
        mMinValue = minValue;
    }

    public void setValueSet(List<Character> valueSet){
        mValueSet = valueSet;
    }


    @Override
    public Character generate() {
        if(mValueSet != null && mValueSet.size() > 0){
            return generateCharFromValueSet(mValueSet);
        } else {
            return (char) generateIntValueInBounds(mMaxValue, mMinValue);
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
        private char mMaxValue = Character.MAX_VALUE;
        private char mMinValue = Character.MIN_VALUE;

        private List<Character> mValueSet;

        public Builder setMaxValue(char maxValue){
            mMaxValue = maxValue;
            return this;
        }

        public Builder setMinValue(char minValue){
            mMinValue = minValue;
            return this;
        }

        public Builder setValueSet(List<Character> valueSet){
            mValueSet = valueSet;
            return this;
        }

        public CharGenerator build(){
            CharGenerator generator = new CharGenerator();
            if(mMinValue > mMaxValue){
                throw new IllegalArgumentException("Min value can't larger than max value");
            }
            generator.setMaxValue(mMaxValue);
            generator.setMinValue(mMinValue);
            if(mValueSet != null && mValueSet.size() > 0){
                generator.setValueSet(mValueSet);
            }
            return generator;
        }


    }

}
