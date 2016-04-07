package cn.campusapp.lib.generator;

import java.util.List;

import cn.campusapp.lib.utils.RandomExtendUtil;

/**
 * Created by kris on 16/3/29.
 */
public class ByteGenerator extends NumberGenerator<Byte> {


    protected ByteGenerator(){}

    @Override
    protected Byte getZero() {
        return 0;
    }

    /**
     * It can't generate Byte.MAX_VALUE;
     * TODO support generate Byte.MAX_VALUE
     * @return
     */
    @Override
    protected Byte getRandomPositiveValue() {
        //min 1 max 10
        byte temp = mMinBound < 1 ? 1 : mMinBound;   //1 -> 1
        if(mMaxBound == Byte.MAX_VALUE){
            mMaxBound = (byte)(mMaxBound - 1);
        }
        return (byte)( temp + RandomExtendUtil.nextByte(mRandom, (byte) (mMaxBound - temp + 1))); // 1 + [0, 10) -> [2, 11)
    }

    /**
     * It can't generate Byte.MIN_VALUE and Byte.MIN_VALUE +1
     * TODO support generate Short.MIN_VALUE and Short.MIN_VALUE + 1
     * @return
     */
    @Override
    protected Byte getRandomNegativeValue() {
        //min -10 max -1
        byte temp = mMaxBound < -1 ? mMaxBound : -1;
        //if (mMinBound = Byte.MIN_VALUE):
        //  - mMinBound will overflow.
        //if(mMinBound = Byte.MIN_VALUE + 1):
        //   -mMinBound + 1 will overflow.
        //so here must handle this
        if(mMinBound == Byte.MIN_VALUE){
            mMinBound = (byte) (mMinBound + 2);
        } else if(mMinBound == Byte.MIN_VALUE + 1){
            mMinBound = (byte) (mMinBound + 1);
        }
        return (byte)(-(-temp + RandomExtendUtil.nextByte(mRandom, (byte) (-mMinBound + temp + 1))));  // -(1 + [0, 10)) -> - [1, 11),;
    }

    @Override
    protected int compare(Byte value1, Byte value2) {
        if(value1 > value2){
            return 1;
        } else if(value1.equals(value2)) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public Class<?> getClassToGenerate() {
        return byte.class;
    }


    public static class Builder extends NumberBuilder<Byte>{

        @Override
        protected Byte getDefaultMaxValue() {
            return Byte.MAX_VALUE;
        }

        @Override
        protected Byte getDefaultMinValue() {
            return Byte.MIN_VALUE;
        }

        @Override
        protected ByteGenerator getNewGenerator() {
            return new ByteGenerator();
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

        public Builder setMaxBound(byte maxBound){
            return (Builder) super.setMaxBound(maxBound);
        }

        /**
         *
         * @param minBound
         * @return
         */
        public Builder setMinBound(byte minBound){
            return (Builder) super.setMinBound(minBound);
        }

        public Builder setPositiveValueSet(List<Byte> set){
            return (Builder) super.setPositiveValueSet(set);
        }

        public Builder setNegativeValueSet(List<Byte> set){
            return (Builder) super.setNegativeValueSet(set);
        }

        public Builder setValueSet(List<Byte> set){
            return (Builder) super.setValueSet(set);
        }


        public Builder setGenerateScale(int positiveScale, int zeroScale, int negativeScale){
            return (Builder) super.setGenerateScale(positiveScale, zeroScale, negativeScale);
        }


        @Override
        public ByteGenerator build() {
            return (ByteGenerator) super.build();
        }

    }
}
