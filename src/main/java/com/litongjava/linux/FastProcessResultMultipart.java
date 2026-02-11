package com.litongjava.linux;

import com.litongjava.http.client.multipart.ByteFieldMapper;
import com.litongjava.http.client.multipart.FastMultipartParser;
import com.litongjava.tio.utils.commandline.ProcessResult;

public class FastProcessResultMultipart {

  public static ProcessResult parse(String contentType, byte[] bodyBytes) {
    ProcessResult r = new ProcessResult();

    ByteFieldMapper<ProcessResult> m = defaultMapper();
    FastMultipartParser.parse(contentType, bodyBytes, (name, buf, off, len) -> {
      m.apply(r, name, buf, off, len);
    });

    return r;
  }

  public static ByteFieldMapper<ProcessResult> defaultMapper() {
    ByteFieldMapper<ProcessResult> m = new ByteFieldMapper<>(true);

    m.bindBytes("exit_code", (r, b, o, l) -> r.setExitCode(ByteFieldMapper.parseIntAscii0(b, o, l)));
    m.bindUtf8("std_out", ProcessResult::setStdOut);
    m.bindUtf8("std_err", ProcessResult::setStdErr);

    m.bindBytes("elapsed", (r, b, o, l) -> r.setElapsed(ByteFieldMapper.parseLongAsciiObj(b, o, l)));
    m.bindBytes("cached", (r, b, o, l) -> r.setCached(ByteFieldMapper.parseBoolAsciiObj(b, o, l)));
    m.bindBytes("prt", (r, b, o, l) -> r.setPrt(ByteFieldMapper.parseLongAsciiObj(b, o, l)));
    m.bindBytes("session_id", (r, b, o, l) -> r.setSessionId(ByteFieldMapper.parseLongAsciiObj(b, o, l)));
    m.bindBytes("task_id", (r, b, o, l) -> r.setTaskId(ByteFieldMapper.parseLongAsciiObj(b, o, l)));

    // 这些通常是文本，直接 UTF-8 解码
    m.bindUtf8("sources", ProcessResult::setSources);
    m.bindUtf8("execute_code", ProcessResult::setExecuteCode);
    m.bindUtf8("output", ProcessResult::setOutput);
    m.bindUtf8("json", ProcessResult::setJson);
    m.bindUtf8("message", ProcessResult::setMessage);
    m.bindUtf8("text", ProcessResult::setText);
    m.bindUtf8("subtitle", ProcessResult::setSubtitle);
    m.bindUtf8("image", ProcessResult::setImage);
    m.bindUtf8("audio", ProcessResult::setAudio);
    m.bindUtf8("video", ProcessResult::setVideo);
    m.bindUtf8("hls_url", ProcessResult::setHlsUrl);
    m.bindUtf8("ppt", ProcessResult::setPpt);

    // video_length 如果确实要 Double，可保持 utf8->Double；也能做 bytes 解析
    m.bindUtf8("video_length", (r, v) -> {
      try {
        r.setVideo_length(Double.valueOf(v.trim()));
      } catch (Exception e) {
        r.setVideo_length(null);
      }
    });

    return m;
  }
}
