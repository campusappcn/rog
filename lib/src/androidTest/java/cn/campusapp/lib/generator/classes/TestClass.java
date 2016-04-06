package cn.campusapp.lib.generator.classes;

/**
 * Created by kris on 16/4/6.
 */
public class TestClass{

    private TestClass(){}
    int p1;
    float p2;
    byte p3;
    char p4;
    long p5;
    double p6;
    final float p7 = 1.0f;
    private short p8;
    String p9;
    TestEnum p10;
    TestClass2 class2;

    @Override
    public String toString() {
        return "TestClass{" +
                "class2=" + (class2!= null ? class2.toString():null) +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                ", p4=" + p4 +
                ", p5=" + p5 +
                ", p6=" + p6 +
                ", p7=" + p7 +
                ", p8=" + p8 +
                ", p9='" + p9 + '\'' +
                ", p10=" + p10 +
                '}';
    }
}