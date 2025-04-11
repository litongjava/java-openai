package com.litongjava.fishaudio.tts;

import java.io.IOException;

import org.msgpack.MessagePack;

/**
 * MsgPackUtils 提供将 Java 对象转换为 MsgPack 格式字节数组的方法。
 * 通过设置系统属性 "msgpack.builder" 为 "reflect" 来使用反射构造器，避免 Javassist 的模块访问问题。
 */
public class MsgPackUtils {

  static {
    // 使用反射模式构建模板，避免 Java 模块系统限制导致的 InaccessibleObjectException
    System.setProperty("msgpack.builder", "reflect");
  }

  // 创建一个 MessagePack 实例（线程安全可重复使用）
  private static final MessagePack messagePack = new MessagePack();

  /**
   * 将对象转换为 MsgPack 序列化后的字节数组。
   *
   * @param object 待序列化的对象，要求支持 msgpack 序列化规则（建议在目标类上添加 @Message 注解）
   * @return 序列化后的字节数组
   * @throws RuntimeException 如果序列化过程中出现异常
   */
  public static byte[] toMsgPackBytes(Object object) {
    try {
      return messagePack.write(object);
    } catch (IOException e) {
      throw new RuntimeException("MsgPack 序列化失败", e);
    }
  }
}
