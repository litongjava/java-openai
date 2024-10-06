# java-openai
## Introduction
An OpenAi client for Java language band base on okhttp and fastjson

## Chat Example
1. add dependecy to pom.xml
```xml
    <dependency>
      <groupId>com.litongjava</groupId>
      <artifactId>java-openai</artifactId>
      <version>1.0.4</version>
    </dependency>
    <dependency>
      <groupId>com.alibaba.fastjson2</groupId>
      <artifactId>fastjson2</artifactId>
      <version>2.0.52</version>
    </dependency>
```
2. add OPENAI_API_KEY to src/main/resources/app.properties
```properties
OPENAI_API_KEY=apikey from openai
```

3. do a simple test
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
    // load OPENAI_API_KEY from the config()
    EnvUtils.load();

    // messages
    List<ChatMessage> messages = new ArrayList<>();
    ChatMessage message = new ChatMessage().role("user").content("How are you");
    messages.add(message);

    // reqvo
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel(OpenAiModels.gpt_4o_mini);
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

```java
package com.litongjava.openai.example;

import com.litongjava.openai.chat.ChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class ChatCompletionsWithRoleExample {
  public static void main(String[] args) {
    // load OPENAI_API_KEY from the config()
    EnvUtils.load();
    ChatResponseVo chatCompletionsWithRole = OpenAiClient.chatCompletionsWithRole("user", "How are you");
    System.out.println(JsonUtils.toJson(chatCompletionsWithRole));
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

```java
package com.litongjava.example;

import org.junit.Test;

import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.openai.constants.OpenAiModels;
import com.litongjava.tio.utils.environment.EnvUtils;

public class SimpleEmbeddingExample {

  @Test
  public void embedding() {
    EnvUtils.load();
    String text = "Hi";
    float[] embeddingArray = OpenAiClient.embeddingArray(text, OpenAiModels.text_embedding_3_large);
    System.out.println(embeddingArray.length);
    //3072
  }
}

```
## llama
```
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
  public void test01() {
    EnvUtils.load();
    List<ChatMessage> messages = new ArrayList<>();
    messages.add(new ChatMessage("user", "How are you"));

    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo()
        // .setModel(PerplexityModles.llama_3_sonar_large_32k_online)
        .setModel(LlamaModels.llama3_1_8b).setMessages(messages);

    String apiKey = EnvUtils.get("LLAMA_API_KEY");
    chatRequestVo.setMax_tokens(3000);

    ChatResponseVo chatCompletions = OpenAiClient.chatCompletions(LlamaConstants.server_url, apiKey, chatRequestVo);
    String content = chatCompletions.getChoices().get(0).getMessage().getContent();
    System.out.println(content);
  }

}
```

## Perplexity
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

public class Perplexity_Test {

  @Test
  public void test01() {
    EnvUtils.load();
    List<ChatMessage> messages = new ArrayList<>();
    messages.add(new ChatMessage("user", "How are you"));

    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo()
        // .setModel(PerplexityModles.llama_3_sonar_large_32k_online)
        .setModel(PerplexityModels.llama_3_1_sonar_small_128k_online).setMessages(messages);

    String apiKey = EnvUtils.get("PERPLEXITY_API_KEY");
    chatRequestVo.setMax_tokens(3000);

    ChatResponseVo chatCompletions = OpenAiClient.chatCompletions(PerplexityConstants.server_url, apiKey, chatRequestVo);
    String content = chatCompletions.getChoices().get(0).getMessage().getContent();
    System.out.println(content);
  }

}

```

