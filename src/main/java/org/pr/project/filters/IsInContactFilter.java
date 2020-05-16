package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.specifications.IsInContactSpecification;
import org.pr.project.strategies.NumericFilteringStrategy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("isInContact")
public class IsInContactFilter extends AbstractFilter<Double> {

	@JsonCreator
	public IsInContactFilter(
			@JsonProperty("strategy") final NumericFilteringStrategy possitiveNumberStrategy) {
		this.strategy = possitiveNumberStrategy;
	}

	@Override
	public List<Match> runFilter(final List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return (c.getContactsExchanged() != null
					&& c.getContactsExchanged() > 0);
		}).collect(Collectors.toList());
	}

	@Override
	public IsInContactSpecification getSpecification() {
		return new IsInContactSpecification(
				(NumericFilteringStrategy) this.strategy);
	}
}
