package com.litongjava.fishaudio.tts;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * HardCodedMsgPackConverter 将 FishAudioTTSRequestVo 对象手动转换为 msgpack 格式字节数组，
 * 采用 Map 结构（与 ormsgpack.OPT_SERIALIZE_PYDANTIC 生成的结构一致）。
 */
public class FishAudioMsgPackConverter {

  /**
   * 将 FishAudioTTSRequestVo 对象编码为 msgpack 格式的字节数组（Map 格式）。
   *
   * @param vo 请求对象
   * @return msgpack 编码后的字节数组
   */
  public static byte[] encodeFishAudioTTSRequestVo(FishAudioTTSRequestVo vo) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(baos);

      // 我们将 vo 编码为一个 Map，共包含 8 个键值对
      writeMapHeader(out, 8);

      // 键 "text"
      writeString(out, "text");
      writeString(out, vo.getText());

      // 键 "chunk_length"
      writeString(out, "chunk_length");
      writeInt(out, vo.getChunk_length());

      // 键 "format"
      writeString(out, "format");
      writeString(out, vo.getFormat());

      // 键 "mp3_bitrate"
      writeString(out, "mp3_bitrate");
      writeInt(out, vo.getMp3_bitrate());

      // 键 "references"
      writeString(out, "references");
      List<FishAudioReferenceAudio> refs = vo.getReferences();
      if (refs == null) {
        writeArrayHeader(out, 0);
      } else {
        writeArrayHeader(out, refs.size());
        for (FishAudioReferenceAudio ref : refs) {
          encodeFishAudioReferenceAudio(out, ref);
        }
      }

      // 键 "reference_id"
      writeString(out, "reference_id");
      if (vo.getReference_id() == null) {
        writeNil(out);
      } else {
        writeString(out, vo.getReference_id());
      }

      // 键 "normalize"
      writeString(out, "normalize");
      writeBoolean(out, vo.getNormalize());

      // 键 "latency"
      writeString(out, "latency");
      writeString(out, vo.getLatency());

      out.flush();
      return baos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException("Error encoding FishAudioTTSRequestVo", e);
    }
  }

  /**
   * 将 FishAudioReferenceAudio 对象编码为 msgpack 格式数据（Map 格式），包含 2 个键值对：audio 和 text。
   */
  private static void encodeFishAudioReferenceAudio(DataOutputStream out, FishAudioReferenceAudio ref) throws IOException {
    // 编码为 Map，包含 2 个字段
    writeMapHeader(out, 2);
    // 键 "audio"
    writeString(out, "audio");
    byte[] audio = ref.getAudio();
    if (audio == null) {
      writeNil(out);
    } else {
      writeByteArray(out, audio);
    }
    // 键 "text"
    writeString(out, "text");
    writeString(out, ref.getText());
  }

  //////////////// 以下为 msgpack 编码辅助方法 ////////////////

  // 写入 Map 头（如果 size < 16 使用 fixmap）
  private static void writeMapHeader(DataOutputStream out, int size) throws IOException {
    if (size < 16) {
      out.writeByte(0x80 | size); // fixmap
    } else if (size < 65536) {
      out.writeByte(0xde);
      out.writeShort(size);
    } else {
      out.writeByte(0xdf);
      out.writeInt(size);
    }
  }

  // 写入 Array 头（如果 size < 16 使用 fixarray）
  private static void writeArrayHeader(DataOutputStream out, int size) throws IOException {
    if (size < 16) {
      out.writeByte(0x90 | size); // fixarray
    } else if (size < 65536) {
      out.writeByte(0xdc);
      out.writeShort(size);
    } else {
      out.writeByte(0xdd);
      out.writeInt(size);
    }
  }

  // 写入 nil 标记
  private static void writeNil(DataOutputStream out) throws IOException {
    out.writeByte(0xc0);
  }

  // 写入 Boolean 值
  private static void writeBoolean(DataOutputStream out, boolean value) throws IOException {
    out.writeByte(value ? 0xc3 : 0xc2);
  }

  // 写入整型数值（这里只处理正数，适用于本例中的 chunk_length 和 mp3_bitrate）
  private static void writeInt(DataOutputStream out, int value) throws IOException {
    if (value >= 0 && value < 128) {
      out.writeByte(value); // positive fixnum
    } else if (value < 256) {
      out.writeByte(0xcc);
      out.writeByte(value);
    } else if (value < 65536) {
      out.writeByte(0xcd);
      out.writeShort(value);
    } else {
      out.writeByte(0xce);
      out.writeInt(value);
    }
  }

  // 写入字符串
  private static void writeString(DataOutputStream out, String str) throws IOException {
    if (str == null) {
      writeNil(out);
      return;
    }
    byte[] utf8 = str.getBytes(StandardCharsets.UTF_8);
    int length = utf8.length;
    if (length < 32) {
      out.writeByte(0xa0 | length); // fixstr
    } else if (length < 256) {
      out.writeByte(0xd9);
      out.writeByte(length);
    } else if (length < 65536) {
      out.writeByte(0xda);
      out.writeShort(length);
    } else {
      out.writeByte(0xdb);
      out.writeInt(length);
    }
    out.write(utf8);
  }

  // 写入 byte 数组（二进制数据）
  private static void writeByteArray(DataOutputStream out, byte[] data) throws IOException {
    int length = data.length;
    if (length < 256) {
      out.writeByte(0xc4);
      out.writeByte(length);
    } else if (length < 65536) {
      out.writeByte(0xc5);
      out.writeShort(length);
    } else {
      out.writeByte(0xc6);
      out.writeInt(length);
    }
    out.write(data);
  }
}
