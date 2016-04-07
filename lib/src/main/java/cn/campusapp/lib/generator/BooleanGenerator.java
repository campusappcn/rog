package cn.campusapp.lib.generator;

import java.util.Random;

/**
 * Created by kris on 16/3/25.
 */
public class BooleanGenerator implements IGenerator<Boolean>{

    protected boolean mGenerateFalse = true;
    protected boolean mGenerateTrue = true;

    protected float mProportionOfFalse = 0.5f;
    protected float mProportionOfTrue = 0.5f;

    Random mRandom = new Random();

    protected BooleanGenerator(){}

    /**
     * set if the generator will generate false
     * @param generateFalse  true: will generate false, false: wont generate false
     */
    public void setGenerateFalse(boolean generateFalse){
        mGenerateFalse = generateFalse;
    }


    /**
     * set if the generator will generate true
     * @param generateTrue true: will generate true, false: wont generate true
     */
    public void setGenerateTrue(boolean generateTrue){
        mGenerateTrue = generateTrue;
    }

    /**
     * set the proportion of generating true and false
     * @param proportionOfFalse should be in [0, n)
     * @param proportionOfTrue  should be in [0, n)
     */
    public void setProportion(int proportionOfFalse, int proportionOfTrue){
        if(proportionOfFalse == 0 && proportionOfTrue == 0){
            throw new IllegalArgumentException("Scale of true and false can't be all zero");
        } else if(proportionOfFalse < 0 || proportionOfTrue < 0){
            throw new IllegalArgumentException("Scale of true and false can't be negative");
        }
        int scaleTotal = proportionOfFalse + proportionOfTrue;
        mProportionOfFalse = 1.0f * proportionOfFalse / scaleTotal;
        mProportionOfTrue = 1.0f * proportionOfTrue / scaleTotal;
    }

    @Override
    public Boolean generate() {
        float proportionOfFalse = mGenerateFalse ? mProportionOfFalse : 0;
        float proportionOfTrue = mGenerateTrue ? mProportionOfTrue : 0;

        return getTrueOrFalse(proportionOfFalse, proportionOfTrue);
    }

    @Override
    public Class<?> getClassToGenerate() {
        return boolean.class;
    }

    /**
     * decide if the return value is false or true according to the proportion
     * @return
     */
    private boolean getTrueOrFalse(float proportionOfFalse, float proportionOfTrue){
        float r = mRandom.nextFloat();
        return r >= proportionOfFalse;
    }


    public static class Builder{

        protected boolean mGenerateFalse = true;
        protected boolean mGenerateTrue = true;

        protected int mProportionOfFalse = -1;
        protected int mProportionOfTrue = -1;

        public Builder(){
        }

        /**
         * set if the generator will generate false
         * @param generateFalse  true: will generate false, false: wont generate false
         */
        public Builder setGenerateFalse(boolean generateFalse){
            mGenerateFalse = generateFalse;
            return this;
        }



        /**
         * set if the generator will generate true
         * @param generateTrue true: will generate true, false: wont generate true
         */
        public Builder setGenerateTrue(boolean generateTrue){
            mGenerateTrue = generateTrue;
            return this;
        }


        /**
         * set the proportion of generating true and false
         * @param proportionOfFalse should be in [0, n)
         * @param proportionOfTrue  should be in [0, n)
         */
        public Builder setProportion(int proportionOfFalse, int proportionOfTrue){
            if(proportionOfFalse < 0 || proportionOfTrue < 0 || proportionOfFalse + proportionOfTrue <= 0){
                throw new IllegalArgumentException("The proportionOfFalse and proportionOfTrue should be in bound of [0, n) and their sum should be in (0, n)");
            }
            mProportionOfFalse = proportionOfFalse;
            mProportionOfTrue = proportionOfTrue;
            return this;
        }



        public BooleanGenerator build(){
            BooleanGenerator generator = new BooleanGenerator();
            if(!mGenerateFalse && !mGenerateTrue){
                throw new IllegalArgumentException("Generate false and true can't be all false");
            }
            generator.setGenerateTrue(mGenerateTrue);
            generator.setGenerateFalse(mGenerateFalse);


            if(mProportionOfFalse >=0 && mProportionOfTrue >=0 && (mProportionOfTrue + mProportionOfFalse) > 0){
                generator.setProportion(mProportionOfFalse, mProportionOfTrue);
            }
            return generator;
        }


    }

}
