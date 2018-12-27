package ags.goldenlionerp.application.system.user;

import java.util.Optional;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@DiscriminatorValue("U")
@Access(AccessType.FIELD)
public class User extends UserBase {
	
	@JoinColumn(name="SCUSG", updatable=false, insertable=false)
	@ManyToOne @NotFound(action=NotFoundAction.IGNORE)
	private UserGroup userSecurityGroup;

	public int getUserSecurityLevel() {
		return getUserSecurityGroup()
				.map(UserBase::getUserSecurityLevel)
				.orElse(userSecurityLevel);
	}

	public String getMenuToExecuteBOS() {
		return getUserSecurityGroup()
				.map(UserBase::getMenuToExecuteBOS)
				.orElse(menuToExecuteBOS);
	}

	public String getMenuToExecutePOS() {
		return getUserSecurityGroup()
				.map(UserBase::getMenuToExecutePOS)
				.orElse(menuToExecutePOS);
	}

	public String getMenuToExecuteHOS() {
		return getUserSecurityGroup()
				.map(UserBase::getMenuToExecuteHOS)
				.orElse(menuToExecuteHOS);
	}

	public Optional<UserGroup> getUserSecurityGroup() {
		if (userSecurityGroupId == null || userSecurityGroupId.isEmpty())
			return Optional.empty();
		return Optional.ofNullable(userSecurityGroup);
	}
	
	void setUserSecurityGroup(UserGroup userSecurityGroup) {
		this.userSecurityGroup = userSecurityGroup;
	}
	
	

}
