package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.specifications.HasImageSpecification;
import org.pr.project.strategies.StringIsNotNullStrategy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("hasImage")
public class HasImageFilter extends AbstractFilter<String> {

	@JsonCreator
	public HasImageFilter(
			@JsonProperty("strategy") final StringIsNotNullStrategy imageExistsStrategy) {
		this.strategy = imageExistsStrategy;
	}

	@Override
	public List<Match> runFilter(final List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return (strategy.apply(c.getPhotoURI()));
		}).collect(Collectors.toList());
	}

	@Override
	public HasImageSpecification getSpecification() {
		return new HasImageSpecification(
				(StringIsNotNullStrategy) this.strategy);
	}

}
