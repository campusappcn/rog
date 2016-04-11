package cn.campusapp.rog.generator;

import java.util.List;

import cn.campusapp.rog.utils.RandomExtendUtil;

/**
 * Created by kris on 16/3/28.
 */
public class DoubleGenerator extends NumberGenerator<Double> {
    @Override
    protected Double getZero() {
        return 0d;
    }


    /**
     * TODO can't get value of maxBound now. Should support it afterwards.
     * @return
     */
    @Override
    protected Double getRandomPositiveValue() {
        //min 1 max 10
        double temp = mMinBound <= 0 ? Double.MIN_VALUE : mMinBound;
        double value = mMaxBound - temp;
        return  temp + RandomExtendUtil.nextDouble(mRandom, value <= 0 ? 1 : value);

    }

    /**
     * TODO  can't get value of minBound now. Should support it afterwards.
     * @return
     */
    @Override
    protected Double getRandomNegativeValue() {
        //min -10 , max -1
        double temp = mMaxBound < 0 ? mMaxBound : - Double.MIN_VALUE;
        double value = -mMinBound + temp;

        return -(-temp + RandomExtendUtil.nextDouble(mRandom, value <= 0 ? 1 : value));
    }

    @Override
    protected int compare(Double value1, Double value2) {
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
        return double.class;
    }

    public static class Builder extends NumberBuilder<Double>{

        @Override
        protected Double getDefaultMaxValue() {
            return Double.MAX_VALUE;
        }

        @Override
        protected Double getDefaultMinValue() {
            return - Double.MAX_VALUE;
        }

        @Override
        protected DoubleGenerator getNewGenerator() {
            return new DoubleGenerator();
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

        public Builder setMaxBound(Double maxBound){
            return (Builder) super.setMaxBound(maxBound);
        }

        public Builder setMinBound(Double minBound){
            return (Builder) super.setMinBound(minBound);
        }

        public Builder setPositiveValueSet(List<Double> set){
            return (Builder) super.setPositiveValueSet(set);
        }

        public Builder setNegativeValueSet(List<Double> set){
            return (Builder) super.setNegativeValueSet(set);
        }

        public Builder setValueSet(List<Double> set){
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
        public DoubleGenerator build() {
            return (DoubleGenerator) super.build();
        }
    }
}
