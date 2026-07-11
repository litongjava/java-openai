package com.litongjava.gitee;

import java.util.List;

import org.junit.Test;

import nexus.io.chat.ChatModelEntity;
import nexus.io.chat.ChatModelResponse;
import nexus.io.gitee.GiteeClient;
import nexus.io.gitee.GiteeDocumentOutput;
import nexus.io.gitee.GiteeSimpleMarkdownUtils;
import nexus.io.gitee.GiteeTaskResponse;
import nexus.io.tio.utils.environment.EnvUtils;
import nexus.io.tio.utils.json.JsonUtils;

public class GiteeClientTest {

  @Test
  public void testMarkdown() {
    EnvUtils.load();
    GiteeClient giteeClient = new GiteeClient();
    GiteeTaskResponse task = giteeClient.getTask("X4IC3C9Q88N65OCWEAI0M1YWFPUCHQZ9");
    GiteeDocumentOutput output = task.getOutput();
    String markdown = GiteeSimpleMarkdownUtils.toMarkdown(output, null);
    System.out.println(markdown);
  }
  
  @Test
  public void getModels() {
    EnvUtils.load();
    ChatModelResponse models = GiteeClient.getModels();
    List<ChatModelEntity> data = models.getData();
    for (ChatModelEntity chatModelEntity : data) {
      if("deepseek-ai".equals(chatModelEntity.getOwned_by())){
        String json = JsonUtils.toJson(chatModelEntity);
        System.out.println(json);
      }
    }
      
    
    
  }
}
