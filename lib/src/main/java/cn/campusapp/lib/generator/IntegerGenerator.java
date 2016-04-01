package cn.campusapp.lib.generator;

/**
 * Created by kris on 16/3/25.
 * A integer generator
 */
public class IntegerGenerator extends NumberGenerator<Integer> {

    protected IntegerGenerator(){}

    @Override
    protected Integer getZero() {
        return 0;
    }

    @Override
    protected Integer getRandomPositiveValue() {
        // min 1, max 9
        int temp = mMinBound < 0 ? 0 : mMinBound;   // 1 -> 1
        return temp + mRandom.nextInt(mMaxBound - temp + 1); // 1 + [0, 9)  -> [1, 10)
    }

    @Override
    protected Integer getRandomNegativeValue() {
        //min -10 , max -1
        int temp = mMaxBound < 0 ? mMaxBound : 0;  // -1 -> -1
        return -(-temp + mRandom.nextInt(-mMinBound + temp + 1));  // -(1 + [0, 10)) -> - [1, 11),
    }

    @Override
    protected int compare(Integer value1, Integer value2) {
        if(value1 > value2){
            return 1;
        } else if(value1.equals(value2)){
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public Class<?> getClassToGenerate() {
        return int.class;
    }


    public static class Builder extends NumberBuilder<Integer>{

        @Override
        protected Integer getDefaultMaxValue() {
            return Integer.MAX_VALUE;
        }

        @Override
        protected Integer getDefaultMinValue() {
            return Integer.MIN_VALUE;
        }

        @Override
        protected IntegerGenerator getNewGenerator() {
            return new IntegerGenerator();
        }

        @Override
        public IntegerGenerator build() {
            return (IntegerGenerator) super.build();
        }
    }


}
