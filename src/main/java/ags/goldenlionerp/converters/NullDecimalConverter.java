package ags.goldenlionerp.converters;

import java.math.BigDecimal;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class NullDecimalConverter implements AttributeConverter<BigDecimal, BigDecimal>{

	@Override
	public BigDecimal convertToDatabaseColumn(BigDecimal attribute) {
		return attribute == null ? BigDecimal.ZERO : attribute;
	}

	@Override
	public BigDecimal convertToEntityAttribute(BigDecimal dbData) {
		return dbData;
	}

}
