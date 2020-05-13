package org.pr.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableMongoRepositories(basePackages = {"org.pr.project.repo"})
public class MatchFilterDBConfigs {

	public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.addMixIn(GeoJsonPoint.class, GeoJsonPointMixin.class);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);

		final Jackson2RepositoryPopulatorFactoryBean factoryBean = new Jackson2RepositoryPopulatorFactoryBean();
		factoryBean.setResources(
				new Resource[]{new ClassPathResource("matches.json")});
		factoryBean.setMapper(mapper);

		return factoryBean;
	}

	static abstract class GeoJsonPointMixin {
		GeoJsonPointMixin(@JsonProperty("lon") final double x,
				@JsonProperty("lat") final double y) {
		}
	}
}
