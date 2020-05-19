package org.pr.project.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.pr.project.strategies.StringIsNotNullStrategy;
import org.springframework.data.mongodb.core.query.Criteria;

public class HasImageSpecificationTests {

	@Test
	public void test_hasImageSpecification() {
		StringIsNotNullStrategy fileringStartegy = new StringIsNotNullStrategy();
		HasImageSpecification specification = new HasImageSpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
		assertThat(crit).isNotNull();
		List<String> expected = new ArrayList<String>();
		expected.add("photoURI");
		expected.add("$ne=null");
		assertThat(crit.getCriteriaObject().toString()).contains(expected);
	}
	
	@Test
	public void test_hasImageSpecWhenNoStrategy() {
		HasImageSpecification specification = new HasImageSpecification(null);
		Criteria crit = specification.getCriteria();
		assertThat(crit).isNotNull();
		assertThat(crit.getKey()).isNull();
	}
	
}
