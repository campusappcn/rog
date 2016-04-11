package cn.campusapp.rog.generator;

import java.util.List;
import java.util.Random;

import cn.campusapp.rog.utils.StringUtil;

/**
 * Created by kris on 16/3/25.
 * a String generator
 */
public class StringGenerator implements IGenerator<String> {
    public static final int MODE_RANDOM = 0;//generate unicode char randomly
    public static final int MODE_ASCII = 1;  //generate only ascii char
    public static final int MODE_NORMAL = 2; //generate only ascii char and chinese
    private int mMode = MODE_NORMAL;
    //will the generator generate null
    protected boolean mGenerateNull = true;
    //will the generator generate ""
    protected boolean mGenerateEmpty = true;
    //will the generator generate not empty string
    protected boolean mGenerateNotEmpty = true;

    // the max length of string generated
    protected int mMaxLengthOfString = 100;

    // if this settled, the character make up the string will be fetched from this list
    protected List<Character> mCharSet;

    // if this settled, the string will be fetched from this list
    protected List<String> mValueSet;

    // the scale to generate null
    protected float mProportionGenerateNull = 0.1f;

    protected float mProportionGenerateEmpty = 0.1f;

    protected float mProportionGenerateNotEmpty = 0.8f;

    //
    protected float mProportionGenerateChinese = 0.7f;

    //the proportion to generate english in string, and number character is including in it
    protected float mProportionGenerateEnglish = 0.3f;

    private Random mRandom = new Random();

    protected StringGenerator(){}

    /**
     * set if the generator generate null
     * @param generateNull
     */
    public void setGenerateNull(boolean generateNull){
        mGenerateNull = generateNull;
    }

    /**
     * set if the generator generate empty string
     * @param generateEmpty
     */
    public void setGenerateEmpty(boolean generateEmpty){
        mGenerateEmpty = generateEmpty;
    }

    /**
     * set if the generator generate not empty string (string != null && string.length > 0)
     * @param generateNotEmpty
     */
    public void setGenerateNotEmpty(boolean generateNotEmpty){
        mGenerateNotEmpty = generateNotEmpty;
    }

    /**
     * set max length of strings generated
     * @param maxLength
     */
    public void setMaxLength(int maxLength){
        mMaxLengthOfString = maxLength;
    }

    /**
     * if the set settled, the characters in the strings generated will be fetched from this set.
     * @param charSet
     */
    public void setCharSet(List<Character> charSet){
        mCharSet = charSet;
    }

    /**
     * if the set settled, the strings generated will be fetched from this set
     * @param valueSet
     */
    public void setValueSet(List<String> valueSet){
        mValueSet = valueSet;
    }

    /**
     * set the proportion to generate null, empty and not empty strings.
     * @param proportionOfNull in [0, n)
     * @param proportionOfEmpty in [0, n)
     * @param proportionOfNotEmpty in [0, n)
     */
    public void setProportion(int proportionOfNull, int proportionOfEmpty, int proportionOfNotEmpty){
        if(proportionOfNull == 0 && proportionOfEmpty == 0 && proportionOfNotEmpty == 0){
            throw new IllegalArgumentException("Proportion of null, empty, not empty can't be all 0");
        } else if(proportionOfNull < 0 || proportionOfEmpty < 0 || proportionOfNotEmpty < 0){
            throw new IllegalArgumentException("Proportion can't be negative");
        }
        int scaleCount = proportionOfEmpty + proportionOfNull + proportionOfNotEmpty;
        mProportionGenerateNull = 1.0f * proportionOfNull / scaleCount;
        mProportionGenerateEmpty = 1.0f * proportionOfEmpty / scaleCount;
        mProportionGenerateNotEmpty = 1.0f * proportionOfNotEmpty / scaleCount;
    }


    /**
     * set the string generating MODE
     * @param mode  MODE_RANDOM = 0; //generate unicode char randomly MODE_ASCII = 1;  //generate only ascii char   MODE_NORMAL = 2; //generate only ascii char and chinese
     */
    public void setMode(int mode){
        mMode = mode;
    }


    /**
     * only when mode is MODE_NORMAL, this function will make effect. It set the proportion to generate Chinese and
     * English characters in the strings generated.
     * @param proportionOfChinese
     * @param proportionOfEnglish
     */
    public void setChineseProportion(int proportionOfChinese, int proportionOfEnglish){
        if(proportionOfChinese == 0 && proportionOfEnglish == 0){
            throw new RuntimeException("Scale of chinese and english can't be all 0");
        }
        int scaleCount = proportionOfChinese + proportionOfEnglish;
        mProportionGenerateChinese = 1.0f * proportionOfChinese / scaleCount;
        mProportionGenerateEnglish = 1.0f * proportionOfEnglish / scaleCount;
    }



