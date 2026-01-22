package com.litongjava.openrouter;

public class OpenrouterPalmyraX5 {

  private String id;
  private String canonical_slug;
  private String hugging_face_id;
  private String name;
  private long created;
  private String description;
  private long context_length;
  private OpenrouterArchitecture architecture;
  private OpenrouterPricing pricing;
  private OpenrouterTopProvider top_provider;
  private Object per_request_limits;
  private String[] supported_parameters;
  private OpenrouterDefaultParameters default_parameters;
  private Object expiration_date;

  public OpenrouterPalmyraX5() {
  }

  public OpenrouterPalmyraX5(String id, String canonical_slug, String hugging_face_id, String name, long created,
      String description, long context_length, OpenrouterArchitecture architecture, OpenrouterPricing pricing,
      OpenrouterTopProvider top_provider, Object per_request_limits, String[] supported_parameters,
      OpenrouterDefaultParameters default_parameters, Object expiration_date) {
    this.id = id;
    this.canonical_slug = canonical_slug;
    this.hugging_face_id = hugging_face_id;
    this.name = name;
    this.created = created;
    this.description = description;
    this.context_length = context_length;
    this.architecture = architecture;
    this.pricing = pricing;
    this.top_provider = top_provider;
    this.per_request_limits = per_request_limits;
    this.supported_parameters = supported_parameters;
    this.default_parameters = default_parameters;
    this.expiration_date = expiration_date;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCanonical_slug() {
    return canonical_slug;
  }

  public void setCanonical_slug(String canonical_slug) {
    this.canonical_slug = canonical_slug;
  }

  public String getHugging_face_id() {
    return hugging_face_id;
  }

  public void setHugging_face_id(String hugging_face_id) {
    this.hugging_face_id = hugging_face_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getCreated() {
    return created;
  }

  public void setCreated(long created) {
    this.created = created;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getContext_length() {
    return context_length;
  }

  public void setContext_length(long context_length) {
    this.context_length = context_length;
  }

  public OpenrouterArchitecture getArchitecture() {
    return architecture;
  }

  public void setArchitecture(OpenrouterArchitecture architecture) {
    this.architecture = architecture;
  }

  public OpenrouterPricing getPricing() {
    return pricing;
  }

  public void setPricing(OpenrouterPricing pricing) {
    this.pricing = pricing;
  }

  public OpenrouterTopProvider getTop_provider() {
    return top_provider;
  }

  public void setTop_provider(OpenrouterTopProvider top_provider) {
    this.top_provider = top_provider;
  }

  public Object getPer_request_limits() {
    return per_request_limits;
  }

  public void setPer_request_limits(Object per_request_limits) {
    this.per_request_limits = per_request_limits;
  }

  public String[] getSupported_parameters() {
    return supported_parameters;
  }

  public void setSupported_parameters(String[] supported_parameters) {
    this.supported_parameters = supported_parameters;
  }

  public OpenrouterDefaultParameters getDefault_parameters() {
    return default_parameters;
  }

  public void setDefault_parameters(OpenrouterDefaultParameters default_parameters) {
    this.default_parameters = default_parameters;
  }

  public Object getExpiration_date() {
    return expiration_date;
  }

  public void setExpiration_date(Object expiration_date) {
    this.expiration_date = expiration_date;
  }
}
