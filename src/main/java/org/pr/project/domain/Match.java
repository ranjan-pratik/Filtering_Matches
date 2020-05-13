package org.pr.project.domain;

import java.io.Serializable;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Document
public class Match implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Religion {
		Christian, Islam, Agnostic, Atheist, Buddhist, Jewish
	}

	@JsonIgnore
	@Id
	private String id;

	@JsonProperty("display_name")
	@Indexed(name = "display_name_index", direction = IndexDirection.ASCENDING)
	private String displayName;

	@JsonProperty("job_title")
	private String jobTitle;

	@JsonProperty("age")
	private Integer age;

	@JsonProperty("religion")
	private Religion religion;

	@JsonProperty("main_photo")
	private String photoURI;

	@JsonProperty("height_in_cm")
	private Double height;

	@JsonProperty("compatibility_score")
	private Double compatibilityScore;

	@JsonProperty("contacts_exchanged")
	private Integer contactsExchanged;

	@JsonProperty("favourite")
	private Boolean isFavourite;

	@JsonProperty("city")
	private City city;

	public Match() {
	}

	public Match(final String displayName, final String jobTitle,
			final Integer age, final Religion religion, final String photoURI,
			final Double height, final Double compatibilityScore,
			final Integer contactsExchanged, final Boolean isFavourite,
			final City city) {
		super();
		this.displayName = displayName;
		this.jobTitle = jobTitle;
		this.age = age;
		this.religion = religion;
		this.photoURI = photoURI;
		this.height = height;
		this.compatibilityScore = compatibilityScore;
		this.contactsExchanged = contactsExchanged;
		this.isFavourite = isFavourite;
		this.city = city;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(final String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(final Integer age) {
		this.age = age;
	}

	public Religion getReligion() {
		return religion;
	}

	public void setReligion(final Religion religion) {
		this.religion = religion;
	}

	public String getPhotoURI() {
		return photoURI;
	}

	public void setPhotoURI(final String photoURI) {
		this.photoURI = photoURI;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(final Double height) {
		this.height = height;
	}

	public Double getCompatibilityScore() {
		return compatibilityScore;
	}

	public void setCompatibilityScore(final Double compatibilityScore) {
		this.compatibilityScore = compatibilityScore;
	}

	public Integer getContactsExchanged() {
		return contactsExchanged;
	}

	public Boolean getIsFavourite() {
		return isFavourite;
	}

	public void setIsFavourite(final Boolean isFavourite) {
		this.isFavourite = isFavourite;
	}

	public void setContactsExchanged(final Integer contactsExchanged) {
		this.contactsExchanged = contactsExchanged;
	}

	public City getCity() {
		return city;
	}

	public void setCity(final City city) {
		this.city = city;
	}

}
