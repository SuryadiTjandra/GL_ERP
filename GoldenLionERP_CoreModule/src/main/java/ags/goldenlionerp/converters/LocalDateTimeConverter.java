package ags.goldenlionerp.converters;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
		if (attribute == null) return null;
		
		return Timestamp.valueOf(attribute);
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
		if (dbData == null) return null;
		
		return dbData.toLocalDateTime();
	}

}
