package com.litongjava.deepseek;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.litongjava.chat.ChatImageFile;
import com.litongjava.chat.UniChatMessage;
import com.litongjava.openai.chat.OpenAiChatRequest;
import com.litongjava.openai.chat.OpenAiChatResponse;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.tio.utils.base64.Base64Utils;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.ContentTypeUtils;
import com.litongjava.tio.utils.hutool.FileUtil;
import com.litongjava.tio.utils.hutool.FilenameUtils;

/**
 * 
 * @author Tong Li
 *
 */
public class RustDeepseekOcrClient {

  public static final String BASE_URL = EnvUtils.getStr(DeepSeekConst.RUST_DEEPSEEK_OCR_API_URL);

  public static OpenAiChatResponse ocr(String prompt, File file) {
    String suffix = FilenameUtils.getSuffix(file.getName());
    String mimeType = ContentTypeUtils.getContentType(suffix);
    byte[] imageBytes = FileUtil.readBytes(file);
    return ocr(prompt, imageBytes, mimeType);
  }

  public static OpenAiChatResponse ocr(String prompt, byte[] imageBytes, String mimeType) {
    OpenAiChatRequest openAiChatRequest = new OpenAiChatRequest();
    openAiChatRequest.setModel("deepseek-ocr");
    List<UniChatMessage> messages = new ArrayList<>();

    String encodeImage = Base64Utils.encodeImage(imageBytes, mimeType);

    List<ChatImageFile> files = new ArrayList<>();
    files.add(new ChatImageFile(encodeImage));

    UniChatMessage userMessage = UniChatMessage.buildUser(prompt);
    userMessage.setFiles(files);
    
    messages.add(userMessage);

    openAiChatRequest.setChatMessages(messages);
    OpenAiChatResponse chatResponse = OpenAiClient.generate(BASE_URL, openAiChatRequest);
    return chatResponse;
  }
}
