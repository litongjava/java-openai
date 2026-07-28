# Gitee Client

`GiteeClient` wraps Gitee AI document parsing, task polling, model listing, and audio
transcription helpers.

## Configuration

Load `app.properties` or environment variables before calling the client:

```properties
GITEE_API_KEY=your-gitee-api-key

# Optional. Used by OpenAI-compatible endpoints such as /v1/models.
GITEE_API_URL=https://ai.gitee.com/v1
```

`new GiteeClient()` reads `GITEE_API_KEY` and defaults the base URL to
`https://ai.gitee.com`.

## Read A Parsed Task As Markdown

This example follows `com.litongjava.gitee.GiteeClientTest.testMarkdown()`:

```java
import nexus.io.gitee.GiteeClient;
import nexus.io.gitee.GiteeDocumentOutput;
import nexus.io.gitee.GiteeSimpleMarkdownUtils;
import nexus.io.gitee.GiteeTaskResponse;
import nexus.io.tio.utils.environment.EnvUtils;

public class GiteeMarkdownExample {
  public static void main(String[] args) {
    EnvUtils.load();

    GiteeClient client = new GiteeClient();
    GiteeTaskResponse task = client.getTask("X4IC3C9Q88N65OCWEAI0M1YWFPUCHQZ9");

    GiteeDocumentOutput output = task.getOutput();
    String markdown = GiteeSimpleMarkdownUtils.toMarkdown(output, null);
    System.out.println(markdown);
  }
}
```

`GiteeSimpleMarkdownUtils.toMarkdown(...)` converts page results into readable
Markdown. When pages are present, it adds a `> Page N` marker per page and removes
Gitee `ref/det` location tags. Image refs are converted into Markdown image links
using each page's `result_image`.

Pass a URL mapper when image URLs need to be rewritten before they are emitted:

```java
String markdown = GiteeSimpleMarkdownUtils.toMarkdown(output, url -> cdnUrl(url));
```

## Parse A Document

Use `parseDocument(...)` to submit a file to `/v1/async/documents/parse`.

```java
import java.io.File;

import nexus.io.gitee.GiteeClient;
import nexus.io.gitee.GiteeModels;
import nexus.io.gitee.GiteePromptConst;
import nexus.io.gitee.GiteeTaskResponse;
import nexus.io.tio.utils.environment.EnvUtils;

public class GiteeParseDocumentExample {
  public static void main(String[] args) {
    EnvUtils.load();

    File file = new File("data/sample.pdf");
    GiteeTaskResponse task = new GiteeClient().parseDocument(
        file,
        GiteeModels.DEEPSEEK_OCR,
        GiteePromptConst.pdf_to_markdown_prompt);

    System.out.println(task.getTask_id());
    System.out.println(task.getStatus());
  }
}
```

For byte-array uploads, the default overload uses `GiteeModels.DEEPSEEK_OCR` and
`GiteePromptConst.pdf_to_markdown_prompt`:

```java
GiteeTaskResponse task = new GiteeClient().parseDocument(data, "sample.pdf");
```

## Custom Document Parse Request

Use `GiteeDocumentParseRequest` for optional parsing parameters:

```java
import nexus.io.gitee.GiteeClient;
import nexus.io.gitee.GiteeDocumentParseRequest;
import nexus.io.gitee.GiteeModels;
import nexus.io.gitee.GiteeTaskResponse;

GiteeDocumentParseRequest request = new GiteeDocumentParseRequest();
request.setModel(GiteeModels.DEEPSEEK_OCR);
request.setPrompt("Convert this document to markdown.");
request.setInclude_image(true);
request.setInclude_image_base64(false);
request.setEnd_pages(5);
request.setOutput_format("markdown");

GiteeTaskResponse task = new GiteeClient().parseDocument(file, request);
```

| Field | Description |
| --- | --- |
| `model` | Document parsing model, for example `GiteeModels.DEEPSEEK_OCR`. |
| `prompt` | Prompt sent with the document parsing request. |
| `include_image` | Whether to include rendered page images. |
| `include_image_base64` | Whether to include image data as base64. |
| `end_pages` | Last page to parse. |
| `output_format` | Provider output format, for example `markdown`. |

## Task Response

`GiteeTaskResponse` represents both newly submitted async jobs and queried tasks:

| Field | Description |
| --- | --- |
| `task_id` | Async task ID. Use this with `getTask(taskId)`. |
| `status` | Provider task status. |
| `output` | Parsed document, transcription, or task output payload. |
| `created_at` | Task creation timestamp. |
| `started_at` | Task start timestamp. |
| `completed_at` | Task completion timestamp. |
| `urls` | Provider URLs associated with the task. |

Document output may contain page-based or segment-based results:

| Field | Description |
| --- | --- |
| `output.pages` | Page list. Each page can include `page_index`, `text_result`, `result_image`, `bboxes`, and `bbox_count`. |
| `output.segments` | Segment list. Some models, such as PaddleOCR-VL, return parsed content here. |
| `output.text_result` | Whole-document text result when returned by the provider. |
| `output.text` | Text result used by some audio tasks. |
| `output.content` | Generic content result used by some async tasks. |

## List Models

`GiteeClient.getModels()` calls the OpenAI-compatible models endpoint and returns a
`ChatModelResponse`.

```java
import java.util.List;

import nexus.io.chat.ChatModelEntity;
import nexus.io.chat.ChatModelResponse;
import nexus.io.gitee.GiteeClient;
import nexus.io.tio.utils.environment.EnvUtils;
import nexus.io.tio.utils.json.JsonUtils;

EnvUtils.load();

ChatModelResponse models = GiteeClient.getModels();
List<ChatModelEntity> data = models.getData();

for (ChatModelEntity model : data) {
  if ("deepseek-ai".equals(model.getOwned_by())) {
    System.out.println(JsonUtils.toJson(model));
  }
}
```

## Audio Transcription

`GiteeClient` also exposes audio transcription helpers:

```java
import java.io.File;

import nexus.io.gitee.GiteeClient;
import nexus.io.gitee.GiteeModels;
import nexus.io.gitee.GiteeTaskResponse;
import nexus.io.openai.whisper.WhisperTranscriptionsRequest;

WhisperTranscriptionsRequest request = new WhisperTranscriptionsRequest();
request.setModel(GiteeModels.AudioFly);
request.setResponse_format("json");
request.setLanguage("zh");

GiteeTaskResponse task = GiteeClient.asyncAudioTranscriptions(new File("audio/test.mp3"), request);
```

Use `GiteeClient.transcriptions(file, request)` for the synchronous
OpenAI-compatible transcription helper.

## Errors

`GiteeClient` throws `RuntimeException` for non-2xx responses. The message includes
the HTTP status code and response body:

```java
try {
  GiteeTaskResponse task = client.getTask(taskId);
} catch (RuntimeException e) {
  log.error("Gitee request failed", e);
}
```
