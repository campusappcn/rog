package cn.campusapp.rog.generator;

/**
 * Created by kris on 16/3/25.
 */
public interface IGenerator <T>{
    T generate();

    Class<?> getClassToGenerate();
}
