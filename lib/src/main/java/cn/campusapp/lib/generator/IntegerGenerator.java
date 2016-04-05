package cn.campusapp.lib.generator;

import java.util.List;

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

    /**
     * It can't generate Integer.MAX_VALUE;
     * If want to generate it, will reduce the perform;
     * TODO support generate Integer.MAX_VALUE
     * @return
     */
    @Override
    protected Integer getRandomPositiveValue() {
        // min 1, max 9
        int temp = mMinBound < 1 ? 1 : mMinBound;   // 1 -> 1
        if(mMaxBound == Integer.MAX_VALUE){
            mMaxBound -= 1;
        }
        return temp + mRandom.nextInt(mMaxBound - temp + 1) ; // 1 + [0, 8)  -> [1, 9)
    }

    /**
     * It can't generate Integer.MIN_VALUE and Integer.MIN_VALUE +1
     * TODO support generate Integer.MIN_VALUE and Integer.MIN_VALUE + 1
     * @return
     */
    @Override
    protected Integer getRandomNegativeValue() {
        //min -10 , max -1
        int temp = mMaxBound < -1 ? mMaxBound : -1;  // -1 -> -1
        //if (mMinBound = Integer.MIN_VALUE):
        //  - mMinBound will overflow.
        //if(mMinBound = Integer.MIN_VALUE + 1):
        //   -mMinBound + 1 will overflow.
        //so here must handle this
        if(mMinBound == Integer.MIN_VALUE){
            mMinBound += 2;
        } else if(mMinBound ==Integer.MIN_VALUE + 1){
            mMinBound += 1;
        }

        return -(-temp + mRandom.nextInt(-mMinBound + temp + 1));  // -(1 + [0, 9)) -> - [1, 10),
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

        public Builder setGenerateNegative(boolean generateNegative){
            return (Builder) super.setGenerateNegative(generateNegative);
        }

        public Builder setGenerateZero(boolean generateZero){
            return (Builder) super.setGenerateZero(generateZero);
        }

        public Builder setGeneratePositive(boolean generatePositive){
            return (Builder) super.setGeneratePositive(generatePositive);
        }

        public Builder setMaxBound(Integer maxBound){
            return (Builder) super.setMaxBound(maxBound);
        }

        public Builder setMinBound(Integer minBound){
            return (Builder) super.setMinBound(minBound);
        }

        public Builder setPositiveValueSet(List<Integer> set){
            return (Builder) super.setPositiveValueSet(set);
        }

        public Builder setNegativeValueSet(List<Integer> set){
            return (Builder) super.setNegativeValueSet(set);
        }

        public Builder setValueSet(List<Integer> set){
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
        public IntegerGenerator build() {
            return (IntegerGenerator) super.build();
        }
    }


}
