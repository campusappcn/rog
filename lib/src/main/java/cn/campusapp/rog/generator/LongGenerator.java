package cn.campusapp.rog.generator;

import java.util.List;

import cn.campusapp.rog.utils.RandomExtendUtil;

/**
 * Created by kris on 16/3/28.
 */
public class LongGenerator extends NumberGenerator<Long> {
    protected LongGenerator(){}
    @Override
    protected Long getZero() {
        return 0l;
    }

    /**
     * It can't generate Long.MAX_VALUE; or it may overflow
     * TODO support generate Long.MAX_VALUE
     * @return
     */
    @Override
    protected Long getRandomPositiveValue() {
        //min 1 max 10
        long temp = mMinBound < 1 ? 1 : mMinBound;   //1 -> 1
        if(mMaxBound == Long.MAX_VALUE){
            mMaxBound -= 1;
        }
        long value = mMaxBound - temp + 1;
        return temp + RandomExtendUtil.nextLong(mRandom, value <=0? 1 : value); // 1 + [0, 10) -> [2, 11)
    }

    /**
     * It can't generate Integer.MIN_VALUE and Integer.MIN_VALUE +1
     * TODO support generate Integer.MIN_VALUE and Integer.MIN_VALUE + 1
     * @return
     */
    @Override
    protected Long getRandomNegativeValue() {
        //min -10 max -1
        long temp = mMaxBound < -1 ? mMaxBound : -1;
        //if (mMinBound = Long.MIN_VALUE):
        //  - mMinBound will overflow.
        //if(mMinBound = Long.MIN_VALUE + 1):
        //   -mMinBound + 1 will overflow.
        //so here must handle this
        if(mMinBound == Long.MIN_VALUE){
            mMinBound += 2;
        } else if(mMinBound == Long.MIN_VALUE + 1){
            mMinBound += 1;
        }
        long value = -mMinBound + temp + 1;
        return -(-temp + RandomExtendUtil.nextLong(mRandom, value <=0 ? 1 : value));  // -(1 + [0, 10)) -> - [1, 11),;
    }

    @Override
    protected int compare(Long value1, Long value2) {
        if(value1 < value2){
            return -1;
        } else if(value1.equals(value2)){
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public Class<?> getClassToGenerate() {
        return long.class;
    }


    public static class Builder extends NumberBuilder<Long>{

        @Override
        protected Long getDefaultMaxValue() {
            return Long.MAX_VALUE;
        }

        @Override
        protected Long getDefaultMinValue() {
            return Long.MIN_VALUE;
        }

        @Override
        protected LongGenerator getNewGenerator() {
            return new LongGenerator();
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

        public Builder setMaxBound(Long maxBound){
            return (Builder) super.setMaxBound(maxBound);
        }

        public Builder setMinBound(Long minBound){
            return (Builder) super.setMinBound(minBound);
        }

        public Builder setPositiveValueSet(List<Long> set){
            return (Builder) super.setPositiveValueSet(set);
        }

        public Builder setNegativeValueSet(List<Long> set){
            return (Builder) super.setNegativeValueSet(set);
        }

        public Builder setValueSet(List<Long> set){
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
        public LongGenerator build() {
            return (LongGenerator) super.build();
        }
    }
}