    @Override
    public String generate() {
        float scaleOfNull = mGenerateNull ? mProportionGenerateNull : 0;
        float scaleOfEmpty = mGenerateEmpty ? mProportionGenerateEmpty : 0;
        float scaleOfNotEmpty = mGenerateNotEmpty ? mProportionGenerateNotEmpty : 0;
        if(mValueSet != null && mValueSet.size() > 0){
            return generateFromSet(mValueSet);
        } else{
            switch (getStringType(scaleOfNull, scaleOfEmpty, scaleOfNotEmpty)){
                case -1:
                    return null;
                case 0:
                    return "";
                case 1:
                    int length = 1 + mRandom.nextInt(mMaxLengthOfString);
                    if(mCharSet != null && mCharSet.size() > 0){
                        return generateFromCharSet(mCharSet, length);
                    } else {
                        return generateRandom(length);
                    }
                default:
                    return null;
            }
        }
    }

    @Override
    public Class<?> getClassToGenerate() {
        return String.class;
    }


    private String generateFromSet(List<String> set){
        if(set == null || set.size() == 0){
            throw new IllegalArgumentException("The set can't be empty");
        }
        return set.get(mRandom.nextInt(set.size()));
    }


    private String generateFromCharSet(List<Character> set, int length){
        if(set == null || set.size() == 0){
            throw new IllegalArgumentException("The set can't be empty");
        }
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<length;i++){
            builder.append(set.get(mRandom.nextInt(set.size())));
        }
        return builder.toString();
    }

    private String generateRandom(int length){
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<length;i++){
            switch (mMode){
                case MODE_ASCII:
                    builder.append(StringUtil.generateRandomEnglishChar());
                    break;
                case MODE_NORMAL:
                    if(getCharType(mProportionGenerateEnglish, mProportionGenerateChinese) == 0) {
                        builder.append(StringUtil.generateRandomChineseWord());
                    } else {
                        builder.append(StringUtil.generateRandomEnglishChar());
                    }
                    break;
                case MODE_RANDOM:
                default:
                    builder.append(StringUtil.generateRandomChar());
                    break;
            }

        }
        return builder.toString();
    }


    /**
     * decide if this char is Chinese or English
     * @return 0 Chinese 1 English
     */
    private int getCharType(float scaleOfEnglish, float scaleOfChinese){
        int englishUp = (int)(100 * scaleOfEnglish);
        int chineseUp = englishUp + (int)(100 * scaleOfChinese);
        int r = mRandom.nextInt(chineseUp);
        if(r < englishUp){
            return 1;
        } else {
            return 0;
        }
    }


    /**
     * decide the return string type
     *
     * @param scaleOfNull
     * @param scaleOfEmpty
     * @param scaleOfNotEmpty
     * @return -1 null, 0 "", 1 not empty string
     */
    private int getStringType(float scaleOfNull, float scaleOfEmpty, float scaleOfNotEmpty){
        int nullUp = (int)(100 * scaleOfNull);  // [0, 40)
        int emptyUp = nullUp + (int)(100 * scaleOfEmpty); //[40, 60)
        int notEmptyUp = emptyUp + (int)(100 * scaleOfNotEmpty); //[60, 100)
        int r = mRandom.nextInt(notEmptyUp);
        if(r < nullUp){
            return -1;
        } else if(r < emptyUp){
            return 0;
        } else {
            return 1;
        }
    }

    public static class Builder{
        //will the generator generate null
        protected boolean mGenerateNull = true;
        //will the generator generate ""
        protected boolean mGenerateEmpty = true;
        //will the generator generate not empty string
        protected boolean mGenerateNotEmpty = true;

        // the max length of string generated
        protected int mMaxLengthOfString = 1000;

        // if this settled, the character make up the string will be fetched from this list
        protected List<Character> mCharSet;

        // if this settled, the string will be fetched from this list
        protected List<String> mValueSet;

        // the proportion to generate null
        protected int mProportionOfNull = -1;

        // the proportion to generate empty Strings.
        protected int mProportionOfEmpty = -1;

        protected int mProportionOfNotEmpty = -1;

        protected int mProportionOfChinese = -1;

        protected int mProportionOfEnglish = -1;

        protected int mMode = -1;

        /**
         * set if the generator generate null
         * @param generateNull
         */
        public Builder setGenerateNull(boolean generateNull){
            mGenerateNull = generateNull;
            return this;
        }

        /**
         * set if the generator generate empty string
         * @param generateEmpty
         */
        public Builder setGenerateEmpty(boolean generateEmpty){
            mGenerateEmpty = generateEmpty;
            return this;
        }

        /**
         * set if the generator generate not empty string (string != null && string.length > 0)
         * @param generateNotEmpty
         */
        public Builder setGenerateNotEmpty(boolean generateNotEmpty){
            mGenerateNotEmpty = generateNotEmpty;
            return this;
        }

        /**
         * set max length of strings generated
         * @param maxLength
         */
        public Builder setMaxLength(int maxLength){
            mMaxLengthOfString = maxLength;
            return this;
        }

        /**
         * if the set settled, the characters in the strings generated will be fetched from this set.
         * @param charSet
         */
        public Builder setCharSet(List<Character> charSet){
            mCharSet = charSet;
            return this;
        }

        /**
         * if the set settled, the strings generated will be fetched from this set
         * @param valueSet
         */
        public Builder setValueSet(List<String> valueSet){
            mValueSet = valueSet;
            return this;
        }

        /**
         * set the string generating MODE
         * @param mode  MODE_RANDOM = 0; //generate unicode char randomly MODE_ASCII = 1;  //generate only ascii char   MODE_NORMAL = 2; //generate only ascii char and chinese
         */
        public Builder setMode(int mode){
            mMode = mode;
            return this;
        }

        /**
         * set the proportion to generate null, empty and not empty strings.
         * @param proportionOfNull in [0, n)
         * @param proportionOfEmpty in [0, n)
         * @param proportionOfNotEmpty in [0, n)
         */
        public Builder setProportion(int proportionOfNull, int proportionOfEmpty, int proportionOfNotEmpty){
            if(proportionOfNull < 0 || proportionOfEmpty < 0 || proportionOfNotEmpty < 0  || proportionOfNull + proportionOfEmpty + proportionOfNotEmpty <= 0){
                throw new IllegalArgumentException("Proportion not in bound");
            }
            mProportionOfNull = proportionOfNull;
            mProportionOfEmpty = proportionOfEmpty;
            mProportionOfNotEmpty = proportionOfNotEmpty;
            return this;
        }

        /**
         * only when mode is MODE_NORMAL, this function will make effect. It set the proportion to generate Chinese and
         * English characters in the strings generated.
         * @param proportionOfChinese
         * @param proportionOfEnglish
         */
        public Builder setChineseProportion(int proportionOfChinese, int proportionOfEnglish){
            if(proportionOfChinese < 0 || proportionOfEnglish < 0 || proportionOfChinese + proportionOfEnglish <=0){
                throw new IllegalArgumentException("Proportion not in bound");
            }
            mProportionOfChinese = proportionOfChinese;
            mProportionOfEnglish = proportionOfEnglish;
            return this;
        }

        public StringGenerator build(){
            StringGenerator generator = new StringGenerator();
            if(!mGenerateEmpty && !mGenerateNull && !mGenerateNotEmpty){
                throw new IllegalArgumentException("The generate null empty and not empty can't be all false");
            }
            generator.setGenerateNull(mGenerateNull);
            generator.setGenerateEmpty(mGenerateEmpty);
            generator.setGenerateNotEmpty(mGenerateNotEmpty);
            generator.setCharSet(mCharSet);
            generator.setValueSet(mValueSet);
            if(mMaxLengthOfString <=0){
                throw new IllegalArgumentException("The max length of the string max must >= 0");
            }
            generator.setMaxLength(mMaxLengthOfString);
            if(mProportionOfNull >= 0 && mProportionOfEmpty >= 0 && mProportionOfNotEmpty >= 0 && (mProportionOfNull + mProportionOfEmpty + mProportionOfNotEmpty) > 0) {
                generator.setProportion(mProportionOfNull, mProportionOfEmpty, mProportionOfNotEmpty);
            }
            if(mProportionOfChinese >= 0 && mProportionOfEnglish >=0 && (mProportionOfChinese + mProportionOfEnglish) > 0){
                generator.setChineseProportion(mProportionOfChinese, mProportionOfEnglish);
            }
            if(mMode != -1){
                generator.setMode(mMode);
            }
            return generator;

        }

    }




}
