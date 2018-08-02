package ags.goldenlionerp;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@SpringBootApplication
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@EnableJpaAuditing
public class GoldenLionErpApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldenLionErpApplication.class, args);
	}
	
	@Bean //the default auditoraware bean if spring security is not used
	public AuditorAware<String> auditorAware(){
		return () -> Optional.of("login not yet");
	}
}
