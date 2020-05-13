package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.specifications.HasImageSpecification;
import org.pr.project.strategies.IsExistStrategy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("hasImage")
public class HasImageFilter extends AbstractFilter<String> {

	private final IsExistStrategy imageExistsStrategy;

	@JsonCreator
	public HasImageFilter(final IsExistStrategy imageExistsStrategy) {
		this.imageExistsStrategy = imageExistsStrategy;
	}

	@Override
	public List<Match> runFilter(final List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return (c.getPhotoURI() != null && c.getPhotoURI().length() > 0);
		}).collect(Collectors.toList());
	}

	@Override
	public HasImageSpecification getSpecification() {
		return new HasImageSpecification(imageExistsStrategy);
	}

}
