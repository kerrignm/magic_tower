package com.game.magictower.util;

import java.util.Arrays;

public class ArrayUtil {
    public static void array2Str(int[] intArray, StringBuilder sb) {
        sb.append("{");
        for (int i = 0; i < intArray.length; i++) {
            sb.append(intArray[i]);
            if (i != intArray.length - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
    }
    
    public static void array2Str2(int[][] intArray2, StringBuilder sb) {
        sb.append("{");
        for (int i = 0; i < intArray2.length; i++) {
            array2Str(intArray2[i], sb);
            if (i != intArray2.length - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
    }
    
    public static void array2Str3(int[][][] intArray3, StringBuilder sb) {
        sb.append("{");
        for (int i = 0; i < intArray3.length; i++) {
            array2Str2(intArray3[i], sb);
            if (i != intArray3.length - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
    }
    
    public static int[] str2Array(String str) {
        int[] array;
        String subStr = str.substring(1, str.length()-1);
        String[] strArray = subStr.split(",");
        array = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            array[i] = Integer.parseInt(strArray[i]);
        }
        return array;
    }
    
    public static int[][] str2Array2(String str) {
        int[][] array2;
        String subStr = str.substring(1, str.length()-1);
        String[] strArray = subStr.split("\\},\\{");
        array2 = new int[strArray.length][];
        for (int i = 0; i < strArray.length; i++) {
            String s = strArray[i];
            if (!s.startsWith("{")) {
                s = "{" + s;
            }
            if (!s.endsWith("}")) {
                s = s + "}";
            }
            array2[i] = str2Array(s);
        }
        return array2;
    }
    
    public static int[][][] str2Array3(String str) {
        int[][][] array3;
        String subStr = str.substring(1, str.length()-1);
        String[] strArray = subStr.split("\\}\\},\\{\\{");
        array3 = new int[strArray.length][][];
        for (int i = 0; i < strArray.length; i++) {
            String s = strArray[i];
            if (!s.startsWith("{{")) {
                s = "{{" + s;
            }
            if (!s.endsWith("}}")) {
                s = s + "}}";
            }
            array3[i] = str2Array2(s);
        }
        return array3;
    }
    
    public static int[] copy(int[] array) {
        return Arrays.copyOf(array, array.length);
    }
    
    public static int[][] copy(int[][] array2) {
        int[][]result = new int[array2.length][];
        for (int i = 0; i < array2.length; i++) {
            result[i] = copy(array2[i]);
        }
        return result;
    }
    
    public static int[][][] copy(int[][][] array3) {
        int[][][]result = new int[array3.length][][];
        for (int i = 0; i < array3.length; i++) {
            result[i] = copy(array3[i]);
        }
        return result;
    }
}
