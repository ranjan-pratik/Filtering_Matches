package org.pr.project.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.pr.project.strategies.NumberBetweenBoundsStrategy;
import org.pr.project.strategies.NumericFilteringStrategy;
import org.springframework.data.mongodb.core.query.Criteria;

public class CompatibilitySpecificationTests {

	@Test
	public void test_compatibilityBetweenValidBounds() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new Double(70.22), new Double(90.55));
		CompatibilitySpecification specification = new CompatibilitySpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
		assertThat(crit).isNotNull();
		List<String> expected = new ArrayList<String>();
		expected.add("compatibility");
		expected.add("gte");
		expected.add("70.22");
		expected.add("lte");
		expected.add("90.55");
		assertThat(crit.getCriteriaObject().toString()).contains(expected);
	}
	
	@Test
	public void test_compatibilityBetweenInvertedBounds() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new Double(90.55), new Double(70.22));
		CompatibilitySpecification specification = new CompatibilitySpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
		assertThat(crit).isNotNull();
		List<String> unexpected = new ArrayList<String>();
		unexpected.add("compatibility");
		unexpected.add("gte");
		unexpected.add("70.22");
		unexpected.add("lte");
		unexpected.add("90.55");
		assertThat(crit.getCriteriaObject().toString()).doesNotContain(unexpected);
	}
	
	@Test
	public void test_compatibilityBelowUpperBound() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(null, new Double(90.55));
		CompatibilitySpecification specification = new CompatibilitySpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
		assertThat(crit).isNotNull();
		List<String> expected = new ArrayList<String>();
		expected.add("compatibility");
		expected.add("lte");
		expected.add("90.55");
		List<String> unexpected = new ArrayList<String>();
		unexpected.add("gte");
		unexpected.add("70.22");
		assertThat(crit.getCriteriaObject().toString()).contains(expected);
		assertThat(crit.getCriteriaObject().toString()).doesNotContain(unexpected);
	}
	
	@Test
	public void test_compatibilityAboveLowerBound() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new Double(70.22), null);
		CompatibilitySpecification specification = new CompatibilitySpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
		assertThat(crit).isNotNull();
		List<String> expected = new ArrayList<String>();
		expected.add("compatibility");
		expected.add("gte");
		expected.add("70.22");
		List<String> unexpected = new ArrayList<String>();
		unexpected.add("lte");
		unexpected.add("90.55");
		assertThat(crit.getCriteriaObject().toString()).contains(expected);
		assertThat(crit.getCriteriaObject().toString()).doesNotContain(unexpected);
	}
	
	@Test
	public void test_compatibilityBetweenNullBounds() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(null, null);
		CompatibilitySpecification specification = new CompatibilitySpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
		assertThat(crit).isNotNull();
		List<String> unexpected = new ArrayList<String>();
		unexpected.add("compatibility");
		unexpected.add("gte");
		unexpected.add("70.22");
		unexpected.add("lte");
		unexpected.add("90.55");
		assertThat(crit.getCriteriaObject().toString()).doesNotContain(unexpected);
	}
	
	@Test
	public void test_compatibilitySpecWhenNoStrategy() {
		CompatibilitySpecification specification = new CompatibilitySpecification(null);
		Criteria crit = specification.getCriteria();
		assertThat(crit).isNotNull();
		assertThat(crit.getKey()).isNull();
	}
}
