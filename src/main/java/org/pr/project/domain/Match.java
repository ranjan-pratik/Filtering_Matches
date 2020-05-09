package org.pr.project.domain;

import java.io.Serializable;
import java.net.URL;

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
	
	public Match(String displayName, String jobTitle, Integer age, Religion religion, String photoURI) {
		super();
		this.displayName = displayName;
		this.jobTitle = jobTitle;
		this.age = age;
		this.religion = religion;
		this.photoURI = photoURI;
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
	
	
	
}
