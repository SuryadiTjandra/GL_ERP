package ags.goldenlionerp.application.system.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("G")
public class UserGroup extends UserBase {

	@Override
	public String getUserSecurityGroupId() {
		return "";
	}
	
}
