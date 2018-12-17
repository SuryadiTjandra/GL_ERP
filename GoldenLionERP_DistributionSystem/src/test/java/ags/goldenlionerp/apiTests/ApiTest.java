package ags.goldenlionerp.apiTests;

public interface ApiTest {
	public abstract void getTestSingle() throws Exception;
	public abstract void getTestCollection() throws Exception;
	public abstract void createTestWithPost() throws Exception;
	public abstract void createTestWithPut() throws Exception;
	public abstract void deleteTest() throws Exception;
	public abstract void updateTestWithPatch() throws Exception;
	public abstract void updateTestWithPut() throws Exception;
}
