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
public class LinkedInProfile {
  private String firstName;
  private String lastName;
  private String fullName;
  private String publicIdentifier;
  private String headline;
  private Integer connections;
  private Integer followers;
  private Boolean openConnection;
  private String urn;
  private String addressCountryOnly;
  private String addressWithCountry;
  private String addressWithoutCountry;
  private String profilePic;
  private List<LinkedinProfilePicDimension> profilePicAllDimensions;
  private List<Object> updates;
  private String about;
  private List<LinkedinExperience> experiences;
  private List<LinkedinEducation> educations;
  private List<LinkedinLicenseAndCertificate> licenseAndCertificates;
  private List<Object> honorsAndAwards;
  private List<Object> languages;
  private List<Object> volunteerAndAwards;
  private List<Object> verifications;
  private List<Object> promos;
  private List<Object> highlights;
  private List<Object> projects;
  private List<Object> publications;
  private List<Object> patents;
  private List<Object> courses;
  private List<Object> testScores;
  private List<Object> organizations;
  private List<Object> volunteerCauses;
  private List<LinkedinSkill> skills;
  private String profilePicHighQuality;
  private String linkedinUrl;
  private String email;
  private String companyName;
  private String companyIndustry;
  private String companyWebsite;
  private String companyLinkedin;
  private Integer companyFoundedIn;
  private String companySize;
}
