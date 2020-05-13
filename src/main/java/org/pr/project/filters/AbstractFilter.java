package org.pr.project.filters;

import java.util.List;

import org.pr.project.domain.Match;
import org.pr.project.specifications.AbstractSpecification;
import org.pr.project.strategies.FilteringStrategy;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = AgeFilter.class, name = "age"),
		@JsonSubTypes.Type(value = CompatibilityFilter.class, name = "compatibility"),
		@JsonSubTypes.Type(value = HasImageFilter.class, name = "hasImage"),
		@JsonSubTypes.Type(value = HeightFilter.class, name = "height"),
		@JsonSubTypes.Type(value = IsFavouriteFilter.class, name = "isFavourite"),
		@JsonSubTypes.Type(value = IsInContactFilter.class, name = "isInContact")})
public abstract class AbstractFilter<T> {

	protected String fieldName;

	protected FilteringStrategy<T> strategy;

	protected AbstractSpecification<T> specification;

	public abstract List<Match> runFilter(List<Match> candidates);

	public AbstractSpecification<T> getSpecification() {
		return this.specification;
	}

}
