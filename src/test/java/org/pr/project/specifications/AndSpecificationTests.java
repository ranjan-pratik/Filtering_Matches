package org.pr.project.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.pr.project.strategies.NumberBetweenBoundsStrategy;
import org.pr.project.strategies.NumericFilteringStrategy;
import org.springframework.data.mongodb.core.query.Criteria;

public class AndSpecificationTests {

	@Test
	public void test_and_of_2_ageBetweenValidBounds() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new Double(10), new Double(20));
		NumericFilteringStrategy fileringStartegy2 = new NumberBetweenBoundsStrategy(new Double(50), new Double(90));
		AgeSpecification specification = new AgeSpecification(fileringStartegy);
		AgeSpecification specification2 = new AgeSpecification(fileringStartegy2);
		AndSpecification andSpec = new AndSpecification(specification, specification2);
		Criteria crit = andSpec.getCriteria();
		assertThat(crit).isNotNull();
		Criteria expectedAnd =  new Criteria().andOperator(Criteria.where("age").lte(20d).gte(10d), Criteria.where("age").lte(90d).gte(50d));
		assertThat(crit.getCriteriaObject().toString()).isEqualTo(expectedAnd.getCriteriaObject().toString());
		
	}
	
	@Test
	public void test_andOfValidAndInvalid_ageBetweenBounds() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new Double(10), new Double(20));
		NumericFilteringStrategy fileringStartegy2 = new NumberBetweenBoundsStrategy(new Double(150), new Double(90));
		AgeSpecification specification = new AgeSpecification(fileringStartegy);
		AgeSpecification specification2 = new AgeSpecification(fileringStartegy2);
		AndSpecification andSpec = new AndSpecification(specification, specification2);
		Criteria crit = andSpec.getCriteria();
		assertThat(crit).isNotNull();
		List<String> expected = new ArrayList<String>();
		expected.add("age");
		expected.add("lte");
		expected.add("20");
		expected.add("gte");
		expected.add("10");
		List<String> unexpected = new ArrayList<String>();
		unexpected.add("150");
		unexpected.add("90");
		assertThat(crit.getCriteriaObject().toString()).contains(expected);
		assertThat(crit.getCriteriaObject().toString()).doesNotContain(unexpected);
	}
	
}
