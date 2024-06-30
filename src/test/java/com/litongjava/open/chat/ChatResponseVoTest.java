package com.litongjava.open.chat;

import org.junit.Test;

import com.litongjava.openai.chat.ChatResponseVo;
import com.litongjava.tio.utils.json.JsonUtils;

public class ChatResponseVoTest {

  @Test
  public void testParse() {
    String content = "{\"id\":\"chatcmpl-9P3fvvyk4IuCprCnvMytoKN8UtskC\",\"object\":\"chat.completion.chunk\",\"created\":1715759355,\"model\":\"gpt-3.5-turbo-0125\",\"system_fingerprint\":null,\"choices\":[{\"index\":0,\"delta\":{\"content\":\" How\"},\"logprobs\":null,\"finish_reason\":null}]}";
    ChatResponseVo chatResponseVo = JsonUtils.parse(content, ChatResponseVo.class);
    String json = JsonUtils.toJson(chatResponseVo);
    System.out.println(json);
  }

  @Test
  public void testParseForNotStreamResponse() {
    String content = "{\r\n" + "  \"id\": \"chatcmpl-9fKfVpk21idHORNRg5nYdwfPVqAWQ\",\r\n"
        + "  \"object\": \"chat.completion\",\r\n" + "  \"created\": 1719637925,\r\n"
        + "  \"model\": \"gpt-4o-2024-05-13\",\r\n" + "  \"choices\": [\r\n" + "    {\r\n" + "      \"index\": 0,\r\n"
        + "      \"message\": {\r\n" + "        \"role\": \"assistant\",\r\n" + "        \"content\": null,\r\n"
        + "        \"tool_calls\": [\r\n" + "          {\r\n"
        + "            \"id\": \"call_ehQixd8yTn8O1cHgw0AOZ4wp\",\r\n" + "            \"type\": \"function\",\r\n"
        + "            \"function\": {\r\n" + "              \"name\": \"find\",\r\n"
        + "              \"arguments\": \"{\\\"sql\\\": \\\"SELECT * FROM rumi_sjsu_shops WHERE name = 'Whole Foods Market' LIMIT 10 OFFSET 0;\\\"}\"\r\n"
        + "            }\r\n" + "          },\r\n" + "          {\r\n"
        + "            \"id\": \"call_bIcMVcifF7392hEmSK80RJIq\",\r\n" + "            \"type\": \"function\",\r\n"
        + "            \"function\": {\r\n" + "              \"name\": \"find\",\r\n"
        + "              \"arguments\": \"{\\\"sql\\\": \\\"SELECT * FROM rumi_sjsu_professors WHERE name = 'Tong Li' LIMIT 10 OFFSET 0;\\\"}\"\r\n"
        + "            }\r\n" + "          }\r\n" + "        ]\r\n" + "      },\r\n" + "      \"logprobs\": null,\r\n"
        + "      \"finish_reason\": \"tool_calls\"\r\n" + "    }\r\n" + "  ],\r\n" + "  \"usage\": {\r\n"
        + "    \"prompt_tokens\": 1356,\r\n" + "    \"completion_tokens\": 90,\r\n" + "    \"total_tokens\": 1446\r\n"
        + "  },\r\n" + "  \"system_fingerprint\": \"fp_4008e3b719\"\r\n" + "}";
    ChatResponseVo chatResponseVo = JsonUtils.parse(content, ChatResponseVo.class);

    System.out.println(JsonUtils.toJson(chatResponseVo));

  }

}
