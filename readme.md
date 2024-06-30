# java-openai
## Introduction
An OpenAi client for Java language band base on okhttp and fastjson

## Chat Example
1. add dependecy to pom.xml
```xml
<dependency>
  <groupId>com.litongjava</groupId>
  <artifactId>java-openai</artifactId>
  <version>1.0.0</version>
</dependency>
<dependency>
  <groupId>com.alibaba</groupId>
  <artifactId>fastjson</artifactId>
  <version>2.0.39</version>
</dependency
```
2. add OPENAI_API_KEY to src/main/resources/app.properties
```
OPENAI_API_KEY=apikey from openai
```

3. do a simple test
```java
package com.litongjava.openai.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.litongjava.openai.chat.ChatMessage;
import com.litongjava.openai.chat.ChatRequestVo;
import com.litongjava.openai.chat.ChatResponseVo;
import com.litongjava.openai.chat.OpenAiClient;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Response;

public class SimpleAskExample {
  public static void main(String[] args) {
    // load OPENAI_API_KEY from the config()
    EnvUtils.load();

    // messages
    List<ChatMessage> messages = new ArrayList<>();
    ChatMessage message = new ChatMessage().role("user").content("How are you");
    messages.add(message);

    // reqvo
    ChatRequestVo chatRequestVo = new ChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel("gpt-4o-2024-05-13");
    chatRequestVo.setMessages(messages);

    String json = JsonUtils.toJson(chatRequestVo);
    System.out.println(json);

    // send http request
    ChatResponseVo chatCompletions = null;
    try (Response response = OpenAiClient.chatCompletions(json)) {
      if (response.isSuccessful()) {
        // process response body
        String string = response.body().string();
        chatCompletions = JsonUtils.parse(string, ChatResponseVo.class);
        System.out.println(JsonUtils.toJson(chatCompletions));
      } else {
        System.out.println(response);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
```
## exmaples
### Ask With Tools
```
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.litongjava.openai.chat.ChatMessage;
import com.litongjava.openai.chat.ChatRequestFunctionParameter;
import com.litongjava.openai.chat.ChatRequestFunctionProperty;
import com.litongjava.openai.chat.ChatRequestTool;
import com.litongjava.openai.chat.ChatRequestToolCallFunction;
import com.litongjava.openai.chat.ChatRequestVo;
import com.litongjava.openai.chat.ChatResponseVo;
import com.litongjava.openai.chat.OpenAiClient;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Response;

public class AskWithTools {

  public static void main(String[] args) {
    EnvUtils.load();
    List<ChatMessage> messages = new ArrayList<>();

    messages.add(new ChatMessage().role("system")
        .content("You are an excellent student advisor. You can query the database.The database is postgresql"));
    messages.add(new ChatMessage().role("system").content("how many tables are in my database"));

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

    // send http request
    ChatResponseVo chatCompletions = null;
    try (Response response = OpenAiClient.chatCompletions(json)) {
      if (response.isSuccessful()) {
        // process response body
        String string = response.body().string();
        System.out.println(string);
        chatCompletions = JsonUtils.parse(string, ChatResponseVo.class);
        System.out.println(JsonUtils.toJson(chatCompletions));
      } else {
        System.out.println(response);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
```

### Ask with image
```java
package com.litongjava.openai.example;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.litongjava.openai.chat.ChatMessage;
import com.litongjava.openai.chat.ChatRequestImage;
import com.litongjava.openai.chat.ChatRequestMultiContent;
import com.litongjava.openai.chat.ChatRequestVo;
import com.litongjava.openai.chat.ChatResponseVo;
import com.litongjava.openai.chat.OpenAiClient;
import com.litongjava.tio.utils.encoder.Base64Utils;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.hutool.FileUtil;
import com.litongjava.tio.utils.hutool.ResourceUtil;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Response;

public class AskWithImages {

  public static void main(String[] args) {
    // load config
    EnvUtils.load();

    // read file as base64
    URL resource = ResourceUtil.getResource("static/images/simple_question.png");
    byte[] bytes = FileUtil.readUrlAsBytes(resource);
    String imageBase64 = Base64Utils.encodeToString(bytes);

    // add to multi content
    ChatRequestImage imageUrl = new ChatRequestImage();
    imageUrl.setUrl("data:image/png;base64," + imageBase64);
    imageUrl.setDetail("auto");
    ChatRequestMultiContent chatRequestMultiContent = new ChatRequestMultiContent();
    chatRequestMultiContent.setType("image_url");
    chatRequestMultiContent.setImage_url(imageUrl);

    List<ChatRequestMultiContent> multiContents = new ArrayList<>();
    multiContents.add(chatRequestMultiContent);

    // and image and text
    List<ChatMessage> messages = new ArrayList<>();
    messages.add(new ChatMessage().role("user").multiContents(multiContents));
    messages.add(new ChatMessage().role("user").content("can you help me resolve this problem"));

    // crate chat vos
    ChatRequestVo chatRequestVo = new ChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel("gpt-4o-2024-05-13");
    chatRequestVo.setMessages(messages);

    String json = JsonUtils.toJson(chatRequestVo);
    System.out.println(json);

    // send http request
    ChatResponseVo chatCompletions = null;
    try (Response response = OpenAiClient.chatCompletions(json)) {
      if (response.isSuccessful()) {
        // process response body
        String string = response.body().string();
        System.out.println(string);
        chatCompletions = JsonUtils.parse(string, ChatResponseVo.class);
        System.out.println(JsonUtils.toJson(chatCompletions));
      } else {
        System.out.println(response);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
```

### Embedding
```java
package com.litongjava.openai.example;

import java.io.IOException;

import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.openai.embedding.EmbeddingRequestVo;
import com.litongjava.openai.embedding.EmbeddingResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Response;

public class EmbeddingExample {

  public static void main(String[] args) {
    // load config
    EnvUtils.load();
    // request model
    EmbeddingRequestVo embeddingRequestVo = new EmbeddingRequestVo();
    embeddingRequestVo.input("What's your name").model("text-embedding-3-small");

    String bodyString = JsonUtils.toJson(embeddingRequestVo);
    // send request
    try (Response response = OpenAiClient.embeddings(bodyString)) {
      if (response.isSuccessful()) {
        // get response string
        String string = response.body().string();
        System.out.println(string);
        // process the resposne data
        EmbeddingResponseVo responseVo = JsonUtils.parse(string, EmbeddingResponseVo.class);
        System.out.println(JsonUtils.toJson(responseVo));
      } else {
        System.out.println(response);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```
