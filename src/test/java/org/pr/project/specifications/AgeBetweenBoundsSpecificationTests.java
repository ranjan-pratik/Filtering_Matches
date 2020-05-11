package org.pr.project.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.pr.project.strategies.NumberBetweenBoundsStrategy;
import org.pr.project.strategies.NumericFilteringStrategy;
import org.springframework.data.mongodb.core.query.Criteria;

public class AgeBetweenBoundsSpecificationTests {

	@Test
	public void test_ageBetweenValidBounds() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new BigDecimal(10), new BigDecimal(20));
		AgeBetweenBoundsSpecification specification = new AgeBetweenBoundsSpecification(fileringStartegy);
		Criteria crit = specification.getSpecification();
		assertThat(crit).isNotNull();
		List<String> expected = new ArrayList<String>();
		expected.add("age");
		expected.add("gte");
		expected.add("10");
		expected.add("lte");
		expected.add("20");
		assertThat(crit.getCriteriaObject().toString()).contains(expected);
	}
	
	@Test
	public void test_ageBetweenInvertedValidBounds() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new BigDecimal(20), new BigDecimal(10));
		AgeBetweenBoundsSpecification specification = new AgeBetweenBoundsSpecification(fileringStartegy);
		Criteria crit = specification.getSpecification();
		assertThat(crit).isNotNull();
		List<String> expected = new ArrayList<String>();
		expected.add("age");
		expected.add("gte");
		expected.add("10");
		expected.add("lte");
		expected.add("20");
		assertThat(crit.getCriteriaObject().toString()).doesNotContain(expected);
	}
	
	@Test
	public void test_ageBelowUpperBound() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(null, new BigDecimal(20));
		AgeBetweenBoundsSpecification specification = new AgeBetweenBoundsSpecification(fileringStartegy);
		Criteria crit = specification.getSpecification();
		assertThat(crit).isNotNull();
		List<String> expected = new ArrayList<String>();
		expected.add("age");
		expected.add("lte");
		expected.add("20");
		List<String> unexpected = new ArrayList<String>();
		unexpected.add("gte");
		unexpected.add("10");
		assertThat(crit.getCriteriaObject().toString()).contains(expected);
		assertThat(crit.getCriteriaObject().toString()).doesNotContain(unexpected);
	}
	
	@Test
	public void test_ageAboveLowerBound() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new BigDecimal(10), null);
		AgeBetweenBoundsSpecification specification = new AgeBetweenBoundsSpecification(fileringStartegy);
		Criteria crit = specification.getSpecification();
		assertThat(crit).isNotNull();
		List<String> expected = new ArrayList<String>();
		expected.add("age");
		expected.add("gte");
		expected.add("10");
		List<String> unexpected = new ArrayList<String>();
		unexpected.add("lte");
		unexpected.add("20");
		assertThat(crit.getCriteriaObject().toString()).contains(expected);
		assertThat(crit.getCriteriaObject().toString()).doesNotContain(unexpected);
	}
}
