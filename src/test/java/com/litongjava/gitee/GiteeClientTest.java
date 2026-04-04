package com.litongjava.gitee;

import org.junit.Test;

import nexus.io.gitee.GiteeClient;
import nexus.io.gitee.GiteeDocumentOutput;
import nexus.io.gitee.GiteeSimpleMarkdownUtils;
import nexus.io.gitee.GiteeTaskResponse;
import nexus.io.tio.utils.environment.EnvUtils;

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
}
