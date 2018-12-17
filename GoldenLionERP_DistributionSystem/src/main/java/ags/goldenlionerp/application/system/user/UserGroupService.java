package ags.goldenlionerp.application.system.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupService extends UserBaseService<UserGroup> {

	@Autowired
	UserGroupService(UserGroupRepository repo) {
		super(repo);
	}

}
