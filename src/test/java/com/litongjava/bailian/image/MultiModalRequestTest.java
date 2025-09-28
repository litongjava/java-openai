package com.litongjava.bailian.image;

import org.junit.Test;

import com.litongjava.tio.utils.json.JsonUtils;

public class MultiModalRequestTest {

  @Test
  public void test() {
    String text = "一副典雅庄重的对联悬挂于厅堂之中，房间是个安静古典的中式布置，桌子上放着一些青花瓷，对联上左书“义本生知人机同道善思新”，右书“通云赋智乾坤启数高志远”， 横批“智启通义”，字体飘逸，中间挂在一着一副中国风的画作，内容是岳阳楼。";
    MultiModalRequest request = MultiModalRequest.build(text);
    System.out.println(JsonUtils.toJson(request));
  }

}
