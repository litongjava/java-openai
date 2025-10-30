package com.litongjava.minimax;

import java.io.File;

import org.junit.Test;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.hex.HexUtils;
import com.litongjava.tio.utils.hutool.FileUtil;

public class MiniMaxHttpClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    // MINIMAX_GROUP_ID and MINIMAX_API_KEY
    // English_expressive_narrator and Chinese_Mandarin_Gentleman
    // String text = "At 0 degrees, cosine equals 1.";
    String text = "At 0 degress, consine equals 1.";
    
    MiniMaxTTSResponse speech = MiniMaxHttpClient.speech(text, MiniMaxVoice.English_magnetic_voiced_man);
    // System.out.println(speech);
    String audio = speech.getData().getAudio();
    byte[] decodeToBytes;
    decodeToBytes = HexUtils.decodeHex(audio);
    FileUtil.writeBytes(decodeToBytes, new File("how_are_you.mp3"));

  }
}
