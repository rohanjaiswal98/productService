package productRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(ProductRepository repository) {

		return args -> {
			log.info("Preloading " + repository.save(new Product("Product 1", "testDescription", 99)));
			log.info("Preloading " + repository.save(new Product("Product 2", "testDescription", 100)));
		};
	}
}
