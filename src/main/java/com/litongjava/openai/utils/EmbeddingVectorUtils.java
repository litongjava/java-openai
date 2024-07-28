package com.litongjava.openai.utils;

import java.util.Arrays;

public class EmbeddingVectorUtils {

  public static String toString(float[] array) {
    return Arrays.toString(array);
  }

  public static float[] parseToFloats(String string) {
    // 移除字符串的方括号
    string = string.substring(1, string.length() - 1);

    // 按逗号分隔字符串
    String[] stringArray = string.split(",");

    // 创建一个新的 float 数组
    float[] floatArray = new float[stringArray.length];

    // 将每个分隔后的字符串转换为 float 并存储在 float 数组中
    for (int i = 0; i < stringArray.length; i++) {
      floatArray[i] = Float.parseFloat(stringArray[i]);
    }

    return floatArray;
  }

}
