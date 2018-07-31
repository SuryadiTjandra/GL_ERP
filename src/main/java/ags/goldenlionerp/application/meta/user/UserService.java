package ags.goldenlionerp.application.meta.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends UserBaseService<User>{

	@Autowired
	UserService(UserRepository repo) {
		super(repo);
	}

}
