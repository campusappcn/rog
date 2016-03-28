package cn.campusapp.lib.generator;

import cn.campusapp.lib.utils.RandomExtendUtil;

/**
 * Created by kris on 16/3/28.
 */
public class LongGenerator extends NumberGenerator<Long> {
    @Override
    protected Long getZero() {
        return 0l;
    }

    @Override
    protected Long getRandomPositiveValue() {
        //min 1 max 10
        long temp = mMinBound < 0 ? 0 : mMinBound;   //1 -> 1
        return temp + RandomExtendUtil.nextLong(mRandom, mMaxBound -temp + 1); // 1 + [0, 10) -> [2, 11)
    }

    @Override
    protected Long getRandomNegativeValue() {
        //min -10 max -1
        long temp = mMaxBound < 0 ? mMaxBound : 0;
        return -(-temp + RandomExtendUtil.nextLong(mRandom, -mMinBound + temp + 1));  // -(1 + [0, 10)) -> - [1, 11),;
    }

    @Override
    protected int compare(Long value1, Long value2) {
        if(value1 < value2){
            return -1;
        } else if(value1 == value2){
            return 0;
        } else {
            return 1;
        }
    }


    public static class Builder extends NumberBuilder{

        @Override
        protected Number getDefaultMaxValue() {
            return Long.MAX_VALUE;
        }

        @Override
        protected Number getDefaultMinValue() {
            return Long.MIN_VALUE;
        }

        @Override
        protected LongGenerator getNewGenerator() {
            return new LongGenerator();
        }
    }
}
