# DeepSeek Official API and OCR

[Online java-openai guide (tio-boot.cn)](https://tio-boot.cn/zh/54_java-openai/19.html) · [Online java-openai guide (tio-boot.com)](https://tio-boot.com/zh/54_java-openai/19.html)


Load configuration before discovering the models available to an API account:

```java
import nexus.io.chat.ChatModelResponse;
import nexus.io.deepseek.DeepSeekClient;
import nexus.io.tio.utils.environment.EnvUtils;

EnvUtils.load();
ChatModelResponse response = DeepSeekClient.getModels();
response.getData().forEach(model -> System.out.println(model.getId()));
```

The helper reads `DEEPSEEK_API_KEY` through `EnvUtils.get` and calls the official
`https://api.deepseek.com/v1/models` endpoint. An overload accepts an explicit key.

Do not infer API availability from an open-source model name. The
[official model-list API](https://api-docs.deepseek.com/api/list-models/)
describes discovery of available hosted models. The
[official DeepSeek-OCR repository](https://github.com/deepseek-ai/DeepSeek-OCR)
documents local vLLM/Transformers inference, including a PDF inference script.
An official API key alone does not provision that self-hosted OCR service.

On 2026-09-26, the account used for the document benchmark returned
`deepseek-flash` and `deepseek-v4-pro`, with no model named `deepseek-ocr`. This does not evaluate OCR capabilities of other vision models. Therefore no official
DeepSeek-OCR HTTP wrapper or fabricated OCR benchmark result was added.
Gitee's hosted `GiteeModels.DEEPSEEK_OCR` is a separate service and uses the Gitee
key. `RustDeepseekOcrClient` targets a separately deployed service configured by
`RUST_DEEPSEEK_OCR_API_URL`; it is not the official hosted DeepSeek API.
