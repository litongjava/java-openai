# Java OpenAI Client

## Introduction

Java OpenAI is a robust client library for integrating OpenAI services into Java applications. Built on top of [OkHttp](https://square.github.io/okhttp/) and [FastJSON](https://github.com/alibaba/fastjson), it provides a seamless and efficient way to interact with OpenAI's APIs, enabling features such as chat completions, image processing, embeddings, and more.

## Table of Contents

- [Java OpenAI Client](#java-openai-client)
  - [Introduction](#introduction)
  - [Table of Contents](#table-of-contents)
  - [Features](#features)
  - [Getting Started](#getting-started)
    - [1. Add Dependencies](#1-add-dependencies)
    - [2. Configure API Key](#2-configure-api-key)
    - [3. Run a Simple Test](#3-run-a-simple-test)
  - [Examples](#examples)
    - [Chat Example](#chat-example)
    - [Ask with Image](#ask-with-image)
    - [Chat with Image](#chat-with-image)
    - [Ask with Tools](#ask-with-tools)
    - [Embedding](#embedding)
    - [Llama Integration](#llama-integration)
    - [Perplexity Integration](#perplexity-integration)
    - [Jina Rerank](#jina-rerank)
  - [License](#license)

## Features

- **Chat Completions:** Interact with OpenAI's chat models effortlessly.
- **Image Processing:** Convert images to text or other formats.
- **Embeddings:** Generate text embeddings for various applications.
- **Tool Integration:** Enhance functionality by integrating with external tools.
- **Support for Llama and Perplexity Models:** Extend capabilities beyond OpenAI with support for other models.
- **Easy Configuration:** Simple setup with configuration files.

## Getting Started

### 1. Add Dependencies

To use Java OpenAI in your project, add the following dependencies to your `pom.xml`:

```xml
<dependencies>
  <dependency>
    <groupId>com.litongjava</groupId>
    <artifactId>java-openai</artifactId>
    <version>1.1.2</version>
  </dependency>
  <dependency>
    <groupId>com.alibaba.fastjson2</groupId>
    <artifactId>fastjson2</artifactId>
    <version>2.0.52</version>
  </dependency>
</dependencies>
```

### 2. Configure API Key

Add your OpenAI API key to the `src/main/resources/app.properties` file:

```properties
OPENAI_API_KEY=your_openai_api_key_here
```

### 3. Run a Simple Test

Create a simple Java class to test the integration:

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
    // Load OPENAI_API_KEY from configuration
    EnvUtils.load();

    // Create messages
    List<ChatMessage> messages = new ArrayList<>();
    ChatMessage message = new ChatMessage().role("user").content("How are you?");
    messages.add(message);

    // Create chat request
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel(OpenAiModels.GPT_4O_MINI);
    chatRequestVo.setMessages(messages);

    String json = JsonUtils.toJson(chatRequestVo);
    System.out.println("Request JSON:\n" + json);

    // Send HTTP request
    try (Response response = OpenAiClient.chatCompletions(json)) {
      if (response.isSuccessful()) {
        String responseBody = response.body().string();
        ChatResponseVo chatCompletions = JsonUtils.parse(responseBody, ChatResponseVo.class);
        System.out.println("Response:\n" + JsonUtils.toJson(chatCompletions));
      } else {
        System.err.println("Request failed: " + response);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```

Another simplified example using roles:

```java
package com.litongjava.openai.example;

import com.litongjava.openai.chat.ChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class ChatCompletionsWithRoleExample {
  public static void main(String[] args) {
    // Load OPENAI_API_KEY from configuration
    EnvUtils.load();
    
    // Make a chat completion request with role
    ChatResponseVo chatResponse = OpenAiClient.chatCompletionsWithRole("user", "How are you?");
    System.out.println("Response:\n" + JsonUtils.toJson(chatResponse));
  }
}
```

## Examples

### Chat Example

A basic example demonstrating how to send a chat message and receive a response.

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
    // Load OPENAI_API_KEY from configuration
    EnvUtils.load();

    // Create messages
    List<ChatMessage> messages = new ArrayList<>();
    ChatMessage message = new ChatMessage().role("user").content("How are you?");
    messages.add(message);

    // Create chat request
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel(OpenAiModels.GPT_4O_MINI);
    chatRequestVo.setMessages(messages);

    String json = JsonUtils.toJson(chatRequestVo);
    System.out.println("Request JSON:\n" + json);

    // Send HTTP request
    try (Response response = OpenAiClient.chatCompletions(json)) {
      if (response.isSuccessful()) {
        String responseBody = response.body().string();
        ChatResponseVo chatCompletions = JsonUtils.parse(responseBody, ChatResponseVo.class);
        System.out.println("Response:\n" + JsonUtils.toJson(chatCompletions));
      } else {
        System.err.println("Request failed: " + response);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```

### Ask with Image

Convert an image to text and output the result.

```java
package com.litongjava.maxkb.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.openai.chat.ChatMessage;
import com.litongjava.openai.chat.ChatRequestImage;
import com.litongjava.openai.chat.ChatRequestMultiContent;
import com.litongjava.openai.chat.ChatResponseMessage;
import com.litongjava.openai.chat.ChatResponseVo;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.openai.constants.OpenAiModels;
import com.litongjava.tio.utils.encoder.Base64Utils;
import com.litongjava.tio.utils.http.ContentTypeUtils;
import com.litongjava.tio.utils.hutool.FileUtil;
import com.litongjava.tio.utils.hutool.FilenameUtils;
import com.litongjava.tio.utils.hutool.ResourceUtil;
import com.litongjava.tio.utils.json.JsonUtils;

public class DatasetDocumentSplitServiceTest {

  @Test
  public void imageToMarkDown() {
    String apiKey = "your_openai_api_key_here";
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

    ChatRequestMultiContent imageContent = new ChatRequestMultiContent("image_url", chatRequestImage);
    List<ChatRequestMultiContent> multiContents = new ArrayList<>();
    multiContents.add(imageContent);

    ChatMessage systemMessage = new ChatMessage("system", prompt);
    ChatMessage userMessage = new ChatMessage();
    userMessage.role("user").multiContents(multiContents);

    List<ChatMessage> messages = new ArrayList<>();
    messages.add(systemMessage);
    messages.add(userMessage);

    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setModel(OpenAiModels.GPT_4O_MINI);
    chatRequestVo.setMessages(messages);
    String json = JsonUtils.toJson(chatRequestVo);
    System.out.println("Request JSON:\n" + json);

    ChatResponseVo chatResponse = OpenAiClient.chatCompletions(apiKey, chatRequestVo);
    ChatResponseMessage responseMessage = chatResponse.getChoices().get(0).getMessage();
    String content = responseMessage.getContent();
    System.out.println("Response Content:\n" + content);
  }
}
```

### Chat with Image

Another example of sending an image along with a prompt.

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
    System.out.println("Response Content:\n" + content);
  }
}
```

### Ask with Tools

Integrate external tools to enhance the functionality of your chat interactions.

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
    // Load configuration
    EnvUtils.load();
    List<ChatMessage> messages = new ArrayList<>();

    messages.add(new ChatMessage()
        .role("system")
        .content("You are an excellent student advisor. You can query the database. The database is PostgreSQL."));
    messages.add(new ChatMessage()
        .role("system")
        .content("How many tables are in my database?"));

    // Define function parameters
    ChatRequestFunctionParameter parameter = new ChatRequestFunctionParameter();
    parameter.setType("object");
    Map<String, ChatRequestFunctionProperty> properties = new HashMap<>();
    properties.put("sql", new ChatRequestFunctionProperty("string", "The SQL statement to execute."));
    parameter.setProperties(properties);

    List<String> required = new ArrayList<>();
    required.add("sql");
    parameter.setRequired(required);

    // Define the function
    ChatRequestToolCallFunction function = new ChatRequestToolCallFunction();
    function.setName("find");
    function.setDescription("Execute SQL query on the database.");
    function.setParameters(parameter);

    // Define the tool
    ChatRequestTool tool = new ChatRequestTool();
    tool.setType("function");
    tool.setFunction(function);
    List<ChatRequestTool> tools = new ArrayList<>();
    tools.add(tool);

    // Create chat request
    ChatRequestVo chatRequestVo = new ChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel("gpt-4o-2024-05-13");
    chatRequestVo.setMessages(messages);
    chatRequestVo.setTools(tools);

    String json = JsonUtils.toJson(chatRequestVo);
    System.out.println("Request JSON:\n" + json);

    // Send HTTP request
    try (Response response = OpenAiClient.chatCompletions(json)) {
      if (response.isSuccessful()) {
        String responseBody = response.body().string();
        System.out.println("Response:\n" + responseBody);
        ChatResponseVo chatResponse = JsonUtils.parse(responseBody, ChatResponseVo.class);
        System.out.println("Parsed Response:\n" + JsonUtils.toJson(chatResponse));
      } else {
        System.err.println("Request failed: " + response);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```

### Embedding

Generate text embeddings for various applications.

**Example 1: Generate Embedding**

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
    // Load configuration
    EnvUtils.load();
    
    // Create embedding request
    EmbeddingRequestVo embeddingRequest = new EmbeddingRequestVo();
    embeddingRequest.setInput("What's your name?");
    embeddingRequest.setModel("text-embedding-3-small");

    String requestBody = JsonUtils.toJson(embeddingRequest);
    System.out.println("Request JSON:\n" + requestBody);

    // Send HTTP request
    try (Response response = OpenAiClient.embeddings(requestBody)) {
      if (response.isSuccessful()) {
        String responseBody = response.body().string();
        System.out.println("Response:\n" + responseBody);
        
        // Parse response
        EmbeddingResponseVo responseVo = JsonUtils.parse(responseBody, EmbeddingResponseVo.class);
        System.out.println("Parsed Response:\n" + JsonUtils.toJson(responseVo));
      } else {
        System.err.println("Request failed: " + response);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```

**Example 2: Simple Embedding**

```java
package com.litongjava.example;

import org.junit.Test;

import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.openai.constants.OpenAiModels;
import com.litongjava.tio.utils.environment.EnvUtils;

public class SimpleEmbeddingExample {

  @Test
  public void embedding() {
    // Load configuration
    EnvUtils.load();
    
    String text = "Hi";
    float[] embeddingArray = OpenAiClient.embeddingArray(text, OpenAiModels.TEXT_EMBEDDING_3_LARGE);
    
    System.out.println("Embedding Length: " + embeddingArray.length);
    // Expected output: 3072
  }
}
```

### Llama Integration

Integrate with Llama models for advanced language processing.

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
    // Load configuration
    EnvUtils.load();
    
    // Create messages
    List<ChatMessage> messages = new ArrayList<>();
    messages.add(new ChatMessage("user", "How are you?"));

    // Create chat request
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo()
        .setModel(LlamaModels.LLAMA3_1_8B)
        .setMessages(messages)
        .setMaxTokens(3000);

    String apiKey = EnvUtils.get("LLAMA_API_KEY");

    // Send HTTP request to Llama server
    ChatResponseVo chatResponse = OpenAiClient.chatCompletions(LlamaConstants.SERVER_URL, apiKey, chatRequestVo);
    String content = chatResponse.getChoices().get(0).getMessage().getContent();
    System.out.println("Response Content:\n" + content);
  }
}
```

### Perplexity Integration

Leverage Perplexity models for enhanced language understanding.

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
    // Load configuration
    EnvUtils.load();
    
    // Create messages
    List<ChatMessage> messages = new ArrayList<>();
    messages.add(new ChatMessage("user", "How are you?"));

    // Create chat request
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo()
        .setModel(PerplexityModels.LLAMA_3_1_SONAR_SMALL_128K_ONLINE)
        .setMessages(messages)
        .setMaxTokens(3000);

    String apiKey = EnvUtils.get("PERPLEXITY_API_KEY");

    // Send HTTP request to Perplexity server
    ChatResponseVo chatResponse = OpenAiClient.chatCompletions(PerplexityConstants.SERVER_URL, apiKey, chatRequestVo);
    String content = chatResponse.getChoices().get(0).getMessage().getContent();
    System.out.println("Response Content:\n" + content);
  }
}
```

### Jina Rerank

Use Jina Rerank to reorder documents based on relevance.

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
    // Load configuration
    EnvUtils.load();
    
    // Prepare documents
    List<String> documents = new ArrayList<>();
    documents.add("Bio-Hautpflege für empfindliche Haut mit Aloe Vera und Kamille: Erleben Sie die wohltuende Wirkung unserer Bio-Hautpflege, speziell für empfindliche Haut entwickelt. Mit den beruhigenden Eigenschaften von Aloe Vera und Kamille pflegen und schützen unsere Produkte Ihre Haut auf natürliche Weise. Verabschieden Sie sich von Hautirritationen und genießen Sie einen strahlenden Teint.");
    documents.add("Neue Make-up-Trends setzen auf kräftige Farben und innovative Techniken: Tauchen Sie ein in die Welt der modernen Schönheit mit den neuesten Make-up-Trends. Kräftige, lebendige Farben und innovative Techniken setzen neue Maßstäbe. Von auffälligen Eyelinern bis hin zu holografischen Highlightern – lassen Sie Ihrer Kreativität freien Lauf und setzen Sie jedes Mal ein Statement.");
    documents.add("Cuidado de la piel orgánico para piel sensible con aloe vera y manzanilla: Descubre el poder de la naturaleza con nuestra línea de cuidado de la piel orgánico, diseñada especialmente para pieles sensibles. Enriquecidos con aloe vera y manzanilla, estos productos ofrecen una hidratación y protección suave. Despídete de las irritaciones y saluda a una piel radiante y saludable.");
    documents.add("Las nuevas tendencias de maquillaje se centran en colores vivos y técnicas innovadoras: Entra en el fascinante mundo del maquillaje con las tendencias más actuales. Colores vivos y técnicas innovadoras están revolucionando el arte del maquillaje. Desde delineadores neón hasta iluminadores holográficos, desata tu creatividad y destaca en cada look.");
    documents.add("针对敏感肌专门设计的天然有机护肤产品：体验由芦荟和洋甘菊提取物带来的自然呵护。我们的护肤产品特别为敏感肌设计，温和滋润，保护您的肌肤不受刺激。让您的肌肤告别不适，迎来健康光彩。");
    documents.add("新的化妆趋势注重鲜艳的颜色和创新的技巧：进入化妆艺术的新纪元，本季的化妆趋势以大胆的颜色和创新的技巧为主。无论是霓虹眼线还是全息高光，每一款妆容都能让您脱颖而出，展现独特魅力。");
    documents.add("敏感肌のために特別に設計された天然有機スキンケア製品: アロエベラとカモミールのやさしい力で、自然の抱擁を感じてください。敏感肌用に特別に設計された私たちのスキンケア製品は、肌に優しく栄養を与え、保護します。肌トラブルにさようなら、輝く健康な肌にこんにちは。");
    documents.add("新しいメイクのトレンドは鮮やかな色と革新的な技術に焦点を当てています: 今シーズンのメイクアップトレンドは、大胆な色彩と革新的な技術に注目しています。ネオンアイライナーからホログラフィックハイライターまで、クリエイティビティを解き放ち、毎回ユニークなルックを演出しましょう。");

    // Create rerank request
    RerankReqVo reqVo = new RerankReqVo();
    reqVo.setModel(JinaModel.JINA_RERANKER_V2_BASE_MULTILINGUAL);
    reqVo.setQuery("Organic skincare products for sensitive skin");
    reqVo.setTopN(3);
    reqVo.setDocuments(documents);

    // Perform rerank
    RerankRespVo respVo = JinaRerankClient.rerank(reqVo);
    System.out.println("Rerank Response:\n" + JsonUtils.toJson(respVo));
  }
}
```

**Sample Output:**

```json
{
  "model": "jina-reranker-v2-base-multilingual",
  "usage": {
    "prompt_tokens": 652,
    "total_tokens": 652,
    "completion_tokens": null
  },
  "results": [
    {
      "index": 4,
      "document": {
        "text": "针对敏感肌专门设计的天然有机护肤产品：体验由芦荟和洋甘菊提取物带来的自然呵护。我们的护肤产品特别为敏感肌设计，温和滋润，保护您的肌肤不受刺激。让您的肌肤告别不适，迎来健康光彩。"
      },
      "relevance_score": 0.8783142566680908
    },
    {
      "index": 2,
      "document": {
        "text": "Cuidado de la piel orgánico para piel sensible con aloe vera y manzanilla: Descubre el poder de la naturaleza con nuestra línea de cuidado de la piel orgánico, diseñada especialmente para pieles sensibles. Enriquecidos con aloe vera y manzanilla, estos productos ofrecen una hidratación y protección suave. Despídete de las irritaciones y saluda a una piel radiante y saludable."
      },
      "relevance_score": 0.8633915781974792
    },
    {
      "index": 6,
      "document": {
        "text": "敏感肌のために特別に設計された天然有機スキンケア製品: アロエベラとカモミールのやさしい力で、自然の抱擁を感じてください。敏感肌用に特別に設計された私たちのスキンケア製品は、肌に優しく栄養を与え、保護します。肌トラブルにさようなら、輝く健康な肌にこんにちは。"
      },
      "relevance_score": 0.7786493301391602
    }
  ]
}
```

## License

This project is licensed under the [MIT License](LICENSE).