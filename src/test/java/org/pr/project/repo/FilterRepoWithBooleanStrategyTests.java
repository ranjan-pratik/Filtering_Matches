package org.pr.project.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.pr.project.domain.Match;
import org.pr.project.specifications.HasImageSpecification;
import org.pr.project.specifications.IsFavouriteSpecification;
import org.pr.project.specifications.IsInContactSpecification;
import org.pr.project.strategies.IsExistStrategy;
import org.pr.project.strategies.IsTrueStrategy;
import org.pr.project.strategies.NumberBetweenBoundsStrategy;
import org.pr.project.strategies.NumericFilteringStrategy;
import org.pr.project.strategies.PossitiveNumberStrategy;
import org.springframework.data.mongodb.core.query.Criteria;

public class FilterRepoWithBooleanStrategyTests extends FilterRepoWithNumericStrategyTests {}
