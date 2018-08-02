package ags.goldenlionerp.util.refresh;

import ags.goldenlionerp.util.mockmvcperformer.MockMvcPerformer;

public class JpaRefresher {

	MockMvcPerformer performer;
	
	public JpaRefresher(MockMvcPerformer performer) {
		this.performer = performer;
	}
	
	public void refresh() throws Exception {
		performer.performPost("/refresh", null);
	}
}
