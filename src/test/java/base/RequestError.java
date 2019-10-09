package base;

import java.util.List;

public class RequestError {

	private String warn;
    private String error;
    private String status;
    private String message;
    private List<String> errors;

    public RequestError() {

    }

	public RequestError(String warn, String error, String status, String message, List<String> errors) {
		super();
		this.warn = warn;
		this.error = error;
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	public String getWarn() {
		return warn;
	}

	public void setWarn(String warn) {
		this.warn = warn;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
