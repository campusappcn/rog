package cn.campusapp.rog.generator.classes;

import cn.campusapp.rog.generator.IGenerator;

/**
 * Created by kris on 16/4/6.
 */
public class TestClass2Generator implements IGenerator<TestClass2> {
    @Override
    public TestClass2 generate() {
        return new TestClass2("HAHA");
    }

    @Override
    public Class<?> getClassToGenerate() {
        return TestClass2.class;
    }
}
