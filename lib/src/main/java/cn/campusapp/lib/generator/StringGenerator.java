package cn.campusapp.lib.generator;

import java.util.List;
import java.util.Random;

import cn.campusapp.lib.utils.StringUtil;

/**
 * Created by kris on 16/3/25.
 * a String generator
 */
public class StringGenerator implements IGenerator<String> {
    private static final int MODE_RANDOM = 0;//generate unicode char randomly
    private static final int MODE_ASCII = 1;  //generate only ascii char
    private static final int MODE_NORMAL = 2; //generate only ascii char and chinese
    private int mMode = MODE_RANDOM;   //default MODE_RANDOM
    //will the generator generate null
    protected boolean mIsGenerateNull = true;
    //will the generator generate ""
    protected boolean mIsGenerateEmpty = true;
    //will the generator generate not empty string
    protected boolean mIsGenerateNotEmpty = true;

    // the max length of string generated
    protected int mMaxLengthOfString = 100;

    // if this settled, the character make up the string will be fetched from this list
    protected List<Character> mCharSet;

    // if this settled, the string will be fetched from this list
    protected List<String> mValueSet;

    // the scale to generate null
    protected float mScaleGenerateNull = 0.1f;

    protected float mScaleGenerateEmpty = 0.1f;

    protected float mScaleGenerateNotEmpty = 0.8f;

    //字符串中生成中文的比例
    protected float mScaleGenerateChinese = 0.7f;

    //the scale to generate english in string, and number character is including in it
    protected float mScaleGenerateEnglish = 0.3f;

    private Random mRandom = new Random();

    protected StringGenerator(){}

    // the scale to generate

    public void setGenerateNull(boolean generateNull){
        mIsGenerateNull = generateNull;
    }

    public void setGenerateEmpty(boolean generateEmpty){
        mIsGenerateEmpty = generateEmpty;
    }

    public void setGenerateNotEmpty(boolean generateNotEmpty){
        mIsGenerateNotEmpty = generateNotEmpty;
    }

    public void setMaxLength(int maxLength){
        mMaxLengthOfString = maxLength;
    }

    public void setCharSet(List<Character> charSet){
        mCharSet = charSet;
    }

    public void setValueSet(List<String> valueSet){
        mValueSet = valueSet;
    }

    public void setScale(int scaleOfNull, int scaleOfEmpty, int scaleOfNotEmpty){
        if(scaleOfNull == 0 && scaleOfEmpty == 0 && scaleOfNotEmpty == 0){
            throw new IllegalArgumentException("Scale of null, empty, not empty can't be all 0");
        } else if(scaleOfNull < 0 || scaleOfEmpty < 0 || scaleOfNotEmpty < 0){
            throw new IllegalArgumentException("Scale can't be negative");
        }
        int scaleCount = scaleOfEmpty + scaleOfNull + scaleOfNotEmpty;
        mScaleGenerateNull = 1.0f * scaleOfNull / scaleCount;
        mScaleGenerateEmpty = 1.0f * scaleOfEmpty / scaleCount;
        mScaleGenerateNotEmpty = 1.0f * scaleOfNotEmpty / scaleCount;
    }


    public void setMode(int mode){
        mMode = mode;
    }


    public void setChineseScale(int scaleOfChinese, int scaleOfEnglish){
        if(scaleOfChinese == 0 && scaleOfEnglish == 0){
            throw new RuntimeException("Scale of chinese and english can't be all 0");
        }
        int scaleCount = scaleOfChinese + scaleOfEnglish;
        mScaleGenerateChinese = 1.0f * scaleOfChinese / scaleCount;
        mScaleGenerateEnglish = 1.0f * scaleOfEnglish / scaleCount;
    }



