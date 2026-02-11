package com.litongjava.http.client.multipart;

@FunctionalInterface
public interface PartConsumer {
  void accept(String name, byte[] body, int off, int len);
}
