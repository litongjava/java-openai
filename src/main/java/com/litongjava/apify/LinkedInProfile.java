package com.litongjava.apify;

import java.util.List;

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

  public LinkedInProfile() {
    super();
    // TODO Auto-generated constructor stub
  }

  public LinkedInProfile(String firstName, String lastName, String fullName, String publicIdentifier, String headline,
      Integer connections, Integer followers, Boolean openConnection, String urn, String addressCountryOnly,
      String addressWithCountry, String addressWithoutCountry, String profilePic,
      List<LinkedinProfilePicDimension> profilePicAllDimensions, List<Object> updates, String about,
      List<LinkedinExperience> experiences, List<LinkedinEducation> educations,
      List<LinkedinLicenseAndCertificate> licenseAndCertificates, List<Object> honorsAndAwards, List<Object> languages,
      List<Object> volunteerAndAwards, List<Object> verifications, List<Object> promos, List<Object> highlights,
      List<Object> projects, List<Object> publications, List<Object> patents, List<Object> courses,
      List<Object> testScores, List<Object> organizations, List<Object> volunteerCauses, List<LinkedinSkill> skills,
      String profilePicHighQuality, String linkedinUrl, String email, String companyName, String companyIndustry,
      String companyWebsite, String companyLinkedin, Integer companyFoundedIn, String companySize) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.fullName = fullName;
    this.publicIdentifier = publicIdentifier;
    this.headline = headline;
    this.connections = connections;
    this.followers = followers;
    this.openConnection = openConnection;
    this.urn = urn;
    this.addressCountryOnly = addressCountryOnly;
    this.addressWithCountry = addressWithCountry;
    this.addressWithoutCountry = addressWithoutCountry;
    this.profilePic = profilePic;
    this.profilePicAllDimensions = profilePicAllDimensions;
    this.updates = updates;
    this.about = about;
    this.experiences = experiences;
    this.educations = educations;
    this.licenseAndCertificates = licenseAndCertificates;
    this.honorsAndAwards = honorsAndAwards;
    this.languages = languages;
    this.volunteerAndAwards = volunteerAndAwards;
    this.verifications = verifications;
    this.promos = promos;
    this.highlights = highlights;
    this.projects = projects;
    this.publications = publications;
    this.patents = patents;
    this.courses = courses;
    this.testScores = testScores;
    this.organizations = organizations;
    this.volunteerCauses = volunteerCauses;
    this.skills = skills;
    this.profilePicHighQuality = profilePicHighQuality;
    this.linkedinUrl = linkedinUrl;
    this.email = email;
    this.companyName = companyName;
    this.companyIndustry = companyIndustry;
    this.companyWebsite = companyWebsite;
    this.companyLinkedin = companyLinkedin;
    this.companyFoundedIn = companyFoundedIn;
    this.companySize = companySize;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPublicIdentifier() {
    return publicIdentifier;
  }

  public void setPublicIdentifier(String publicIdentifier) {
    this.publicIdentifier = publicIdentifier;
  }

  public String getHeadline() {
    return headline;
  }

  public void setHeadline(String headline) {
    this.headline = headline;
  }

  public Integer getConnections() {
    return connections;
  }

  public void setConnections(Integer connections) {
    this.connections = connections;
  }

  public Integer getFollowers() {
    return followers;
  }

  public void setFollowers(Integer followers) {
    this.followers = followers;
  }

  public Boolean getOpenConnection() {
    return openConnection;
  }

  public void setOpenConnection(Boolean openConnection) {
    this.openConnection = openConnection;
  }

  public String getUrn() {
    return urn;
  }

  public void setUrn(String urn) {
    this.urn = urn;
  }

  public String getAddressCountryOnly() {
    return addressCountryOnly;
  }

  public void setAddressCountryOnly(String addressCountryOnly) {
    this.addressCountryOnly = addressCountryOnly;
  }

  public String getAddressWithCountry() {
    return addressWithCountry;
  }

  public void setAddressWithCountry(String addressWithCountry) {
    this.addressWithCountry = addressWithCountry;
  }

  public String getAddressWithoutCountry() {
    return addressWithoutCountry;
  }

  public void setAddressWithoutCountry(String addressWithoutCountry) {
    this.addressWithoutCountry = addressWithoutCountry;
  }

  public String getProfilePic() {
    return profilePic;
  }

  public void setProfilePic(String profilePic) {
    this.profilePic = profilePic;
  }

  public List<LinkedinProfilePicDimension> getProfilePicAllDimensions() {
    return profilePicAllDimensions;
  }

  public void setProfilePicAllDimensions(List<LinkedinProfilePicDimension> profilePicAllDimensions) {
    this.profilePicAllDimensions = profilePicAllDimensions;
  }

  public List<Object> getUpdates() {
    return updates;
  }

  public void setUpdates(List<Object> updates) {
    this.updates = updates;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  public List<LinkedinExperience> getExperiences() {
    return experiences;
  }

  public void setExperiences(List<LinkedinExperience> experiences) {
    this.experiences = experiences;
  }

  public List<LinkedinEducation> getEducations() {
    return educations;
  }

  public void setEducations(List<LinkedinEducation> educations) {
    this.educations = educations;
  }

  public List<LinkedinLicenseAndCertificate> getLicenseAndCertificates() {
    return licenseAndCertificates;
  }

  public void setLicenseAndCertificates(List<LinkedinLicenseAndCertificate> licenseAndCertificates) {
    this.licenseAndCertificates = licenseAndCertificates;
  }

  public List<Object> getHonorsAndAwards() {
    return honorsAndAwards;
  }

  public void setHonorsAndAwards(List<Object> honorsAndAwards) {
    this.honorsAndAwards = honorsAndAwards;
  }

  public List<Object> getLanguages() {
    return languages;
  }

  public void setLanguages(List<Object> languages) {
    this.languages = languages;
  }

  public List<Object> getVolunteerAndAwards() {
    return volunteerAndAwards;
  }

  public void setVolunteerAndAwards(List<Object> volunteerAndAwards) {
    this.volunteerAndAwards = volunteerAndAwards;
  }

  public List<Object> getVerifications() {
    return verifications;
  }

  public void setVerifications(List<Object> verifications) {
    this.verifications = verifications;
  }

  public List<Object> getPromos() {
    return promos;
  }

  public void setPromos(List<Object> promos) {
    this.promos = promos;
  }

  public List<Object> getHighlights() {
    return highlights;
  }

  public void setHighlights(List<Object> highlights) {
    this.highlights = highlights;
  }

  public List<Object> getProjects() {
    return projects;
  }

  public void setProjects(List<Object> projects) {
    this.projects = projects;
  }

  public List<Object> getPublications() {
    return publications;
  }

  public void setPublications(List<Object> publications) {
    this.publications = publications;
  }

  public List<Object> getPatents() {
    return patents;
  }

  public void setPatents(List<Object> patents) {
    this.patents = patents;
  }

  public List<Object> getCourses() {
    return courses;
  }

  public void setCourses(List<Object> courses) {
    this.courses = courses;
  }

  public List<Object> getTestScores() {
    return testScores;
  }

  public void setTestScores(List<Object> testScores) {
    this.testScores = testScores;
  }

  public List<Object> getOrganizations() {
    return organizations;
  }

  public void setOrganizations(List<Object> organizations) {
    this.organizations = organizations;
  }

  public List<Object> getVolunteerCauses() {
    return volunteerCauses;
  }

  public void setVolunteerCauses(List<Object> volunteerCauses) {
    this.volunteerCauses = volunteerCauses;
  }

  public List<LinkedinSkill> getSkills() {
    return skills;
  }

  public void setSkills(List<LinkedinSkill> skills) {
    this.skills = skills;
  }

  public String getProfilePicHighQuality() {
    return profilePicHighQuality;
  }

  public void setProfilePicHighQuality(String profilePicHighQuality) {
    this.profilePicHighQuality = profilePicHighQuality;
  }

  public String getLinkedinUrl() {
    return linkedinUrl;
  }

  public void setLinkedinUrl(String linkedinUrl) {
    this.linkedinUrl = linkedinUrl;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCompanyIndustry() {
    return companyIndustry;
  }

  public void setCompanyIndustry(String companyIndustry) {
    this.companyIndustry = companyIndustry;
  }

  public String getCompanyWebsite() {
    return companyWebsite;
  }

  public void setCompanyWebsite(String companyWebsite) {
    this.companyWebsite = companyWebsite;
  }

  public String getCompanyLinkedin() {
    return companyLinkedin;
  }

  public void setCompanyLinkedin(String companyLinkedin) {
    this.companyLinkedin = companyLinkedin;
  }

  public Integer getCompanyFoundedIn() {
    return companyFoundedIn;
  }

  public void setCompanyFoundedIn(Integer companyFoundedIn) {
    this.companyFoundedIn = companyFoundedIn;
  }

  public String getCompanySize() {
    return companySize;
  }

  public void setCompanySize(String companySize) {
    this.companySize = companySize;
  }
}
