package com.litongjava.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.litongjava.tio.utils.json.JsonUtils;
import com.litongjava.vo.CodeBlock;
import com.litongjava.vo.ToolVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeBlockUtils {
  public static final Pattern XML_FENCE = Pattern.compile("```xml\\s*([\\s\\S]*?)```", Pattern.MULTILINE);
  public static final String EMPTY_LINE_REGEX = "(?m)^(?:[ \\t]*\\r?\\n)+";

  /**
   * Extracts JSON content from the generated text.
   * If the text contains ```json fences, returns the content between them.
   * Otherwise, if the text looks like a JSON object (starts with { and ends with }),
   * returns the trimmed text.
   * @param generatedText the raw text containing JSON
   * @return the extracted JSON or null if none found
   */
  public static String parseJson(String generatedText) {
    if (generatedText == null) {
      log.error("Generated text is null");
      return null;
    }
    String code;
    int indexOf = generatedText.indexOf("```json");
    if (indexOf == -1) {
      generatedText = generatedText.trim();
      if (generatedText.startsWith("{") && generatedText.endsWith("}")) {
        return generatedText;
      } else {
        log.error("No JSON data found in the output:{}", generatedText);
        return null;
      }
    } else {
      int lastIndexOf = generatedText.lastIndexOf("```");
      log.info("JSON fence index:{},{}", indexOf, lastIndexOf);
      if (lastIndexOf > indexOf + 7) {
        try {
          code = generatedText.substring(indexOf + 7, lastIndexOf);
        } catch (Exception e) {
          log.error("Error extracting JSON from text:{}", generatedText, e);
          return null;
        }
      } else {
        try {
          code = generatedText.substring(indexOf + 7);
        } catch (Exception e) {
          log.error("Error extracting JSON from text:{}", generatedText, e);
          return null;
        }
      }
      return code.trim();
    }
  }

  
  public static String parsePythonCode(String generatedText) {
    String code;
    int indexOf = generatedText.indexOf("```python");
    if (indexOf == -1) {
      generatedText = generatedText.trim();
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
      if (lastIndexOf > indexOf + 9) {
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

  /**
   * Extracts XML content from the generated text. If the text contains ```xml
   * fences, returns the content between them. Otherwise, if the text starts with
   * '<' and ends with '>', returns the trimmed text.
   * 
   * @param generatedText the raw text containing XML
   * @return the extracted XML or null if none found
   */

  public static String parseXml(String generatedText) {
    generatedText = generatedText.replaceFirst(EMPTY_LINE_REGEX, "");
    Matcher m = XML_FENCE.matcher(generatedText);
    if (m.find()) {
      return m.group(1).trim();
    }
    // fallback to raw XML
    String txt = generatedText.trim();
    if (txt.startsWith("<") && txt.endsWith(">")) {
      return txt;
    }
    log.error("No XML data found in the output:{}", generatedText);
    return null;
  }

  /**
   * Extracts Java code from the generated text. If the text contains ```java
   * fences, returns the content between them. Otherwise, returns trimmed text.
   * 
   * @param generatedText the raw text containing Java code
   * @return the extracted Java code or null if none found
   */
  public static String parseJavaCode(String generatedText) {
    String code;
    int indexOf = generatedText.indexOf("```java");
    if (indexOf == -1) {
      generatedText = generatedText.trim();
      if (!generatedText.isEmpty()) {
        return generatedText;
      } else {
        log.error("No Java code found in the output:{}", generatedText);
        return null;
      }
    } else {
      int lastIndexOf = generatedText.lastIndexOf("```");
      log.info("Java fence index:{},{}", indexOf, lastIndexOf);
      if (lastIndexOf > indexOf + 7) {
        try {
          code = generatedText.substring(indexOf + 7, lastIndexOf);
        } catch (Exception e) {
          log.error("Error extracting Java from text:{}", generatedText, e);
          return null;
        }
      } else {
        try {
          code = generatedText.substring(indexOf + 7);
        } catch (Exception e) {
          log.error("Error extracting Java from text:{}", generatedText, e);
          return null;
        }
      }
      return code;
    }
  }

  /**
   * Extracts HTML content from the generated text. If the text contains ```html
   * fences, returns the content between them. Otherwise, if it starts with
   * '<html', returns trimmed text.
   * 
   * @param generatedText the raw text containing HTML
   * @return the extracted HTML or null if none found
   */
  public static String parseHtml(String generatedText) {
    String code;
    int indexOf = generatedText.indexOf("```html");
    if (indexOf == -1) {
      generatedText = generatedText.trim();
      String lowerCase = generatedText.toLowerCase();
      if (lowerCase.startsWith("<html")) {
        return generatedText;
      } else if (lowerCase.startsWith("<!doctype html")) {
        return generatedText;
      } else {
        log.error("No HTML data found in the output:{}", generatedText);
        return null;
      }
    } else {
      int lastIndexOf = generatedText.lastIndexOf("```");
      log.info("HTML fence index:{},{}", indexOf, lastIndexOf);
      if (lastIndexOf > indexOf + 8) {
        try {
          code = generatedText.substring(indexOf + 8, lastIndexOf);
        } catch (Exception e) {
          log.error("Error extracting HTML from text:{}", generatedText, e);
          return null;
        }
      } else {
        try {
          code = generatedText.substring(indexOf + 8);
        } catch (Exception e) {
          log.error("Error extracting HTML from text:{}", generatedText, e);
          return null;
        }
      }
      return code;
    }
  }

  /**
   * 提取文本中所有 ``` 开头、``` 结尾的代码块
   * 
   * @param text 待解析的原始文本
   * @return Code 对象列表，每个对象包含 type（标签）和 code（内容）
   */
  public static List<CodeBlock> extractCodeBlocks(String text) {
    List<CodeBlock> result = new ArrayList<>();
    // 正则解释：
    // ``` 匹配三个反引号
    // (\\S*) 捕获紧跟的标签（非空白字符），可以为空
    // [ \\t]* 标签后可选的空格或 tab
    // \\r?\\n 跳过到下一行
    // ([\\s\\S]*?) 非贪婪地捕获所有内容（包括换行），直到下一个 ```
    // ``` 匹配结束的三个反引号
    Pattern p = Pattern.compile("```(\\S*)[ \\t]*\\r?\\n([\\s\\S]*?)```");
    Matcher m = p.matcher(text);
    while (m.find()) {
      String type = m.group(1);
      String code = m.group(2);
      result.add(new CodeBlock(type, code));
    }
    return result;
  }

}
