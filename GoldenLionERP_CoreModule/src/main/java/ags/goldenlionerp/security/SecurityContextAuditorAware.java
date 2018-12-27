package ags.goldenlionerp.security;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Primary
@Component
public class SecurityContextAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.ofNullable(SecurityContextHolder.getContext())
						.map(SecurityContext::getAuthentication)
						.map(Authentication::getName);
	}

}
