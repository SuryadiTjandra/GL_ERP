package ags.goldenlionerp.application.setups.nextnumberconstant;

import java.util.Arrays;

public enum NextNumberResetMethod {

	NO_RESET("NR"),
	YEARLY("YR"),
	MONTHLY("MO"),
	YEARLY_BY_COMPANY("CY"),
	MONTHLY_BY_COMPANY("CM");
	
	private String code;
	
	public String getCode() {
		return code;
	}
	
	public static NextNumberResetMethod fromCode(String code) {
		return Arrays.stream(values())
						.filter(v -> v.getCode().equals(code))
						.findFirst()
						.orElseThrow(() -> new IllegalArgumentException("No NextNumberResetMethod fits the code " + code))
						;
	}
	
	NextNumberResetMethod(String code) {
		this.code = code;
	}
}
