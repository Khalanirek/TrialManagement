package base;

import org.springframework.test.web.servlet.MvcResult;

public class MvcJsonResult<T> {
	private final MvcResult mvcResult;
    private final T json;
    private final RequestError reuqestError;

	public MvcJsonResult(MvcResult mvcResult, T json, RequestError requestError) {
		this.mvcResult = mvcResult;
		this.json = json;
		this.reuqestError = requestError;
	}
	public MvcResult getMvcResult() {
		return this.mvcResult;
	}
	public T getJson() {
		return this.json;
	}
	public RequestError getRequestError() {
		return this.reuqestError;
	}
}
