package cn.campusapp.lib.factory;

import java.util.HashMap;
import java.util.Map;

import cn.campusapp.lib.generator.BooleanGenerator;
import cn.campusapp.lib.generator.ByteGenerator;
import cn.campusapp.lib.generator.CharGenerator;
import cn.campusapp.lib.generator.FloatGenerator;
import cn.campusapp.lib.generator.IGenerator;
import cn.campusapp.lib.generator.IntegerGenerator;
import cn.campusapp.lib.generator.LongGenerator;
import cn.campusapp.lib.generator.ShortGenerator;
import cn.campusapp.lib.generator.StringGenerator;

/**
 * Created by kris on 16/3/29.
 * 基本类型生成器工厂
 *
 */
public enum BasicTypeGeneratorFactory implements ITypeGeneratorFactory{
    INSTANCE;

    private Map<Class<?>, IGenerator> mGeneratorMap = new HashMap<>();

    BasicTypeGeneratorFactory(){
        setGenerator(new IntegerGenerator.Builder().build());
        setGenerator(new FloatGenerator.Builder().build());
        setGenerator(new CharGenerator.Builder().build());
        setGenerator(new LongGenerator.Builder().build());
        setGenerator(new ShortGenerator.Builder().build());
        setGenerator(new BooleanGenerator.Builder().build());
        setGenerator(new StringGenerator.Builder().build());
        setGenerator(new ByteGenerator.Builder().build());
    }

    public final BasicTypeGeneratorFactory getInstance(){
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final <E> IGenerator<E> getGenerator(Class<E> type) {
        return (IGenerator<E>)mGeneratorMap.get(type);
    }

    @Override
    public final void setGenerator(IGenerator generator) {
        if(generator.getClassToGenerate() == null){
            throw new IllegalArgumentException("The generator's getClassToGenerate() returns null");
        }
        mGeneratorMap.put(generator.getClassToGenerate(), generator);
    }

    public boolean isBasicType(Class<?> clazz){
        return getGenerator(clazz) != null;
    }



}
