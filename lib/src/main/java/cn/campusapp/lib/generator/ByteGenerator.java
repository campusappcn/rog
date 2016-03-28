package cn.campusapp.lib.generator;

import java.util.List;
import java.util.Random;

/**
 * Created by kris on 16/3/28.
 * Generator for byte.
 * feature: supports generate random byte value
 * feature: support user setting the scale of generating 0 or 1 at each bit
 * feature: support user defining some values that generator will generate only these user-defined values
 *
 */
@Deprecated
public class ByteGenerator implements IGenerator {

    private float mScaleOfZero = 0.5f;
    private float mScaleOfOne = 0.5f;

    private List<Byte> mValueSet;

    Random mRandom = new Random();

    private ByteGenerator(){}

    public void setGenerateScale(int scaleOfZero, int scaleOfOne){
        if(scaleOfOne == 0 && scaleOfZero == 0 ){
            throw new IllegalArgumentException("Scale of zero and one can't be all zero");
        } else if(scaleOfOne < 0 || scaleOfZero < 0){
            throw new IllegalArgumentException("Scale of zero and one can't be small than 0");
        }
        int scaleCount = scaleOfOne + scaleOfZero;
        mScaleOfOne = 1.0f * scaleOfOne / scaleCount;
        mScaleOfZero = 1.0f * scaleOfZero / scaleCount;
    }

    public void setValueSet(List<Byte> valueSet){
        mValueSet = valueSet;
    }


    @Override
    public Byte generate() {
        return null;
    }


    public static class Builder{
        private int mScaleOfZero = -1;
        private int mScaleOfOne = -1;

        private List<Byte> mValueSet;


        public Builder setGenerateScale(int scaleOfZero, int scaleOfOne){
            mScaleOfZero = scaleOfZero;
            mScaleOfOne = scaleOfOne;
            return this;
        }

        public Builder setValueSet(List<Byte> valueSet){
            mValueSet = valueSet;
            return this;
        }


        public ByteGenerator build(){
            ByteGenerator generator = new ByteGenerator();
            if(mScaleOfOne >=0 && mScaleOfZero >= 0 && (mScaleOfOne + mScaleOfZero) > 0){
                generator.setGenerateScale(mScaleOfZero, mScaleOfOne);
            }
            if(mValueSet != null && mValueSet.size() > 0){
                generator.setValueSet(mValueSet);
            }
            return generator;
        }

    }
}
