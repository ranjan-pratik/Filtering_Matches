package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.specifications.IsFavouriteSpecification;
import org.pr.project.strategies.BooleanFilteringStrategy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("isFavourite")
public class IsFavouriteFilter extends AbstractFilter<Boolean> {

	private final BooleanFilteringStrategy isTrueSrategy;

	@JsonCreator
	public IsFavouriteFilter(final BooleanFilteringStrategy isTrueSrategy) {
		this.isTrueSrategy = isTrueSrategy;
	}

	@Override
	public List<Match> runFilter(final List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return (c.getIsFavourite() != null && c.getIsFavourite());
		}).collect(Collectors.toList());
	}

	@Override
	public IsFavouriteSpecification getSpecification() {
		return new IsFavouriteSpecification(isTrueSrategy);
	}

}
