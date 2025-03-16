package com.litongjava.utils;

import org.junit.Test;

public class YouTubeIdUtilTest {

  @Test
  public void test() {
    String[] urls = { "https://www.youtube.com/watch?v=xxxx", "https://www.youtube.com/embed/xxxx", "https://youtu.be/xxxx" };

    for (String url : urls) {
      System.out.println("URL: " + url);
      System.out.println("视频ID: " + YouTubeIdUtil.extractVideoId(url));
      System.out.println("---------");
    }
  }

}
