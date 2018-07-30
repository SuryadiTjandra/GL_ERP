package ags.goldenlionerp.application.meta.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import ags.goldenlionerp.custompatchers.CustomPatcher;
import ags.goldenlionerp.exceptions.ResourceAlreadyExistsException;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository repo;
	@Autowired @Qualifier("halObjectMapper")
	private ObjectMapper mapper;
	@Autowired
	private CustomPatcher patcher;
	
	public User post(Map<String, Object> request) {
		String userId = request.get("userSecurityId").toString();
		
		if (repo.existsById(userId))
			throw new ResourceAlreadyExistsException("User", userId);
		
		String password = (String) request.getOrDefault("userSecurityCode", "");
		String passwordRe = (String) request.get("userSecurityCodeRe");
		if (!password.equals(passwordRe)) 
			throw new PasswordConfirmFailException();
		
		User user = mapper.convertValue(request, User.class);
		return repo.save(user);
	}
	
	public User patch(String userId, Map<String, Object> request) {
		User user = repo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find user with id " + userId));
		
		request.remove("userSecurityId");
		request.remove("userSecurityCode");
		request.remove("userSecurityCodeRe");
		
		user = patcher.patch(user, request);
		return repo.save(user);
	}
	
	public void changePassword(String userId, String newPassword, String confirmPassword) {
		if (!newPassword.equals(confirmPassword))
			throw new PasswordConfirmFailException();
		
		User user = repo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find user with id " + userId));
		
		user.setUserSecurityCode(newPassword);
		
		repo.save(user);
	}
}
