package ags.goldenlionerp.converters;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDate attribute) {
		if (attribute == null) return null;
		
		return Timestamp.valueOf(attribute.atStartOfDay());
	}

	@Override
	public LocalDate convertToEntityAttribute(Timestamp dbData) {
		if (dbData == null) return null;
		
		return dbData.toLocalDateTime().toLocalDate();
	}

}
