package org.pr.project.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.pr.project.strategies.IsTrueOrFalseStrategy;
import org.springframework.data.mongodb.core.query.Criteria;

public class IsFavouriteStrategyTests {

	@Test
	public void test_IsFavouriteSpecification_withTrue() {
		IsTrueOrFalseStrategy fileringStartegy = new IsTrueOrFalseStrategy(
				true);
		IsFavouriteSpecification specification = new IsFavouriteSpecification(
				fileringStartegy);
		Criteria crit = specification.getCriteria();
		assertThat(crit).isNotNull();
		List<String> expected = new ArrayList<String>();
		expected.add("isFavourite");
		expected.add("$exists=true");
		expected.add("$ne=false");
		System.out.println(crit.getCriteriaObject().toString());
		assertThat(crit.getCriteriaObject().toString()).contains(expected);
	}

	@Test
	public void test_IsFavouriteSpecification_withFalse() {
		IsTrueOrFalseStrategy fileringStartegy = new IsTrueOrFalseStrategy(
				false);
		IsFavouriteSpecification specification = new IsFavouriteSpecification(
				fileringStartegy);
		Criteria crit = specification.getCriteria();
		assertThat(crit).isNotNull();
		List<String> expected = new ArrayList<String>();
		expected.add("isFavourite");
		expected.add("$exists=true");
		expected.add("$ne=true");
		System.out.println(crit.getCriteriaObject().toString());
		assertThat(crit.getCriteriaObject().toString()).contains(expected);
	}

}
