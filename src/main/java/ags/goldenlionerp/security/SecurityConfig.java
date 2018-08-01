package ags.goldenlionerp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		super.configure(http);
	}
	
	@Override @Bean
	protected UserDetailsService userDetailsService() {
		return new AppUserDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		//TODO replace this
		return NoOpPasswordEncoder.getInstance();
	}
}
