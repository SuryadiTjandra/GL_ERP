package ags.goldenlionerp.application.ar.invoice;

import javax.persistence.AttributeConverter;

public class VoidedAttributeConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean voided) {
		return voided ? "V" : "";
	}

	@Override
	public Boolean convertToEntityAttribute(String voidedString) {
		return voidedString.equals("V");
	}

}
