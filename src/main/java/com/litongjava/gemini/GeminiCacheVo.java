package com.litongjava.gemini; // Adjust package as needed

/**
 * 表示一个缓存条目的元数据
 */
public class GeminiCacheVo {
  private String name; // Format: cachedContents/{cache_id}
  private String model;
  private String displayName;
  private String createTime;
  private String updateTime;
  private String expireTime;
  private GeminiCacheUsageMetadataVo usageMetadata;

  public GeminiCacheVo() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiCacheVo(String name, String model, String displayName, String createTime, String updateTime,
      String expireTime, GeminiCacheUsageMetadataVo usageMetadata) {
    super();
    this.name = name;
    this.model = model;
    this.displayName = displayName;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.expireTime = expireTime;
    this.usageMetadata = usageMetadata;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public String getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(String expireTime) {
    this.expireTime = expireTime;
  }

  public GeminiCacheUsageMetadataVo getUsageMetadata() {
    return usageMetadata;
  }

  public void setUsageMetadata(GeminiCacheUsageMetadataVo usageMetadata) {
    this.usageMetadata = usageMetadata;
  }

}