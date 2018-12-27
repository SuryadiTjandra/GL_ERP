package ags.goldenlionerp.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class NullStringConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String attribute) {
		return attribute == null ? "" : attribute;
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return dbData;
	}

}
