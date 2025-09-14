package com.litongjava.chat;

import java.util.ArrayList;
import java.util.List;

public class ResponseModalities {
  public static final String TEXT = "text";
  public static final String IMAGE = "image";

  public static List<String> justText() {
    List<String> modalities = new ArrayList<>();
    modalities.add(TEXT);
    return modalities;
  };

  public static List<String> justImage() {
    List<String> modalities = new ArrayList<>();
    modalities.add(IMAGE);
    return modalities;
  };

  public static List<String> all() {
    List<String> modalities = new ArrayList<>();
    modalities.add(TEXT);
    modalities.add(IMAGE);
    return modalities;
  };
}