## JinaRerank
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
  public void test() {
    EnvUtils.load();
    List<String> documents = new ArrayList<>();
    documents.add(
        "Bio-Hautpflege für empfindliche Haut mit Aloe Vera und Kamille: Erleben Sie die wohltuende Wirkung unserer Bio-Hautpflege, speziell für empfindliche Haut entwickelt. Mit den beruhigenden Eigenschaften von Aloe Vera und Kamille pflegen und schützen unsere Produkte Ihre Haut auf natürliche Weise. Verabschieden Sie sich von Hautirritationen und genießen Sie einen strahlenden Teint.");
    documents.add(
        "Neue Make-up-Trends setzen auf kräftige Farben und innovative Techniken: Tauchen Sie ein in die Welt der modernen Schönheit mit den neuesten Make-up-Trends. Kräftige, lebendige Farben und innovative Techniken setzen neue Maßstäbe. Von auffälligen Eyelinern bis hin zu holografischen Highlightern – lassen Sie Ihrer Kreativität freien Lauf und setzen Sie jedes Mal ein Statement.");
    documents.add(
        "Cuidado de la piel orgánico para piel sensible con aloe vera y manzanilla: Descubre el poder de la naturaleza con nuestra línea de cuidado de la piel orgánico, diseñada especialmente para pieles sensibles. Enriquecidos con aloe vera y manzanilla, estos productos ofrecen una hidratación y protección suave. Despídete de las irritaciones y saluda a una piel radiante y saludable.");
    documents.add(
        "Las nuevas tendencias de maquillaje se centran en colores vivos y técnicas innovadoras: Entra en el fascinante mundo del maquillaje con las tendencias más actuales. Colores vivos y técnicas innovadoras están revolucionando el arte del maquillaje. Desde delineadores neón hasta iluminadores holográficos, desata tu creatividad y destaca en cada look.");
    documents.add("针对敏感肌专门设计的天然有机护肤产品：体验由芦荟和洋甘菊提取物带来的自然呵护。我们的护肤产品特别为敏感肌设计，温和滋润，保护您的肌肤不受刺激。让您的肌肤告别不适，迎来健康光彩。");
    documents.add("新的化妆趋势注重鲜艳的颜色和创新的技巧：进入化妆艺术的新纪元，本季的化妆趋势以大胆的颜色和创新的技巧为主。无论是霓虹眼线还是全息高光，每一款妆容都能让您脱颖而出，展现独特魅力。");
    documents.add("敏感肌のために特別に設計された天然有機スキンケア製品: アロエベラとカモミールのやさしい力で、自然の抱擁を感じてください。敏感肌用に特別に設計された私たちのスキンケア製品は、肌に優しく栄養を与え、保護します。肌トラブルにさようなら、輝く健康な肌にこんにちは。");
    documents.add("新しいメイクのトレンドは鮮やかな色と革新的な技術に焦点を当てています: 今シーズンのメイクアップトレンドは、大胆な色彩と革新的な技術に注目しています。ネオンアイライナーからホログラフィックハイライターまで、クリエイティビティを解き放ち、毎回ユニークなルックを演出しましょう。");

    RerankReqVo reqVo = new RerankReqVo();
    reqVo.setModel(JinaModel.jina_reranker_v2_base_multilingual);
    reqVo.setQuery("Organic skincare products for sensitive skin");
    reqVo.setTop_n(3);
    reqVo.setDocuments(documents);

    RerankRespVo respVo = JinaRerankClient.rerank(reqVo);
    System.out.println(JsonUtils.toJson(respVo));
  }
}
```

output
```json
{
  "model": "jina-reranker-v2-base-multilingual",
  "usage":
  {
    "prompt_tokens": 652,
    "total_tokens": 652,
    "completion_tokens": null
  },
  "results":
  [
    {
      "index": 4,
      "document":
      {
        "text": "针对敏感肌专门设计的天然有机护肤产品：体验由芦荟和洋甘菊提取物带来的自然呵护。我们的护肤产品特别为敏感肌设计，温和滋润，保护您的肌肤不受刺激。让您的肌肤告别不适，迎来健康光彩。"
      },
      "relevance_score": 0.8783142566680908
    },
    {
      "index": 2,
      "document":
      {
        "text": "Cuidado de la piel orgánico para piel sensible con aloe vera y manzanilla: Descubre el poder de la naturaleza con nuestra línea de cuidado de la piel orgánico, diseñada especialmente para pieles sensibles. Enriquecidos con aloe vera y manzanilla, estos productos ofrecen una hidratación y protección suave. Despídete de las irritaciones y saluda a una piel radiante y saludable."
      },
      "relevance_score": 0.8633915781974792
    },
    {
      "index": 6,
      "document":
      {
        "text": "敏感肌のために特別に設計された天然有機スキンケア製品: アロエベラとカモミールのやさしい力で、自然の抱擁を感じてください。敏感肌用に特別に設計された私たちのスキンケア製品は、肌に優しく栄養を与え、保護します。肌トラブルにさようなら、輝く健康な肌にこんにちは。"
      },
      "relevance_score": 0.7786493301391602
    }
  ]
}
```