package ags.goldenlionerp.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class YNConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean isTrue) {
		if (isTrue == null) return "";
		return isTrue ? "Y" : "";
	}

	@Override
	public Boolean convertToEntityAttribute(String dbData) {
		if (!dbData.equals("Y") && !dbData.isEmpty())
			throw new IllegalArgumentException("This converter can only handle Y or empty values");
		return dbData.equals("Y");
	}

}
