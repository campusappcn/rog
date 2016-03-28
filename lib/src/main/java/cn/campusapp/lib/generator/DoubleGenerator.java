package cn.campusapp.lib.generator;

import cn.campusapp.lib.utils.RandomExtendUtil;

/**
 * Created by kris on 16/3/28.
 */
public class DoubleGenerator extends NumberGenerator<Double> {
    @Override
    protected Double getZero() {
        return 0d;
    }

    @Override
    protected Double getRandomPositiveValue() {
        //min 1 max 10
        double temp = mMinBound < 0 ? 0 : mMinBound;   //1 -> 1
        return temp + RandomExtendUtil.nextDouble(mRandom, mMaxBound - temp + 1); // 1 + [0, 10) -> [2, 11)
    }

    @Override
    protected Double getRandomNegativeValue() {
        //min -10 max -1
        double temp = mMaxBound < 0 ? mMaxBound : 0;
        return -(-temp + RandomExtendUtil.nextDouble(mRandom, -mMinBound + temp + 1));  // -(1 + [0, 10)) -> - [1, 11),;
    }

    @Override
    protected int compare(Double value1, Double value2) {
        if(value1 < value2){
            return -1;
        } else if(value1 == value2){
            return 0;
        } else {
            return 1;
        }
    }

    public static class Builder extends NumberBuilder<Double>{

        @Override
        protected Double getDefaultMaxValue() {
            return Double.MAX_VALUE;
        }

        @Override
        protected Double getDefaultMinValue() {
            return Double.MIN_VALUE;
        }

        @Override
        protected DoubleGenerator getNewGenerator() {
            return new DoubleGenerator();
        }
    }
}
