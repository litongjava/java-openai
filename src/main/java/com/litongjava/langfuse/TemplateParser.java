package com.litongjava.langfuse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TemplateParser {

  private static final String OPENING = "{{";
  private static final String CLOSING = "}}";

  private static class ParseResult {
    final String variableName;
    final int startPos;
    final int endPos;

    ParseResult(String variableName, int startPos, int endPos) {
      this.variableName = variableName;
      this.startPos = startPos;
      this.endPos = endPos;
    }
  }

  private static ParseResult parseNextVariable(String content, int startIdx) {
    int varStart = content.indexOf(OPENING, startIdx);
    if (varStart == -1) {
      return null;
    }
    int varEnd = content.indexOf(CLOSING, varStart);
    if (varEnd == -1) {
      return null;
    }

    String variableName = content.substring(varStart + OPENING.length(), varEnd).trim();

    return new ParseResult(variableName, varStart, varEnd + CLOSING.length());
  }

  public static List<String> findVariableNames(String content) {
    List<String> names = new ArrayList<>();
    int currIdx = 0;
    while (currIdx < content.length()) {
      ParseResult result = parseNextVariable(content, currIdx);
      if (result == null) {
        break;
      }
      names.add(result.variableName);
      currIdx = result.endPos;
    }
    return names;
  }

  public static String compileTemplate(String content, Map<String, ?> data) {
    if (data == null) {
      return content;
    }
    StringBuilder result = new StringBuilder();
    int currIdx = 0;

    while (currIdx < content.length()) {
      ParseResult parsed = parseNextVariable(content, currIdx);
      if (parsed == null) {
        result.append(content.substring(currIdx));
        break;
      }

      String variableName = parsed.variableName;
      int varStart = parsed.startPos;
      int varEnd = parsed.endPos;

      result.append(content, currIdx, varStart);

      if (data.containsKey(variableName)) {
        Object value = data.get(variableName);
        if (value != null) {
          result.append(value.toString());
        }
      } else {
        // 保留原样
        result.append(content, varStart, varEnd);
      }

      currIdx = varEnd;
    }

    return result.toString();
  }
}
