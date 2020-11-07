package com.game.magictower.util;

public class MathUtil {

    public static final float EPSILON = 0.00001f;
    
    public static boolean equals(float left, float right) {
        return Math.abs(left -right) < EPSILON;
    }
}
