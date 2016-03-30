package cn.campusapp.lib.generator;

import cn.campusapp.lib.utils.RandomExtendUtil;

/**
 * Created by kris on 16/3/28.
 */
public class FloatGenerator extends NumberGenerator<Float> {

    @Override
    protected Float getZero() {
        return 0f;
    }

    @Override
    protected Float getRandomPositiveValue() {
        //min 1 max 10
        float temp = mMinBound < 0 ? 0 : mMinBound;   //1 -> 1
        return temp + RandomExtendUtil.nextFloat(mRandom, mMaxBound - temp + 1); // 1 + [0, 10) -> [2, 11)
    }

    @Override
    protected Float getRandomNegativeValue() {
        //min -10 max -1
        float temp = mMaxBound < 0 ? mMaxBound : 0;
        return -(-temp + RandomExtendUtil.nextFloat(mRandom, -mMinBound + temp + 1));  // -(1 + [0, 10)) -> - [1, 11),;
    }

    @Override
    protected int compare(Float value1, Float value2) {
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
        return float.class;
    }

    public static class Builder extends NumberBuilder<Float>{

        @Override
        protected Float getDefaultMaxValue() {
            return Float.MAX_VALUE;
        }

        @Override
        protected Float getDefaultMinValue() {
            return Float.MIN_VALUE;
        }

        @Override
        protected FloatGenerator getNewGenerator() {
            return new FloatGenerator();
        }


        @Override
        public FloatGenerator build() {
            return (FloatGenerator) super.build();
        }
    }
}
