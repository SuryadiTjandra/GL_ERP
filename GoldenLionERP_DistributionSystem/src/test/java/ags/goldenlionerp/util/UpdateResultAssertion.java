package ags.goldenlionerp.util;

import org.springframework.test.web.servlet.ResultActions;

@FunctionalInterface
public interface UpdateResultAssertion {

	void assertResult(ResultActions action, String beforeUpdateJson) throws Exception;
}
