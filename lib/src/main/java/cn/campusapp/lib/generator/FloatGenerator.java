package cn.campusapp.lib.generator;

import java.util.List;

import cn.campusapp.lib.utils.RandomExtendUtil;

/**
 * Created by kris on 16/3/28.
 */
public class FloatGenerator extends NumberGenerator<Float> {

    @Override
    protected Float getZero() {
        return 0f;
    }

    /**
     * TODO can't get value of maxBound now. Should support it afterwards.
     * @return
     */
    @Override
    protected Float getRandomPositiveValue() {
        //min 1 max 10
        float temp = mMinBound <= 0 ? Float.MIN_VALUE : mMinBound;
        return  temp + RandomExtendUtil.nextFloat(mRandom, mMaxBound - temp);

    }

    /**
     * TODO  can't get value of minBound now. Should support it afterwards.
     * @return
     */
    @Override
    protected Float getRandomNegativeValue() {
        //min -10 , max -1
        float temp = mMaxBound < 0 ? mMaxBound : - Float.MIN_VALUE;
        return -(-temp + RandomExtendUtil.nextFloat(mRandom, -mMinBound + temp));
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
            return - Float.MAX_VALUE;
        }

        @Override
        protected FloatGenerator getNewGenerator() {
            return new FloatGenerator();
        }



        public Builder setGenerateNegative(boolean generateNegative){
            return (Builder) super.setGenerateNegative(generateNegative);
        }

        public Builder setGenerateZero(boolean generateZero){
            return (Builder) super.setGenerateZero(generateZero);
        }

        public Builder setGeneratePositive(boolean generatePositive){
            return (Builder) super.setGeneratePositive(generatePositive);
        }

        public Builder setMaxBound(Float maxBound){
            return (Builder) super.setMaxBound(maxBound);
        }

        public Builder setMinBound(Float minBound){
            return (Builder) super.setMinBound(minBound);
        }

        public Builder setPositiveValueSet(List<Float> set){
            return (Builder) super.setPositiveValueSet(set);
        }

        public Builder setNegativeValueSet(List<Float> set){
            return (Builder) super.setNegativeValueSet(set);
        }

        public Builder setValueSet(List<Float> set){
            return (Builder) super.setValueSet(set);
        }

        /**
         * the scale to generate positive, zero, negative value, the default is 7:1:2
         * @param positiveScale
         * @param zeroScale
         * @param negativeScale
         */
        public Builder setGenerateScale(int positiveScale, int zeroScale, int negativeScale){
            return (Builder) super.setGenerateScale(positiveScale, zeroScale, negativeScale);
        }

        @Override
        public FloatGenerator build() {
            return (FloatGenerator) super.build();
        }
    }
}
