package ags.goldenlionerp;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter{

	@Autowired
	EntityManager em;
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		Class<?>[] classes = em.getMetamodel().getEntities().stream()
									.map(enType -> enType.getJavaType())
									.toArray(Class<?>[]::new);
		config.exposeIdsFor(classes);
	}
	
	

}