    @Override
    public String generate() {
        float scaleOfNull = mIsGenerateNull ? mScaleGenerateNull : 0;
        float scaleOfEmpty = mIsGenerateEmpty ? mScaleGenerateEmpty : 0;
        float scaleOfNotEmpty = mIsGenerateNotEmpty ? mScaleGenerateNotEmpty : 0;
        if(mValueSet != null && mValueSet.size() > 0){
            return generateFromSet(mValueSet);
        } else{
            switch (getStringType(scaleOfNull, scaleOfEmpty, scaleOfNotEmpty)){
                case -1:
                    return null;
                case 0:
                    return "";
                case 1:
                    int length = mRandom.nextInt(mMaxLengthOfString + 1);
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
                    if(getCharType(mScaleGenerateEnglish, mScaleGenerateChinese) == 0) {
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
        protected boolean mIsGenerateNull = true;
        //will the generator generate ""
        protected boolean mIsGenerateEmpty = true;
        //will the generator generate not empty string
        protected boolean mIsGenerateNotEmpty = true;

        // the max length of string generated
        protected int mMaxLengthOfString = 1000;

        // if this settled, the character make up the string will be fetched from this list
        protected List<Character> mCharSet;

        // if this settled, the string will be fetched from this list
        protected List<String> mValueSet;

        // the scale to generate null
        protected int mScaleOfNull = -1;

        protected int mScaleOfEmpty = -1;

        protected int mScaleOfNotEmpty = -1;

        protected int mScaleOfChinese = -1;

        protected int mScaleOfEnglish = -1;

        public Builder setGenerateNull(boolean generateNull){
            mIsGenerateNull = generateNull;
            return this;
        }

        public Builder setGenerateEmpty(boolean generateEmpty){
            mIsGenerateEmpty = generateEmpty;
            return this;
        }

        public Builder setGenerateNotEmpty(boolean generateNotEmpty){
            mIsGenerateNotEmpty = generateNotEmpty;
            return this;
        }

        public Builder setMaxLength(int maxLength){
            mMaxLengthOfString = maxLength;
            return this;
        }

        public Builder setCharSet(List<Character> charSet){
            mCharSet = charSet;
            return this;
        }

        public Builder setValueSet(List<String> valueSet){
            mValueSet = valueSet;
            return this;
        }

        public Builder setScale(int scaleOfNull, int scaleOfEmpty, int scaleOfNotEmpty){
            mScaleOfNull = scaleOfNull;
            mScaleOfEmpty = scaleOfEmpty;
            mScaleOfNotEmpty = scaleOfNotEmpty;
            return this;
        }

        public Builder setChineseScale(int scaleOfChinese, int scaleOfEnglish){
            mScaleOfChinese = scaleOfChinese;
            mScaleOfEnglish = scaleOfEnglish;
            return this;
        }

        public StringGenerator build(){
            StringGenerator generator = new StringGenerator();
            if(!mIsGenerateEmpty && !mIsGenerateNull && !mIsGenerateNotEmpty){
                throw new IllegalArgumentException("The generate null empty and not empty can't be all false");
            }
            generator.setGenerateNull(mIsGenerateNull);
            generator.setGenerateEmpty(mIsGenerateEmpty);
            generator.setGenerateNotEmpty(mIsGenerateNotEmpty);
            generator.setCharSet(mCharSet);
            generator.setValueSet(mValueSet);
            if(mMaxLengthOfString <=0){
                throw new IllegalArgumentException("The max length of the string max must >= 0");
            }
            generator.setMaxLength(mMaxLengthOfString);
            if(mScaleOfNull >= 0 && mScaleOfEmpty >= 0 && mScaleOfNotEmpty >= 0 && (mScaleOfNull + mScaleOfEmpty + mScaleOfNotEmpty) > 0) {
                generator.setScale(mScaleOfNull, mScaleOfEmpty, mScaleOfNotEmpty);
            }
            if(mScaleOfChinese >= 0 && mScaleOfEnglish >=0 && (mScaleOfChinese + mScaleOfEnglish) > 0){
                generator.setChineseScale(mScaleOfChinese, mScaleOfEnglish);
            }
            return generator;

        }

    }




}
