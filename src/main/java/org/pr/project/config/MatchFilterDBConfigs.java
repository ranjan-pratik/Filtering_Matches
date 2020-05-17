package org.pr.project.config;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pr.project.domain.FilteredListVO;
import org.pr.project.domain.Match;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.data.repository.init.Jackson2ResourceReader;
import org.springframework.data.repository.init.ResourceReader;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableMongoRepositories(basePackages = {"org.pr.project.repo"})
public class MatchFilterDBConfigs {

	final ObjectMapper mapper = new ObjectMapper();

	@Bean
	public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
		mapper.addMixIn(GeoJsonPoint.class, GeoJsonPointMixin.class);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);

		final Jackson2RepositoryPopulatorFactoryBean factoryBean = new CustomJackson2RepositoryPopulatorFactoryBean();
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

	public class CustomJackson2RepositoryPopulatorFactoryBean
			extends
				Jackson2RepositoryPopulatorFactoryBean {
		@Override
		protected ResourceReader getResourceReader() {
			return new CustomJackson2ResourceReader();
		}
	}

	public class CustomJackson2ResourceReader implements ResourceReader {

		private final Logger logger = LogManager
				.getLogger(CustomJackson2ResourceReader.class);

		private final Jackson2ResourceReader resourceReader = new Jackson2ResourceReader();

		@Override
		public Object readFrom(Resource resource, ClassLoader classLoader)
				throws Exception {
			List<Match> result;
			try {
				InputStream stream = resource.getInputStream();
				FilteredListVO node = mapper.readValue(stream,
						FilteredListVO.class);

				result = node.getMatches();
			} catch (Exception e) {
				logger.warn(
						"Error occured while reading initial data to load in DB. Returning an empty list to load.",
						e);
				return Collections.EMPTY_LIST;
			}
			return result;
		}
	}

}
