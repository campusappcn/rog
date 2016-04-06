package cn.campusapp.lib.generator.classes;

import cn.campusapp.lib.generator.IGenerator;

/**
 * Created by kris on 16/4/6.
 */
public class TestIntegerGenerator implements IGenerator<Integer>{
    @Override
    public Integer generate() {
        return 1;
    }

    @Override
    public Class<?> getClassToGenerate() {
        return Integer.TYPE;
    }

}
