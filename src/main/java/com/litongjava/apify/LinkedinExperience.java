package com.litongjava.apify;

import java.util.List;

public class LinkedinExperience {
  private String companyId;
  private String companyUrn;
  private String companyLink1;
  private String logo;
  private String title;
  private String subtitle;
  private String caption;
  private String metadata;
  private Boolean breakdown;
  private List<LinkedinSubComponent> subComponents;

  public LinkedinExperience() {
    super();
    // TODO Auto-generated constructor stub
  }

  public LinkedinExperience(String companyId, String companyUrn, String companyLink1, String logo, String title,
      String subtitle, String caption, String metadata, Boolean breakdown, List<LinkedinSubComponent> subComponents) {
    super();
    this.companyId = companyId;
    this.companyUrn = companyUrn;
    this.companyLink1 = companyLink1;
    this.logo = logo;
    this.title = title;
    this.subtitle = subtitle;
    this.caption = caption;
    this.metadata = metadata;
    this.breakdown = breakdown;
    this.subComponents = subComponents;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getCompanyUrn() {
    return companyUrn;
  }

  public void setCompanyUrn(String companyUrn) {
    this.companyUrn = companyUrn;
  }

  public String getCompanyLink1() {
    return companyLink1;
  }

  public void setCompanyLink1(String companyLink1) {
    this.companyLink1 = companyLink1;
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public String getMetadata() {
    return metadata;
  }

  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  public Boolean getBreakdown() {
    return breakdown;
  }

  public void setBreakdown(Boolean breakdown) {
    this.breakdown = breakdown;
  }

  public List<LinkedinSubComponent> getSubComponents() {
    return subComponents;
  }

  public void setSubComponents(List<LinkedinSubComponent> subComponents) {
    this.subComponents = subComponents;
  }

}
