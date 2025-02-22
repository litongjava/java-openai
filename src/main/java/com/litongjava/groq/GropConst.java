package com.litongjava.groq;

import java.util.Arrays;
import java.util.List;

public interface GropConst {

  String API_BASE = "https://api.groq.com";

  String[] SUPPORT_EXTS = { "flac", "mp3", "mp4", "mpeg", "mpga", "m4a", "ogg", "wav", "webm" };

  List<String> SUPPORT_LIST = Arrays.asList(SUPPORT_EXTS);;
}
