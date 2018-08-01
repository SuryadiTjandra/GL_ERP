package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.Serializable;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import ags.goldenlionerp.util.WebIdUtil;

/**
 * A new base class for API tests. Contain the boilerplates for the standard test as specified by {@link ApiTest} interface.
 * Subclasses only need to implement the result checking parts.
 * @author user
 *
 * @param <ID>
 */
public abstract class ApiTestBaseNew<ID extends Serializable> extends ApiTestBase<ID>{
	
	public ResultActions performGet(String url) throws Exception {
		return mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
	}
	
	public ResultActions performPost(String url, Object content) throws Exception {
		return mockMvc.perform(post(url)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(content)));
	}
	
	public ResultActions performPatch(String url, Object content) throws Exception {
		return mockMvc.perform(patch(url)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(content)));
	}
	
	public ResultActions performPut(String url, Object content) throws Exception {
		return mockMvc.perform(put(url)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(content)));
	}
	
	public ResultActions performDelete(String url) throws Exception {
		return mockMvc.perform(delete(url));
	}
	
	public void refreshData() {
		em.flush();
		em.clear();
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		String existingId = WebIdUtil.toWebId(existingId());
		ResultActions action = performGet(baseUrl() + existingId)
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		assertGetSingleResult(action);
	}
	
	public abstract void assertGetSingleResult(ResultActions action) throws Exception;

	@Override @Test
	public void getTestCollection() throws Exception {
		ResultActions action = performGet(baseUrl())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
			
		assertGetCollectionResult(action);	
	}
	
	public abstract void assertGetCollectionResult(ResultActions action) throws Exception;

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		String newId = WebIdUtil.toWebId(newId());
		assumeNotExists(baseUrl()+newId);
		
		performPost(baseUrl(), requestObject)
			.andExpect(status().isCreated());
		
		refreshData();
		
		ResultActions action = performGet(baseUrl() + newId);
		assertCreateWithPostResult(action);
		
		String getRes = action.andReturn().getResponse().getContentAsString();
		assertCreationInfo(getRes);
		
	}

	public abstract void assertCreateWithPostResult(ResultActions action) throws Exception;

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		String existingId = WebIdUtil.toWebId(existingId());
		assumeExists(baseUrl() + existingId);
		
		String beforePatch = performGet(baseUrl() + existingId)
								.andReturn().getResponse().getContentAsString();
		
		performPatch(baseUrl() + existingId, requestObject)
			.andExpect(status().is2xxSuccessful());
	
		refreshData();
		
		ResultActions action = performGet(baseUrl() + existingId);
		assertUpdateWithPatchResult(action, beforePatch);
		
		String getResult = action.andReturn().getResponse().getContentAsString();
		assertUpdateInfo(getResult, beforePatch);

	}
	
	public abstract void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception;

	@Override @Test @Rollback
	public void updateTestWithPut() throws Exception {
		String existingId = WebIdUtil.toWebId(existingId());
		assumeExists(baseUrl()+existingId);
		
		performPut(baseUrl() + existingId, requestObject)
			.andExpect(status().isMethodNotAllowed());
		
	}
	
	@Override @Test @Rollback
	public void createTestWithPut() throws Exception {
		String newId = WebIdUtil.toWebId(newId());
		assumeNotExists(baseUrl()+newId);
		
		performPut(baseUrl() + newId, requestObject)
			.andExpect(status().isMethodNotAllowed());
		
	}

	@Override @Test @Rollback
	public void deleteTest() throws Exception {
		String existingId = WebIdUtil.toWebId(existingId());
		assumeExists(baseUrl()+existingId);
		
		performDelete(baseUrl() + existingId)
			.andExpect(status().isNoContent());
		
		performGet(baseUrl() + existingId)
			.andExpect(status().isNotFound());
			
	}

}
