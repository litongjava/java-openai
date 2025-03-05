package com.litongjava.apify;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkedinEducation {
  private String companyId;
  private String companyUrn;
  private String companyLink1;
  private String logo;
  private String title;
  private String subtitle;
  private String caption;
  private Boolean breakdown;
  private List<LinkedinSubComponent> subComponents;
}
