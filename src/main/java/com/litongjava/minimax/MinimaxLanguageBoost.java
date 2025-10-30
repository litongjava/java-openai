package com.litongjava.minimax;

public enum MinimaxLanguageBoost {

  CHINESE("Chinese"),
  //
  CHINESE_YUE("Chinese,Yue"),
  //
  ENGLISH("English"),
  //
  ARABIC("Arabic"),
  //
  RUSSIAN("Russian"),
  //
  SPANISH("Spanish"),
  //
  FRENCH("French"),
  //
  PORTUGUESE("Portuguese"),
  //
  GERMAN("German"),
  //
  TURKISH("Turkish"),
  //
  DUTCH("Dutch"),
  //
  UKRAINIAN("Ukrainian"),
  //
  VIETNAMESE("Vietnamese"),
  //
  INDONESIAN("Indonesian"),
  //
  JAPANESE("Japanese"),
  //
  ITALIAN("Italian"),
  //
  KOREAN("Korean"),
  //
  THAI("Thai"),
  //
  POLISH("Polish"),
  //
  ROMANIAN("Romanian"),
  //
  GREEK("Greek"),
  //
  CZECH("Czech"),
  //
  FINNISH("Finnish"),
  //
  HINDI("Hindi"),
  //
  BULGARIAN("Bulgarian"),
  //
  DANISH("Danish"),
  //
  HEBREW("Hebrew"),
  //
  MALAY("Malay"),
  //
  PERSIAN("Persian"),
  //
  SLOVAK("Slovak"),
  //
  SWEDISH("Swedish"),
  //
  CROATIAN("Croatian"),
  //
  FILIPINO("Filipino"),
  //
  HUNGARIAN("Hungarian"),
  //
  NORWEGIAN("Norwegian"),
  //
  SLOVENIAN("Slovenian"),
  //
  CATALAN("Catalan"),
  //
  NYNORSK("Nynorsk"),
  //
  TAMIL("Tamil"),
  //
  AFRIKAANS("Afrikaans"),
  //
  AUTO("auto");

  private final String code;

  MinimaxLanguageBoost(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
