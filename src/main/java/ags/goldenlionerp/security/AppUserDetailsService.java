package ags.goldenlionerp.security;

import java.util.Collections;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ags.goldenlionerp.application.system.user.User;
import ags.goldenlionerp.application.system.user.UserRepository;

public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findById(username)
					.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
		
		return org.springframework.security.core.userdetails.User.builder()
					.username(username)
					.password(user.getUserSecurityCode())
					.authorities(Collections.emptyList())
					.passwordEncoder(Function.identity())
					.build();
	}

}
