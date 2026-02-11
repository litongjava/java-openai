package com.litongjava.http.client.multipart;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;

public class ByteFieldMapper<T> {

  @FunctionalInterface
  public interface ByteSetter<T> {
    void set(T target, byte[] buf, int off, int len);
  }

  private final Map<String, ByteSetter<T>> map = new HashMap<>();
  private final boolean caseInsensitive;

  public ByteFieldMapper(boolean caseInsensitive) {
    this.caseInsensitive = caseInsensitive;
  }

  public ByteFieldMapper<T> bindUtf8(String name, BiConsumer<T, String> setter) {
    map.put(key(name), (t, b, o, l) -> setter.accept(t, new String(b, o, l, StandardCharsets.UTF_8)));
    return this;
  }

  public ByteFieldMapper<T> bindBytes(String name, ByteSetter<T> setter) {
    map.put(key(name), setter);
    return this;
  }

  public void apply(T target, String name, byte[] buf, int off, int len) {
    ByteSetter<T> s = map.get(key(name));
    if (s != null)
      s.set(target, buf, off, len);
  }

  private String key(String s) {
    if (!caseInsensitive)
      return s;
    return s == null ? null : s.toLowerCase(Locale.ROOT);
  }

  // 直接从 bytes 解析 int/long/bool（避免 String）
  public static int parseIntAscii0(byte[] b, int off, int len) {
    int i = off, end = off + len;
    while (i < end && b[i] <= ' ')
      i++;
    int sign = 1;
    if (i < end && b[i] == '-') {
      sign = -1;
      i++;
    }
    int v = 0;
    while (i < end) {
      byte c = b[i];
      if (c < '0' || c > '9')
        break;
      v = v * 10 + (c - '0');
      i++;
    }
    return v * sign;
  }

  public static Long parseLongAsciiObj(byte[] b, int off, int len) {
    int i = off, end = off + len;
    while (i < end && b[i] <= ' ')
      i++;
    if (i >= end)
      return null;
    int sign = 1;
    if (b[i] == '-') {
      sign = -1;
      i++;
    }
    long v = 0;
    boolean any = false;
    while (i < end) {
      byte c = b[i];
      if (c < '0' || c > '9')
        break;
      any = true;
      v = v * 10 + (c - '0');
      i++;
    }
    return any ? Long.valueOf(v * sign) : null;
  }

  public static Boolean parseBoolAsciiObj(byte[] b, int off, int len) {
    // trim
    int i = off, end = off + len;
    while (i < end && b[i] <= ' ')
      i++;
    while (end > i && b[end - 1] <= ' ')
      end--;
    int n = end - i;
    if (n <= 0)
      return null;

    // '1'/'0'
    if (n == 1) {
      if (b[i] == '1')
        return Boolean.TRUE;
      if (b[i] == '0')
        return Boolean.FALSE;
    }

    // "true"/"false" (case-insensitive ASCII)
    if (n == 4
        && (toLower(b[i]) == 't' && toLower(b[i + 1]) == 'r' && toLower(b[i + 2]) == 'u' && toLower(b[i + 3]) == 'e')) {
      return Boolean.TRUE;
    }
    if (n == 5 && (toLower(b[i]) == 'f' && toLower(b[i + 1]) == 'a' && toLower(b[i + 2]) == 'l'
        && toLower(b[i + 3]) == 's' && toLower(b[i + 4]) == 'e')) {
      return Boolean.FALSE;
    }
    return null;
  }

  private static byte toLower(byte c) {
    return (c >= 'A' && c <= 'Z') ? (byte) (c + 32) : c;
  }
}
