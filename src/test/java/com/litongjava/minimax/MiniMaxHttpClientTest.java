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
