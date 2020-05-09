package org.pr.project.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Match implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Religion {
		Christian, Islam, Agnostic, Athiest
	}

	private String displayName;
	private String jobTitle;
	private Integer age;
	private Religion religion;
	private String photoURI;
	private Double height;
	private Double compatibilityScore;

	public Match(String displayName, String jobTitle, Integer age, Religion religion, String photoURI, Double height,
			Double compatibilityScore) {
		super();
		this.displayName = displayName;
		this.jobTitle = jobTitle;
		this.age = age;
		this.religion = religion;
		this.photoURI = photoURI;
		this.height = height;
		this.compatibilityScore = compatibilityScore;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Religion getReligion() {
		return religion;
	}

	public void setReligion(Religion religion) {
		this.religion = religion;
	}

	public String getPhotoURI() {
		return photoURI;
	}

	public void setPhotoURI(String photoURI) {
		this.photoURI = photoURI;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getCompatibilityScore() {
		return compatibilityScore;
	}

	public void setCompatibilityScore(Double compatibilityScore) {
		this.compatibilityScore = compatibilityScore;
	}

}
