# Java OpenAI Client

## Introduction

**Java OpenAI** is a robust client library for integrating OpenAI services into Java applications. Built on top of [OkHttp](https://square.github.io/okhttp/) and [FastJSON](https://github.com/alibaba/fastjson), it provides a seamless, efficient way to interact with OpenAI's APIs—enabling features such as chat completions, image processing, embeddings, and more. In addition to OpenAI, the library supports integration with Gemini, Jina, Textin, DeepSeek, and other services.

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
    - [Whisper Transcription](#whisper-transcription)
    - [Embedding](#embedding)
      - [Example 1: Generate Embedding](#example-1-generate-embedding)
      - [Example 2: Simple Embedding](#example-2-simple-embedding)
    - [Llama Integration](#llama-integration)
    - [Perplexity Integration](#perplexity-integration)
    - [Jina Rerank](#jina-rerank)
    - [Jina Search](#jina-search)
    - [Parse Markdown Response](#parse-markdown-response)
  - [GOOGLE GEMINI](#google-gemini)
    - [GOOGLE GEMINI Text](#google-gemini-text)
    - [Google GEMINI Images](#google-gemini-images)
    - [GOOGLE GEMINI Function Call](#google-gemini-function-call)
    - [Gemini Upload File](#gemini-upload-file)
    - [Gemini Ask with PDF](#gemini-ask-with-pdf)
    - [Generate Image](#generate-image)
    - [Gemini OpenAI](#gemini-openai)
  - [deepseek-openai](#deepseek-openai)
  - [SiliconFlow](#siliconflow)
    - [SiliconFlow DeepSeek](#siliconflow-deepseek)
    - [SiliconFlow DeepSeek Image](#siliconflow-deepseek-image)
  - [Additional Integrations](#additional-integrations)
    - [VOLCENGINE: DEEPSEEK](#volcengine-deepseek)
    - [Groq Integration](#groq-integration)
      - [GroqSpeechClientTest](#groqspeechclienttest)
    - [ApiFy](#apify)
      - [LinkedIn Profile Scraper](#linkedin-profile-scraper)
  - [Google Custom Search JSON API](#google-custom-search-json-api)
    - [Example Code](#example-code)
    - [Explanation](#explanation)
  - [SearchAPI](#searchapi)
    - [Google Search](#google-search)
  - [Supadata.ai](#supadataai)
    - [YouTube Subtitle](#youtube-subtitle)
  - [Fish audio](#fish-audio)
  - [License](#license)

## Features

- **Chat Completions**: Interact effortlessly with OpenAI's chat models.
- **Image Processing**: Convert images to text or other formats.
- **Embeddings**: Generate text embeddings for various applications.
- **Tool Integration**: Enhance functionality by integrating with external tools.
- **Support for Additional Models**: Includes support for Llama, Perplexity, Gemini, Jina, DeepSeek, SiliconFlow, VOLCENGINE, Groq, ApiFy, SearchAPI, and Supadata.ai.
- **Whisper Transcriptions**: Transcribe audio using Whisper.
- **Easy Configuration**: Simplified setup using configuration files.

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

Other service keys (if applicable) should be added similarly (e.g., `GEMINI_API_KEY`, `DEEPSEEK_API_KEY`, `LLAMA_API_KEY`, etc.).

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
    String example = PromptEngine.renderToString("professor_emojis_introduction_weaknesses_strengths_prompt_example.txt");
    messages.add(new OpenAiChatMessage("system", prompt));
    messages.add(new OpenAiChatMessage("user", example));

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

**professor_emojis_introduction_weaknesses_strengths_prompt.txt**

```txt
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
    "strengths": "**Strength_name_1**:strength_description_1\n**Strength_name_2**:strength_description_2\n..."
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

---

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

### Whisper Transcription

```java
package com.litongjava.client;

import java.io.File;

import org.junit.Test;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.openai.whisper.WhisperClient;
import com.litongjava.openai.whisper.WhisperResponseFormat;
import com.litongjava.tio.utils.environment.EnvUtils;

public class WhisperClientTest {
  @Test
  public void transcriptions() {
    EnvUtils.load();
    String filename = "path/to/your/audio.mp3";
    File file = new File(filename);
    ResponseVo responseVo = WhisperClient.transcriptions(file, WhisperResponseFormat.srt);
    System.out.println(responseVo.getBodyString());
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
    documents.add("Natural organic skincare products for sensitive skin: Experience the gentle care of aloe vera and chamomile extracts...");
    documents.add("New makeup trends focus on vivid colors and innovative techniques: A new era in the art of makeup...");
    documents.add("天然有機護膚產品，專為敏感肌設計：感受蘆薈與洋甘菊提取物的溫柔呵護...");
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

### Parse Markdown Response

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
public class WebPageContent {
  private String title;
  private String url;
  private String description;
  private String content;
}
```

And a hypothetical parse method:

```java
public static List<WebPageContent> parse(String markdown) {
  // Implementation that parses a given markdown string
  // to extract relevant fields (title, url, description, etc.)
  // and returns a list of WebPageContent objects.
}
```

## GOOGLE GEMINI

### GOOGLE GEMINI Text

```java
package com.litongjava.gemini;

import java.util.Collections;
import java.util.List;

import com.litongjava.tio.utils.environment.EnvUtils;

/**
 * Demo for Gemini
 */
public class GeminiDemo {

  public static void main(String[] args) {
    EnvUtils.load();
    String googleApiKey = EnvUtils.getStr("GEMINI_API_KEY");

    // 1. Construct request body
    GeminiPartVo part = new GeminiPartVo("Hello, how are you?");
    GeminiContentVo content = new GeminiContentVo("user", Collections.singletonList(part));
    GeminiChatRequestVo reqVo = new GeminiChatRequestVo(Collections.singletonList(content));

    // 2. Send sync request: generateContent
    GeminiChatResponseVo respVo = GeminiClient.generate(googleApiKey, GoogleGeminiModels.GEMINI_1_5_FLASH, reqVo);

    if (respVo != null) {
      List<GeminiCandidateVo> candidates = respVo.getCandidates();
      GeminiCandidateVo candidate = candidates.get(0);
      List<GeminiPartVo> parts = candidate.getContent().getParts();
      if (candidate != null && candidate.getContent() != null && parts != null) {
        String text = parts.get(0).getText();
        System.out.println("Gemini answer text: " + text);
      }
    }
  }
}
```

### Google GEMINI Images

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
    String result = Aop.get(LlmOcrService.class).parse(bytes, file.getName());
    System.out.println(result);
  }
}
```

```json
{
  "contents":
  [
    {
      "parts":
      [
        {
          "text": "text"
        },
        {
          "inlineData":
          {
            "data":"base64Code",
            "mimeType": "image/png"
          }
        },
        {
          "inlineData":
          {
            "data": "base64Code",
            "mimeType": "image/png"
          }
        }
      ],
      "role": "user"
    }
  ],
  "generationConfig":
  {
    "temperature": 0.0
  },
  "system_instruction":
  {
    "parts":
    [
      {
        "text": "Answer the question using the format below:\r\n**Answer**:\r\n{{correct_answer}}\r\n**Steps**:\r\n{{key steps}}\r\n\r\nOutput using the language of the en."
      }
    ]
  }
}
```

### GOOGLE GEMINI Function Call

**Request Body JSON Example:**

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
            "required": ["dummy"]
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
            "required": ["rgb_hex"]
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
            "required": ["dummy"]
          }
        }
      ]
    }
  ],
  "tool_config": {
    "function_calling_config": {
      "mode": "ANY",
      "allowed_function_names": ["enable_lights", "set_light_color", "stop_lights"]
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

**Java Code:**

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
        new GeminiPartVo("You are a helpful lighting system bot. You can turn lights on and off, and you can set the color. Do not perform any other tasks."));

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
    System.out.println("==== Request JSON ====");
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

### Gemini Upload File

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

**Sample Response:**

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

### Gemini Ask with PDF

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

    // Suppose you have an uploaded PDF file
    String mimeType = "application/pdf";
    String fileUri = "https://generativelanguage.googleapis.com/v1beta/files/4eqnhyuzvzkb";

    // 1. Construct request
    List<GeminiPartVo> parts = new ArrayList<>();
    parts.add(new GeminiPartVo("Translate to Chinese"));
    parts.add(new GeminiPartVo(new GeminiFileDataVo(mimeType, fileUri)));
    GeminiContentVo content = new GeminiContentVo("user", parts);
    GeminiChatRequestVo reqVo = new GeminiChatRequestVo(Collections.singletonList(content));

    // 2. Send sync request
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
### Generate Image
```java
package com.litongjava.manim.services;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.Kv;
import com.litongjava.gemini.GeminiChatRequestVo;
import com.litongjava.gemini.GeminiChatResponseVo;
import com.litongjava.gemini.GeminiClient;
import com.litongjava.gemini.GeminiGenerationConfigVo;
import com.litongjava.gemini.GeminiPartVo;
import com.litongjava.gemini.GoogleGeminiModels;
import com.litongjava.jfinal.aop.Aop;
import com.litongjava.openai.chat.ChatMessage;
import com.litongjava.template.PromptEngine;
import com.litongjava.tio.boot.admin.services.AwsS3StorageService;
import com.litongjava.tio.boot.admin.utils.AwsS3Utils;
import com.litongjava.tio.boot.admin.vo.UploadResultVo;
import com.litongjava.tio.http.common.UploadFile;
import com.litongjava.tio.utils.encoder.Base64Utils;
import com.litongjava.tio.utils.snowflake.SnowflakeIdUtils;

public class ImageGenerateService {

  public String generate(String topic) {
    String userMessage = PromptEngine.renderToString("image_generate_prompt.txt", Kv.by("topic", topic));

    List<ChatMessage> messages = new ArrayList<>();
    messages.add(new ChatMessage(userMessage));

    GeminiChatRequestVo geminiChatRequestVo = new GeminiChatRequestVo();
    geminiChatRequestVo.setChatMessages(messages);

    GeminiGenerationConfigVo config = new GeminiGenerationConfigVo();
    config.setResponseMimeType("text/plain");
    List<String> responseModalities = new ArrayList<>();
    responseModalities.add("image");
    responseModalities.add("text");
    config.setResponseModalities(responseModalities);

    geminiChatRequestVo.setGenerationConfig(config);

    GeminiChatResponseVo responseVo = GeminiClient.generate(GoogleGeminiModels.GEMINI_2_0_FLASH_EXP_IMAGE_GENERATION, geminiChatRequestVo);
    GeminiPartVo geminiPartVo = responseVo.getCandidates().get(0).getContent().getParts().get(0);
    String data = geminiPartVo.getInlineData().getData();
    byte[] decodeToBytes = Base64Utils.decodeToBytes(data);
    UploadFile uploadFile = new UploadFile(SnowflakeIdUtils.id() + ".png", decodeToBytes);

    UploadResultVo uploadResultVo = Aop.get(AwsS3StorageService.class).uploadFile("video/cover", uploadFile);
    String url = uploadResultVo.getUrl();
    return url;
  }
}

```
```java
package com.litongjava.manim.services;

import org.junit.Test;

import com.litongjava.jfinal.aop.Aop;
import com.litongjava.manim.config.AdminAppConfig;
import com.litongjava.tio.boot.testing.TioBootTest;
import com.litongjava.tio.utils.environment.EnvUtils;

public class ImageGenerateServiceTest {

  @Test
  public void test() {
    EnvUtils.load();
    TioBootTest.runWith(AdminAppConfig.class);
    String url = Aop.get(ImageGenerateService.class).generate("How Does ChatGPT Work?");
    System.out.println(url);
  }
}

```
### Gemini OpenAI

In your `app.properties` add:

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
## Claude
### Image
```java
package com.litongjava.manim.services;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.chat.ChatFile;
import com.litongjava.chat.ChatMessage;
import com.litongjava.claude.ClaudeClient;
import com.litongjava.exception.GenerateException;
import com.litongjava.gemini.GeminiClient;
import com.litongjava.jfinal.aop.Aop;
import com.litongjava.manim.config.AdminAppConfig;
import com.litongjava.tio.boot.admin.services.AwsS3StorageService;
import com.litongjava.tio.boot.admin.vo.UploadResultVo;
import com.litongjava.tio.boot.testing.TioBootTest;
import com.litongjava.tio.utils.encoder.Base64Utils;
import com.litongjava.tio.utils.http.ContentTypeUtils;
import com.litongjava.tio.utils.http.HttpDownloadUtils;
import com.litongjava.tio.utils.hutool.FilenameUtils;

public class AnswerServiceTest {

  @Test
  public void testWithClaude() {
    TioBootTest.runWith(AdminAppConfig.class);
    String string = "识别文件内容";
    List<Long> imageIds = new ArrayList<>();
    imageIds.add(513563844319756288L);
    imageIds.add(513561326562951168L);

    List<ChatFile> files = new ArrayList<>(imageIds.size());
    for (Long id : imageIds) {
      UploadResultVo resultVo = Aop.get(AwsS3StorageService.class).getUrlById(id);
      String url = resultVo.getUrl();
      //files.add(ChatFile.url());
      String suffix = FilenameUtils.getSuffix(url);
      String mimeType = ContentTypeUtils.getContentType(suffix);
      ByteArrayOutputStream download = HttpDownloadUtils.download(url, null);
      byte[] data = download.toByteArray();
      String encodeImage = Base64Utils.encodeToString(data);
      ChatFile base64 = ChatFile.base64(mimeType, encodeImage);
      files.add(base64);
    }

    ClaudeClient.debug = true;
    ChatMessage chatMessage = new ChatMessage(string);
    chatMessage.setFiles(files);
    try {
      String answer = Aop.get(AnswerService.class).genAnswer(chatMessage, "en");
      System.out.println(answer);
    } catch (GenerateException e) {
      e.printStackTrace();
      String responseBody = e.getResponseBody();
      String requestJson = e.getRequestJson();
      System.out.println(requestJson);
      System.out.println(responseBody);
    }
  }
}
```
```java
package com.litongjava.manim.services;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.Kv;
import com.litongjava.chat.ChatMessage;
import com.litongjava.chat.UniChatClient;
import com.litongjava.chat.UniChatRequest;
import com.litongjava.chat.UniChatResponse;
import com.litongjava.claude.ClaudeModels;
import com.litongjava.consts.AiProviderName;
import com.litongjava.jfinal.aop.Aop;
import com.litongjava.template.PromptEngine;

public class AnswerService {

  public String genAnswer(ChatMessage topic, String language) {
    String apiKey = Aop.get(ApiKeyRotator.class).getNextKey();
    String systemPrompt = PromptEngine.renderToString("gen_answer.txt", Kv.by("language", language));

    List<ChatMessage> messages = new ArrayList<>();
    messages.add(topic);

    UniChatRequest uniChatRequest = new UniChatRequest(messages);
    uniChatRequest.setTemperature(0.0f);
    uniChatRequest.setProvider(AiProviderName.CLAUDE);
    uniChatRequest.setModel(ClaudeModels.CLAUDE_3_7_SONNET_20250219);
    uniChatRequest.setSystemPrompt(systemPrompt).setUseSystemPrompt(true);
    UniChatResponse response = UniChatClient.generate(apiKey, uniChatRequest);
    return response.getMessage().getContent();
  }
}
```
```json
{
    "temperature": 0.0,
    "messages":
    [
        {
            "content":
            [
                {
                    "type": "image",
                    "source":
                    {
                        "type": "base64",
                        "data": "base64Code",
                        "media_type": "image/png"
                    }
                },
                {
                    "type": "image",
                    "source":
                    {
                        "type": "base64",
                        "data": "Base64Code",
                        "media_type": "image/png"
                    }
                },
                {
                    "type": "text",
                    "text": "识别文件内容"
                }
            ],
            "role": "user"
        }
    ],
    "system":
    [
        {
            "type": "text",
            "text": "Answer the question using the format below:\r\n**Answer**:\r\n{{correct_answer}}\r\n**Steps**:\r\n{{key steps}}\r\n\r\nOutput using the language of the en."
        }
    ],
    "max_tokens": 64000,
    "model": "claude-3-7-sonnet-20250219"
}
```

### Cache
enable file cache
```java
ChatFile chatFile = ChatFile.base64(mimeType, encodeImage);
chatFile.setCached(true);
```
## deepseek-openai

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

## SiliconFlow
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
    // DEEPSEEK_R1 is a text-only model, so use DEEPSEEK_VL2 for image processing
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

---
## VOLCENGINE

### VOLCENGINE: DEEPSEEK

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
    messages.add(new OpenAiChatMessage("system", "You are an AI assistant."));
    messages.add(new OpenAiChatMessage("user", "What are the common cruciferous vegetables?"));

    OpenAiChatRequestVo chatRequest = new OpenAiChatRequestVo();
    chatRequest.setModel(VolcEngineModels.DEEPSEEK_V3_241226);
    chatRequest.setMessages(messages);

    OpenAiChatResponseVo chatResponse = OpenAiClient.chatCompletions(VolcEngineConst.BASE_URL, apiKey, chatRequest);
    System.out.println(JsonUtils.toSkipNullJson(chatResponse));
  }
}
```
## OpenRouter
```java
package com.litongjava.manim.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.chat.ChatMessage;
import com.litongjava.chat.UniChatClient;
import com.litongjava.chat.UniChatRequest;
import com.litongjava.chat.UniChatResponse;
import com.litongjava.consts.AiProviderName;
import com.litongjava.exception.GenerateException;
import com.litongjava.openrouter.OpenRouterModels;
import com.litongjava.tio.utils.environment.EnvUtils;

public class OpenRouterTest {

  @Test
  public void test() {
    EnvUtils.load();
    String apiKey = EnvUtils.getStr("OPENROUTER_API_KEY");
    String systemPrompt = "请回答问题";

    List<ChatMessage> messages = new ArrayList<>();
    messages.add(new ChatMessage("什么是勾股定理"));

    UniChatRequest uniChatRequest = new UniChatRequest(messages);
    uniChatRequest.setTemperature(0.0f);
    uniChatRequest.setProvider(AiProviderName.OPENROUTER);
    uniChatRequest.setModel(OpenRouterModels.DEEPSEEK_R1_250528);
    uniChatRequest.setSystemPrompt(systemPrompt).setUseSystemPrompt(true);
    try {
      UniChatResponse response = UniChatClient.generate(apiKey, uniChatRequest);
      String content = response.getMessage().getContent();
      System.out.println(content);
    }catch (GenerateException e) {
      String responseBody = e.getResponseBody();
      System.out.println(responseBody);
    }
    
  }
}
```

## Aliyun Bailian

### Text
```java
package com.litongjava.manim.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.bailian.BaiLianAiModels;
import com.litongjava.chat.ChatMessage;
import com.litongjava.chat.UniChatClient;
import com.litongjava.chat.UniChatRequest;
import com.litongjava.chat.UniChatResponse;
import com.litongjava.consts.AiProviderName;
import com.litongjava.exception.GenerateException;
import com.litongjava.tio.utils.environment.EnvUtils;

public class AliyunBailianTest {

  @Test
  public void test() {
    EnvUtils.load();
    String apiKey = EnvUtils.getStr("BAILIAN_API_KEY");
    String systemPrompt = "请回答问题";

    List<ChatMessage> messages = new ArrayList<>();
    messages.add(new ChatMessage("什么是勾股定理"));

    UniChatRequest uniChatRequest = new UniChatRequest(messages);
    uniChatRequest.setTemperature(0.0f);
    uniChatRequest.setProvider(AiProviderName.BAILIAN);
    uniChatRequest.setModel(BaiLianAiModels.QWEN3_235B_A22B);
    uniChatRequest.setSystemPrompt(systemPrompt).setUseSystemPrompt(true);

    try {
      UniChatResponse response = UniChatClient.generate(apiKey, uniChatRequest);
      String content = response.getMessage().getContent();
      System.out.println(content);
    } catch (GenerateException e) {
      String responseBody = e.getResponseBody();
      System.out.println(responseBody);
    }

  }
}
```
### Image
```java
package com.litongjava.manim.services;

import java.io.File;

import org.junit.Test;

import com.litongjava.jfinal.aop.Aop;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.hutool.FileUtil;

public class LlmQwenOcrServiceTest {

  @Test
  public void test() {
    EnvUtils.load();
    String path = "C:\\Users\\Administrator\\Pictures\\200-dpi.png";
    File file = new File(path);
    byte[] bytes = FileUtil.readBytes(file);
    OpenAiClient.debug = true;
    Aop.get(LlmQwenOcrService.class).ocr(bytes, file.getName());
  }
}
```

```java
package com.litongjava.manim.services;

import java.util.ArrayList;
import java.util.List;

import com.litongjava.bailian.BaiLianAiModels;
import com.litongjava.chat.ChatFile;
import com.litongjava.chat.ChatMessage;
import com.litongjava.chat.UniChatClient;
import com.litongjava.chat.UniChatRequest;
import com.litongjava.chat.UniChatResponse;
import com.litongjava.consts.AiProviderName;
import com.litongjava.exception.GenerateException;
import com.litongjava.manim.utils.AlarmUtils;
import com.litongjava.template.PromptEngine;
import com.litongjava.tio.utils.encoder.Base64Utils;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.ContentTypeUtils;
import com.litongjava.tio.utils.hutool.FilenameUtils;

public class LlmQwenOcrService {

  public String ocr(byte[] data, String filename) {
    String prompt = PromptEngine.renderToString("ocr_prompt.txt");

    String suffix = FilenameUtils.getSuffix(filename);
    String mimeType = ContentTypeUtils.getContentType(suffix.toLowerCase());
    String encodeImage = Base64Utils.encodeImage(data, mimeType );

    //api key
    String apiKey = EnvUtils.getStr("BAILIAN_API_KEY");

    // 1. Build request body
    ChatMessage chatMessage = new ChatMessage();

    ChatFile chatFile = ChatFile.base64(mimeType, encodeImage);
    List<ChatFile> chatFiles = new ArrayList<>();
    chatFiles.add(chatFile);
    chatMessage.setFiles(chatFiles);

    List<ChatMessage> messages = new ArrayList<>();
    messages.add(new ChatMessage("system", prompt));
    messages.add(chatMessage);
    UniChatRequest uniChatRequest = new UniChatRequest(messages);
    uniChatRequest.setApiKey(apiKey).setProvider(AiProviderName.BAILIAN).setModel(BaiLianAiModels.QWEN_VL_MAX);

    // 2. generateContent
    try {
      UniChatResponse generated = UniChatClient.generate(uniChatRequest);
      return generated.getMessage().getContent();
    } catch (GenerateException e) {
      e.printStackTrace();
      String responseBody = e.getResponseBody();
      System.out.println(responseBody);
      String name = "tio-boot";
      String warningName = "LlmOcrService Failed to ocr file:" + filename;
      AlarmUtils.sendExcpetion(name, warningName, responseBody, e);
    }

    return null;
  }
}
```
## Additional Integrations
### Groq Integration

#### GroqSpeechClientTest

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
    byte[] audioData = null;
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

### ApiFy

#### LinkedIn Profile Scraper

```java
package com.litongjava.client;

import org.junit.Test;

import com.litongjava.apify.ApiFyClient;
import com.litongjava.apify.ApiFyLinkedProfileReqVo;
import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;

public class ApiFyClientTest {

  @Test
  public void testLinkedIn() {
    // Load APIFY_API_KEY
    EnvUtils.load();
    ApiFyLinkedProfileReqVo reqVo = new ApiFyLinkedProfileReqVo("https://www.linkedin.com/in/nicolaushilleary");
    ResponseVo response = ApiFyClient.linkedinProfileScraper(reqVo);
    System.out.println(response.getBodyString());
  }
}
```

Below is the translated version of the provided content:

---

## Google Custom Search JSON API

### Example Code

```java
package com.litongjava.perplexica.services;

import com.litongjava.google.search.GoogleCustomSearchClient;
import com.litongjava.google.search.GoogleCustomSearchResponse;
import com.litongjava.tio.utils.environment.EnvUtils;

public class GoogleCustomSearchService {

  /**
   * Invokes the Google Custom Search JSON API to search for specified content.
   *
   * @param ctx   The search engine ID (CSE_ID).
   * @param text  The search keyword.
   * @return      The encapsulated search result object.
   */
  public GoogleCustomSearchResponse search(String ctx, String text) {
    // Retrieve the API Key from the environment variables.
    String key = EnvUtils.getStr("GOOGLE_API_KEY");
    // Call the wrapped search client to execute the search request and return the response.
    return GoogleCustomSearchClient.search(key, ctx, text);
  }
}
```

---

### Explanation

In the example code above, we define a class called `GoogleCustomSearchService`. Its `search` method takes two parameters:

- **ctx**: Represents the search engine ID (CSE_ID).
- **text**: Represents the search keyword.

Inside the method, it first retrieves the API Key stored in the environment variable with the key "GOOGLE_API_KEY" using `EnvUtils.getStr`. Then it calls the `GoogleCustomSearchClient.search` method to initiate the search request and returns a `GoogleCustomSearchResponse` object as the search result.

## SearchAPI

### Google Search

```java
package com.litongjava.client;

import org.junit.Test;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.searchapi.SearchapiClient;
import com.litongjava.searchapi.SearchapiResult;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.FastJson2Utils;

public class SearchapiClientTest {
  @Test
  public void search() {
    // SEARCHAPI_API_KEY
    EnvUtils.load();
    ResponseVo responseVo = SearchapiClient.search("KaiZhao at SJSU");
    String bodyString = responseVo.getBodyString();
    if (responseVo.isOk()) {
      SearchapiResult result = FastJson2Utils.parse(bodyString, SearchapiResult.class);
      System.out.println(result);
    } else {
      System.out.println(bodyString);
    }
  }
}
```

## Supadata.ai

### YouTube Subtitle

```java
package com.litongjava.client;

import java.util.List;

import org.junit.Test;

import com.litongjava.supadata.SubTitleContent;
import com.litongjava.supadata.SubTitleResponse;
import com.litongjava.supadata.SupadataClient;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.video.VideoTimeUtils;

public class SupadataClientTest {

  @Test
  public void getSubtitleTest() {
    // Example: You can use a YouTube video id
    String id = "31FpW6CMmYE";
    // Load SUPADATA_API_KEY
    EnvUtils.load();
    SubTitleResponse subTitle = SupadataClient.getSubTitle(id);
    List<SubTitleContent> content = subTitle.getContent();
    StringBuffer stringBuffer = new StringBuffer();
    for (SubTitleContent subTitleContent : content) {
      long offset = subTitleContent.getOffset();
      long duration = subTitleContent.getDuration();
      // Calculate end time = start time + duration
      long endTime = offset + duration;
      String startStr = VideoTimeUtils.formatTime(offset);
      String endStr = VideoTimeUtils.formatTime(endTime);
      String text = subTitleContent.getText();
      stringBuffer.append(startStr).append("-").append(endStr).append(" ").append(text).append("\r\n");
    }
    System.out.println(stringBuffer.toString());
  }
}
```

---

## Fish audio

add key to .env

```
FISHAUDIO_API_KEY
```

```java
package com.litongjava.manim.services;

import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.litongjava.fishaudio.tts.FishAudioClient;
import com.litongjava.fishaudio.tts.FishAudioTTSRequestVo;
import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;

public class FishAudioClientTest {

  @Test
  public void fishAudioTest() {
    EnvUtils.load();
    ResponseVo responseVo = FishAudioClient.speech("今天天气怎么样");
    if (responseVo.isOk()) {
      byte[] audioBytes = responseVo.getBodyBytes();
      try (FileOutputStream fos = new FileOutputStream("output.mp3")) {
        fos.write(audioBytes);
        System.out.println("MP3 文件已保存到 output.mp3");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.err.println("请求失败：" + responseVo);
    }
  }

  @Test
  public void testWithReferenceId() {
    EnvUtils.load();
    // 构造请求对象，并指定参考语音ID（发音人）
    FishAudioTTSRequestVo vo = new FishAudioTTSRequestVo();
    vo.setText("今天天气怎么样");
    vo.setReference_id("03397b4c4be74759b72533b663fbd001");

    // 其它参数保持默认或根据需要进行设置，例如：
    vo.setChunk_length(200);
    vo.setFormat("mp3");
    vo.setMp3_bitrate(128);
    // 如果有需要使用参考音频（in-context learning），也可以通过 vo.setReferences(...) 传入对应参考语音数据

    // 使用 FishAudioClient 发起请求
    ResponseVo responseVo = FishAudioClient.speech(vo);
    if (responseVo.isOk()) {
      // 处理返回的音频数据，例如保存到文件
      byte[] audioBytes = responseVo.getBodyBytes();
      try (FileOutputStream fos = new FileOutputStream("output.mp3")) {
        fos.write(audioBytes);
        System.out.println("MP3 文件已保存到 output.mp3");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.err.println("请求失败：" + responseVo.getBodyString());
    }

  }
}
```
## MiniMax
### TTS 
```java
package com.litongjava.minimax;

import java.io.File;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.hutool.FileUtil;

public class MiniMaxHttpClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    //MINIMAX_GROUP_ID and MINIMAX_API_KEY
    // English_expressive_narrator and Chinese_Mandarin_Gentleman
    MiniMaxTTSResponse speech = MiniMaxHttpClient.speech("How are you", MiniMaxVoice.English_expressive_narrator);
    System.out.println(speech);
    String audio = speech.getData().getAudio();
    byte[] decodeToBytes;
    try {
      decodeToBytes = Hex.decodeHex(audio);
      FileUtil.writeBytes(decodeToBytes, new File("how_are_you.mp3"));
    } catch (DecoderException e) {
      e.printStackTrace();
    }
    
  }
}

```
## License

This project is licensed under the [MIT License](LICENSE). Feel free to contribute, report issues, or submit pull requests for improvements.
