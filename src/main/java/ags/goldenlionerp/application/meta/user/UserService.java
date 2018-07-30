package ags.goldenlionerp.application.meta.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	
	public void changePassword(String userId, String newPassword, String confirmPassword) {
		if (!newPassword.equals(confirmPassword))
			throw new PasswordConfirmFailException();
		
		User user = repo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find user with id " + userId));
		
		user.setUserSecurityCode(newPassword);
		
		repo.save(user);
	}
}
