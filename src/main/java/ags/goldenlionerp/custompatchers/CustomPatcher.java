package ags.goldenlionerp.custompatchers;

import java.util.Map;

public interface CustomPatcher {

	public <T> T patch(T objectToPatch, Map<String, Object> patchRequest);
}
