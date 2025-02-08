# Java OpenAI 客户端

## 简介

**Java OpenAI** 是一个强大的Ai客户端库，用于将 OpenAI 服务集成到 Java 应用程序中。该库构建于 [OkHttp](https://square.github.io/okhttp/) 和 [FastJSON](https://github.com/alibaba/fastjson) 之上，为与 OpenAI 的 API 交互提供了一种无缝高效的方式——支持聊天补全、图像处理、嵌入等功能。并通知调用 gemini,jina,textin,deepseek等服务端.

## 目录

- [Java OpenAI 客户端](#java-openai-客户端)
  - [简介](#简介)
  - [目录](#目录)
  - [功能](#功能)
  - [快速开始](#快速开始)
    - [1. 添加依赖](#1-添加依赖)
    - [2. 配置 API 密钥](#2-配置-api-密钥)
    - [3. 使用 PromptEngine 运行](#3-使用-promptengine-运行)
    - [4. 运行一个简单测试](#4-运行一个简单测试)
    - [5. 使用角色的另一简化示例](#5-使用角色的另一简化示例)
  - [示例](#示例)
    - [聊天示例](#聊天示例)
    - [带图像的提问](#带图像的提问)
    - [带图像的聊天](#带图像的聊天)
    - [使用工具提问](#使用工具提问)
    - [嵌入](#嵌入)
      - [示例 1：生成嵌入](#示例-1生成嵌入)
      - [示例 2：简单嵌入](#示例-2简单嵌入)
    - [Llama 集成](#llama-集成)
    - [Perplexity 集成](#perplexity-集成)
    - [Jina Rerank](#jina-rerank)
    - [Jina 搜索](#jina-搜索)
    - [解析 Markdown 响应](#解析-markdown-响应)
    - [GOOGLE GEMINI](#google-gemini)
      - [Google GEMINI 图像](#google-gemini-图像)
      - [GOOGLE GEMINI 函数调用](#google-gemini-函数调用)
      - [gemini 上传文件](#gemini-上传文件)
      - [gemini 使用 PDF 提问](#gemini-使用-pdf-提问)
      - [gemini openai](#gemini-openai)
    - [deepseek-openai](#deepseek-openai)
    - [SiliconFlow DeepSeek](#siliconflow-deepseek)
    - [SiliconFlow DeepSeek 图像](#siliconflow-deepseek-图像)
  - [许可证](#许可证)

## 功能

- **聊天补全**：轻松与 OpenAI 的聊天模型进行交互。
- **图像处理**：将图像转换为文本或其他格式。
- **嵌入**：生成文本嵌入，可用于各种应用场景。
- **工具集成**：通过集成外部工具增强功能。
- **支持 Llama 和 Perplexity 模型**：扩展能力，不仅限于 OpenAI，还支持其他模型。
- **简单配置**：通过配置文件实现简单设置。

## 快速开始

### 1. 添加依赖

在 `pom.xml` 中添加如下依赖：

```xml
<dependencies>
  <dependency>
    <groupId>com.litongjava</groupId>
    <artifactId>java-openai</artifactId>
    <version>1.1.4</version>
  </dependency>
  <dependency>
    <groupId>com.alibaba.fastjson2</groupId>
    <artifactId>fastjson2</artifactId>
    <version>2.0.52</version>
  </dependency>
</dependencies>
```

### 2. 配置 API 密钥

创建或更新 `src/main/resources/app.properties` 文件，添加以下内容：

```properties
OPENAI_API_KEY=your_openai_api_key_here
```

### 3. 使用 PromptEngine 运行

```java
package com.litongjava.prompt;

import java.util.ArrayList;
import java.util.List;

import com.litongjava.openai.chat.ChatResponseFormatType;
import com.litongjava.openai.chat.OpenAiChatMessage;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.chat.OpenAiChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.openai.constants.OpenAiModels;
import com.litongjava.template.PromptEngine;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class ProfessorEmojisTest {
  public static void main(String[] args) {
    // 从配置中加载 OPENAI_API_KEY
    EnvUtils.load();

    // 创建消息列表
    List<OpenAiChatMessage> messages = new ArrayList<>();
    String prompt = PromptEngine.renderToString("professor_emojis_introduction_weaknesses_strengths_prompt.txt");
    String exmaple = PromptEngine.renderToString("professor_emojis_introduction_weaknesses_strengths_prompt_example.txt");
    messages.add(new OpenAiChatMessage("system", prompt));
    messages.add(new OpenAiChatMessage("user", exmaple));

    // 创建聊天请求
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel(OpenAiModels.GPT_4O_MINI);
    chatRequestVo.setMessages(messages);

    chatRequestVo
        .setTemperature(0f)
        .setTop_p(1.0f)
        .setFrequency_penalty(0.0f)
        .setPresence_penalty(0.0f)
        .setResponse_format(ChatResponseFormatType.json_object);

    String json = JsonUtils.toSkipNullJson(chatRequestVo);
    System.out.println("请求 JSON:\n" + json);

    // 发送 HTTP 请求
    OpenAiChatResponseVo chatCompletions = OpenAiClient.chatCompletions(chatRequestVo);
    System.out.println(JsonUtils.toSkipNullJson(chatCompletions));
  }
}
```

**professor_emojis_introduction_weaknesses_strengths_prompt.txt**

```txt
<instruction>
  你需要根据 RateMyProfessor 提供的数据生成一份全面的教授分析报告。请按照以下步骤完成任务：

  1. 使用提供的变量填充 JSON 格式中所需的字段。
  2. 基于学生评价，分析教授的教学风格、课堂互动和个人魅力。
  3. 确保报告整体语气既幽默诙谐又客观。
  4. 使用能反映教授个性和特点的表情符号填充 'emojis' 字段。
  5. 撰写一个简洁且有力的介绍，总结教授的教学风格、学术背景或显著特点。
  6. 按照指定格式列出教授的弱点，确保每项条目独占一行，键和值之间用冒号分隔。
  7. 按照指定格式列出教授的优点，确保每项条目独占一行，键和值之间用冒号分隔。
  8. 确保输出严格为 JSON 格式，不包含任何 XML 标签。

  输出应如下所示：
  {
    "emojis": ["emoji1", "emoji_2", ...],
    "introduction": "professor_introduction",
    "weaknesses": "**Weakness_name_1**:weakness_description_1\n**Weakness_name_2**:weakness_description_2\n...",
    "strengths": "**Strength__name_1**:strength_description_1\n**Strength_name_2**:strength_description_2\n..."
  }
</instruction>
```

**prompts/professor_emojis_introduction_weaknesses_strengths_prompt_example.txt**

```json
[
  {
    "Quality": 5.0,
    "Difficulty": 1.0,
    "Course": "EGMT1510",
    "Date": "Jan 31st, 2025",
    "For Credit": "Yes",
    "Attendance": "Not Mandatory",
    "Would Take Again": "Yes",
    "Grade": "A+",
    "Textbook": "Yes",
    "Review": "I had Professor Levenson for the London First Program, and he was genuinely the kindest and most caring professor I have ever had. He is incredibly engaged with his students, and will make the effort to have a personal relationship with anyone willing to talk to him. He is an incredibly reasonable grader, and his course is genuinely enjoyable.",
    "Tags": ["Amazing lectures", "Caring", "Accessible outside class", "Helpful"],
    "Thumbs Up": 0,
    "Thumbs Down": 0
  },
  {
    "Quality": 5.0,
    "Difficulty": 1.0,
    "Course": "LONDON",
    "Date": "Jan 12th, 2025",
    "For Credit": "Yes",
    "Attendance": "Not Mandatory",
    "Would Take Again": "Yes",
    "Grade": "A",
    "Textbook": "N/A",
    "Review": "I had him for my first semester during London First and he was amazing.",
    "Tags": ["Get ready to read", "Amazing lectures", "Hilarious", "Helpful"],
    "Thumbs Up": 0,
    "Thumbs Down": 0
  },
  {
    "Quality": 5.0,
    "Difficulty": 2.0,
    "Course": "EGMT1510",
    "Date": "May 28th, 2024",
    "For Credit": "Yes",
    "Attendance": "Mandatory",
    "Would Take Again": "Yes",
    "Grade": "A",
    "Textbook": "N/A",
    "Review": "Professor Levenson is one of the sweetest instructors I have ever had. He was in charge of the London First Program and was the instructor for my engagements, which were (contrary to most Engagements) very fun, as we traveled around the city and watched plays and films as a class. I loved the discussions he led and he was always available to chat.",
    "Tags": ["Inspirational", "Respected", "Accessible outside class", "Helpful"],
    "Thumbs Up": 0,
    "Thumbs Down": 0
  },
  {
    "Quality": 5.0,
    "Difficulty": 2.0,
    "Course": "ENGL3610",
    "Date": "May 2nd, 2023",
    "For Credit": "Yes",
    "Attendance": "Not Mandatory",
    "Would Take Again": "Yes",
    "Grade": "A",
    "Textbook": "N/A",
    "Review": "Professor Levenson is a great lecturer, and I really enjoyed the course material! I feel like I've learned a lot.",
    "Tags": ["Get ready to read", "Amazing lectures", "Helpful"],
    "Thumbs Up": 0,
    "Thumbs Down": 0
  },
  {
    "Quality": 5.0,
    "Difficulty": 3.0,
    "Course": "EGMT1510",
    "Date": "Sep 14th, 2020",
    "For Credit": "Yes",
    "Attendance": "Mandatory",
    "Would Take Again": "Yes",
    "Grade": "A",
    "Textbook": "No",
    "Review": "I did Global First, so I took Professor Levenson's class in London. It was a great experience, and much of the class involved taking field trips and discussing plays we saw. He was very engaging, and discussions were always interesting.",
    "Tags": ["Participation matters", "Amazing lectures", "Caring", "Helpful"],
    "Thumbs Up": 0,
    "Thumbs Down": 0
  }
]
```

### 4. 运行一个简单测试

```java
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.litongjava.openai.chat.OpenAiChatMessage;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.chat.OpenAiChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.openai.constants.OpenAiModels;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Response;

public class SimpleAskExample {
  public static void main(String[] args) {
    // 从配置中加载 OPENAI_API_KEY
    EnvUtils.load();

    // 创建消息列表
    List<OpenAiChatMessage> messages = new ArrayList<>();
    messages.add(new OpenAiChatMessage("user", "How are you?"));

    // 创建聊天请求
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel(OpenAiModels.GPT_4O_MINI);
    chatRequestVo.setMessages(messages);

    String json = JsonUtils.toSkipNullJson(chatRequestVo);
    System.out.println("请求 JSON:\n" + json);

    // 发送 HTTP 请求
    try (Response response = OpenAiClient.chatCompletions(json)) {
      if (response.isSuccessful()) {
        String responseBody = response.body().string();
        OpenAiChatResponseVo chatCompletions = JsonUtils.parse(responseBody, OpenAiChatResponseVo.class);
        System.out.println("响应:\n" + JsonUtils.toJson(chatCompletions));
      } else {
        System.err.println("请求失败: " + response);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```

### 5. 使用角色的另一简化示例

```java
package com.litongjava.openai.example;

import com.litongjava.openai.chat.ChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class ChatCompletionsWithRoleExample {
  public static void main(String[] args) {
    // 从配置中加载 OPENAI_API_KEY
    EnvUtils.load();
    
    // 使用指定角色发起聊天补全请求
    ChatResponseVo chatResponse = OpenAiClient.chatCompletionsWithRole("user", "How are you?");
    System.out.println("响应:\n" + JsonUtils.toJson(chatResponse));
  }
}
```

## 示例

### 聊天示例

```java
package com.litongjava.openai.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.litongjava.openai.chat.ChatMessage;
import com.litongjava.openai.chat.ChatResponseVo;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.openai.constants.OpenAiModels;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Response;

public class SimpleAskExample {
  public static void main(String[] args) {
    // 从配置中加载 OPENAI_API_KEY
    EnvUtils.load();

    // 创建消息列表
    List<ChatMessage> messages = new ArrayList<>();
    ChatMessage message = new ChatMessage().role("user").content("How are you?");
    messages.add(message);

    // 创建聊天请求
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel(OpenAiModels.GPT_4O_MINI);
    chatRequestVo.setMessages(messages);

    String json = JsonUtils.toJson(chatRequestVo);
    System.out.println("请求 JSON:\n" + json);

    // 发送 HTTP 请求
    try (Response response = OpenAiClient.chatCompletions(json)) {
      if (response.isSuccessful()) {
        String responseBody = response.body().string();
        ChatResponseVo chatCompletions = JsonUtils.parse(responseBody, ChatResponseVo.class);
        System.out.println("响应:\n" + JsonUtils.toJson(chatCompletions));
      } else {
        System.err.println("请求失败: " + response);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```

### 带图像的提问

```java
package com.litongjava.perplexica.services;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.openai.chat.ChatMesageContent;
import com.litongjava.openai.chat.ChatRequestImage;
import com.litongjava.openai.chat.ChatResponseMessage;
import com.litongjava.openai.chat.OpenAiChatMessage;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.chat.OpenAiChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.openai.constants.OpenAiModels;
import com.litongjava.tio.utils.encoder.Base64Utils;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.ContentTypeUtils;
import com.litongjava.tio.utils.hutool.FileUtil;
import com.litongjava.tio.utils.hutool.FilenameUtils;
import com.litongjava.tio.utils.hutool.ResourceUtil;
import com.litongjava.tio.utils.json.JsonUtils;

public class AskWithImageOpenai {

  @Test
  public void imageToMarkDown() {
    EnvUtils.load();
    String apiKey = EnvUtils.getStr("OPENAI_API_KEY");

    String prompt = "Convert the image to text and just output the text.";

    String filePath = "images/200-dpi.png";
    URL url = ResourceUtil.getResource(filePath);
    byte[] imageBytes = FileUtil.readUrlAsBytes(url);
    String suffix = FilenameUtils.getSuffix(filePath);
    String mimeType = ContentTypeUtils.getContentType(suffix);

    String imageBase64 = Base64Utils.encodeImage(imageBytes, mimeType);

    ChatRequestImage chatRequestImage = new ChatRequestImage();
    chatRequestImage.setDetail("auto");
    chatRequestImage.setUrl(imageBase64);

    List<ChatMesageContent> multiContents = new ArrayList<>();
    multiContents.add(new ChatMesageContent(prompt));
    multiContents.add(new ChatMesageContent(chatRequestImage));

    OpenAiChatMessage userMessage = new OpenAiChatMessage();
    userMessage.role("user").multiContents(multiContents);

    List<OpenAiChatMessage> messages = new ArrayList<>();
    messages.add(userMessage);

    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setModel(OpenAiModels.GPT_4O_MINI);
    chatRequestVo.setMessages(messages);
    String json = JsonUtils.toSkipNullJson(chatRequestVo);
    System.out.println("请求 JSON:\n" + json);

    OpenAiChatResponseVo chatResponse = OpenAiClient.chatCompletions(apiKey, chatRequestVo);
    ChatResponseMessage responseMessage = chatResponse.getChoices().get(0).getMessage();
    String content = responseMessage.getContent();
    System.out.println("响应内容:\n" + content);
  }
}
```

### 带图像的聊天

```java
package com.litongjava.maxkb.service;

import java.net.URL;

import org.junit.Test;

import com.litongjava.openai.chat.ChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.tio.utils.hutool.FileUtil;
import com.litongjava.tio.utils.hutool.FilenameUtils;
import com.litongjava.tio.utils.hutool.ResourceUtil;

public class DatasetDocumentSplitServiceTest {

  @Test
  public void chatWithImage() {
    String apiKey = "sk-your_openai_api_key_here";
    String prompt = "Convert the image to text and just output the text.";

    String filePath = "images/200-dpi.png";
    URL url = ResourceUtil.getResource(filePath);
    byte[] imageBytes = FileUtil.readUrlAsBytes(url);
    String suffix = FilenameUtils.getSuffix(filePath);

    ChatResponseVo chatResponse = OpenAiClient.chatWithImage(apiKey, prompt, imageBytes, suffix);
    String content = chatResponse.getChoices().get(0).getMessage().getContent();
    System.out.println("响应内容:\n" + content);
  }
}
```

### 使用工具提问

```java
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
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Response;

public class AskWithTools {

  public static void main(String[] args) {
    // 加载配置
    EnvUtils.load();
    List<ChatMessage> messages = new ArrayList<>();

    messages.add(new ChatMessage()
        .role("system")
        .content("You are an excellent student advisor. You can query the database. The database is PostgreSQL."));
    messages.add(new ChatMessage()
        .role("system")
        .content("How many tables are in my database?"));

    // 定义函数参数
    ChatRequestFunctionParameter parameter = new ChatRequestFunctionParameter();
    parameter.setType("object");
    Map<String, ChatRequestFunctionProperty> properties = new HashMap<>();
    properties.put("sql", new ChatRequestFunctionProperty("string", "The SQL statement to execute."));
    parameter.setProperties(properties);

    List<String> required = new ArrayList<>();
    required.add("sql");
    parameter.setRequired(required);

    // 定义函数
    ChatRequestToolCallFunction function = new ChatRequestToolCallFunction();
    function.setName("find");
    function.setDescription("Execute SQL query on the database.");
    function.setParameters(parameter);

    // 定义工具
    ChatRequestTool tool = new ChatRequestTool();
    tool.setType("function");
    tool.setFunction(function);
    List<ChatRequestTool> tools = new ArrayList<>();
    tools.add(tool);

    // 创建聊天请求
    ChatRequestVo chatRequestVo = new ChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel("gpt-4o-2024-05-13");
    chatRequestVo.setMessages(messages);
    chatRequestVo.setTools(tools);

    String json = JsonUtils.toJson(chatRequestVo);
    System.out.println("请求 JSON:\n" + json);

    // 发送 HTTP 请求
    try (Response response = OpenAiClient.chatCompletions(json)) {
      if (response.isSuccessful()) {
        String responseBody = response.body().string();
        System.out.println("响应:\n" + responseBody);
        ChatResponseVo chatResponse = JsonUtils.parse(responseBody, ChatResponseVo.class);
        System.out.println("解析后的响应:\n" + JsonUtils.toJson(chatResponse));
      } else {
        System.err.println("请求失败: " + response);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```

### 嵌入

#### 示例 1：生成嵌入

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
    // 加载配置
    EnvUtils.load();
    
    // 创建嵌入请求
    EmbeddingRequestVo embeddingRequest = new EmbeddingRequestVo();
    embeddingRequest.setInput("What's your name?");
    embeddingRequest.setModel("text-embedding-3-small");

    String requestBody = JsonUtils.toJson(embeddingRequest);
    System.out.println("请求 JSON:\n" + requestBody);

    // 发送 HTTP 请求
    try (Response response = OpenAiClient.embeddings(requestBody)) {
      if (response.isSuccessful()) {
        String responseBody = response.body().string();
        System.out.println("响应:\n" + responseBody);
        
        // 解析响应
        EmbeddingResponseVo responseVo = JsonUtils.parse(responseBody, EmbeddingResponseVo.class);
        System.out.println("解析后的响应:\n" + JsonUtils.toJson(responseVo));
      } else {
        System.err.println("请求失败: " + response);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```

#### 示例 2：简单嵌入

```java
package com.litongjava.example;

import org.junit.Test;

import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.openai.constants.OpenAiModels;
import com.litongjava.tio.utils.environment.EnvUtils;

public class SimpleEmbeddingExample {

  @Test
  public void embedding() {
    // 加载配置
    EnvUtils.load();
    
    String text = "Hi";
    float[] embeddingArray = OpenAiClient.embeddingArray(text, OpenAiModels.TEXT_EMBEDDING_3_LARGE);
    
    System.out.println("嵌入长度: " + embeddingArray.length);
    // 预期输出：3072
  }
}
```

### Llama 集成

```java
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.openai.chat.ChatMessage;
import com.litongjava.openai.chat.ChatResponseVo;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.openai.constants.LlamaConstants;
import com.litongjava.openai.constants.LlamaModels;
import com.litongjava.tio.utils.environment.EnvUtils;

public class Llama3_1_Test {

  @Test
  public void testLlamaChat() {
    // 加载配置
    EnvUtils.load();
    
    // 创建消息列表
    List<ChatMessage> messages = new ArrayList<>();
    messages.add(new ChatMessage("user", "How are you?"));

    // 创建聊天请求
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo()
        .setModel(LlamaModels.LLAMA3_1_8B)
        .setMessages(messages)
        .setMaxTokens(3000);

    String apiKey = EnvUtils.get("LLAMA_API_KEY");

    // 发送 HTTP 请求至 Llama 服务器
    ChatResponseVo chatResponse = OpenAiClient.chatCompletions(LlamaConstants.SERVER_URL, apiKey, chatRequestVo);
    String content = chatResponse.getChoices().get(0).getMessage().getContent();
    System.out.println("响应内容:\n" + content);
  }
}
```

### Perplexity 集成

```java
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.openai.chat.ChatMessage;
import com.litongjava.openai.chat.ChatResponseVo;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.openai.constants.PerplexityConstants;
import com.litongjava.openai.constants.PerplexityModels;
import com.litongjava.tio.utils.environment.EnvUtils;

public class PerplexityTest {

  @Test
  public void testPerplexityChat() {
    // 加载配置
    EnvUtils.load();
    
    // 创建消息列表
    List<ChatMessage> messages = new ArrayList<>();
    messages.add(new ChatMessage("user", "How are you?"));

    // 创建聊天请求
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo()
        .setModel(PerplexityModels.LLAMA_3_1_SONAR_SMALL_128K_ONLINE)
        .setMessages(messages)
        .setMaxTokens(3000);

    String apiKey = EnvUtils.get("PERPLEXITY_API_KEY");

    // 发送 HTTP 请求至 Perplexity 服务器
    ChatResponseVo chatResponse = OpenAiClient.chatCompletions(PerplexityConstants.SERVER_URL, apiKey, chatRequestVo);
    String content = chatResponse.getChoices().get(0).getMessage().getContent();
    System.out.println("响应内容:\n" + content);
  }
}
```

### Jina Rerank

```java
package com.litongjava.open.chat.client;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.jian.rerank.JinaModel;
import com.litongjava.jian.rerank.JinaRerankClient;
import com.litongjava.jian.rerank.RerankReqVo;
import com.litongjava.jian.rerank.RerankRespVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class JinaRerankClientTest {

  @Test
  public void testRerank() {
    // 加载配置
    EnvUtils.load();
    
    // 准备文档
    List<String> documents = new ArrayList<>();
    documents.add("Bio-Hautpflege für empfindliche Haut mit Aloe Vera und Kamille: ...");
    documents.add("Neue Make-up-Trends setzen auf kräftige Farben und innovative Techniken: ...");
    documents.add("Cuidado de la piel orgánico para piel sensible con aloe vera y manzanilla: ...");
    documents.add("Las nuevas tendencias de maquillaje se centran en colores vivos y técnicas innovadoras: ...");
    documents.add("针对敏感肌专门设计的天然有机护肤产品：体验由芦荟和洋甘菊提取物带来的自然呵护。...");
    documents.add("新的化妆趋势注重鲜艳的颜色和创新的技巧：进入化妆艺术的新纪元...");
    documents.add("敏感肌のために特別に設計された天然有機スキンケア製品: ...");
    documents.add("新しいメイクのトレンドは鮮やかな色と革新的な技術に焦点を当てています: ...");

    // 创建 rerank 请求
    RerankReqVo reqVo = new RerankReqVo();
    reqVo.setModel(JinaModel.JINA_RERANKER_V2_BASE_MULTILINGUAL);
    reqVo.setQuery("Organic skincare products for sensitive skin");
    reqVo.setTopN(3);
    reqVo.setDocuments(documents);

    // 执行 rerank
    RerankRespVo respVo = JinaRerankClient.rerank(reqVo);
    System.out.println("Rerank 响应:\n" + JsonUtils.toJson(respVo));
  }
}
```

### Jina 搜索

```java
import org.junit.Test;

import com.litongjava.jian.search.JinaSearchClient;
import com.litongjava.tio.utils.environment.EnvUtils;

public class JinaSearchServiceTest {

  @Test
  public void test() {
    EnvUtils.load();
    String result = JinaSearchClient.search("How can I run deepseek r1 with lama.cpp");
    System.out.println(result);
  }
}
```

**示例输出**:

```
[1] Title: Run Deepseek-R1 / R1 Zero
[1] URL Source: https://unsloth.ai/blog/deepseek-r1
...
```

### 解析 Markdown 响应

一个简单的数据类示例：

```java
package com.litongjava.searxng;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WebPageConteont {
  private String title;
  private String url;
  private String description;
  private String content;
}
```

以及一个假设的解析方法：

```java
public static List<WebPageConteont> parse(String markdown) {
  // 实现解析给定的 markdown 字符串，
  // 提取相关字段（标题、URL、描述等），
  // 并返回一个 WebPageConteont 对象列表。
}
```

### GOOGLE GEMINI

```java
package com.litongjava.gemini;

import java.util.Collections;

/**
 * Gemini 示例演示
 */
public class GeminiDemo {

  public static void main(String[] args) {
    String googleApiKey = "YOUR_GOOGLE_API_KEY";
    // 1. 构造请求体
    GeminiPartVo part = new GeminiPartVo("Hello, how are you?");
    GeminiContentVo content = new GeminiContentVo("user", Collections.singletonList(part));
    GeminiChatRequestVo reqVo = new GeminiChatRequestVo(Collections.singletonList(content));

    // 2. 同步请求：generateContent
    GeminiChatResponseVo respVo = GeminiClient.generate(googleApiKey, GoogleGeminiModels.GEMINI_1_5_FLASH, reqVo);
    if (respVo != null && respVo.getCandidates() != null) {
      respVo.getCandidates().forEach(candidate -> {
        if (candidate.getContent() != null && candidate.getContent().getParts() != null) {
          candidate.getContent().getParts().forEach(partVo -> {
            System.out.println("Gemini 回答文本: " + partVo.getText());
          });
        }
      });
    }
  }
}
```

#### Google GEMINI 图像

```java
package com.litongjava.gemini;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.litongjava.tio.utils.encoder.Base64Utils;
import com.litongjava.tio.utils.environment.EnvUtils;

public class GeminiClientImageTest {

  public void toMarkdown(Path path) {
    // 将图像读取为 base64 编码
    byte[] readAllBytes = null;
    try {
      readAllBytes = Files.readAllBytes(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (readAllBytes == null) {
      return;
    }
    String mimeType = "image/png";
    String encodeImage = Base64Utils.encodeToString(readAllBytes);
    String googleApiKey = EnvUtils.getStr("GEMINI_API_KEY");

    // 1. 构造请求体
    List<GeminiPartVo> parts = new ArrayList<>();
    parts.add(new GeminiPartVo("识别图片内容"));
    parts.add(new GeminiPartVo(new GeminiInlineDataVo(mimeType, encodeImage)));
    GeminiContentVo content = new GeminiContentVo("user", parts);
    GeminiChatRequestVo reqVo = new GeminiChatRequestVo(Collections.singletonList(content));

    // 2. 同步请求：generateContent
    GeminiChatResponseVo respVo = GeminiClient.generate(googleApiKey, GoogleGeminiModels.GEMINI_1_5_FLASH, reqVo);
    if (respVo != null && respVo.getCandidates() != null) {
      respVo.getCandidates().forEach(candidate -> {
        if (candidate.getContent() != null && candidate.getContent().getParts() != null) {
          candidate.getContent().getParts().forEach(partVo -> {
            System.out.println("Gemini 回答文本: " + partVo.getText());
          });
        }
      });
    }
    return;
  }
}
```

#### GOOGLE GEMINI 函数调用

**请求体 JSON 示例**：

```json
{
  "system_instruction": {
    "parts": {
      "text": "You are a helpful lighting system bot. You can turn lights on and off, and you can set the color. Do not perform any other tasks."
    }
  },
  "tools": [
    {
      "function_declarations": [
        {
          "name": "enable_lights",
          "description": "Turn on the lighting system.",
          "parameters": {
            "type": "object",
            "properties": {
              "dummy": {
                "type": "boolean",
                "description": "A placeholder parameter."
              }
            },
            "required": [
              "dummy"
            ]
          }
        },
        {
          "name": "set_light_color",
          "description": "Set the light color. Lights must be enabled for this to work.",
          "parameters": {
            "type": "object",
            "properties": {
              "rgb_hex": {
                "type": "string",
                "description": "The light color as a 6-digit hex string, e.g. ff0000 for red."
              }
            },
            "required": [
              "rgb_hex"
            ]
          }
        },
        {
          "name": "stop_lights",
          "description": "Turn off the lighting system.",
          "parameters": {
            "type": "object",
            "properties": {
              "dummy": {
                "type": "boolean",
                "description": "A placeholder parameter."
              }
            },
            "required": [
              "dummy"
            ]
          }
        }
      ]
    }
  ],
  "tool_config": {
    "function_calling_config": {
      "mode": "ANY",
      "allowed_function_names": [
        "enable_lights",
        "set_light_color",
        "stop_lights"
      ]
    }
  },
  "contents": {
    "role": "user",
    "parts": {
      "text": "Turn off the lighting system"
    }
  }
}
```

**Java 代码**：

```java
package com.litongjava.gemini;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class GeminiFunctionCallExample {

  public static void main(String[] args) {
    EnvUtils.load();

    // 1. system_instruction
    GeminiSystemInstructionVo systemInstruction = new GeminiSystemInstructionVo(
        new GeminiPartVo("You are a helpful lighting system bot. You can turn lights on and off, "
            + "and you can set the color. Do not perform any other tasks."));

    // 2. tools -> function_declarations
    GeminiFunctionDeclarationVo enableLightsFunc = new GeminiFunctionDeclarationVo();
    enableLightsFunc.setName("enable_lights");
    enableLightsFunc.setDescription("Turn on the lighting system.");
    Map<String, Object> enableLightsProperties = new HashMap<>();
    enableLightsProperties.put("dummy", new HashMap<String, Object>() {{
      put("type", "boolean");
      put("description", "A placeholder parameter.");
    }});
    Map<String, Object> enableLightsParams = new HashMap<>();
    enableLightsParams.put("type", "object");
    enableLightsParams.put("properties", enableLightsProperties);
    enableLightsParams.put("required", Arrays.asList("dummy"));
    enableLightsFunc.setParameters(enableLightsParams);

    GeminiFunctionDeclarationVo setLightColorFunc = new GeminiFunctionDeclarationVo();
    setLightColorFunc.setName("set_light_color");
    setLightColorFunc.setDescription("Set the light color. Lights must be enabled for this to work.");
    Map<String, Object> setLightColorProperties = new HashMap<>();
    setLightColorProperties.put("rgb_hex", new HashMap<String, String>() {{
      put("type", "string");
      put("description", "The light color as a 6-digit hex string, e.g. ff0000 for red.");
    }});
    Map<String, Object> setLightColorParams = new HashMap<>();
    setLightColorParams.put("type", "object");
    setLightColorParams.put("properties", setLightColorProperties);
    setLightColorParams.put("required", Arrays.asList("rgb_hex"));
    setLightColorFunc.setParameters(setLightColorParams);

    GeminiFunctionDeclarationVo stopLightsFunc = new GeminiFunctionDeclarationVo();
    stopLightsFunc.setName("stop_lights");
    stopLightsFunc.setDescription("Turn off the lighting system.");
    Map<String, Object> stopLightsProperties = new HashMap<>();
    stopLightsProperties.put("dummy", new HashMap<String, Object>() {{
      put("type", "boolean");
      put("description", "A placeholder parameter.");
    }});
    Map<String, Object> stopLightsParams = new HashMap<>();
    stopLightsParams.put("type", "object");
    stopLightsParams.put("properties", stopLightsProperties);
    stopLightsParams.put("required", Arrays.asList("dummy"));
    stopLightsFunc.setParameters(stopLightsParams);

    // 单个工具包含多个函数
    GeminiToolVo tool = new GeminiToolVo(Arrays.asList(enableLightsFunc, setLightColorFunc, stopLightsFunc));

    // 3. tool_config
    GeminiFunctionCallingConfigVo funcCallingConfig = new GeminiFunctionCallingConfigVo();
    funcCallingConfig.setMode("ANY");
    funcCallingConfig.setAllowed_function_names(Arrays.asList("enable_lights", "set_light_color", "stop_lights"));

    GeminiToolConfigVo toolConfig = new GeminiToolConfigVo(funcCallingConfig);

    // 4. 用户消息内容
    GeminiPartVo userPart = new GeminiPartVo("Turn off the lighting system");
    GeminiContentVo userContent = new GeminiContentVo("user", Arrays.asList(userPart));

    // 5. 组装请求
    GeminiChatRequestVo requestVo = new GeminiChatRequestVo();
    requestVo.setSystem_instruction(systemInstruction);
    requestVo.setTools(Arrays.asList(tool));
    requestVo.setTool_config(toolConfig);
    requestVo.setContents(Arrays.asList(userContent));

    // 转换为 JSON
    String requestJson = JsonUtils.toJson(requestVo);
    System.out.println("==== 请求 JSON ====");
    System.out.println(requestJson);

    // 6. 发送请求
    GeminiChatResponseVo response = GeminiClient.generate("gemini-1.5-flash", requestVo);

    // 7. 打印响应
    if (response != null && response.getCandidates() != null) {
      for (GeminiCandidateVo candidate : response.getCandidates()) {
        GeminiContentResponseVo content = candidate.getContent();
        if (content == null || content.getParts() == null) continue;
        for (GeminiPartVo part : content.getParts()) {
          if (part.getFunctionCall() != null) {
            System.out.println("[函数调用响应]");
            System.out.println("函数名称: " + part.getFunctionCall().getName());
            System.out.println("函数参数: " + part.getFunctionCall().getArgs());
          } else {
            System.out.println("[文本响应]");
            System.out.println("模型文本: " + part.getText());
          }
        }
      }
    } else {
      System.out.println("没有收到 Gemini 的响应或候选项为空。");
    }
  }
}
```

#### gemini 上传文件

```java
package com.litongjava.gemini;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class GeminiClientUploadFileTest {
  public static void main(String[] args) throws IOException {
    EnvUtils.load();
    Path path = Paths.get("C:\\Users\\Administrator\\Downloads\\Lab Equipments Activity Sheet.pdf");
    byte[] bytes = Files.readAllBytes(path);
    FileUploadResponseVo responseVo = GeminiClient.uploadFile(bytes);
    System.out.println(JsonUtils.toJson(responseVo));
  }
}
```

**示例响应**:

```json
{
  "file": {
    "name": "files/mo7v85d4zum5",
    "mimeType": "application/pdf",
    "sizeBytes": "295078",
    "createTime": "2025-01-23T06:54:56.365928Z",
    "updateTime": "2025-01-23T06:54:56.365928Z",
    "expirationTime": "2025-01-25T06:54:56.347859648Z",
    "sha256Hash": "...",
    "uri": "https://generativelanguage.googleapis.com/v1beta/files/mo7v85d4zum5",
    "state": "ACTIVE",
    "source": "UPLOADED"
  }
}
```

#### gemini 使用 PDF 提问

```java
package com.litongjava.gemini;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.litongjava.tio.utils.environment.EnvUtils;

public class GeminiClientAskWithPdfTest {

  public static void main(String[] args) {
    EnvUtils.load();
    String googleApiKey = EnvUtils.getStr("GEMINI_API_KEY");

    // 假设你已经上传了一个 PDF 文件
    String mimeType = "application/pdf";
    String fileUri = "https://generativelanguage.googleapis.com/v1beta/files/4eqnhyuzvzkb";

    // 1. 构造请求
    List<GeminiPartVo> parts = new ArrayList<>();
    parts.add(new GeminiPartVo("翻译成中文"));
    parts.add(new GeminiPartVo(new GeminiFileDataVo(mimeType, fileUri)));
    GeminiContentVo content = new GeminiContentVo("user", parts);
    GeminiChatRequestVo reqVo = new GeminiChatRequestVo(Collections.singletonList(content));

    // 2. 同步请求
    GeminiChatResponseVo respVo = GeminiClient.generate(googleApiKey, GoogleGeminiModels.GEMINI_1_5_FLASH, reqVo);

    // 3. 打印响应
    if (respVo != null && respVo.getCandidates() != null) {
      respVo.getCandidates().forEach(candidate -> {
        if (candidate.getContent() != null && candidate.getContent().getParts() != null) {
          candidate.getContent().getParts().forEach(partVo -> {
            System.out.println("Gemini 回答文本: " + partVo.getText());
          });
        }
      });
    }
  }
}
```

#### gemini openai

在你的 `app.properties` 中添加：

```properties
OPENAI_API_KEY=<Gemini key here>
OPENAI_API_URL=https://generativelanguage.googleapis.com/v1beta/openai
```

然后在代码中：

```java
package com.litongjava.perplexica.services;

import com.litongjava.gemini.GoogleGeminiModels;
import com.litongjava.openai.chat.OpenAiChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class GeminiClientTest {
  public static void main(String[] args) {
    EnvUtils.load();
    OpenAiChatResponseVo chatResponse = OpenAiClient.chatWithModel(
        GoogleGeminiModels.GEMINI_2_0_FLASH_EXP,
        "user",
        "How are you?"
    );
    System.out.println(JsonUtils.toJson(chatResponse));
  }
}
```

### deepseek-openai

```java
import java.util.ArrayList;
import java.util.List;

import com.litongjava.deepseek.DeepSeekConst;
import com.litongjava.deepseek.DeepSeekModels;
import com.litongjava.openai.chat.OpenAiChatMessage;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.chat.OpenAiChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class DeepSeekClientTest {

  public static void main(String[] args) {
    EnvUtils.load();
    List<OpenAiChatMessage> messages = new ArrayList<>();
    messages.add(new OpenAiChatMessage("user", "Hi"));

    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo()
        .setModel(DeepSeekModels.DEEPSEEK_REASONER)
        .setMessages(messages)
        .setMax_tokens(8000);

    String apiKey = EnvUtils.getStr("DEEPSEEK_API_KEY");
    OpenAiChatResponseVo chatResponse = OpenAiClient.chatCompletions(DeepSeekConst.BASE_URL, apiKey, chatRequestVo);
    System.out.println(JsonUtils.toSkipNullJson(chatResponse));
  }
}
```

### SiliconFlow DeepSeek

```java
package com.litongjava.perplexica.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.litongjava.openai.chat.OpenAiChatMessage;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.chat.OpenAiChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.siliconflow.SiliconFlowConsts;
import com.litongjava.siliconflow.SiliconFlowModels;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Response;

public class SiliconFlowDeepSeekTest {
  public static void main(String[] args) {
    // 从配置中加载 OPENAI_API_KEY
    EnvUtils.load();
    String apiKey = EnvUtils.getStr("SILICONFLOW_API_KEY");

    // 创建消息列表
    List<OpenAiChatMessage> messages = new ArrayList<>();
    messages.add(new OpenAiChatMessage("user", "How are you?"));

    // 创建聊天请求
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel(SiliconFlowModels.DEEPSEEK_R1);
    chatRequestVo.setMessages(messages);

    String json = JsonUtils.toSkipNullJson(chatRequestVo);
    System.out.println("请求 JSON:\n" + json);

    // 发送 HTTP 请求
    try (Response response = OpenAiClient.chatCompletions(SiliconFlowConsts.SELICONFLOW_API_BASE, apiKey, json)) {
      if (response.isSuccessful()) {
        String responseBody = response.body().string();
        OpenAiChatResponseVo chatCompletions = JsonUtils.parse(responseBody, OpenAiChatResponseVo.class);
        System.out.println("响应:\n" + JsonUtils.toSkipNullJson(chatCompletions));
      } else {
        System.err.println("请求失败: " + response);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```

### SiliconFlow DeepSeek 图像

```java
package com.litongjava.perplexica.services;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.openai.chat.ChatMesageContent;
import com.litongjava.openai.chat.ChatRequestImage;
import com.litongjava.openai.chat.ChatResponseMessage;
import com.litongjava.openai.chat.OpenAiChatMessage;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.chat.OpenAiChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.siliconflow.SiliconFlowConsts;
import com.litongjava.siliconflow.SiliconFlowModels;
import com.litongjava.tio.utils.encoder.Base64Utils;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.ContentTypeUtils;
import com.litongjava.tio.utils.hutool.FileUtil;
import com.litongjava.tio.utils.hutool.FilenameUtils;
import com.litongjava.tio.utils.hutool.ResourceUtil;
import com.litongjava.tio.utils.json.JsonUtils;

public class AskWithImageDeepSeek {

  @Test
  public void imageToMarkDown() {
    EnvUtils.load();
    String apiKey = EnvUtils.getStr("SILICONFLOW_API_KEY");

    String prompt = "Convert the image to text and just output the text.";

    String filePath = "images/200-dpi.png";
    URL url = ResourceUtil.getResource(filePath);
    byte[] imageBytes = FileUtil.readUrlAsBytes(url);
    String suffix = FilenameUtils.getSuffix(filePath);
    String mimeType = ContentTypeUtils.getContentType(suffix);

    String imageBase64 = Base64Utils.encodeImage(imageBytes, mimeType);

    ChatRequestImage chatRequestImage = new ChatRequestImage();
    chatRequestImage.setUrl(imageBase64);

    List<ChatMesageContent> multiContents = new ArrayList<>();
    multiContents.add(new ChatMesageContent(chatRequestImage));
    multiContents.add(new ChatMesageContent(prompt));

    OpenAiChatMessage userMessage = new OpenAiChatMessage();
    userMessage.role("user").multiContents(multiContents);

    List<OpenAiChatMessage> messages = new ArrayList<>();
    messages.add(userMessage);

    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    // DEEPSEEK_R1 为纯文本模型，因此这里使用 DEEPSEEK_VL2 来处理图像
    chatRequestVo.setModel(SiliconFlowModels.DEEPSEEK_VL2);
    chatRequestVo.setMax_tokens(1024).setTemperature(0.7f).setTop_p(0.7f).setTop_k(50).setFrequency_penalty(0);
    chatRequestVo.setMessages(messages);

    OpenAiChatResponseVo chatResponse =
        OpenAiClient.chatCompletions(SiliconFlowConsts.SELICONFLOW_API_BASE, apiKey, chatRequestVo);
    ChatResponseMessage responseMessage = chatResponse.getChoices().get(0).getMessage();
    String content = responseMessage.getContent();
    System.out.println("响应内容:\n" + content);
  }
}
```

## 许可证

该项目遵循 [MIT License](LICENSE) 许可协议。欢迎贡献、报告问题或提交 pull request 以改进项目。