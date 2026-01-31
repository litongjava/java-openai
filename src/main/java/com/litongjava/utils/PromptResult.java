package com.litongjava.utils;

import com.litongjava.tio.utils.hutool.StrUtil;

public class PromptResult {
  public static final String YES = "yes";
  public static final String NO = "no";

  public static final String TRUE = "true";
  public static final String FALSE = "false";

  public static final String DONE = "done";

  public static boolean isTrue(String input) {
    if (StrUtil.isBlank(input)) {
      return false;
    }
    input = input.trim().toLowerCase();
    if (YES.equals(input)) {
      return true;
    } else if (TRUE.equals(input)) {
      return true;
    }
    return false;
  }
}
