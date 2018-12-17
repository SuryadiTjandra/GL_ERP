package ags.goldenlionerp.application.system.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import ags.goldenlionerp.custompatchers.CustomPatcher;
import ags.goldenlionerp.exceptions.ResourceAlreadyExistsException;

@Transactional
public class UserBaseService<T extends UserBase> {

	private CrudRepository<T, String> repo;
	@Autowired @Qualifier("halObjectMapper")
	protected ObjectMapper mapper;
	@Autowired
	protected CustomPatcher patcher;
	
	UserBaseService(CrudRepository<T, String> repo){
		this.repo = repo;
	}
	
	public T post(Map<String, Object> request) {
		String userId = request.get("userSecurityId").toString();
		
		if (repo.existsById(userId))
			throw new ResourceAlreadyExistsException("User", userId);
		
		String password = (String) request.getOrDefault("userSecurityCode", "");
		String passwordRe = (String) request.get("userSecurityCodeRe");
		if (!password.equals(passwordRe)) 
			throw new PasswordConfirmFailException();
		
		@SuppressWarnings("unchecked")
		T user = (T) mapper.convertValue(request, UserBase.class);
		return repo.save(user);
	}
	
	public T patch(String userId, Map<String, Object> request) {
		T user = repo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find user with id " + userId));
		
		request.remove("userSecurityId");
		request.remove("userSecurityCode");
		request.remove("userSecurityCodeRe");
		request.remove("userSecurityType");
		
		user = patcher.patch(user, request);
		return repo.save(user);
	}
	
	public void changePassword(String userId, String newPassword, String confirmPassword) {
		if (!newPassword.equals(confirmPassword))
			throw new PasswordConfirmFailException();
		
		T user = repo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find user with id " + userId));
		
		user.setUserSecurityCode(newPassword);
		
		repo.save(user);
	}
}
