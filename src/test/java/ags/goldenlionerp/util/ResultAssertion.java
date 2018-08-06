package ags.goldenlionerp.util;

import org.springframework.test.web.servlet.ResultActions;

@FunctionalInterface
public interface ResultAssertion {

	void assertResult(ResultActions action) throws Exception;
}
