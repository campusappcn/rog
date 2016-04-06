package cn.campusapp.lib.generator;

import java.util.Random;

/**
 * Created by kris on 16/3/25.
 */
public class BooleanGenerator implements IGenerator<Boolean>{

    protected boolean mIsGenerateFalse = true;
    protected boolean mIsGenerateTrue = true;

    protected float mScaleOfFalse = 0.5f;
    protected float mScaleOfTrue = 0.5f;

    Random mRandom = new Random();

    protected BooleanGenerator(){}

    public void setGenerateFalse(boolean generateFalse){
        mIsGenerateFalse = generateFalse;
    }

    public void setGenerateTrue(boolean generateTrue){
        mIsGenerateTrue = generateTrue;
    }

    public void setScale(int scaleOfFalse, int scaleOfTrue){
        if(scaleOfFalse == 0 && scaleOfTrue == 0){
            throw new IllegalArgumentException("Scale of true and false can't be all zero");
        } else if(scaleOfFalse < 0 || scaleOfTrue < 0){
            throw new IllegalArgumentException("Scale of true and false can't be negative");
        }
        int scaleTotal = scaleOfFalse + scaleOfTrue;
        mScaleOfFalse = 1.0f * scaleOfFalse / scaleTotal;
        mScaleOfTrue = 1.0f * scaleOfTrue / scaleTotal;
    }

    @Override
    public Boolean generate() {
        float scaleOfFalse = mIsGenerateFalse ? mScaleOfFalse : 0;
        float scaleOfTrue = mIsGenerateTrue ? mScaleOfTrue : 0;

        return getTrueOrFalse(scaleOfFalse, scaleOfTrue);
    }

    @Override
    public Class<?> getClassToGenerate() {
        return boolean.class;
    }

    /**
     * decide if the return value is false or true according to the scale
     * @return
     */
    private boolean getTrueOrFalse(float scaleOfFalse, float scaleOfTrue){
        int falseUp = (int) (100 * scaleOfFalse);
        int trueUp = falseUp + (int)(100 * scaleOfTrue);
        int r = mRandom.nextInt(trueUp);
        return r >= falseUp;
    }


    public static class Builder{

        protected boolean mIsGenerateFalse = true;
        protected boolean mIsGenerateTrue = true;

        protected int mScaleOfFalse = -1;
        protected int mScaleOfTrue = -1;

        public Builder(){

        }

        public Builder setGenerateFalse(boolean generateFalse){
            mIsGenerateFalse = generateFalse;
            return this;
        }

        public Builder setGenerateTrue(boolean generateTrue){
            mIsGenerateTrue = generateTrue;
            return this;
        }

        public Builder setScale(int scaleOfFalse, int scaleOfTrue){
            mScaleOfFalse = scaleOfFalse;
            mScaleOfTrue = scaleOfTrue;
            return this;
        }



        public BooleanGenerator build(){
            BooleanGenerator generator = new BooleanGenerator();
            if(!mIsGenerateFalse && !mIsGenerateTrue){
                throw new IllegalArgumentException("Generate false and true can't be all false");
            }
            generator.setGenerateTrue(mIsGenerateTrue);
            generator.setGenerateFalse(mIsGenerateFalse);


            if(mScaleOfFalse >=0 && mScaleOfTrue >=0 && (mScaleOfTrue + mScaleOfFalse) > 0){
                generator.setScale(mScaleOfFalse, mScaleOfTrue);
            }
            return generator;
        }


    }

}
