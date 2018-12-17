package ags.goldenlionerp.util.mockmvcperformer;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MockMvcPerformer {
	private MockMvc mockMvc;
	private ObjectMapper mapper;
	private UserDetails user;
	private boolean enableCsrf = true;
	
	public MockMvcPerformer(MockMvc mockMvc, ObjectMapper mapper, UserDetails defaultUser) {
		this(mockMvc, mapper);
		this.user = defaultUser;
	}
	
	public MockMvcPerformer(MockMvc mockMvc, ObjectMapper mapper) {
		this.mockMvc = mockMvc;
		this.mapper = mapper;
	}
	
	public MockMvcPerformer withUser(UserDetails user) {
		return new MockMvcPerformer(this.mockMvc, this.mapper, user);
	}
	
	public MockMvcPerformer withAnonymous() {
		return new MockMvcPerformer(this.mockMvc, this.mapper);
	}
	
	public MockMvcPerformer withCsrfDisabled() {
		MockMvcPerformer copy = new MockMvcPerformer(this.mockMvc, this.mapper, user);
		copy.enableCsrf = false;
		return copy;
	}
	
	
	
	private RequestPostProcessor getUser() {
		return user != null ? SecurityMockMvcRequestPostProcessors.user(user) : anonymous();
	}
	
	private RequestPostProcessor getCsrf() {
		return enableCsrf ? csrf() : NullRequestPostProcessor.instance();
	}
	
	
	
	public ResultActions performGet(String url) throws Exception {
		return mockMvc.perform(get(url)
				.with(getUser())
				.accept(MediaType.APPLICATION_JSON));
	}
	
	public ResultActions performPost(String url, Object content) throws Exception {
		return mockMvc.perform(post(url)
					.with(getUser())
					.with(getCsrf())
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(content)));
	}
	
	public ResultActions performPatch(String url, Object content) throws Exception {
		return mockMvc.perform(patch(url)
					.with(getUser())
					.with(getCsrf())
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(content)));
	}
	
	public ResultActions performPut(String url, Object content) throws Exception {
		return mockMvc.perform(put(url)
					.with(getUser())
					.with(getCsrf())
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(content)));
	}
	
	public ResultActions performDelete(String url) throws Exception {
		return mockMvc.perform(delete(url)
						.with(getUser())
						.with(getCsrf()));
	}
	
}
