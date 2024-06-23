package com.example.springbootgeodesy.common;

/**
 * =====================================================================================
 *
 * @Created :   2024/6/23 14:49
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class GeoTest {
    public static void main(String[] args) {
        // source （113.324553,23.106414）
        // target （121.499718, 31.239703）
        double distance1 = GeodsyDistanceUtils.getDistance(113.324553,23.106414,
                121.499718, 31.239703,2);
        System.out.println("distant1（m）：" + distance1);
        double distance2 = MathDistanceUtil.getDistance(113.324553, 23.106414, 121.499718, 31.239703);
        System.out.println("distant2（m）：" + distance2);
    }
}
