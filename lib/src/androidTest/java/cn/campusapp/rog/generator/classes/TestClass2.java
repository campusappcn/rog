package cn.campusapp.rog.generator.classes;

/**
 * Created by kris on 16/4/6.
 */
public class TestClass2{
    private TestClass2(){}

    public TestClass2(String tag){
        this.tag = tag;
    }
    int p1;
    float p2;
    byte p3;
    char p4;
    long p5;
    double p6;
    final float p7 = 1.0f;
    private short p8;
    public String tag;
    TestEnum p10;


    @Override
    public String toString() {
        return "TestClass2{" +
                "p10=" + p10 +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                ", p4=" + p4 +
                ", p5=" + p5 +
                ", p6=" + p6 +
                ", p7=" + p7 +
                ", p8=" + p8 +
                ", p9='" + tag + '\'' +
                '}';
    }
}