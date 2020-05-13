package org.pr.project.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.pr.project.strategies.NumberBetweenBoundsStrategy;
import org.pr.project.strategies.NumericFilteringStrategy;
import org.springframework.data.mongodb.core.query.Criteria;

public class AgeSpecificationTests {

	@Test
	public void test_ageBetweenValidBounds() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new Double(10), new Double(20));
		AgeSpecification specification = new AgeSpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
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
	public void test_ageBetweenInvertedBounds() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new Double(20), new Double(10));
		AgeSpecification specification = new AgeSpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
		assertThat(crit).isNotNull();
		List<String> unexpected = new ArrayList<String>();
		unexpected.add("age");
		unexpected.add("gte");
		unexpected.add("10");
		unexpected.add("lte");
		unexpected.add("20");
		assertThat(crit.getCriteriaObject().toString()).doesNotContain(unexpected);
	}
	
	@Test
	public void test_ageBelowUpperBound() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(null, new Double(20));
		AgeSpecification specification = new AgeSpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
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
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new Double(10), null);
		AgeSpecification specification = new AgeSpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
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
	
	@Test
	public void test_ageBetweenNullBounds() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(null, null);
		AgeSpecification specification = new AgeSpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
		assertThat(crit).isNotNull();
		List<String> unexpected = new ArrayList<String>();
		unexpected.add("age");
		unexpected.add("gte");
		unexpected.add("10");
		unexpected.add("lte");
		unexpected.add("20");
		assertThat(crit.getCriteriaObject().toString()).doesNotContain(unexpected);
	}
}
