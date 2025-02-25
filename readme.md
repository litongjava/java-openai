# Java OpenAI Client

## Introduction

**Java OpenAI** is a robust client library for integrating OpenAI services into Java applications. Built on top of [OkHttp](https://square.github.io/okhttp/) and [FastJSON](https://github.com/alibaba/fastjson), it provides a seamless, efficient way to interact with OpenAI's APIs—enabling features such as chat completions, image processing, embeddings, and more.

## Table of Contents

- [Java OpenAI Client](#java-openai-client)
  - [Introduction](#introduction)
  - [Table of Contents](#table-of-contents)
  - [Features](#features)
  - [Getting Started](#getting-started)
    - [1. Add Dependencies](#1-add-dependencies)
    - [2. Configure API Key](#2-configure-api-key)
    - [3. Run With PromptEngine](#3-run-with-promptengine)
    - [4. Run a Simple Test](#4-run-a-simple-test)
    - [5. Another Simplified Example Using Roles](#5-another-simplified-example-using-roles)
  - [Examples](#examples)
    - [Chat Example](#chat-example)
    - [Ask with Image](#ask-with-image)
    - [Chat with Image](#chat-with-image)
    - [Ask with Tools](#ask-with-tools)
    - [Embedding](#embedding)
      - [Example 1: Generate Embedding](#example-1-generate-embedding)
      - [Example 2: Simple Embedding](#example-2-simple-embedding)
    - [Llama Integration](#llama-integration)
    - [Perplexity Integration](#perplexity-integration)
    - [Jina Rerank](#jina-rerank)
    - [Jina Search](#jina-search)
    - [PARSE markdown response](#parse-markdown-response)
    - [GOOGLE GEMINI](#google-gemini)
      - [Google GEMINI images](#google-gemini-images)
      - [GOOGLE GEMINI Function Call](#google-gemini-function-call)
      - [gemini upload file](#gemini-upload-file)
      - [gemini ask with pdf](#gemini-ask-with-pdf)
      - [gemini openai](#gemini-openai)
    - [deepseek-openai](#deepseek-openai)
    - [SiliconFlow DeepSeek](#siliconflow-deepseek)
    - [SiliconFlow DeepSeek Image](#siliconflow-deepseek-image)
  - [Groq](#groq)
    - [GroqSpeechClientTest](#groqspeechclienttest)
  - [License](#license)

## Features

- **Chat Completions**: Interact with OpenAI's chat models effortlessly.
- **Image Processing**: Convert images to text or other formats.
- **Embeddings**: Generate text embeddings for various applications.
- **Tool Integration**: Enhance functionality by integrating with external tools.
- **Support for Llama and Perplexity Models**: Extend capabilities beyond OpenAI with support for other models.
- **Easy Configuration**: Simple setup with configuration files.

## Getting Started

### 1. Add Dependencies

Add the following dependencies to your `pom.xml`:

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

### 2. Configure API Key

Create or update `src/main/resources/app.properties`:

```properties
OPENAI_API_KEY=your_openai_api_key_here
```

### 3. Run With PromptEngine

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
    // Load OPENAI_API_KEY from configuration
    EnvUtils.load();

    // Create messages
    List<OpenAiChatMessage> messages = new ArrayList<>();
    String prompt = PromptEngine.renderToString("professor_emojis_introduction_weaknesses_strengths_prompt.txt");
    String exmaple = PromptEngine.renderToString("professor_emojis_introduction_weaknesses_strengths_prompt_example.txt");
    messages.add(new OpenAiChatMessage("system", prompt));
    messages.add(new OpenAiChatMessage("user", exmaple));

    // Create chat request
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
    System.out.println("Request JSON:\n" + json);

    // Send HTTP request
    OpenAiChatResponseVo chatCompletions = OpenAiClient.chatCompletions(chatRequestVo);
    System.out.println(JsonUtils.toSkipNullJson(chatCompletions));
  }
}

```
professor_emojis_introduction_weaknesses_strengths_prompt.txt
```
<instruction>
  You are tasked with generating a comprehensive analysis report of a professor based on data from RateMyProfessor. Follow these steps to complete the task:

  1. Use the provided variables to fill in the required fields in the JSON format.
  2. Analyze the professor's teaching style, classroom interaction, and personal charisma based on student reviews.
  3. Ensure the tone is humorous, witty, yet objective throughout the report.
  4. Populate the 'emojis' field with emojis that reflect the professor's personality and characteristics.
  5. Write a concise and impactful introduction summarizing the professor's teaching style, academic background, or notable qualities.
  6. Identify and list the professor's weaknesses using the specified format, ensuring each entry is on a new line with a key and description separated by a colon.
  7. Identify and list the professor's strengths using the specified format, ensuring each entry is on a new line with a key and description separated by a colon.
  8. Ensure the output is strictly in JSON format and does not contain any XML tags.

  The output should look like this:
  {
    "emojis": ["emoji1", "emoji_2", ...],
    "introduction": "professor_introduction",
    "weaknesses": "**Weakness_name_1**:weakness_description_1\n**Weakness_name_2**:weakness_description_2\n...",
    "strengths": "**Strength__name_1**:strength_description_1\n**Strength_name_2**:strength_description_2\n..."
  }
</instruction>
```
prompts\professor_emojis_introduction_weaknesses_strengths_prompt_example.txt
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

### 4. Run a Simple Test

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
    // Load OPENAI_API_KEY from configuration
    EnvUtils.load();

    // Create messages
    List<OpenAiChatMessage> messages = new ArrayList<>();
    messages.add(new OpenAiChatMessage("user", "How are you?"));

    // Create chat request
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel(OpenAiModels.GPT_4O_MINI);
    chatRequestVo.setMessages(messages);

    String json = JsonUtils.toSkipNullJson(chatRequestVo);
    System.out.println("Request JSON:\n" + json);

    // Send HTTP request
    try (Response response = OpenAiClient.chatCompletions(json)) {
      if (response.isSuccessful()) {
        String responseBody = response.body().string();
        OpenAiChatResponseVo chatCompletions = JsonUtils.parse(responseBody, OpenAiChatResponseVo.class);
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

### 5. Another Simplified Example Using Roles

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
    
    // Make a chat completion request with a specific role
    ChatResponseVo chatResponse = OpenAiClient.chatCompletionsWithRole("user", "How are you?");
    System.out.println("Response:\n" + JsonUtils.toJson(chatResponse));
  }
}
```

## Examples

### Chat Example

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
    System.out.println("Request JSON:\n" + json);

    OpenAiChatResponseVo chatResponse = OpenAiClient.chatCompletions(apiKey, chatRequestVo);
    ChatResponseMessage responseMessage = chatResponse.getChoices().get(0).getMessage();
    String content = responseMessage.getContent();
    System.out.println("Response Content:\n" + content);
  }
}
```

### Chat with Image

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

#### Example 1: Generate Embedding

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

#### Example 2: Simple Embedding

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
    documents.add("Bio-Hautpflege für empfindliche Haut mit Aloe Vera und Kamille: ...");
    documents.add("Neue Make-up-Trends setzen auf kräftige Farben und innovative Techniken: ...");
    documents.add("Cuidado de la piel orgánico para piel sensible con aloe vera y manzanilla: ...");
    documents.add("Las nuevas tendencias de maquillaje se centran en colores vivos y técnicas innovadoras: ...");
    documents.add("针对敏感肌专门设计的天然有机护肤产品：体验由芦荟和洋甘菊提取物带来的自然呵护。...");
    documents.add("新的化妆趋势注重鲜艳的颜色和创新的技巧：进入化妆艺术的新纪元...");
    documents.add("敏感肌のために特別に設計された天然有機スキンケア製品: ...");
    documents.add("新しいメイクのトレンドは鮮やかな色と革新的な技術に焦点を当てています: ...");

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

### Jina Search

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

**Sample Output**:

```
[1] Title: Run Deepseek-R1 / R1 Zero
[1] URL Source: https://unsloth.ai/blog/deepseek-r1
...
```

### PARSE markdown response

A simple data class example:

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

And a hypothetical parse method:
```java
public static List<WebPageConteont> parse(String markdown) {
  // Implementation that parses a given markdown string
  // to extract relevant fields (title, url, description, etc.)
  // and returns a list of WebPageConteont objects.
}
```

### GOOGLE GEMINI

```java
package com.litongjava.gemini;

import java.util.Collections;

/**
 * Demo for Gemini
 */
public class GeminiDemo {

  public static void main(String[] args) {
    String googleApiKey = "YOUR_GOOGLE_API_KEY";
    // 1. Construct request body
    GeminiPartVo part = new GeminiPartVo("Hello, how are you?");
    GeminiContentVo content = new GeminiContentVo("user", Collections.singletonList(part));
    GeminiChatRequestVo reqVo = new GeminiChatRequestVo(Collections.singletonList(content));

    // 2. Send sync request: generateContent
    GeminiChatResponseVo respVo = GeminiClient.generate(googleApiKey, GoogleGeminiModels.GEMINI_1_5_FLASH, reqVo);
    if (respVo != null && respVo.getCandidates() != null) {
      respVo.getCandidates().forEach(candidate -> {
        if (candidate.getContent() != null && candidate.getContent().getParts() != null) {
          candidate.getContent().getParts().forEach(partVo -> {
            System.out.println("Gemini answer text: " + partVo.getText());
          });
        }
      });
    }
  }
}
```

#### Google GEMINI images

```java
package com.litongjava.llm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.litongjava.gemini.GeminiCandidateVo;
import com.litongjava.gemini.GeminiChatRequestVo;
import com.litongjava.gemini.GeminiChatResponseVo;
import com.litongjava.gemini.GeminiClient;
import com.litongjava.gemini.GeminiContentVo;
import com.litongjava.gemini.GeminiInlineDataVo;
import com.litongjava.gemini.GeminiPartVo;
import com.litongjava.gemini.GoogleGeminiModels;
import com.litongjava.tio.utils.encoder.Base64Utils;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.ContentTypeUtils;
import com.litongjava.tio.utils.hutool.FilenameUtils;

public class LlmOcrService {

  String prompt = "Convert the image to text and just output the text.";

  public String parse(byte[] data, String filename) {

    String suffix = FilenameUtils.getSuffix(filename);
    String mimeType = ContentTypeUtils.getContentType(suffix);
    String encodeImage = Base64Utils.encodeToString(data);
    String googleApiKey = EnvUtils.getStr("GEMINI_API_KEY");

    // 1. Build request body
    List<GeminiPartVo> parts = new ArrayList<>();
    parts.add(new GeminiPartVo(prompt));
    parts.add(new GeminiPartVo(new GeminiInlineDataVo(mimeType, encodeImage)));
    GeminiContentVo content = new GeminiContentVo("user", parts);
    GeminiChatRequestVo reqVo = new GeminiChatRequestVo(Collections.singletonList(content));

    // 2. Sync request: generateContent
    GeminiChatResponseVo respVo = GeminiClient.generate(googleApiKey, GoogleGeminiModels.GEMINI_2_0_FLASH_EXP, reqVo);
    if (respVo != null && respVo.getCandidates() != null) {
      GeminiCandidateVo candidate = respVo.getCandidates().get(0);
      if (candidate.getContent() != null && candidate.getContent().getParts() != null) {
        GeminiPartVo partVo = candidate.getContent().getParts().get(0);
        return partVo.getText();
      }
    }
    return null;
  }
}
```
```java
package com.litongjava.llm.service;

import java.io.File;

import org.junit.Test;

import com.litongjava.jfinal.aop.Aop;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.hutool.FileUtil;

public class LlmOcrServiceTest {

  @Test
  public void test() {
    EnvUtils.load();
    String path = "C:\\Users\\Administrator\\Pictures\\200-dpi.png";
    File file = new File(path);
    byte[] bytes = FileUtil.readBytes(file);
    String string = Aop.get(LlmOcrService.class).parse(bytes, file.getName());
    System.out.println(string);
  }
}
```

#### GOOGLE GEMINI Function Call

**Request Body JSON Example**:

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

**Java Code**:

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

    // Single tool with multiple functions
    GeminiToolVo tool = new GeminiToolVo(Arrays.asList(enableLightsFunc, setLightColorFunc, stopLightsFunc));

    // 3. tool_config
    GeminiFunctionCallingConfigVo funcCallingConfig = new GeminiFunctionCallingConfigVo();
    funcCallingConfig.setMode("ANY");
    funcCallingConfig.setAllowed_function_names(Arrays.asList("enable_lights", "set_light_color", "stop_lights"));

    GeminiToolConfigVo toolConfig = new GeminiToolConfigVo(funcCallingConfig);

    // 4. user message contents
    GeminiPartVo userPart = new GeminiPartVo("Turn off the lighting system");
    GeminiContentVo userContent = new GeminiContentVo("user", Arrays.asList(userPart));

    // 5. Assemble request
    GeminiChatRequestVo requestVo = new GeminiChatRequestVo();
    requestVo.setSystem_instruction(systemInstruction);
    requestVo.setTools(Arrays.asList(tool));
    requestVo.setTool_config(toolConfig);
    requestVo.setContents(Arrays.asList(userContent));

    // Convert to JSON
    String requestJson = JsonUtils.toJson(requestVo);
    System.out.println("==== 请求 JSON ====");
    System.out.println(requestJson);

    // 6. Send request
    GeminiChatResponseVo response = GeminiClient.generate("gemini-1.5-flash", requestVo);

    // 7. Print response
    if (response != null && response.getCandidates() != null) {
      for (GeminiCandidateVo candidate : response.getCandidates()) {
        GeminiContentResponseVo content = candidate.getContent();
        if (content == null || content.getParts() == null) continue;
        for (GeminiPartVo part : content.getParts()) {
          if (part.getFunctionCall() != null) {
            System.out.println("[Function Call Response]");
            System.out.println("Function Name: " + part.getFunctionCall().getName());
            System.out.println("Function Args: " + part.getFunctionCall().getArgs());
          } else {
            System.out.println("[Text Response]");
            System.out.println("Model Text: " + part.getText());
          }
        }
      }
    } else {
      System.out.println("No response or no candidates from Gemini.");
    }
  }
}
```

#### gemini upload file

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

**Sample Response**:

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

#### gemini ask with pdf

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

    // Suppose you have an uploaded PDF
    String mimeType = "application/pdf";
    String fileUri = "https://generativelanguage.googleapis.com/v1beta/files/4eqnhyuzvzkb";

    // 1. Construct request
    List<GeminiPartVo> parts = new ArrayList<>();
    parts.add(new GeminiPartVo("翻译成中文"));
    parts.add(new GeminiPartVo(new GeminiFileDataVo(mimeType, fileUri)));
    GeminiContentVo content = new GeminiContentVo("user", parts);
    GeminiChatRequestVo reqVo = new GeminiChatRequestVo(Collections.singletonList(content));

    // 2. Sync request
    GeminiChatResponseVo respVo = GeminiClient.generate(googleApiKey, GoogleGeminiModels.GEMINI_1_5_FLASH, reqVo);

    // 3. Print response
    if (respVo != null && respVo.getCandidates() != null) {
      respVo.getCandidates().forEach(candidate -> {
        if (candidate.getContent() != null && candidate.getContent().getParts() != null) {
          candidate.getContent().getParts().forEach(partVo -> {
            System.out.println("Gemini answer text: " + partVo.getText());
          });
        }
      });
    }
  }
}
```

#### gemini openai

In your `app.properties`:

```properties
OPENAI_API_KEY=<Gemini key here>
OPENAI_API_URL=https://generativelanguage.googleapis.com/v1beta/openai
```

Then in code:

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
    // Load OPENAI_API_KEY from configuration
    EnvUtils.load();
    String apiKey = EnvUtils.getStr("SILICONFLOW_API_KEY");

    // Create messages
    List<OpenAiChatMessage> messages = new ArrayList<>();
    messages.add(new OpenAiChatMessage("user", "How are you?"));

    // Create chat request
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel(SiliconFlowModels.DEEPSEEK_R1);
    chatRequestVo.setMessages(messages);

    String json = JsonUtils.toSkipNullJson(chatRequestVo);
    System.out.println("Request JSON:\n" + json);

    // Send HTTP request
    try (Response response = OpenAiClient.chatCompletions(SiliconFlowConsts.SELICONFLOW_API_BASE, apiKey, json)) {
      if (response.isSuccessful()) {
        String responseBody = response.body().string();
        OpenAiChatResponseVo chatCompletions = JsonUtils.parse(responseBody, OpenAiChatResponseVo.class);
        System.out.println("Response:\n" + JsonUtils.toSkipNullJson(chatCompletions));
      } else {
        System.err.println("Request failed: " + response);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```

### SiliconFlow DeepSeek Image

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
    // DEEPSEEK_R1 is text-only, so let's use DEEPSEEK_VL2 for image
    chatRequestVo.setModel(SiliconFlowModels.DEEPSEEK_VL2);
    chatRequestVo.setMax_tokens(1024).setTemperature(0.7f).setTop_p(0.7f).setTop_k(50).setFrequency_penalty(0);
    chatRequestVo.setMessages(messages);

    OpenAiChatResponseVo chatResponse =
        OpenAiClient.chatCompletions(SiliconFlowConsts.SELICONFLOW_API_BASE, apiKey, chatRequestVo);
    ChatResponseMessage responseMessage = chatResponse.getChoices().get(0).getMessage();
    String content = responseMessage.getContent();
    System.out.println("Response Content:\n" + content);
  }
}
```
## VOLCENGINE
### DEEPSEEK
```java
package llm;

import java.util.ArrayList;
import java.util.List;

import com.litongjava.openai.chat.OpenAiChatMessage;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.chat.OpenAiChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;
import com.litongjava.volcengine.VolcEngineConst;
import com.litongjava.volcengine.VolcEngineModels;

public class VolcEngineDeepSeekClient {
  public static void main(String[] args) {
    EnvUtils.load();
    String apiKey = EnvUtils.get("VOLCENGINE_API_KEY");

    List<OpenAiChatMessage> messages = new ArrayList<>();
    messages.add(new OpenAiChatMessage("system", "你是人工智能助手."));
    messages.add(new OpenAiChatMessage("user", "常见的十字花科植物有哪些？"));

    OpenAiChatRequestVo chatRequest = new OpenAiChatRequestVo();
    chatRequest.setModel(VolcEngineModels.DEEPSEEK_V3_241226);
    chatRequest.setMessages(messages);

    OpenAiChatResponseVo chatResponse = OpenAiClient.chatCompletions(VolcEngineConst.BASE_URL, apiKey, chatRequest);
    System.out.println(JsonUtils.toSkipNullJson(chatResponse));
  }
}
```
## Groq
### GroqSpeechClientTest
```java
package com.litongjava.groq;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Test;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class GroqSpeechClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    String filePath = "recording_1739969996898.wav";
    String model = "whisper-large-v3";
    String fileName = new File(filePath).getName();

    TranscriptionsRequest reqVo = new TranscriptionsRequest();
    reqVo.setModel(model).setLanguage("zh-cn");
    byte[] audioData=null;
    try {
      audioData = Files.readAllBytes(new File(filePath).toPath());
      TranscriptionsResponse transcriptions = GroqSpeechClient.transcriptions(audioData, fileName, reqVo);
      System.out.println(JsonUtils.toJson(transcriptions));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```
## License

This project is licensed under the [MIT License](LICENSE). Feel free to contribute, report issues, or submit pull requests for improvements.