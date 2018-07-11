package ags.goldenlionerp.util;

import java.time.format.DateTimeFormatter;

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
	
	public static DateTimeFormatter getWebIdDateFormatter() {
		return DateTimeFormatter.ofPattern("yyyyMMdd");
	}
}
