package org.pr.project.domain;

import java.io.Serializable;
import java.util.List;

import org.pr.project.filters.AbstractFilter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FiltersVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "appliedFilters")
	// @JsonDeserialize(contentUsing = NamedPolymorphicDeserializer.class)
	private List<AbstractFilter> appliedFilters;

	public List<AbstractFilter> getAppliedFilters() {
		return appliedFilters;
	}

	public void setAppliedFilters(final List<AbstractFilter> appliedFilters) {
		this.appliedFilters = appliedFilters;
	}

}
