package uk.co.northrealm.postit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages={"uk.co.northrealm.postit"})
@EnableJpaRepositories(basePackages="uk.co.northrealm.postit.repositories")
@EnableTransactionManagement
@EntityScan(basePackages="uk.co.northrealm.postit.model")
public class PostitApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostitApplication.class, args);
	}

}
