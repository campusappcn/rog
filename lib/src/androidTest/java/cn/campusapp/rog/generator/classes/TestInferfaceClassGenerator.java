package cn.campusapp.rog.generator.classes;

import cn.campusapp.rog.generator.IGenerator;

/**
 * Created by kris on 16/4/6.
 */
public class TestInferfaceClassGenerator implements IGenerator<TestInterfaceClass> {
    @Override
    public TestInterfaceClass generate() {
        return new TestInterfaceClass(10);
    }

    @Override
    public Class<?> getClassToGenerate() {
        return TestInterfaceClass.class;
    }
}
