package cn.campusapp.rog.generator;

import java.util.List;

import cn.campusapp.rog.utils.RandomExtendUtil;

/**
 * Created by kris on 16/3/28.
 */
public class ShortGenerator extends NumberGenerator<Short> {

    protected ShortGenerator(){}
    @Override
    protected Short getZero() {
        return 0;
    }

    /**
     * It can't generate Short.MAX_VALUE;
     * If want to generate it, will reduce the perform;
     * TODO support generate Short.MAX_VALUE
     * @return
     */
    @Override
    protected Short getRandomPositiveValue() {
        //min 1 max 10
        short temp = mMinBound < 1 ? 1 : mMinBound;   //1 -> 1
        if(mMaxBound == Short.MAX_VALUE){
            mMaxBound = (short)(mMaxBound - 1);
        }
        short value = (short)(mMaxBound - temp + 1);
        return (short)( temp + RandomExtendUtil.nextShort(mRandom, value <=0? 1 : value)); // 1 + [0, 10) -> [2, 11)
    }

    /**
     * It can't generate Short.MIN_VALUE and Short.MIN_VALUE +1
     * TODO support generate Short.MIN_VALUE and Short.MIN_VALUE + 1
     * @return
     */
    @Override
    protected Short getRandomNegativeValue() {
        //min -10 max -1
        short temp = mMaxBound < -1 ? mMaxBound : -1;
        //if (mMinBound = Short.MIN_VALUE):
        //  - mMinBound will overflow.
        //if(mMinBound = Short.MIN_VALUE + 1):
        //   -mMinBound + 1 will overflow.
        //so here must handle this
        if(mMinBound == Short.MIN_VALUE){
            mMinBound = (short)(mMinBound + 2);
        } else if(mMinBound ==Short.MIN_VALUE + 1){
            mMinBound = (short)(mMinBound + 1);
        }
        short value =  (short)(-mMinBound + temp + 1);
        return (short)(-(-temp + RandomExtendUtil.nextShort(mRandom, value <=0? 1:value)));  // -(1 + [0, 10)) -> - [1, 11),;
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

        public Builder setGenerateNegative(boolean generateNegative){
            return (Builder) super.setGenerateNegative(generateNegative);
        }

        public Builder setGenerateZero(boolean generateZero){
            return (Builder) super.setGenerateZero(generateZero);
        }

        public Builder setGeneratePositive(boolean generatePositive){
            return (Builder) super.setGeneratePositive(generatePositive);
        }

        public Builder setMaxBound(short maxBound){
            return (Builder) super.setMaxBound(maxBound);
        }

        /**
         *
         * @param minBound
         * @return
         */
        public Builder setMinBound(short minBound){
            return (Builder) super.setMinBound(minBound);
        }

        public Builder setPositiveValueSet(List<Short> set){
            return (Builder) super.setPositiveValueSet(set);
        }

        public Builder setNegativeValueSet(List<Short> set){
            return (Builder) super.setNegativeValueSet(set);
        }

        public Builder setValueSet(List<Short> set){
            return (Builder) super.setValueSet(set);
        }

        /**
         * the scale to generate positive, zero, negative value, the default is 7:1:2
         * @param positiveProportion
         * @param zeroProportion
         * @param negativeProportion
         */
        public Builder setGenerateProportion(int positiveProportion, int zeroProportion, int negativeProportion){
            return (Builder) super.setGenerateProportion(positiveProportion, zeroProportion, negativeProportion);
        }


        @Override
        public ShortGenerator build() {
            return (ShortGenerator) super.build();
        }
    }
}
