package cn.campusapp.lib.utils;

import java.util.Random;

/**
 * Created by kris on 16/3/28.
 */
public class RandomExtendUtil {


    /**
     *
     * @param random
     * @param n
     * @return long [0, n)
     */
    public static long nextLong(Random random, long n){
        // error checking and 2^x checking removed for simplicity.
        long bits, val;
        do {
            bits = (random.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits-val+(n-1) < 0L);
        return val;
    }


    public static byte nextByte(Random random, byte n){
        return (byte) random.nextInt(n);
    }

    /**
     *
     * @param random
     * @param n
     * @return short [0, n)
     */
    public static short nextShort(Random random, short n){
        return (short) random.nextInt(n);
    }

    /**
     *
     * @param random
     * @param n
     * @return float [0, n)
     */
    public static float nextFloat(Random random, float n){
        return (n - 1)* random.nextFloat();
    }

    /**
     *
     * @param random
     * @param n
     * @return double [0, n)
     */
    public static double nextDouble(Random random, double n){
        return (n - 1) * random.nextDouble();
    }



}
