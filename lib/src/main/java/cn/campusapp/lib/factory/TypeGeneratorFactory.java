package cn.campusapp.lib.factory;

import java.util.HashMap;
import java.util.Map;

import cn.campusapp.lib.generator.IGenerator;

/**
 * Created by kris on 16/3/28.
 */
public class TypeGeneratorFactory implements ITypeGeneratorFactory{

    private Map<Class<?>, IGenerator> mGeneratorMap = new HashMap<>();
    private static BasicTypeGeneratorFactory mBasicTypeFactory = BasicTypeGeneratorFactory.INSTANCE;

    private TypeGeneratorFactory(){}

    public static TypeGeneratorFactory getFactory(){
        return new TypeGeneratorFactory();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E>IGenerator<E> getGenerator(Class<E> type){
        IGenerator<E> generator = (IGenerator < E >)mGeneratorMap.get(type);
        if(generator == null){
            if(mBasicTypeFactory.isBasicType(type)){
                return mBasicTypeFactory.getGenerator(type);
            } else {
                return null;
            }
        } else {
            return generator;
        }
    }

    @Override
    public void setGenerator(IGenerator generator){
        if(generator.getClassToGenerate() == null){
            throw new IllegalArgumentException("generator's get class to generate() returns null");
        }
        mGeneratorMap.put(generator.getClassToGenerate(), generator);
    }

}
