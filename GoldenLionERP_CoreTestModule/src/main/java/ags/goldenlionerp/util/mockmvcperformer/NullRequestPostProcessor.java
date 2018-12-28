package ags.goldenlionerp.util.mockmvcperformer;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

/**
 * An implementation of {@link RequestPostProcessor} that does nothing. A singleton.
 * @author user
 *
 */
class NullRequestPostProcessor implements RequestPostProcessor{

	private static NullRequestPostProcessor instance;
	
	static NullRequestPostProcessor instance() {
		if (instance == null)
			instance = new NullRequestPostProcessor();
	
		return instance;
	}
	
	@Override
	public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
		//does nothing
		return request;
	}

}
