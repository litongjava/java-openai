package com.litongjava.open.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.litongjava.openai.chat.ChatMessage;
import com.litongjava.openai.chat.ChatRequestFunctionParameter;
import com.litongjava.openai.chat.ChatRequestFunctionProperty;
import com.litongjava.openai.chat.ChatRequestTool;
import com.litongjava.openai.chat.ChatRequestToolCallFunction;
import com.litongjava.openai.chat.ChatRequestVo;
import com.litongjava.tio.utils.json.JsonUtils;

public class ChatRequestVoTest {

  @Test
  public void test() {
    ChatMessage chatRequestMessage = new ChatMessage();
    chatRequestMessage.role("user");
    List<ChatMessage> messages = new ArrayList<>();

    ChatMessage message = new ChatMessage().role("user").content("How are you");

    messages.add(message);

    ChatRequestVo chatRequestVo = new ChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel("gpt-4o-2024-05-13");

    chatRequestVo.setMessages(messages);

    System.out.println(JsonUtils.toJson(chatRequestVo));
  }

  @Test
  public void testFunctionTools() {

    ChatMessage systemMessage = new ChatMessage().role("system")
        .content("You are an excellent student advisor. You can query the database");
    ChatMessage userMessage = new ChatMessage().role("system").content("I have a user table,how to count this table");

    List<ChatMessage> messages = new ArrayList<>();
    messages.add(systemMessage);
    messages.add(userMessage);

    ChatRequestFunctionParameter parameter = new ChatRequestFunctionParameter();
    parameter.setType("object");
    Map<String, ChatRequestFunctionProperty> properties = new HashMap<>();
    properties.put("sql", new ChatRequestFunctionProperty("string", "The SQL statement that needs to be executed"));
    parameter.setProperties(properties);

    List<String> required = new ArrayList<>();
    required.add("sql");
    parameter.setRequired(required);

    ChatRequestToolCallFunction function = new ChatRequestToolCallFunction();
    function.setName("find");
    function.setDescription("Execute SQL query on the database");
    function.setParameters(parameter);

    ChatRequestTool tool = new ChatRequestTool();
    tool.setType("function");

    tool.setFunction(function);

    List<ChatRequestTool> tools = new ArrayList<>();

    tools.add(tool);

    ChatRequestVo chatRequestVo = new ChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel("gpt-4o-2024-05-13");
    chatRequestVo.setMessages(messages);
    chatRequestVo.setTools(tools);

    String json = JsonUtils.toJson(chatRequestVo);
    System.out.println(json);
  }

}
