package ags.goldenlionerp.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class YNConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean isTrue) {
		return isTrue ? "Y" : "";
	}

	@Override
	public Boolean convertToEntityAttribute(String dbData) {
		return dbData.equals("Y");
	}

}
