package com.aiocdwacs.awacscloudauthserver.converter;

import java.sql.Timestamp;
import java.util.Optional;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<java.time.LocalDateTime, java.sql.Timestamp> {

	@Override
	public java.sql.Timestamp convertToDatabaseColumn(java.time.LocalDateTime attribute) {
		return Optional.ofNullable(attribute)
				.map(Timestamp::valueOf)
				.orElse(null);
	}

	@Override
	public java.time.LocalDateTime convertToEntityAttribute(java.sql.Timestamp dbData) {
		return Optional.ofNullable(dbData)
				.map(Timestamp::toLocalDateTime)
				//.map(LocalDateTime::truncatedTo(ChronoUnit.SECONDS))
				.orElse(null);
	}
}