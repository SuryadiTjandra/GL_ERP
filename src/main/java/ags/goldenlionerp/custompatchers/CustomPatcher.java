package ags.goldenlionerp.custompatchers;

import java.util.Map;

public abstract class CustomPatcher {

	public abstract <T> T patch(T objectToPatch, Map<String, Object> patchRequest);
}
