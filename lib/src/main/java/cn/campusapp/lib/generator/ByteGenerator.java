package cn.campusapp.lib.generator;

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

    @Override
    protected Byte getRandomPositiveValue() {
        //min 1 max 10
        byte temp = mMinBound < 0 ? 0 : mMinBound;   //1 -> 1
        return (byte)( temp + RandomExtendUtil.nextByte(mRandom, (byte) (mMaxBound - temp + 1))); // 1 + [0, 10) -> [2, 11)
    }

    @Override
    protected Byte getRandomNegativeValue() {
        //min -10 max -1
        byte temp = mMaxBound < 0 ? mMaxBound : 0;
        return (byte)(-(-temp + RandomExtendUtil.nextByte(mRandom, (byte) (-mMinBound + temp + 1))));  // -(1 + [0, 10)) -> - [1, 11),;
    }

    @Override
    protected int compare(Byte value1, Byte value2) {
        if(value1 > value2){
            return 1;
        } else if(value1 == value2) {
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

    }
}
