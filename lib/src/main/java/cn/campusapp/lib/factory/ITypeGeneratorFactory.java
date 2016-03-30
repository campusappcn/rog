package cn.campusapp.lib.factory;

import cn.campusapp.lib.generator.IGenerator;

/**
 * Created by kris on 16/3/29.
 */
public interface ITypeGeneratorFactory {

    <E> IGenerator<E> getGenerator(Class<E> type);

    void setGenerator(IGenerator generator);

}
