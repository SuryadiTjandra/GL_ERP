package ags.goldenlionerp.entities;

import java.time.LocalDateTime;

public interface DatabaseAuditable {

	String getInputUserId();

	//@JsonProperty(access=Access.READ_ONLY)
	LocalDateTime getInputDateTime();

	String getLastUpdateUserId();

	//@JsonProperty(access=Access.READ_ONLY)
	LocalDateTime getLastUpdateDateTime();

	String getComputerId();

}