package com.litongjava.openrouter;

public class OpenrouterArchitecture {

  private String modality;
  private String[] input_modalities;
  private String[] output_modalities;
  private String tokenizer;
  private Object instruct_type;

  public OpenrouterArchitecture() {
  }

  public OpenrouterArchitecture(String modality, String[] input_modalities, String[] output_modalities,
      String tokenizer, Object instruct_type) {
    this.modality = modality;
    this.input_modalities = input_modalities;
    this.output_modalities = output_modalities;
    this.tokenizer = tokenizer;
    this.instruct_type = instruct_type;
  }

  public String getModality() {
    return modality;
  }

  public void setModality(String modality) {
    this.modality = modality;
  }

  public String[] getInput_modalities() {
    return input_modalities;
  }

  public void setInput_modalities(String[] input_modalities) {
    this.input_modalities = input_modalities;
  }

  public String[] getOutput_modalities() {
    return output_modalities;
  }

  public void setOutput_modalities(String[] output_modalities) {
    this.output_modalities = output_modalities;
  }

  public String getTokenizer() {
    return tokenizer;
  }

  public void setTokenizer(String tokenizer) {
    this.tokenizer = tokenizer;
  }

  public Object getInstruct_type() {
    return instruct_type;
  }

  public void setInstruct_type(Object instruct_type) {
    this.instruct_type = instruct_type;
  }
}
