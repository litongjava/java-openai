package com.litongjava.libreoffice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.hutool.FileUtil;

public class LibreofficeClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    try {
      byte[] bytes = Files.readAllBytes(Paths.get("F:\\document\\subject-docs\\04_Chemistry\\CHEM_161\\ch1\\Chapter 1 Lectture Slides Set 1.pptx"));
      ResponseVo responseVo = LibreOfficeClient.convertToPdf(bytes, "Chapter 1 Lectture Slides Set 1.pptx");
      if (responseVo.isOk()) {
        FileUtil.writeBytes(responseVo.getBodyBytes(), new File("Chapter 1 Lectture Slides Set 1.pdf"));
      } else {
        System.err.print(responseVo.getBodyString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
