package ags.goldenlionerp.util;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;

public class  WebIdUtil {

	public static String toWebId(String entityId) {
		if (entityId == null)
			return null;
		
		return entityId.replaceAll(" ", "_").replaceAll("\t", "_t");
	}
	
	public static String toEntityId(String webId) {
		if (webId == null)
			return null;
		
		return webId.replaceAll("_t", "\t").replaceAll("_", " ");
	}
	
	public static String toWebId(Serializable webId) {
		if (webId instanceof TemporalAccessor)
			return toWebId((TemporalAccessor) webId);
		
		return BeanFinder.findBeans(BackendIdConverter.class)
						.values().stream()
						.filter(bic -> bic.supports(webId.getClass()))
						.findFirst()
						.map(bic -> bic.toRequestId(webId, webId.getClass()))
						.orElse(webId.toString());
	}
	
	public static String toWebId(TemporalAccessor temporalId) {
		String str = getWebIdDateFormatter().format(temporalId);
		return str;
	}
	
	public static DateTimeFormatter getWebIdDateFormatter() {
		return DateTimeFormatter.ofPattern("yyyyMMdd");
	}
}
