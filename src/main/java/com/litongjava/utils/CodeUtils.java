package com.litongjava.utils;

import com.litongjava.tio.utils.json.JsonUtils;
import com.litongjava.vo.ToolVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeUtils {

  public static String parsePythonCode(String generatedText) {
    String code;
    int indexOf = generatedText.indexOf("```python");
    if (indexOf == -1) {
      if (generatedText.startsWith("{") && generatedText.endsWith("}")) {
        code = generatedText;

        ToolVo toolVo = null;
        try {
          toolVo = JsonUtils.parse(code.trim(), ToolVo.class);
        } catch (Exception e) {
          log.error("Failed to parse Json:{}", code);
          return null;
        }
        return toolVo.getCode();
      } else if ("done".contentEquals(generatedText)) {
        return generatedText;

      } else if (generatedText.startsWith("import ") || generatedText.startsWith("from ")) {
        return generatedText;

      } else if (generatedText.startsWith("# -*- coding: utf-8 -*-")) {
        return generatedText;
      } else {
        log.error("No code data found in the output:{}", generatedText);
        return null;
      }
    } else {
      int lastIndexOf = generatedText.lastIndexOf("```");
      log.info("index:{},{}", indexOf, lastIndexOf);
      if (lastIndexOf > 9) {
        try {
          code = generatedText.substring(indexOf + 9, lastIndexOf);
        } catch (Exception e) {
          log.error("generated text:{}", generatedText, e);
          return null;
        }
      } else {
        try {
          code = generatedText.substring(indexOf + 9);
        } catch (Exception e) {
          log.error("generated text:{}", generatedText, e);
          return null;
        }
      }
    }
    return code;
  }

}
