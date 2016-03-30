package cn.campusapp.lib.generator;

import cn.campusapp.lib.utils.RandomExtendUtil;

/**
 * Created by kris on 16/3/28.
 */
public class ShortGenerator extends NumberGenerator<Short> {

    protected ShortGenerator(){}
    @Override
    protected Short getZero() {
        return 0;
    }

    @Override
    protected Short getRandomPositiveValue() {
        //min 1 max 10
        short temp = mMinBound < 0 ? 0 : mMinBound;   //1 -> 1
        return (short)( temp + RandomExtendUtil.nextShort(mRandom, (short)(mMaxBound - temp + 1))); // 1 + [0, 10) -> [2, 11)
    }

    @Override
    protected Short getRandomNegativeValue() {
        //min -10 max -1
        short temp = mMaxBound < 0 ? mMaxBound : 0;
        return (short)(-(-temp + RandomExtendUtil.nextShort(mRandom, (short)(-mMinBound + temp + 1))));  // -(1 + [0, 10)) -> - [1, 11),;
    }

    @Override
    protected int compare(Short value1, Short value2) {
        if(value1 < value2){
            return -1;
        } else if(value1 == value2){
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public Class<?> getClassToGenerate() {
        return short.class;
    }

    public static class Builder extends NumberBuilder<Short>{

        @Override
        protected Short getDefaultMaxValue() {
            return Short.MAX_VALUE;
        }

        @Override
        protected Short getDefaultMinValue() {
            return Short.MIN_VALUE;
        }

        @Override
        protected ShortGenerator getNewGenerator() {
            return new ShortGenerator();
        }


        @Override
        public ShortGenerator build() {
            return (ShortGenerator) super.build();
        }
    }
}
